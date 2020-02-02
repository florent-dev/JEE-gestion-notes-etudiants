<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="classes.data.Etudiant" %>
<%@ page import="classes.data.EtudiantDAO" %>
<%@ page import="java.util.Collection" %>

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<%
    // Récupération de la liste d'étudiants
    Collection<Etudiant> listeEtudiants = EtudiantDAO.getAll();
%>

<h2 class="display-4 text-white">Liste des étudiants</h2>

<div class="row text-white">

    <div class="col-lg-7">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <table class="table table-striped table-borderless">
                <tr>
                    <th>Liste des étudiants</th>
                    <th>Groupe</th>
                    <th></th>
                </tr>
                <% for (Etudiant etudiant: listeEtudiants) { %>
                <tr>
                    <td>
                        <a href="<%= application.getContextPath() %>/do/details?id=<%= etudiant.getId() %>">
                            <%= etudiant.getNom() %>
                            <%= etudiant.getPrenom() %>
                        </a>
                    </td>
                    <td>
                        <% if (etudiant.getGroupe() != null) { %>
                        <a href="<%= application.getContextPath() %>/do/groupe?id=<%= etudiant.getGroupe().getId() %>">
                            <%= etudiant.getGroupe().getNom() %>
                        </a>
                        <% } %>
                    </td>
                    <td class="text-right">
                        <a href="<%= application.getContextPath() %>/do/etudiants?supprimerEtudiant=true&idEtudiant=<%= etudiant.getId() %>">
                            <i class="fa fa-trash fa-fw text-primary mt-1"></i>
                        </a>
                    </td>
                </tr>
                <% } %>
            </table>
        </div>
    </div>

    <div class="col-lg-5">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <form method="post" action="<%= application.getContextPath() %>/do/etudiants">
                <input type="text" class="form-control mb-2" name="nomEtudiant" placeholder="Nom" />
                <input type="text" class="form-control mb-2" name="prenomEtudiant" placeholder="Prénom" />
                <input type="submit" class="btn btn-info" name="ajouterEtudiant" value="Ajouter" />
            </form>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage")%>'/>
