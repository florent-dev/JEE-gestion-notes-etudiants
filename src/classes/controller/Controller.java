package classes.controller;

import classes.data.*;
import classes.data.Module;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/servlet_traitement_details")
public class Controller extends HttpServlet {

    // URL
    private String urlAccueil;
    private String urlDetails;
    private String urlGroupe;
    private String urlEtudiants;
    private String urlModules;
    private String url404;

    @Override
    public void init() throws ServletException {
        // Récupération des URLs en paramètre du web.xml
        urlAccueil = getInitParameter("index");
        urlDetails = getInitParameter("details");
        urlGroupe = getInitParameter("groupe");
        urlEtudiants = getInitParameter("etudiants");
        urlModules = getInitParameter("modules");
        url404 = getInitParameter("404");

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
            //MIAM.addModule(MI1);
            //MIAM.addModule(MI4);
            //SIMO.addModule(MI1);

            MI1.addGroupe(MIAM);
            MI4.addGroupe(MIAM);
            MI1.addGroupe(SIMO);

            //GroupeDAO.update(MIAM);
            //GroupeDAO.update(SIMO);

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
            //System.out.println("action == null");
        }

        // Accès aux différentes pages, pas de .jsp dans le nom de l'action
        switch (action) {
            case "/index":
                doAccueil(request, response);
                break;
            case "/details":
                doDetails(request, response);
                break;
            case "/groupe":
                doGroupe(request, response);
                break;
            case "/etudiants":
                doEtudiants(request, response);
                break;
            case "/modules":
                doModules(request, response);
                break;
            default:
                do404(request, response);
        }
    }

    private void doAccueil(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loadJSP(urlAccueil, request, response);
    }

    private void doDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Etudiant etudiant = EtudiantDAO.find(id);
        etudiant.setNbAbsences(7);

        request.setAttribute("etudiant", etudiant);

        loadJSP(urlDetails, request, response);
    }

    private void doGroupe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ajouterGroupeAction = request.getParameter("ajouterGroupe");
        String supprimerEtudiantAction = request.getParameter("supprimerEtudiant");

        // Ajouter un groupe
        if (ajouterGroupeAction != null) {
            String nomGroupe = request.getParameter("nomGroupe");

            if (nomGroupe != null) {
                GroupeDAO.create(nomGroupe);
                response.sendRedirect(request.getContextPath() + "/do/index");
                return;
            }
        }

        int id = Integer.parseInt(request.getParameter("id"));

        Groupe groupe = GroupeDAO.find(id);

        request.setAttribute("groupe", groupe);

        loadJSP(urlGroupe, request, response);
    }

    private void doEtudiants(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ajouterEtudiantAction = request.getParameter("ajouterEtudiant");
        String supprimerEtudiantAction = request.getParameter("supprimerEtudiant");

        // Ajouter un étudiant
        if (ajouterEtudiantAction != null) {
            String nomEtudiant = request.getParameter("nomEtudiant");
            String prenomEtudiant = request.getParameter("prenomEtudiant");

            if (nomEtudiant != null && prenomEtudiant != null) {
                EtudiantDAO.create(prenomEtudiant, nomEtudiant, null);
                response.sendRedirect(request.getContextPath() + "/do/etudiants");
                return;
            }
        }

        // Supprimer un étudiant
        if (supprimerEtudiantAction != null) {

            if (request.getParameter("idEtudiant") != null) {
                int idEtudiant = Integer.parseInt(request.getParameter("idEtudiant"));
                Etudiant etudiant = EtudiantDAO.find(idEtudiant);

                if (etudiant != null) {
                    EtudiantDAO.remove(etudiant);
                }

            }

            response.sendRedirect(request.getContextPath() + "/do/etudiants");
            return;
        }

        loadJSP(urlEtudiants, request, response);
    }

    private void doModules(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ajouterModuleAction = request.getParameter("ajouterModule");
        String supprimerModuleAction = request.getParameter("supprimerModule");

        // Ajouter un module
        if (ajouterModuleAction != null) {
            String nomModule = request.getParameter("nomModule");

            if (nomModule != null) {
                ModuleDAO.create(nomModule);
                response.sendRedirect(request.getContextPath() + "/do/modules");
                return;
            }
        }

        loadJSP(urlModules, request, response);
    }

    private void do404(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
