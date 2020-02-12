package classes.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Appel implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private Date date;

    @OneToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    @OneToOne
    @JoinColumn(name = "module_id")
    private Module module;

    @OneToOne
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;

    public Appel(Integer id, Date date, Enseignant enseignant, Groupe groupe, Module module) {
        super();
        this.id = id;
        this.date = date;
        this.enseignant = enseignant;
        this.module = module;
        this.groupe = groupe;
    }

    public Appel() { super(); }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
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
