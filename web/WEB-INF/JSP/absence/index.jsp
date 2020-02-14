<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="classes.entity.Groupe" %>
<%@ page import="classes.repository.GroupeDAO" %>
<%@ page import="java.util.Collection" %>

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<%
    // Récupération de la liste des modules
    Collection<Groupe> listeGroupes = GroupeDAO.getAll();
%>

<h2 class="display-4 text-white">Gestion des appels</h2>

<div class="row text-white">

    <div class="col-lg-7">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead"><b>Choisissez le groupe concerné</b></p>
            <% for (Groupe groupe: listeGroupes) { %>
                <a href="<%= application.getContextPath() %>/absence/viewGroupe?id=<%= groupe.getId() %>" class="btn btn-info w-100 mb-2 pt-3 pb-3">
                    <%= groupe.getNom() %>
                </a>
            <% } %>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage")%>'/>
