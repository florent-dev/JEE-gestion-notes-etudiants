<%@ page import="classes.entity.Absence" %>
<%@ page import="classes.entity.Module" %>
<%@ page import="classes.repository.AbsenceDAO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.utils.CalculUtils" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="etudiant" class="classes.entity.Etudiant" scope="request" />

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<%
    List<Absence> absenceList = AbsenceDAO.getAllByEtudiant(etudiant);
    List<Module> moduleList = etudiant.getGroupe().getModules();
    List<Double> moyenneModuleList = new ArrayList<Double>();
%>

<h2 class="display-4 text-white">Fiche étudiant n°<%= etudiant.getId() %></h2>

<div class="row text-white">

    <div class="col-12">
        <div class="bg-white pl-5 pr-5 pb-1 pt-3 rounded my-5 shadow-sm">
            <p class="lead">
                Nom : <%= etudiant.getNom() %><br />
                Prénom : <%= etudiant.getPrenom() %><br />
                Groupe : <a href="<%= application.getContextPath() %>/groupe/view?id=<%= etudiant.getGroupe().getId() %>"><%= etudiant.getGroupe().getNom() %></a>
            </p>
        </div>
    </div>

    <div class="col-lg-6">
        <div class="bg-white p-5 rounded my-1 shadow-sm">
            <p class="lead"><b>Moyennes</b></p>
            <table class="table table-sm">
                <% for (Module module: moduleList) { %>
                <tr>
                    <td><%= module.getNom() %></td>
                    <td class="text-right">
                        <%
                            // Récupération de la moy. module et stockage pour calculer la moy. générale.
                            Double d = module.getMoyenneEtudiant(etudiant);
                            if (d != null) moyenneModuleList.add(d);
                        %>
                        <%= CalculUtils.echoDoubleHTML(d) %>
                    </td>
                </tr>
                <% } %>
                <tr>
                    <td></td>
                    <td class="text-right font-weight-bold"><%= CalculUtils.echoDoubleHTML(CalculUtils.calculMoyenne(moyenneModuleList)) %></td>
                </tr>
            </table>
        </div>
    </div>

    <div class="col-lg-6">
        <div class="bg-white p-5 rounded my-1 shadow-sm">
            <p class="lead"><b>Absences (<%= absenceList.size() %>)</b></p>
            <table class="table table-sm">
                <%
                    if (absenceList.size() > 0) {
                        for (Absence absence: AbsenceDAO.getAllByEtudiant(etudiant)) {
                            String dateAbsStrFR = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(absence.getAppel().getDate());
                %>
                <tr>
                    <td>
                        <%= dateAbsStrFR %> - Cours de <%= absence.getAppel().getModule().getNom() %>
                        <% if (absence.isJustifie()) { %> <i class="fa fa-check-circle fa-fw text-success mt-1 ml-3"></i> <small class="font-weight-bold text-uppercase text-sm-left">JUSTIFIÉE</small>  <% } %>
                    </td>
                    <td class="text-right">
                        <a href="<%= application.getContextPath() %>/absence/updateAppelGroupe?id=<%= absence.getAppel().getId() %>"><i class="fa fa-edit fa-fw text-primary mt-1"></i></a>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr><td>Aucune absence enregistrée.</td></tr>
                <% } %>
            </table>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage") %>' />