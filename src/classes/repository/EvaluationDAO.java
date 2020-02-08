package classes.repository;

import classes.entity.Evaluation;
import classes.utils.GestionFactory;
import classes.entity.Groupe;
import classes.entity.Module;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class EvaluationDAO {

    public static Evaluation find(int id) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();
        Evaluation evaluation = em.find(Evaluation.class, id);
        em.close();

        return evaluation;
    }

    public static Evaluation create(String nom, Date date, String description, Groupe groupe, Module module) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // create new etudiant
        Evaluation evaluation = new Evaluation();
        evaluation.setNom(nom);
        evaluation.setDate(date);
        evaluation.setDescription(description);
        evaluation.setGroupe(groupe);
        evaluation.setModule(module);
        em.persist(evaluation);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return evaluation;
    }

    public static Evaluation update(Evaluation evaluation) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // Attacher une entité persistante (etudiant) à l’EntityManager courant  pour réaliser la modification
        em.merge(evaluation);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return evaluation;
    }

    public static void remove(Evaluation evaluation) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        // Retrouver l'entité persistante et ses liens avec d'autres entités en vue de la suppression
        evaluation = em.find(Evaluation.class, evaluation.getId());
        em.remove(evaluation);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();
    }

    public static int removeAll() {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // RemoveAll
        int deletedCount = em.createQuery("DELETE FROM Evaluation ").executeUpdate();

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return deletedCount;
    }

    // Retourne l'ensemble des évaluations
    public static List<Evaluation> getAll() {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        // Recherche
        Query q = em.createQuery("SELECT e FROM Evaluation e");

        @SuppressWarnings("unchecked")
        List<Evaluation> listEvaluation = q.getResultList();

        return listEvaluation;
    }

    // Retourne les évaluations d'un module d'un groupe
    public static List<Evaluation> getByGroupeAndModule(Groupe groupe, Module module) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        // Recherche
        Query q = em.createQuery("SELECT e FROM Evaluation e WHERE e.groupe = :groupe AND e.module = :module")
                .setParameter("groupe", groupe)
                .setParameter("module", module);

        @SuppressWarnings("unchecked")
        List<Evaluation> listEvaluation = q.getResultList();

        return listEvaluation;
    }

}