<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="java.util.Collection" %>
<%@ page import="classes.data.Etudiant" %>
<%@ page import="classes.data.GestionFactory" %>

<jsp:useBean id="etudiant" class="classes.data.Etudiant" scope="request"/>

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<h2 class="display-4 text-white">Fiche étudiant n°<%= etudiant.getId() %></h2>

<div class="row text-white">

    <div class="col-lg-7">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead">
                Nom : <%= etudiant.getNom() %><br />
                Prénom : <%= etudiant.getPrenom() %><br />
                Absences : <%= etudiant.getNbAbsences() %>
            </p>
        </div>
    </div>

    <div class="col-lg-5">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead">
                Groupe : <a href="groupe?id=<%= etudiant.getGroupe().getId() %>"><%= etudiant.getGroupe().getNom() %></a>
            </p>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage") %>' />