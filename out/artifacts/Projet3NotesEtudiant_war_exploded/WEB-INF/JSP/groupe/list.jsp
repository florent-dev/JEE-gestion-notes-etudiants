<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="java.util.Collection" %>
<%@ page import="classes.entity.*" %>
<%@ page import="classes.repository.GroupeDAO" %>

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<%
  // Récupération de la liste d'étudiants
  Collection<Groupe> listeGroupes = GroupeDAO.getAll();
%>

<h2 class="display-4 text-white">Liste des groupes</h2>

<div class="row text-white">

    <div class="col-lg-7">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <table class="table table-striped table-borderless">
                <tr>
                    <th>Liste des groupes</th>
                </tr>
                <% for (Groupe groupe: listeGroupes) { %>
                <tr>
                    <td>
                        <a href="<%= application.getContextPath() %>/groupe/view?id=<%= groupe.getId() %>">
                            <%= groupe.getNom() %>
                        </a>
                    </td>
                </tr>
                <% } %>
            </table>
        </div>
    </div>

    <div class="col-lg-5">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <form action="<%= application.getContextPath() %>/groupe/create">
                <input type="text" name="nomGroupe" class="form-control mb-2" placeholder="Nom du groupe" />
                <input type="submit" name="ajouterGroupe" class="btn btn-info" value="Ajouter" />
            </form>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage")%>'/>
