<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<div class="row text-white">

    <div class="col">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead">
                Bienvenue sur EasyTeach !<br/>
                <i class="fa fa-info-circle fa-fw text-primary mt-1"></i> Easyteach est une application pédagogique destinée aux enseignants du service informatique de l'IUT2 de Grenoble.<br/>
            </p>

            <hr>

            <p class="lead">
                <a class="btn btn-info" href="<%= application.getContextPath() %>/groupe/">Se rendre sur la liste des groupes</a>
            </p>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage") %>' />