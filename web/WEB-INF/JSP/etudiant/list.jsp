<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="classes.entity.Etudiant" %>
<%@ page import="classes.repository.EtudiantDAO" %>
<%@ page import="classes.entity.Groupe" %>
<%@ page import="classes.repository.GroupeDAO" %>
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
            <p class="lead"><b>Liste des étudiants</b></p>
            <table class="table">
                <tr>
                    <th>Étudiant</th>
                    <th>Groupe</th>
                    <th></th>
                </tr>
                <% for (Etudiant etudiant: listeEtudiants) { %>
                <tr>
                    <td>
                        <a href="<%= application.getContextPath() %>/etudiant/view?id=<%= etudiant.getId() %>">
                            <%= etudiant.getNom() %>
                            <%= etudiant.getPrenom() %>
                        </a>
                    </td>
                    <td>
                        <% if (etudiant.getGroupe() != null) { %>
                        <a href="<%= application.getContextPath() %>/groupe/view?id=<%= etudiant.getGroupe().getId() %>">
                            <%= etudiant.getGroupe().getNom() %>
                        </a>
                        <% } %>
                    </td>
                    <td class="text-right">
                        <a href="<%= application.getContextPath() %>/etudiant/update?id=<%= etudiant.getId() %>"><i class="fa fa-pencil fa-fw text-primary mt-1"></i></a>
                        <a href="<%= application.getContextPath() %>/etudiant/delete?id=<%= etudiant.getId() %>"><i class="fa fa-trash fa-fw text-primary mt-1"></i></a>
                    </td>
                </tr>
                <% } %>
            </table>
        </div>
    </div>

    <div class="col-lg-5">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead"><b>Ajouter un étudiant</b></p>
            <form method="post" action="<%= application.getContextPath() %>/etudiant/create">
                <input type="text" class="form-control mb-2" name="nomEtudiant" placeholder="Nom" required />
                <input type="text" class="form-control mb-2" name="prenomEtudiant" placeholder="Prénom" required />
                <select class="form-control mb-2" name="groupeEtudiant" required>
                    <option class="disabled" value="" disabled selected>Attribuer un groupe</option>
                    <% for (Groupe groupe: GroupeDAO.getAll()) { %>
                    <option value="<%= groupe.getId() %>"><%= groupe.getNom() %></option>
                    <% } %>
                </select>
                <input type="submit" class="btn btn-info mt-2" name="ajouterEtudiant" value="Ajouter" />
            </form>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage")%>'/>
