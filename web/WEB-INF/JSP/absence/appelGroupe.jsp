<%@ page import="classes.entity.Etudiant" %>
<%@ page import="classes.entity.Module" %>
<%@ page import="classes.repository.EtudiantDAO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="groupe" class="classes.entity.Groupe" scope="request" />

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<h2 class="display-4 text-white">Appel en <%= groupe.getNom() %></h2>

<div class="row text-white">

    <div class="col">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <form method="post" class="lead" action="<%= application.getContextPath() %>/absence/appelGroupe?id=<%= groupe.getId() %>">

                <select class="form-control mb-1" name="moduleAppel" required>
                    <option class="disabled" disabled selected value="">Sélectionnez le module</option>
                    <% for (Module module: groupe.getModules()) { %>
                        <% if (groupe.getModules().contains(module)) { %>
                        <option value="<%= module.getId() %>"><%= module.getNom() %></option>
                        <% } %>
                    <% } %>
                </select>

                <input class="form-control mb-1" name="dateAppel" type="date" value="<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>" required />
                <input class="form-control mb-4" name="dateTimeAppel" type="time" value="<%= new SimpleDateFormat("HH").format(new Date()) + ":00" %>" required />

                <table class="table table-striped table-borderless">
                    <tr>
                        <th>Étudiant</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <% for (Etudiant etudiant: EtudiantDAO.getAllByGroupe(groupe)) { %>
                    <tr>
                        <td><%= etudiant.getNom() %> <%= etudiant.getPrenom() %></td>
                        <td>
                            <input id="absence<%= etudiant.getId() %>" name="absence<%= etudiant.getId() %>" type="checkbox" value="" />
                            <label for="absence<%= etudiant.getId() %>">Noter absent</label>
                        </td>
                        <td>
                            <input id="absenceJustifiee<%= etudiant.getId() %>" name="absenceJustifiee<%= etudiant.getId() %>" type="checkbox" value="" />
                            <label for="absenceJustifiee<%= etudiant.getId() %>">Abs. Justifiée</label>
                        </td>
                    </tr>
                    <% } %>
                </table>

                <input type="submit" class="btn btn-info" value="Valider l'appel" />
                <a href="<%= application.getContextPath() %>/absence/viewGroupe?id=<%= groupe.getId() %>" class="btn btn-warning">Annuler</a>
            </form>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage") %>' />