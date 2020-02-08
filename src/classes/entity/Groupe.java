package classes.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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

    @ManyToMany(mappedBy = "groupes")
    private List<Module> modules = new ArrayList<>();

    public Groupe(Integer id, String nom, List<Etudiant> etudiants) {
        super();
        this.id = id;
        this.nom = nom;
        this.etudiants = etudiants;
        //this.modules = ModuleDAO.getByGroupeId(this.id);
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

    public void addModule(Module module) {
        modules.add(module);
    }

    public List<Module> getModules() {
        return modules;
    }
}
