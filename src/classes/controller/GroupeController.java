package classes.controller;

import classes.entity.*;
import classes.entity.Module;
import classes.repository.EtudiantDAO;
import classes.repository.GroupeDAO;
import classes.repository.ModuleDAO;
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

@WebServlet("/groupeController")
public class GroupeController extends HttpServlet {

    // URL
    private String urlList;
    private String urlView;
    private String urlCreate;
    private String urlUpdate;
    private String urlDelete;
    private String url404;

    @Override
    public void init() throws ServletException {
        // Récupération des URLs en paramètre du web.xml
        urlList = getInitParameter("list");
        urlView = getInitParameter("view");
        url404 = getInitParameter("404");

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
            case "/delete":
                deleteAction(request, response);
                break;
            case "/ajouterModule":
                ajouterModuleAction(request, response);
                break;
            default:
                notFoundAction(request, response);
        }
    }

    private void listAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loadJSP(urlList, request, response);
    }

    private void viewAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = ControllerUtils.parseRequestId(request.getParameter("id"));

        if (id == 0) {
            response.sendRedirect(request.getContextPath() + "/groupe/list");
            return;
        }

        Groupe groupe = GroupeDAO.find(id);

        if (groupe == null) {
            response.sendRedirect(request.getContextPath() + "/groupe/list");
            return;
        }

        request.setAttribute("groupe", groupe);

        loadJSP(urlView, request, response);
    }

    private void createAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomGroupe = request.getParameter("nomGroupe");

        if (nomGroupe != null) {
            GroupeDAO.create(nomGroupe);
        }

        response.sendRedirect(request.getContextPath() + "/groupe/list");
    }

    private void updateAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idGroupe = ControllerUtils.parseRequestId(request.getParameter("id"));
        String nomGroupe = request.getParameter("nomGroupe");

        // Protection sur l'idEtudiant.
        if (idGroupe == 0) {
            response.sendRedirect(request.getContextPath() + "/groupe/list");
            return;
        }

        if (nomGroupe != null) {
            Groupe groupe = GroupeDAO.find(idGroupe);

            if (groupe != null) {
                groupe.setNom(nomGroupe);
                GroupeDAO.update(groupe);
                response.sendRedirect(request.getContextPath() + "/etudiant/list");
                return;
            }
        }

        loadJSP(urlUpdate, request, response);
    }

    private void deleteAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idEtudiant = ControllerUtils.parseRequestId(request.getParameter("idEtudiant"));

        if (idEtudiant != 0) {
            Etudiant etudiant = EtudiantDAO.find(idEtudiant);

            if (etudiant != null) {
                EtudiantDAO.remove(etudiant);
            }

        }

        response.sendRedirect(request.getContextPath() + "/etudiant/list");
    }

    private void ajouterModuleAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ajouter un module au groupe
        int groupeId = ControllerUtils.parseRequestId(request.getParameter("id"));
        int moduleId = ControllerUtils.parseRequestId(request.getParameter("ajouterModuleAction"));

        if (groupeId != 0 && moduleId != 0) {
            Groupe groupe = GroupeDAO.find(groupeId);
            Module module = ModuleDAO.find(moduleId);

            if (groupe != null && module != null) {
                groupe.addModule(module);
                module.addGroupe(groupe);

                GroupeDAO.update(groupe);
                ModuleDAO.update(module);

                response.sendRedirect(request.getContextPath() + "/groupe/?id=" + groupe.getId());
                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/groupe/list");
    }

    private void notFoundAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loadJSP(url404, request, response);
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
