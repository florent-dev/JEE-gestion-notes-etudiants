package classes.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Evaluation implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date date;

    @ManyToMany
    private Module module;

    @ManyToMany
    private Groupe groupe;

    public Evaluation(Integer id, String nom, String description, Date date, Groupe groupe, Module module) {
        super();
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.groupe = groupe;
        this.module = module;
    }

    public Evaluation() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Groupe getGroupe() {
        return this.groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public String getNom() { return nom; }

    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public Module getModule() { return module; }

    public void setModule(Module module) { this.module = module; }
}
