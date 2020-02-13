package classes.controller;

import classes.entity.*;
import classes.entity.Module;
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

@WebServlet("/absenceController")
public class AbsenceController extends HttpServlet {

    // URL
    private String urlIndexTemplate;
    private String urlViewGroupeTemplate;
    private String urlAppelGroupeTemplate;
    private String urlUpdateAppelGroupeTemplate;
    private String url404Template;

    @Override
    public void init() throws ServletException {
        // Récupération des URLs en paramètre du web.xml
        urlIndexTemplate = getInitParameter("index");
        urlViewGroupeTemplate = getInitParameter("viewGroupe");
        urlAppelGroupeTemplate = getInitParameter("appelGroupe");
        urlUpdateAppelGroupeTemplate = getInitParameter("updateAppelGroupe");
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
            case "/updateAppelGroupe":
                updateAppelGroupeAction(request, response);
                break;
            default:
                notFoundAction(request, response);
        }
    }

    private void indexAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loadJSP(urlIndexTemplate, request, response);
    }

    private void viewGroupeAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int groupeId = ControllerUtils.parseRequestId(request.getParameter("id"));

        if (groupeId == 0) {
            response.sendRedirect(request.getContextPath() + "/absence/404");
            return;
        }

        Groupe groupe = GroupeDAO.find(groupeId);

        if (groupe == null) {
            response.sendRedirect(request.getContextPath() + "/absence/404");
            return;
        }

        request.setAttribute("groupe", groupe);

        loadJSP(urlViewGroupeTemplate, request, response);
    }

    private void appelGroupeAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int groupeId = ControllerUtils.parseRequestId(request.getParameter("id"));

        if (groupeId == 0) {
            response.sendRedirect(request.getContextPath() + "/absence/404");
            return;
        }

        Groupe groupe = GroupeDAO.find(groupeId);

        if (groupe == null) {
            response.sendRedirect(request.getContextPath() + "/absence/404");
            return;
        }

        int moduleAppelId = ControllerUtils.parseRequestId(request.getParameter("moduleAppel"));
        String dateAppel = request.getParameter("dateAppel");
        String dateTimeAppel = request.getParameter("dateTimeAppel");

        // Si le formulaire de création a été envoyé.
        if (moduleAppelId != 0 && dateAppel != null && dateTimeAppel != null) {
            Module module = ModuleDAO.find(moduleAppelId);

            if (module != null) {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateAppel + " " + dateTimeAppel);
                    Appel appel = AppelDAO.create(date, null, groupe, module);

                    // Fetch des notes/étudiants de l'évaluation et récupération du paramètre si existant
                    for (Etudiant etudiant: EtudiantDAO.getAllByGroupe(groupe)) {
                        String idAbsParameter = "absence" + etudiant.getId();
                        String absenceParam = request.getParameter(idAbsParameter);
                        String idAbsJustifieeParameter = "absenceJustifiee" + etudiant.getId();
                        String absenceJustifieeParam = request.getParameter(idAbsJustifieeParameter);

                        // Si absence au minimum est coché, on créé l'absence.
                        // Autrement on retire la note si elle existait.
                        if (absenceParam != null) {
                            Boolean absJustifiee = (Boolean.valueOf(absenceJustifieeParam));
                            AbsenceDAO.create(absJustifiee, date, appel, etudiant);
                        }
                    }

                } catch (Exception e) {
                    //System.out.println(e);
                }

                response.sendRedirect(request.getContextPath() + "/absence/viewGroupe?id=" + groupe.getId());
                return;
            }
        }

        request.setAttribute("groupe", groupe);

        loadJSP(urlAppelGroupeTemplate, request, response);
    }

    private void updateAppelGroupeAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int appelId = ControllerUtils.parseRequestId(request.getParameter("id"));

        if (appelId == 0) {
            loadJSP(url404Template, request, response);
            return;
        }

        Appel appel = AppelDAO.find(appelId);

        if (appel == null) {
            loadJSP(url404Template, request, response);
            return;
        }

        // Partie formulaire soumis. On récolte les supposées informations.
        int moduleAppelId = ControllerUtils.parseRequestId(request.getParameter("moduleAppel"));
        String dateAppel = request.getParameter("dateAppel");
        String dateTimeAppel = request.getParameter("dateTimeAppel");

        // Si le formulaire de création a été envoyé.
        if (moduleAppelId != 0 && dateAppel != null && dateTimeAppel != null) {
            Module module = ModuleDAO.find(moduleAppelId);

            if (module != null) {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateAppel + " " + dateTimeAppel);
                    appel.setModule(module);
                    appel.setDate(date);

                    // Fetch des notes/étudiants de l'évaluation et récupération du paramètre si existant
                    for (Etudiant etudiant: EtudiantDAO.getAllByGroupe(appel.getGroupe())) {
                        String idAbsParameter = "absence" + etudiant.getId();
                        String absenceParam = request.getParameter(idAbsParameter);
                        String idAbsJustifieeParameter = "absenceJustifiee" + etudiant.getId();
                        String absenceJustifieeParam = request.getParameter(idAbsJustifieeParameter);

                        // L'absence existe peut-être déjà.
                        Absence absence = AbsenceDAO.getAbsenceEtudiantSurUnAppel(etudiant, appel);

                        // Si absence au minimum est coché, on créé ou update l'absence.
                        // Autrement on la retire dans le cas où l'absence existe.
                        if (absenceParam != null) {
                            Boolean absJustifiee = (Boolean.valueOf(absenceJustifieeParam));

                            // Si l'absence existe, on l'update.
                            // Autrement on la créé.
                            if (absence != null) {
                                absence.setJustifie(absJustifiee);
                                AbsenceDAO.update(absence);
                            } else {
                                AbsenceDAO.create(absJustifiee, date, appel, etudiant);
                            }

                        } else {

                            if (absence != null) {
                                AbsenceDAO.remove(absence);
                            }

                        }
                    }

                } catch (Exception e) {
                    //System.out.println(e);
                }

                response.sendRedirect(request.getContextPath() + "/absence/viewGroupe?id=" + appel.getGroupe().getId());
                return;
            }
        }

        request.setAttribute("appel", appel);

        loadJSP(urlUpdateAppelGroupeTemplate, request, response);
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
