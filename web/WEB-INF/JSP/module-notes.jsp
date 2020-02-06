<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="classes.data.Module" %>
<%@ page import="classes.data.ModuleDAO" %>
<%@ page import="java.util.Collection" %>

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<jsp:useBean id="groupe" class="classes.data.Groupe" scope="request"/>
<jsp:useBean id="module" class="classes.data.Module" scope="request"/>

<%
    // Récupération de la liste d'étudiants
    Collection<Module> listeModules = ModuleDAO.getAll();
%>

<h2 class="display-4 text-white">Notes du groupe <%= groupe.getNom() %> en <%= module.getNom() %></h2>

<div class="row text-white">

    <div class="col-lg-7">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <table class="table table-striped table-borderless">
                <tr>
                    <th>Notes</th>
                </tr>

            </table>
        </div>
    </div>

    <div class="col-lg-5">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <form action="<%= application.getContextPath() %>/do/modules">
                <input type="text" name="nomModule" class="form-control mb-2" placeholder="Nom du contrôle" />
                <input type="submit" name="ajouterNote" class="btn btn-info" value="Ajouter" />
            </form>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage")%>'/>
