package classes.entity;

import classes.repository.EvaluationDAO;
import classes.repository.NoteDAO;
import classes.utils.CalculUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Module implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique=true, nullable=false)
    private String nom;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "MODULE_GROUP",
            joinColumns = @JoinColumn(name = "module_id"),
            inverseJoinColumns = @JoinColumn(name = "groupe_id")
    )
    private List<Groupe> groupes = new ArrayList<>();

    public Module() { super(); }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<Groupe> groupeList) {
        this.groupes = groupeList;
    }

    public void addGroupe(Groupe groupe) {
        groupes.add(groupe);
    }

    public void removeGroupe(Groupe groupe) {
        // On retire le groupe par son nom.
        for (Groupe g: getGroupes()) {
            if (g.getNom().equals(groupe.getNom())) {
                this.getGroupes().remove(g);
                return;
            }
        }
    }

    /**
     * Calculer la moyenne de l'étudiant pour le module.
     * @param etudiant -
     * @return moyenneEtudiantModule
     */
    public Double getMoyenneEtudiant(Etudiant etudiant) {
        // On récupère les evaluations par : module & groupe de l'étudiant.
        List<Evaluation> evaluationList = EvaluationDAO.getByGroupeAndModule(etudiant.getGroupe(), this);
        List<Note> notesEtudiant = new ArrayList<Note>();

        // On va retrouver les notes avec l'évaluation et l'étudiant.
        for (Evaluation evaluation: evaluationList) {
            Note note = NoteDAO.findByEtudiantAndEvaluation(etudiant, evaluation);

            // Si la note n'est pas à null.
            if (note != null) {
                notesEtudiant.add(note);
            }
        }

        // On a toutes les notes de l'étudiant pour ce module, on calcule la moyenne.
        double somme = 0;

        for (Note n: notesEtudiant) {
            somme += n.getNote();
        }

        return (notesEtudiant.size() > 0) ? (somme / notesEtudiant.size()) : null;
    }

    /**
     * Calculer la moyenne de module.
     * @param groupe -
     * @return moyenneGroupeModule
     */
    public Double getMoyenne(Groupe groupe) {
        // On récupère les evaluations par : module & groupe de l'étudiant
        List<Evaluation> evaluationList = EvaluationDAO.getByGroupeAndModule(groupe, this);
        List<Double> moyenneEvaluationList = new ArrayList<Double>();

        // Pour chaque évaluation du module.
        // On récupère les notes et on établit une moyenne de l'évaluation.
        // Afin de calculer la moyenne des évaluations du module.
        for (Evaluation evaluation: evaluationList) {
            List<Double> notesEvaluationList = new ArrayList<Double>();
            for (Note note: NoteDAO.getByEvaluation(evaluation)) {
                // Si la note n'est pas à null.
                if (note != null) {
                    notesEvaluationList.add((double)note.getNote());
                }
            }
            // Si on a au moins une note sur l'évaluation, on ajoute la moyenne évaluation à notre liste de moy. éval.
            if (notesEvaluationList.size() > 0) {
                moyenneEvaluationList.add(CalculUtils.calculMoyenne(notesEvaluationList));
            }
        }

        // On renvoie la moyenne des moyennes évaluation.
        return CalculUtils.calculMoyenne(moyenneEvaluationList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Module)) return false;
        return id != null && id.equals(((Module) o).id);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
