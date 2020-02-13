<%@ page import="classes.entity.Absence" %>
<%@ page import="classes.repository.AbsenceDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="etudiant" class="classes.entity.Etudiant" scope="request" />

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<h2 class="display-4 text-white">Fiche étudiant n°<%= etudiant.getId() %></h2>

<div class="row text-white">

    <div class="col-lg-7">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead">
                Nom : <%= etudiant.getNom() %><br />
                Prénom : <%= etudiant.getPrenom() %><br />
            </p>
        </div>
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead"><b>Absences</b></p>
            <table class="table table-sm">
                <%
                    List<Absence> absenceList = AbsenceDAO.getAllByEtudiant(etudiant);
                    if (absenceList.size() > 0) {
                        for (Absence absence: AbsenceDAO.getAllByEtudiant(etudiant)) {
                        String dateAbsStrFR = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(absence.getAppel().getDate());
                %>
                <tr>
                    <td><%= dateAbsStrFR %> - Cours de <%= absence.getAppel().getModule().getNom() %></td>
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

    <div class="col-lg-5">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead">
                Groupe : <a href="<%= application.getContextPath() %>/groupe/view?id=<%= etudiant.getGroupe().getId() %>"><%= etudiant.getGroupe().getNom() %></a>
            </p>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage") %>' />