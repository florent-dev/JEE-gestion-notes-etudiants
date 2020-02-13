<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="module" class="classes.entity.Module" scope="request" />

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<h2 class="display-4 text-white">Édition du module n°<%= module.getId() %></h2>

<div class="row text-white">

    <div class="col">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <form method="post" class="lead" action="<%= application.getContextPath() %>/module/update?id=<%= module.getId() %>">
                Libellé :
                <input class="form-control mb-1" name="nomModule" required type="text" value="<%= module.getNom() %>"/>
                <input type="hidden" value="<%= module.getId() %>" name="idModule" required />
                <input type="submit" class="btn btn-info" />
                <a href="<%= application.getContextPath() %>/module/list" class="btn btn-danger">Annuler</a>
            </form>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage") %>' />