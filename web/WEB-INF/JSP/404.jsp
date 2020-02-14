<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<div class="row text-white">

    <div class="col">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead">
                Une erreur est survenue. La page demandée n'a pas pu s'afficher ou n'existe plus.
            </p>
            <p class="lead">
                <a class="btn btn-info" href="<%= application.getContextPath() %>/index">Retourner sur l'accueil</a>
            </p>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage") %>' />