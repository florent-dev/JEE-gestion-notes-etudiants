package classes.repository;


import classes.entity.Groupe;
import classes.utils.GestionFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class GroupeDAO {

    public static Groupe create(String nom) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        Groupe groupe = new Groupe();
        groupe.setNom(nom);
        em.persist(groupe);

        em.getTransaction().commit();
        em.close();

        return groupe;
    }


    public static int removeAll() {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        // RemoveAll
        int deletedCount = em.createQuery("DELETE FROM Groupe").executeUpdate();

        em.getTransaction().commit();
        em.close();

        return deletedCount;
    }

    public static Groupe find(int id) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        Groupe groupe = em.find(Groupe.class, id);
        em.close();

        return groupe;
    }

    public static List<Groupe> getAll() {
        EntityManager em = GestionFactory.factory.createEntityManager();

        // Recherche
        Query q = em.createQuery("SELECT g FROM Groupe g");

        @SuppressWarnings("unchecked")
        List<Groupe> listGroupe = q.getResultList();

        return listGroupe;
    }

    public static Groupe update(Groupe groupe) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        em.merge(groupe);

        em.getTransaction().commit();
        em.close();

        return groupe;
    }
}
