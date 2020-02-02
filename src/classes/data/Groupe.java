package classes.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Groupe implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true, nullable = false)
    private String nom;

    @OneToMany(mappedBy = "groupe", fetch = FetchType.LAZY)
    private List<Etudiant> etudiants;

    public Groupe(Integer id, String nom, List<Etudiant> etudiants) {
        super();
        this.id = id;
        this.nom = nom;
        this.etudiants = etudiants;
    }

    public Groupe() {
        super();
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
