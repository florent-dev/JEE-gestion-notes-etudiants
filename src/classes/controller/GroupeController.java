package classes.controller;

import classes.entity.Module;
import classes.entity.*;
import classes.repository.*;
import classes.utils.ControllerUtils;
import classes.utils.GestionFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("ALL")
@WebServlet("/groupeController")
public class GroupeController extends HttpServlet {

    // URL
    private String urlListTemplate;
    private String urlViewTemplate;
    private String urlUpdateTemplate;
    private String urlEvaluationTemplate;
    private String url404Template;

    @Override
    public void init() throws ServletException {
        // Récupération des URLs en paramètre du web.xml
        urlListTemplate = getInitParameter("list");
        urlViewTemplate = getInitParameter("view");
        urlUpdateTemplate = getInitParameter("update");
        urlEvaluationTemplate = getInitParameter("viewEvaluation");
        url404Template = getInitParameter("404");

        // Création de la factory permettant la création d'EntityManager (gestion des transactions)
        GestionFactory.open();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // On passe la main au get
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        // On récupère la méthode d'envoi de la requête
        String methode = request.getMethod().toLowerCase();

        // On récupère l'action à exécuter
        String action = request.getPathInfo();
        //System.out.println(action);

        if (action == null || action.equals("/")) {
            action = "/list";
            //System.out.println("action == null");
        }

        // Accès aux différentes pages, pas de .jsp dans le nom de l'action
        switch (action) {
            case "/list":
                listAction(request, response);
                break;
            case "/view":
                viewAction(request, response);
                break;
            case "/create":
                createAction(request, response);
                break;
            case "/update":
                updateAction(request, response);
                break;
            case "/delete":
                deleteAction(request, response);
                break;
            case "/ajouterModule":
                ajouterModuleAction(request, response);
                break;
            case "/retirerModule":
                retirerModuleAction(request, response);
                break;
            case "/createEvaluation":
                createEvaluationAction(request, response);
                break;
            case "/evaluation":
                manageEvaluationAction(request, response);
                break;
            case "/updateEvaluation":
                updateEvaluationAction(request, response);
                break;
            default:
                notFoundAction(request, response);
        }
    }

    private void listAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loadJSP(urlListTemplate, request, response);
    }

    private void viewAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = ControllerUtils.parseRequestId(request.getParameter("id"));

        if (id == 0) {
            response.sendRedirect(request.getContextPath() + "/groupe/");
            return;
        }

        Groupe groupe = GroupeDAO.find(id);

        if (groupe == null) {
            response.sendRedirect(request.getContextPath() + "/groupe/");
            return;
        }

        request.setAttribute("groupe", groupe);

        loadJSP(urlViewTemplate, request, response);
    }

    private void createAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomGroupe = request.getParameter("nomGroupe");

        if (nomGroupe != null) {
            GroupeDAO.create(nomGroupe);
        }

        response.sendRedirect(request.getContextPath() + "/groupe/");
    }

    private void updateAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idGroupe = ControllerUtils.parseRequestId(request.getParameter("id"));
        String nomGroupe = request.getParameter("nomGroupe");

        try {
            Groupe groupe = GroupeDAO.find(idGroupe);
            request.setAttribute("groupe", groupe);

            if (nomGroupe != null) {
                groupe.setNom(nomGroupe);
                GroupeDAO.update(groupe);
                response.sendRedirect(request.getContextPath() + "/groupe/");
                return;
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/groupe/");
            return;
        }

        loadJSP(urlUpdateTemplate, request, response);
    }

    private void deleteAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idGroupe = ControllerUtils.parseRequestId(request.getParameter("id"));

        if (idGroupe != 0) {
            Groupe groupe = GroupeDAO.find(idGroupe);

            if (groupe != null) {
                GroupeDAO.remove(groupe);
            }

        }

        response.sendRedirect(request.getContextPath() + "/groupe/");
    }

    private void ajouterModuleAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ajouter un module au groupe
        int groupeId = ControllerUtils.parseRequestId(request.getParameter("id"));
        int moduleId = ControllerUtils.parseRequestId(request.getParameter("ajouterModuleAuGroupe"));

        if (groupeId != 0 && moduleId != 0) {
            Groupe groupe = GroupeDAO.find(groupeId);
            Module module = ModuleDAO.find(moduleId);

            if (groupe != null && module != null) {
                groupe.addModule(module);
                module.addGroupe(groupe);

                GroupeDAO.update(groupe);
                ModuleDAO.update(module);

                response.sendRedirect(request.getContextPath() + "/groupe/view?id=" + groupe.getId());
                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/groupe/");
    }

    private void retirerModuleAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retirer un module au groupe
        int groupeId = ControllerUtils.parseRequestId(request.getParameter("id"));
        int moduleId = ControllerUtils.parseRequestId(request.getParameter("mid"));

        if (groupeId != 0 && moduleId != 0) {
            Groupe groupe = GroupeDAO.find(groupeId);
            Module module = ModuleDAO.find(moduleId);

            if (groupe != null && module != null) {
                groupe.removeModule(module);
                module.removeGroupe(groupe);

                ModuleDAO.update(module);
                GroupeDAO.update(groupe);

                response.sendRedirect(request.getContextPath() + "/groupe/view?id=" + groupe.getId());
                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/groupe/");
    }

    private void createEvaluationAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int groupeId = ControllerUtils.parseRequestId(request.getParameter("gid"));
        int moduleId = ControllerUtils.parseRequestId(request.getParameter("mid"));

        if (groupeId != 0 && moduleId != 0) {
            Groupe groupe = GroupeDAO.find(groupeId);
            Module module = ModuleDAO.find(moduleId);

            String nomEvaluation = request.getParameter("nomEvaluation");
            String descriptionEvaluation = request.getParameter("descriptionEvaluation");
            String dateEvaluation = request.getParameter("dateEvaluation");

            if (groupe != null && module != null && nomEvaluation != null && descriptionEvaluation != null && dateEvaluation != null) {
                try {
                    Date dateEvaluationParsed = new SimpleDateFormat("yyyy-MM-dd").parse(dateEvaluation);
                    EvaluationDAO.create(nomEvaluation, dateEvaluationParsed, descriptionEvaluation, groupe, module);
                } catch (Exception e) {
                    //System.out.println(e);
                }

                response.sendRedirect(request.getContextPath() + "/groupe/view?id=" + groupe.getId());
                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/groupe/");
    }

    private void manageEvaluationAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int evaluationId = ControllerUtils.parseRequestId(request.getParameter("id"));

        if (evaluationId == 0) {
            response.sendRedirect(request.getContextPath() + "/groupe/");
            return;
        }

        Evaluation evaluation = EvaluationDAO.find(evaluationId);
        request.setAttribute("evaluation", evaluation);

        if (evaluation == null) {
            response.sendRedirect(request.getContextPath() + "/groupe/");
            return;
        }

        loadJSP(urlEvaluationTemplate, request, response);
    }

    private void updateEvaluationAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int evaluationId = ControllerUtils.parseRequestId(request.getParameter("id"));

        if (evaluationId == 0) {
            response.sendRedirect(request.getContextPath() + "/groupe/");
            return;
        }

        Evaluation evaluation = EvaluationDAO.find(evaluationId);
        request.setAttribute("evaluation", evaluation);

        if (evaluation == null) {
            response.sendRedirect(request.getContextPath() + "/groupe/");
            return;
        }

        int idEvaluationForm = ControllerUtils.parseRequestId(request.getParameter("idEvaluation"));
        String nomEvaluation = request.getParameter("nomEvaluation");
        String descriptionEvaluation = request.getParameter("descriptionEvaluation");
        String dateEvaluation = request.getParameter("dateEvaluation");

        // Mise à jour des données de l'évaluation.
        if (nomEvaluation != null && descriptionEvaluation != null && dateEvaluation != null) {
            try {
                Date dateEvaluationParsed = new SimpleDateFormat("yyyy-MM-dd").parse(dateEvaluation);
                evaluation.setNom(nomEvaluation);
                evaluation.setDescription(descriptionEvaluation);
                evaluation.setDate(dateEvaluationParsed);
                EvaluationDAO.update(evaluation);
            } catch (Exception e) {
                //System.out.println(e);
            }
        }

        // Fetch des notes/étudiants de l'évaluation et récupération du paramètre si existant
        for (Etudiant etudiant: EtudiantDAO.getAllByGroupe(evaluation.getGroupe())) {
            String idParameter = "note" + etudiant.getId();
            String noteParam = request.getParameter(idParameter);

            // Récupération de la note.
            Note noteInstance = NoteDAO.findByEtudiantAndEvaluation(etudiant, evaluation);

            // La note de l'étudiant est renseignée, on la créé ou màj.
            // Autrement on retire la note si elle existait.
            if (noteParam != null && noteParam.length() > 0) {
                float noteParamParsed = ControllerUtils.parseNote(noteParam);
                if (noteInstance == null) {
                    NoteDAO.create(noteParamParsed, etudiant, evaluation);
                } else {
                    noteInstance.setNote(noteParamParsed);
                    NoteDAO.update(noteInstance);
                }
                System.out.println(etudiant.getId() + ": " + noteParam);
            } else {
                if (noteInstance != null) {
                    NoteDAO.remove(noteInstance);
                }
            }
        }

        response.sendRedirect(request.getContextPath() + "/groupe/view?id=" + evaluation.getGroupe().getId());
    }

    private void notFoundAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loadJSP(url404Template, request, response);
    }

    public void loadJSP(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    public void destroy() {
        super.destroy();
        GestionFactory.close();
    }
}
