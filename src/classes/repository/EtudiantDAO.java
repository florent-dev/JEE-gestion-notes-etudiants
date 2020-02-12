package classes.repository;

import classes.entity.Etudiant;
import classes.utils.GestionFactory;
import classes.entity.Groupe;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class EtudiantDAO {

    public static Etudiant find(int id) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        Etudiant etu = em.find(Etudiant.class, id);
        em.close();

        return etu;
    }

    public static Etudiant create(String prenom, String nom, Groupe groupe) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        Etudiant etudiant = new Etudiant();
        etudiant.setPrenom(prenom);
        etudiant.setNom(nom);
        etudiant.setGroupe(groupe);
        em.persist(etudiant);

        em.getTransaction().commit();
        em.close();

        return etudiant;
    }

    public static Etudiant update(Etudiant etudiant) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        em.merge(etudiant);

        em.getTransaction().commit();
        em.close();

        return etudiant;
    }

    public static Etudiant addAbsences(int id, int absencesAAjouter) {
        EntityManager em = GestionFactory.factory.createEntityManager();

        // Find
        Etudiant etudiant = em.find(Etudiant.class, id);
        em.getTransaction().begin();

        int absencesInitiales = etudiant.getNbAbsences();
        etudiant.setNbAbsences(absencesInitiales + absencesAAjouter);

        em.getTransaction().commit();
        em.close();

        return etudiant;
    }

    public static void addAbsences(Etudiant etudiant, int absences) {

        // Ajouter ou enlever une absence à l'étudiant
        int nbAbsences = etudiant.getNbAbsences();
        if ((nbAbsences + absences) >= 0 ) {
            nbAbsences = nbAbsences + absences;
        }
        etudiant.setNbAbsences(nbAbsences);

        // Mettre l'étudiant à jour
        EtudiantDAO.update(etudiant);
    }


    public static void remove(Etudiant etudiant) {
        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        // Retrouver l'entité persistante et ses liens avec d'autres entités en vue de la suppression
        etudiant = em.find(Etudiant.class, etudiant.getId());
        em.remove(etudiant);

        em.getTransaction().commit();
        em.close();

        // if EclipseLink cache enable -->
        // GestionFactory.factory.getCache().evictAll();
    }

    public static void remove(int id) {
        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        em.createQuery("DELETE FROM Etudiant AS e WHERE e.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        em.getTransaction().commit();
        em.close();
    }

    public static int removeAll() {
        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        // RemoveAll
        int deletedCount = em.createQuery("DELETE FROM Etudiant").executeUpdate();

        em.getTransaction().commit();
        em.close();

        return deletedCount;
    }

    // Retourne l'ensemble des etudiants
    public static List<Etudiant> getAll() {
        EntityManager em = GestionFactory.factory.createEntityManager();

        // Recherche
        Query q = em.createQuery("SELECT e FROM Etudiant e");

        @SuppressWarnings("unchecked")
        List<Etudiant> listEtudiant = q.getResultList();

        return listEtudiant;
    }

    // Retourne les etudiants d'un groupe
    public static List<Etudiant> getAllByGroupe(Groupe groupe) {
        EntityManager em = GestionFactory.factory.createEntityManager();

        // Recherche
        Query q = em.createQuery("SELECT e FROM Etudiant e WHERE e.groupe = :groupe").setParameter("groupe", groupe);

        @SuppressWarnings("unchecked")
        List<Etudiant> listEtudiant = q.getResultList();

        return listEtudiant;
    }

    // Retourne l'ensemble des etudiants d'un groupe donné
    public static List<Etudiant> getAllByAbsences() {
        EntityManager em = GestionFactory.factory.createEntityManager();

        // Recherche
        Query q = em.createQuery("SELECT e FROM Etudiant e WHERE e.nbAbsences > 0");

        @SuppressWarnings("unchecked")
        List<Etudiant> listEtudiant = q.getResultList();

        return listEtudiant;
    }

}