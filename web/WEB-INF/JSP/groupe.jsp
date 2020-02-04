<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="classes.data.Etudiant" %>
<%@ page import="classes.data.EtudiantDAO" %>
<%@ page import="java.util.Collection" %>
<%@ page import="classes.data.Module" %>
<%@ page import="classes.data.ModuleDAO" %>

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<jsp:useBean id="groupe" class="classes.data.Groupe" scope="request"/>

<%
    // Récupération de la liste d'étudiants
    Collection<Etudiant> listeEtudiants = EtudiantDAO.getAllByGroupe(groupe);

    // Récupération de la liste des modules
    Collection<Module> listeModules = ModuleDAO.getAll();

    Collection<Module> groupeModules = groupe.getModules();
%>

<h2 class="display-4 text-white">Groupe <%= groupe.getNom() %></h2>

<div class="row text-white">

    <div class="col-lg-7">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <table class="table table-striped table-borderless">
                <tr>
                    <th>Liste des étudiants</th>
                </tr>
                <% for (Etudiant etudiant: listeEtudiants) { %>
                <tr>
                    <td>
                        <a href="<%= application.getContextPath() %>/do/details?id=<%= etudiant.getId() %>">
                            <%= etudiant.getNom() %>
                            <%= etudiant.getPrenom() %>
                        </a>
                    </td>
                </tr>
                <% } %>
            </table>
        </div>
    </div>

    <div class="col-lg-5">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <table class="table table-striped table-borderless">
                <tr>
                    <th>Liste des modules</th>
                </tr>
                <% for (Module module: groupeModules) { %>
                <tr>
                    <td>
                        <%= module.getNom() %>
                    </td>
                </tr>
                <% } %>
            </table>

            <hr>

            <form action="<%= application.getContextPath() %>/do/groupe?id=<%= groupe.getId() %>" method="POST">
                <select class="form-control" name="ajouterModuleAuGroupe" onchange="this.form.submit()">
                    <option class="disabled" disabled selected value="">Ajouter un module au groupe</option>
                    <% for (Module module: listeModules) { %>
                        <% if (!groupeModules.contains(module)) { %>
                        <option value="<%= module.getId() %>"><%= module.getNom() %></option>
                        <% } %>
                    <% } %>
                </select>
            </form>
        </div>
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead">Contrôles</p>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage")%>'/>
