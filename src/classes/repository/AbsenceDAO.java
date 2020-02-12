package classes.repository;

import classes.entity.*;
import classes.entity.Absence;
import classes.entity.Module;
import classes.utils.GestionFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class AbsenceDAO {

    public static Absence find(int id) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();
        Absence absence = em.find(Absence.class, id);
        em.close();

        return absence;
    }

    public static Absence create(Boolean justifie, Date date, Appel appel, Etudiant etudiant) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        // create new Absence
        Absence absence = new Absence();
        absence.setJustifie(justifie);
        absence.setAppel(appel);
        absence.setEtudiant(etudiant);
        em.persist(absence);

        em.getTransaction().commit();
        em.close();

        return absence;
    }

    public static Absence update(Absence absence) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        em.merge(absence);

        em.getTransaction().commit();
        em.close();

        return absence;
    }

    public static void remove(Absence absence) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        absence = em.find(Absence.class, absence.getId());
        em.remove(absence);

        em.getTransaction().commit();
        em.close();
    }

    public static void remove(int id) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        em.createQuery("DELETE FROM Absence AS e WHERE e.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        em.getTransaction().commit();
        em.close();
    }

    // Retourne l'ensemble des Absences
    public static List<Absence> getAll() {
        EntityManager em = GestionFactory.factory.createEntityManager();

        Query q = em.createQuery("SELECT e FROM Absence e");

        @SuppressWarnings("unchecked")
        List<Absence> listAbsence = q.getResultList();

        return listAbsence;
    }

    // Retourne l'ensemble des Absences d'un appel donné
    public static List<Absence> getAllByAppel(Appel appel) {
        EntityManager em = GestionFactory.factory.createEntityManager();

        Query q = em.createQuery("SELECT e FROM Absence e WHERE e.appel = :appel").setParameter("appel", appel);

        @SuppressWarnings("unchecked")
        List<Absence> listAbsence = q.getResultList();

        return listAbsence;
    }

}