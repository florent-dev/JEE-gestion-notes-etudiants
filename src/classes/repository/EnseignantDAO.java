package classes.repository;

import classes.entity.Enseignant;
import classes.utils.GestionFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class EnseignantDAO {

    public static Enseignant find(int id) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();
        Enseignant enseignant = em.find(Enseignant.class, id);
        em.close();

        return enseignant;
    }

    public static Enseignant create(String prenom, String nom) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // create new Enseignant
        Enseignant enseignant = new Enseignant();
        enseignant.setPrenom(prenom);
        enseignant.setNom(nom);
        em.persist(enseignant);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return enseignant;
    }

    public static Enseignant update(Enseignant enseignant) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // Attacher une entité persistante (Enseignant) à l’EntityManager courant  pour réaliser la modification
        em.merge(enseignant);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return enseignant;
    }

    public static void remove(Enseignant enseignant) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        enseignant = em.find(Enseignant.class, enseignant.getId());
        em.remove(enseignant);

        em.getTransaction().commit();
        em.close();
    }

    public static void remove(int id) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        em.createQuery("DELETE FROM Enseignant AS e WHERE e.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        em.getTransaction().commit();
        em.close();
    }

    public static int removeAll() {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        // RemoveAll
        int deletedCount = em.createQuery("DELETE FROM Enseignant").executeUpdate();

        em.getTransaction().commit();
        em.close();

        return deletedCount;
    }

    // Retourne l'ensemble des Enseignants
    public static List<Enseignant> getAll() {
        EntityManager em = GestionFactory.factory.createEntityManager();

        Query q = em.createQuery("SELECT e FROM Enseignant e");

        @SuppressWarnings("unchecked")
        List<Enseignant> listEnseignant = q.getResultList();

        return listEnseignant;
    }

}