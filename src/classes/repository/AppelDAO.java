package classes.repository;

import classes.entity.Appel;
import classes.entity.Enseignant;
import classes.entity.Groupe;
import classes.entity.Module;
import classes.utils.GestionFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class AppelDAO {

    public static Appel find(int id) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();
        Appel absence = em.find(Appel.class, id);
        em.close();

        return absence;
    }

    public static Appel create(Date date, Enseignant enseignant, Groupe groupe, Module module) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        // create new Appel
        Appel appel = new Appel();
        appel.setDate(date);
        appel.setEnseignant(enseignant);
        appel.setGroupe(groupe);
        appel.setModule(module);
        em.persist(appel);

        em.getTransaction().commit();
        em.close();

        return appel;
    }

    public static Appel update(Appel appel) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        em.merge(appel);

        em.getTransaction().commit();
        em.close();

        return appel;
    }

    public static void remove(Appel appel) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        appel = em.find(Appel.class, appel.getId());
        em.remove(appel);

        em.getTransaction().commit();
        em.close();
    }

    public static void remove(int id) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        em.createQuery("DELETE FROM Appel AS e WHERE e.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        em.getTransaction().commit();
        em.close();
    }

    // Retourne l'ensemble des Absences
    public static List<Appel> getAll() {
        EntityManager em = GestionFactory.factory.createEntityManager();

        Query q = em.createQuery("SELECT e FROM Appel e");

        @SuppressWarnings("unchecked")
        List<Appel> listAppels = q.getResultList();

        return listAppels;
    }

    // Retourne l'ensemble des Absences d'un groupe donné
    public static List<Appel> getAllByGroupe(Groupe groupe) {
        EntityManager em = GestionFactory.factory.createEntityManager();

        Query q = em.createQuery("SELECT e FROM Appel e WHERE e.groupe = :groupe ORDER BY e.date DESC").setParameter("groupe", groupe);

        @SuppressWarnings("unchecked")
        List<Appel> listAppels = q.getResultList();

        return listAppels;
    }

}