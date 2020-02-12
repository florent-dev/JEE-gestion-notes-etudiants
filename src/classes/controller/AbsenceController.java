package classes.controller;

import classes.utils.GestionFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/absenceController")
public class AbsenceController extends HttpServlet {

    // URL
    private String urlIndexTemplate;
    private String urlViewGroupeTemplate;
    private String urlAppelGroupeTemplate;
    private String url404Template;

    @Override
    public void init() throws ServletException {
        // Récupération des URLs en paramètre du web.xml
        urlIndexTemplate = getInitParameter("index");
        urlViewGroupeTemplate = getInitParameter("viewGroupe");
        urlAppelGroupeTemplate = getInitParameter("appelGroupe");
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
            action = "/index";
            //System.out.println("action == null");
        }

        // Accès aux différentes pages, pas de .jsp dans le nom de l'action
        switch (action) {
            case "/index":
                indexAction(request, response);
                break;
            case "/viewGroupe":
                viewGroupeAction(request, response);
                break;
            case "/appelGroupe":
                appelGroupeAction(request, response);
                break;
            default:
                notFoundAction(request, response);
        }
    }

    private void indexAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loadJSP(urlIndexTemplate, request, response);
    }

    private void viewGroupeAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loadJSP(urlViewGroupeTemplate, request, response);
    }

    private void appelGroupeAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loadJSP(urlAppelGroupeTemplate, request, response);
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
