package classes.entity;

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
        for (Groupe g: getGroupes()) {
            if (g.getNom().equals(groupe.getNom())) {
                this.getGroupes().remove(g);
                return;
            }
        }
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
