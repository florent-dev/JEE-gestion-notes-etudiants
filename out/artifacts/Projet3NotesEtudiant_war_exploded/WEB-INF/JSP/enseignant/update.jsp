<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="enseignant" class="classes.entity.Enseignant" scope="request" />

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<h2 class="display-4 text-white">Édition de l'enseignant n°<%= enseignant.getId() %></h2>

<div class="row text-white">

    <div class="col">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <form method="post" class="lead" action="<%= application.getContextPath() %>/enseignant/update?id=<%= enseignant.getId() %>">
                Nom : <input class="form-control" name="nomEnseignant" required type="text" value="<%= enseignant.getNom() %>"/>
                Prénom : <input class="form-control mb-4" name="prenomEnseignant" required type="text" value="<%= enseignant.getPrenom() %>"/>
                <input type="hidden" value="<%= enseignant.getId() %>" name="idEnseignant" required />
                <input type="submit" class="btn btn-info" />
                <a href="<%= application.getContextPath() %>/enseignant/list" class="btn btn-danger">Annuler</a>
            </form>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage") %>' />