package classes.controller;

import classes.entity.*;
import classes.entity.Module;
import classes.repository.EtudiantDAO;
import classes.repository.GroupeDAO;
import classes.repository.ModuleDAO;
import classes.utils.GestionFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/defaultController")
public class DefaultController extends HttpServlet {

    // URL
    private String urlAccueilTemplate;
    private String url404Template;

    @Override
    public void init() throws ServletException {
        // Récupération des URLs en paramètre du web.xml
        urlAccueilTemplate = getInitParameter("index");
        url404Template = getInitParameter("404");

        // Création de la factory permettant la création d'EntityManager (gestion des transactions)
        GestionFactory.open();

        // Initialisation de la BDD
        if ((GroupeDAO.getAll().size() == 0) && (EtudiantDAO.getAll().size() == 0)) {

            // Création des groupes
            Groupe MIAM = GroupeDAO.create("AW");
            Groupe SIMO = GroupeDAO.create("SIMO");
            Groupe MESSI = GroupeDAO.create("ASSR");

            // Création des étudiants
            EtudiantDAO.create("Francis", "Brunet-Manquat", MIAM);
            EtudiantDAO.create("Philippe", "Martin", MIAM);
            EtudiantDAO.create("Mario", "Cortes-Cornax", MIAM);
            EtudiantDAO.create("Françoise", "Coat", SIMO);
            EtudiantDAO.create("Laurent", "Bonnaud", MESSI);
            EtudiantDAO.create("Sébastien", "Bourdon", MESSI);
            EtudiantDAO.create("Mathieu", "Gatumel", SIMO);

            // Création des groupes
            Module MI1 = ModuleDAO.create("MI1");
            Module MI4 = ModuleDAO.create("MI4");

            // Liés groupe et module
            MIAM.addModule(MI1);
            MIAM.addModule(MI4);
            SIMO.addModule(MI1);

            MI1.addGroupe(MIAM);
            MI4.addGroupe(MIAM);
            MI1.addGroupe(SIMO);

            GroupeDAO.update(MIAM);
            GroupeDAO.update(SIMO);

            ModuleDAO.update(MI1);
            ModuleDAO.update(MI4);

        }
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

        if (action == null) {
            action = "/index";
        }

        // Accès aux différentes pages, pas de .jsp dans le nom de l'action
        switch (action) {
            case "/index":
                accueilAction(request, response);
                break;
            default:
                do404(request, response);
        }
    }

    private void accueilAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loadJSP(urlAccueilTemplate, request, response);
    }

    private void do404(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
