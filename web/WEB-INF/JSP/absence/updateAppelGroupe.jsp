<%@ page import="classes.entity.Etudiant" %>
<%@ page import="classes.entity.Module" %>
<%@ page import="classes.repository.EtudiantDAO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="classes.entity.Absence" %>
<%@ page import="classes.repository.AbsenceDAO" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="appel" class="classes.entity.Appel" scope="request" />

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<h2 class="display-4 text-white">Appel en <%= appel.getGroupe().getNom() %></h2>

<div class="row text-white">

    <div class="col">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <form method="post" class="lead" action="<%= application.getContextPath() %>/absence/updateAppelGroupe?id=<%= appel.getId() %>">

                <select class="form-control" name="moduleAppel">
                    <option class="disabled" disabled selected value="">Sélectionnez le module</option>
                    <% for (Module module: appel.getGroupe().getModules()) { %>
                        <% if (appel.getGroupe().getModules().contains(module)) { %>
                        <option value="<%= module.getId() %>" <% if (module.getId().equals(appel.getModule().getId())) { %>selected<% } %>>
                            <%= module.getNom() %>
                        </option>
                        <% } %>
                    <% } %>
                </select>

                <input class="form-control" name="dateAppel" type="date" value="<%= new SimpleDateFormat("yyyy-MM-dd").format(appel.getDate()) %>" />
                <input class="form-control" name="dateTimeAppel" type="time" value="<%= new SimpleDateFormat("HH:mm").format(appel.getDate()) %>" />

                <table class="table table-striped table-borderless">
                    <tr>
                        <th>Étudiant</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <%
                        for (Etudiant etudiant: EtudiantDAO.getAllByGroupe(appel.getGroupe())) {
                            Absence absence = AbsenceDAO.getAbsenceEtudiantSurUnAppel(etudiant, appel);
                            String absenceChecked = (absence != null) ? "checked" : "";
                            String absenceJustifieeChecked = (absence != null && absence.isJustifie()) ? "checked" : "";
                    %>
                    <tr>
                        <td><%= etudiant.getNom() %> <%= etudiant.getPrenom() %></td>
                        <td>
                            <input id="absence<%= etudiant.getId() %>" name="absence<%= etudiant.getId() %>" type="checkbox" <%= absenceChecked %>/>
                            <label for="absence<%= etudiant.getId() %>">Noter absent</label>
                        </td>
                        <td>
                            <input id="absenceJustifiee<%= etudiant.getId() %>" name="absenceJustifiee<%= etudiant.getId() %>" type="checkbox" <%= absenceJustifieeChecked %>/>
                            <label for="absenceJustifiee<%= etudiant.getId() %>">Abs. Justifiée</label>
                        </td>
                    </tr>
                    <% } %>
                </table>

                <input type="submit" class="btn btn-info" value="Modifier l'appel" />
                <a href="<%= application.getContextPath() %>/absence/viewGroupe?id=<%= appel.getGroupe().getId() %>" class="btn btn-warning">Annuler</a>
            </form>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage") %>' />