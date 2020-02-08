<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="groupe" class="classes.entity.Groupe" scope="request" />

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<h2 class="display-4 text-white">Édition du groupe n°<%= groupe.getId() %></h2>

<div class="row text-white">

    <div class="col">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <form method="post" class="lead" action="<%= application.getContextPath() %>/groupe/update?id=<%= groupe.getId() %>">
                Libellé :
                <input class="form-control mb-1" name="nomGroupe" required type="text" value="<%= groupe.getNom() %>"/>
                <input type="hidden" value="<%= groupe.getId() %>" name="idGroupe" required />
                <input type="submit" class="btn btn-info" />
            </form>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage") %>' />