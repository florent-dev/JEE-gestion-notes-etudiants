package classes.repository;

import classes.entity.Etudiant;
import classes.entity.Evaluation;
import classes.utils.GestionFactory;
import classes.entity.Note;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class NoteDAO {

    public static Note find(int id) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        Note note = em.find(Note.class, id);
        em.close();

        return note;
    }

    public static Note create(float noteEtudiant, Etudiant etudiant, Evaluation evaluation) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        Note note = new Note();
        note.setNote(noteEtudiant);
        note.setEtudiant(etudiant);
        note.setEvaluation(evaluation);
        em.persist(note);

        em.getTransaction().commit();
        em.close();

        return note;
    }

    public static Note update(Note note) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        em.merge(note);

        em.getTransaction().commit();
        em.close();

        return note;
    }

    public static void remove(Note note) {
        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        note = em.find(Note.class, note.getId());
        em.remove(note);

        em.getTransaction().commit();
        em.close();
    }

    public static int removeByEvaluation(Evaluation evaluation) {
        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        // RemoveAll
        int deletedCount = em.createQuery("DELETE FROM Note n WHERE n.evaluation = :evaluation").setParameter("evaluation", evaluation).executeUpdate();

        em.getTransaction().commit();
        em.close();

        return deletedCount;
    }

    // Retourne l'ensemble des évaluations
    public static List<Note> getByEvaluation(Evaluation evaluation) {
        EntityManager em = GestionFactory.factory.createEntityManager();

        // Recherche
        Query q = em.createQuery("SELECT n FROM Note n WHERE n.evaluation = :evaluation").setParameter("evaluation", evaluation);

        @SuppressWarnings("unchecked")
        List<Note> listNotes = q.getResultList();

        return listNotes;
    }

    // Retourne l'ensemble des évaluations
    public static Note findByEtudiantAndEvaluation(Etudiant etudiant, Evaluation evaluation) {
        EntityManager em = GestionFactory.factory.createEntityManager();

        // Recherche
        Query q = em.createQuery("SELECT n FROM Note n WHERE n.etudiant = :etudiant AND n.evaluation = :evaluation")
                .setParameter("etudiant", etudiant)
                .setParameter("evaluation", evaluation);

        @SuppressWarnings("unchecked")
        List<Note> listNotes = q.getResultList();

        return (listNotes.size() > 0) ? listNotes.get(0) : null;
    }

}