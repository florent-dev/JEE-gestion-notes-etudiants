package classes.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Note implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private float note;

    @Column(nullable = false)
    private Etudiant etudiant;

    @Column(nullable = false)
    private Evaluation evaluation;

    public Note(Integer id, float note, Etudiant etudiant, Evaluation evaluation) {
        super();
        this.id = id;
        this.note = note;
        this.etudiant = etudiant;
        this.evaluation = evaluation;
    }

    public Note() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
}
