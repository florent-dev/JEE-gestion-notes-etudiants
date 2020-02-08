package classes.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Enseignant implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String nom;

    public Enseignant(Integer id, String prenom, String nom) {
        super();
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
    }

    public Enseignant() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
