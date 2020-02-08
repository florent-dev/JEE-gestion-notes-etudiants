<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="classes.entity.Enseignant" %>
<%@ page import="classes.repository.EnseignantDAO" %>
<%@ page import="java.util.Collection" %>

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<%
    // Récupération de la liste d'enseignants
    Collection<Enseignant> listeEnseignants = EnseignantDAO.getAll();
%>

<h2 class="display-4 text-white">Liste des enseignants</h2>

<div class="row text-white">

    <div class="col-lg-7">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <table class="table table-striped table-borderless">
                <tr>
                    <th>Liste des enseignants</th>
                    <th></th>
                </tr>
                <% for (Enseignant enseignant: listeEnseignants) { %>
                <tr>
                    <td>
                        <a href="<%= application.getContextPath() %>/enseignant/view?id=<%= enseignant.getId() %>">
                            <%= enseignant.getNom() %>
                            <%= enseignant.getPrenom() %>
                        </a>
                    </td>
                    <td class="text-right">
                        <a href="<%= application.getContextPath() %>/enseignant/delete?id=<%= enseignant.getId() %>">
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
            <form method="post" action="<%= application.getContextPath() %>/enseignant/create">
                <input type="text" class="form-control mb-2" name="nomEnseignant" placeholder="Nom" required />
                <input type="text" class="form-control mb-2" name="prenomEnseignant" placeholder="Prénom" required />
                <input type="submit" class="btn btn-info" name="ajouterEnseignant" value="Ajouter" />
            </form>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage")%>'/>
