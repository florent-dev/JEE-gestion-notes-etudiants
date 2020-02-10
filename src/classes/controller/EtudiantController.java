package classes.controller;

import classes.entity.*;
import classes.repository.EtudiantDAO;
import classes.repository.GroupeDAO;
import classes.utils.GestionFactory;
import classes.utils.ControllerUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/etudiantController")
public class EtudiantController extends HttpServlet {

    // URL
    private String urlListTemplate;
    private String urlViewTemplate;
    private String urlUpdateTemplate;
    private String url404Template;

    @Override
    public void init() throws ServletException {
        // Récupération des URLs en paramètre du web.xml
        urlListTemplate = getInitParameter("list");
        urlViewTemplate = getInitParameter("view");
        urlUpdateTemplate = getInitParameter("update");
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
        // On récupère la méthode d'envoi de la requête
        String methode = request.getMethod().toLowerCase();

        // On récupère l'action à exécuter
        String action = request.getPathInfo();
        System.out.println(action);

        if (action == null || action.equals("/")) {
            action = "/list";
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
            default:
                notFoundAction(request, response);
        }
    }

    private void listAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loadJSP(urlListTemplate, request, response);
    }

    private void viewAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Etudiant etudiant = EtudiantDAO.find(id);
        etudiant.setNbAbsences(7);

        request.setAttribute("etudiant", etudiant);

        loadJSP(urlViewTemplate, request, response);
    }

    private void createAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomEtudiant = request.getParameter("nomEtudiant");
        String prenomEtudiant = request.getParameter("prenomEtudiant");
        String groupeEtudiantId = request.getParameter("groupeEtudiant");

        if (nomEtudiant != null && prenomEtudiant != null && groupeEtudiantId != null) {
            Groupe groupeEtudiant = GroupeDAO.find(Integer.parseInt(groupeEtudiantId));

            if (groupeEtudiant != null) {
                EtudiantDAO.create(prenomEtudiant, nomEtudiant, groupeEtudiant);
            }
        }

        response.sendRedirect(request.getContextPath() + "/etudiant/list");
    }

    private void updateAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idEtudiant = ControllerUtils.parseRequestId(request.getParameter("id"));
        String nomEtudiant = request.getParameter("nomEtudiant");
        String prenomEtudiant = request.getParameter("prenomEtudiant");
        int groupeEtudiantId = ControllerUtils.parseRequestId(request.getParameter("groupeEtudiant"));

        // Protection sur l'idEtudiant.
        if (idEtudiant == 0) {
            response.sendRedirect(request.getContextPath() + "/etudiant/list");
            return;
        }

        Etudiant etudiant = EtudiantDAO.find(idEtudiant);

        if (etudiant == null) {
            response.sendRedirect(request.getContextPath() + "/etudiant/list");
            return;
        }

        // Si est sur la page update et qu'on a envoyé le formulaire.
        if (nomEtudiant != null && prenomEtudiant != null) {
            int idEtudiantForm = ControllerUtils.parseRequestId(request.getParameter("idEtudiant"));

            // Mesure de protection, vérification de l'id de l'URL et du formulaire.
            if (idEtudiantForm == 0 || idEtudiantForm != idEtudiant) {
                response.sendRedirect(request.getContextPath() + "/etudiant/list");
                return;
            }

            etudiant.setNom(nomEtudiant);
            etudiant.setPrenom(prenomEtudiant);

            Groupe groupe = GroupeDAO.find(groupeEtudiantId);

            if (groupe != null) {
                etudiant.setGroupe(groupe);
            }

            EtudiantDAO.update(etudiant);

            response.sendRedirect(request.getContextPath() + "/etudiant/list");
            return;
        }

        request.setAttribute("etudiant", etudiant);

        loadJSP(urlUpdateTemplate, request, response);
    }

    private void deleteAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idEtudiant = ControllerUtils.parseRequestId(request.getParameter("id"));

        if (idEtudiant != 0) {
            Etudiant etudiant = EtudiantDAO.find(idEtudiant);

            if (etudiant != null) {
                EtudiantDAO.remove(etudiant);
            }

        }

        response.sendRedirect(request.getContextPath() + "/etudiant/list");
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
