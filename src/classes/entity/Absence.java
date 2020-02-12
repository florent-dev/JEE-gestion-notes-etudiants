package classes.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Absence implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private boolean justifie;

    @Column(nullable = false)
    private Date date;

    @OneToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @OneToOne
    @JoinColumn(name = "module_id")
    private Module module;

    @OneToOne
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;

    public Absence(Integer id, Boolean justifie, Date date, Etudiant etudiant, Groupe groupe, Module module) {
        super();
        this.id = id;
        this.justifie = justifie;
        this.date = date;
        this.etudiant = etudiant;
        this.module = module;
        this.groupe = groupe;
    }

    public Absence() { super(); }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isJustifie() {
        return justifie;
    }

    public void setJustifie(boolean justifie) {
        this.justifie = justifie;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }
}
