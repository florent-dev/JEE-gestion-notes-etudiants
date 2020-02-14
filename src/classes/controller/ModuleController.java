package classes.controller;

import classes.entity.Groupe;
import classes.entity.Module;
import classes.repository.GroupeDAO;
import classes.repository.ModuleDAO;
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

@WebServlet("/moduleController")
public class ModuleController extends HttpServlet {

    // URL
    private String urlListTemplate;
    private String urlUpdateTemplate;
    private String url404Template;

    @Override
    public void init() throws ServletException {
        // Récupération des URLs en paramètre du web.xml
        urlListTemplate = getInitParameter("list");
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
        request.setCharacterEncoding("UTF-8");
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

    private void createAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomModule = request.getParameter("nomModule");

        if (nomModule != null) {
            ModuleDAO.create(nomModule);
        }

        response.sendRedirect(request.getContextPath() + "/module/");
    }

    private void updateAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idModule = ControllerUtils.parseRequestId(request.getParameter("id"));
        String nomModule = request.getParameter("nomModule");

        // Protection sur l'idEtudiant.
        if (idModule == 0) {
            response.sendRedirect(request.getContextPath() + "/module/");
            return;
        }

        Module module = ModuleDAO.find(idModule);

        if (module == null) {
            response.sendRedirect(request.getContextPath() + "/module/");
            return;
        }

        if (nomModule != null) {
            module.setNom(nomModule);
            ModuleDAO.update(module);

            response.sendRedirect(request.getContextPath() + "/module/");
            return;
        }

        request.setAttribute("module", module);

        loadJSP(urlUpdateTemplate, request, response);
    }

    private void deleteAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idModule = ControllerUtils.parseRequestId(request.getParameter("id"));

        if (idModule != 0) {
            Module module = ModuleDAO.find(idModule);

            if (module != null) {
                ModuleDAO.remove(module);
            }
        }

        response.sendRedirect(request.getContextPath() + "/module/");
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
