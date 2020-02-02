<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="classes.data.Etudiant" %>
<%@ page import="classes.data.EtudiantDAO" %>
<%@ page import="java.util.Collection" %>

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<jsp:useBean id="groupe" class="classes.data.Groupe" scope="request"/>

<%
    // Récupération de la liste d'étudiants
    Collection<Etudiant> listeEtudiants = EtudiantDAO.getAllByGroupe(groupe);
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
            <p class="lead">Modules</p>
            <p class="lead">
                <form action="<%= application.getContextPath() %>/do/groupe">
                    <input type="text" name="nomGroupe" class="form-control mb-2" placeholder="Nom du module" />
                    <input type="submit" name="ajouterGroupe" class="btn btn-info" value="Ajouter" />
                </form>
            </p>
        </div>
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead">Contrôles</p>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage")%>'/>
