package classes.controller;

import classes.entity.Enseignant;
import classes.entity.Etudiant;
import classes.entity.Groupe;
import classes.repository.EnseignantDAO;
import classes.repository.EtudiantDAO;
import classes.repository.GroupeDAO;
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

@WebServlet("/enseignantController")
public class EnseignantController extends HttpServlet {

    // URL
    private String urlList;
    private String urlView;
    private String urlUpdate;
    private String url404;

    @Override
    public void init() throws ServletException {
        // Récupération des URLs en paramètre du web.xml
        urlList = getInitParameter("list");
        urlView = getInitParameter("view");
        urlUpdate = getInitParameter("update");
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
        loadJSP(urlList, request, response);
    }

    private void viewAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Enseignant enseignant = EnseignantDAO.find(id);

        request.setAttribute("enseignant", enseignant);

        loadJSP(urlView, request, response);
    }

    private void createAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomEnseignant = request.getParameter("nomEnseignant");
        String prenomEnseignant = request.getParameter("prenomEnseignant");

        if (nomEnseignant != null && prenomEnseignant != null) {
            EnseignantDAO.create(prenomEnseignant, nomEnseignant);
        }

        response.sendRedirect(request.getContextPath() + "/enseignant/");
    }

    private void updateAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idEnseignant = ControllerUtils.parseRequestId(request.getParameter("id"));
        String nomEnseignant = request.getParameter("nomEnseignant");
        String prenomEnseignant = request.getParameter("prenomEnseignant");

        // Protection sur l'idEnseignant.
        if (idEnseignant == 0) {
            response.sendRedirect(request.getContextPath() + "/enseignant/list");
            return;
        }

        Enseignant enseignant = EnseignantDAO.find(idEnseignant);

        if (enseignant == null) {
            response.sendRedirect(request.getContextPath() + "/enseignant/list");
            return;
        }

        // Si est sur la page update et qu'on a envoyé le formulaire.
        if (nomEnseignant != null && prenomEnseignant != null) {
            int idEnseignantForm = ControllerUtils.parseRequestId(request.getParameter("idEnseignant"));

            // Mesure de protection, vérification de l'id de l'URL et du formulaire.
            if (idEnseignantForm == 0 || idEnseignantForm != idEnseignant) {
                response.sendRedirect(request.getContextPath() + "/enseignant/list");
                return;
            }

            enseignant.setNom(nomEnseignant);
            enseignant.setPrenom(prenomEnseignant);

            EnseignantDAO.update(enseignant);

            response.sendRedirect(request.getContextPath() + "/enseignant/list");
            return;
        }

        request.setAttribute("enseignant", enseignant);

        loadJSP(urlUpdate, request, response);
    }

    private void deleteAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idEnseignant = ControllerUtils.parseRequestId(request.getParameter("id"));

        if (idEnseignant != 0) {
            Enseignant enseignant = EnseignantDAO.find(idEnseignant);

            if (enseignant != null) {
                EnseignantDAO.remove(enseignant);
            }

        }

        response.sendRedirect(request.getContextPath() + "/enseignant/list");
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
