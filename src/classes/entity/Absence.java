package classes.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Absence implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private boolean justifie;

    @OneToOne
    @JoinColumn(name = "appel_id")
    private Appel appel;

    @OneToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    public Absence(Integer id, Boolean justifie, Appel appel, Etudiant etudiant) {
        super();
        this.id = id;
        this.justifie = justifie;
        this.appel = appel;
        this.etudiant = etudiant;
    }

    public Absence() { super(); }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) { this.id = id; }

    public boolean isJustifie() {
        return justifie;
    }

    public void setJustifie(boolean justifie) {
        this.justifie = justifie;
    }

    public Appel getAppel() { return appel; }

    public void setAppel(Appel appel) { this.appel = appel; }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
}
