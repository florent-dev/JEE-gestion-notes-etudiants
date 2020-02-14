<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="java.util.Collection" %>
<%@ page import="classes.entity.*" %>
<%@ page import="classes.repository.GroupeDAO" %>
<%@ page import="classes.repository.EtudiantDAO" %>

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<%
  // Récupération de la liste d'étudiants
  Collection<Groupe> listeGroupes = GroupeDAO.getAll();
%>

<h2 class="display-4 text-white">Liste des groupes</h2>

<div class="row text-white">

    <div class="col-lg-7">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead"><b>Liste des groupes</b></p>
            <table class="table">
                <tr>
                    <th>Groupe</th>
                    <th class="text-center">Effectifs</th>
                    <th></th>
                </tr>
                <% for (Groupe groupe: listeGroupes) { %>
                <tr>
                    <td>
                        <a href="<%= application.getContextPath() %>/groupe/view?id=<%= groupe.getId() %>">
                            <%= groupe.getNom() %>
                        </a>
                    </td>
                    <td class="text-center">
                        <%= EtudiantDAO.getNbEtudiantsByGroupe(groupe) %>
                    </td>
                    <td class="text-right">
                        <a href="<%= application.getContextPath() %>/groupe/update?id=<%= groupe.getId() %>"><i class="fa fa-pencil fa-fw text-primary mt-1"></i></a>
                        <a href="<%= application.getContextPath() %>/groupe/delete?id=<%= groupe.getId() %>"><i class="fa fa-trash fa-fw text-primary mt-1"></i></a>
                    </td>
                </tr>
                <% } %>
            </table>
        </div>
    </div>

    <div class="col-lg-5">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead"><b>Créer un groupe</b></p>
            <form action="<%= application.getContextPath() %>/groupe/create">
                <input type="text" name="nomGroupe" class="form-control mb-2" placeholder="Nom du groupe" />
                <input type="submit" name="ajouterGroupe" class="btn btn-info mt-2" value="Ajouter" />
            </form>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage")%>'/>
