package classes.data;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class NoteDAO {

    public static Note find(int id) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();
        Note note = em.find(Note.class, id);
        em.close();

        return note;
    }

    public static Note create(float noteEtudiant, Etudiant etudiant, Evaluation evaluation) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // create new etudiant
        Note note = new Note();
        note.setNote(noteEtudiant);
        note.setEtudiant(etudiant);
        note.setEvaluation(evaluation);
        em.persist(note);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return note;
    }

    public static Note update(Note note) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // Attacher une entité persistante (etudiant) à l’EntityManager courant  pour réaliser la modification
        em.merge(note);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return note;
    }

    public static void remove(Note note) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        // Retrouver l'entité persistante et ses liens avec d'autres entités en vue de la suppression
        note = em.find(Note.class, note.getId());
        em.remove(note);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();
    }

    public static int removeByEvaluation(Evaluation evaluation) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        // RemoveAll
        int deletedCount = em.createQuery("DELETE FROM Note n WHERE n.evaluation = :evaluation").setParameter("evaluation", evaluation).executeUpdate();

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return deletedCount;
    }

    // Retourne l'ensemble des évaluations
    public static List<Note> getByEvaluation(Evaluation evaluation) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        // Recherche
        Query q = em.createQuery("SELECT n FROM Note n WHERE n.evaluation = :evaluation").setParameter("evaluation", evaluation);

        @SuppressWarnings("unchecked")
        List<Note> listNotes = q.getResultList();

        return listNotes;
    }

}