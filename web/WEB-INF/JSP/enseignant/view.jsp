<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="enseignant" class="classes.entity.Enseignant" scope="request" />

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<h2 class="display-4 text-white">Fiche enseignant n°<%= enseignant.getId() %></h2>

<div class="row text-white">

    <div class="col-lg-7">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead">
                Nom : <%= enseignant.getNom() %><br />
                Prénom : <%= enseignant.getPrenom() %>
            </p>
        </div>
    </div>

    <div class="col-lg-5">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead">
                Informations diverses... [non renseigné]
            </p>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage") %>' />