<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="classes.entity.Groupe" %>
<%@ page import="classes.repository.GroupeDAO" %>

<jsp:useBean id="etudiant" class="classes.entity.Etudiant" scope="request" />

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<h2 class="display-4 text-white">Fiche étudiant n°<%= etudiant.getId() %></h2>

<div class="row text-white">

    <div class="col">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <form method="post" class="lead" action="<%= application.getContextPath() %>/etudiant/update?id=<%= etudiant.getId() %>">
                Nom : <input class="form-control" name="nomEtudiant" required type="text" value="<%= etudiant.getNom() %>"/>
                Prénom : <input class="form-control" name="prenomEtudiant" required type="text" value="<%= etudiant.getPrenom() %>"/>
                Groupe :
                <select class="form-control mb-2" name="groupeEtudiant" required>
                    <option class="disabled" value="" disabled selected>Attribuer un groupe</option>
                    <% for (Groupe groupe: GroupeDAO.getAll()) { %>
                    <option value="<%= groupe.getId() %>" <%if (groupe.getId().equals(etudiant.getGroupe().getId())) { %>selected<% } %>>
                        <%= groupe.getNom() %>
                    </option>
                    <% } %>
                </select>
                <input type="hidden" value="<%= etudiant.getId() %>" name="idEtudiant" required />
                <input type="submit" class="btn btn-info" />
            </form>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage") %>' />