<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="classes.entity.Module" %>
<%@ page import="classes.repository.ModuleDAO" %>
<%@ page import="java.util.Collection" %>

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<%
    // Récupération de la liste d'étudiants
    Collection<Module> listeModules = ModuleDAO.getAll();
%>

<h2 class="display-4 text-white">Modules</h2>

<div class="row text-white">

    <div class="col-lg-7">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead"><b>Liste des modules</b></p>
            <table class="table">
                <% for (Module module: listeModules) { %>
                <tr>
                    <td>
                        <%= module.getNom() %>
                    </td>
                    <td class="text-right">
                        <a href="<%= application.getContextPath() %>/module/update?id=<%= module.getId() %>"><i class="fa fa-pencil fa-fw text-primary mt-1"></i></a>
                        <a href="<%= application.getContextPath() %>/module/delete?id=<%= module.getId() %>"><i class="fa fa-trash fa-fw text-primary mt-1"></i></a>
                    </td>
                </tr>
                <% } %>
            </table>
        </div>
    </div>

    <div class="col-lg-5">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <form action="<%= application.getContextPath() %>/module/create">
                <input type="text" name="nomModule" class="form-control mb-2" placeholder="Nom du module" />
                <input type="submit" class="btn btn-info" value="Ajouter" />
            </form>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage")%>'/>
