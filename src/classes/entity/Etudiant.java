package classes.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Etudiant implements Serializable {

	@Id
	@GeneratedValue
	private Integer id;

	@Column(nullable = false)
	private String prenom;

	@Column(nullable = false)
	private String nom;

	private Integer nbAbsences = 0;

	@ManyToOne
	private Groupe groupe;
	
	public Etudiant(Integer id, String prenom, String nom, Integer nbAbsences, Groupe groupe) {
		super();
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.nbAbsences = nbAbsences;
		this.groupe = groupe;
	}

	public Etudiant() {
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

	public void setNbAbsences(int nbAbsences) {
		this.nbAbsences = nbAbsences;
	}

	public int getNbAbsences() {
		return this.nbAbsences;
	}

	public Groupe getGroupe() {
		return this.groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
}
