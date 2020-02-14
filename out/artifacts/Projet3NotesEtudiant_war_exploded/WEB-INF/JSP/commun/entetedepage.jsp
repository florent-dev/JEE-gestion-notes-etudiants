<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta charset="utf-8">
    <%--  on récupère les paramètres d'initialisation de la servlet --%>
    <title><%= application.getInitParameter("title") %></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" crossorigin="anonymous">
    <style><%@ include file="/assets/css/custom.css" %></style>
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</head>

<body>

<!-- Vertical navbar -->
<nav class="vertical-nav bg-white" id="sidebar">
    <div class="py-4 px-3 mb-4 bg-light">
        <div class="media-body">
            <h4 class="m-0"><a href="<%= application.getContextPath() %>/" class="text-decoration-none text-black-50">Easyteach</a></h4>
            <p class="font-weight-light text-muted mb-0">Support enseignant</p>
        </div>
    </div>

    <p class="text-gray font-weight-bold text-uppercase px-3 small pb-4 mb-0">Gestion</p>

    <ul class="nav flex-column bg-white mb-0">
        <li class="nav-item">
            <a href="<%= application.getContextPath() %>/groupe/" class="nav-link text-dark font-italic bg-light">
                <i class="fa fa-th-large mr-3 text-primary fa-fw"></i>
                Groupes
            </a>
        </li>
        <li class="nav-item">
            <a href="<%= application.getContextPath() %>/module/" class="nav-link text-dark font-italic">
                <i class="fa fa-cubes mr-3 text-primary fa-fw"></i>
                Modules
            </a>
        </li>
        <li class="nav-item">
            <a href="<%= application.getContextPath() %>/etudiant/" class="nav-link text-dark font-italic">
                <i class="fa fa-address-card mr-3 text-primary fa-fw"></i>
                Étudiants
            </a>
        </li>
        <li class="nav-item">
            <a href="<%= application.getContextPath() %>/enseignant/" class="nav-link text-dark font-italic">
                <i class="fa fa-address-card mr-3 text-primary fa-fw"></i>
                Enseignants
            </a>
        </li>
    </ul>

    <p class="text-gray font-weight-bold text-uppercase px-3 small py-4 mb-0">Absences</p>

    <ul class="nav flex-column bg-white mb-0">
        <li class="nav-item">
            <a href="<%= application.getContextPath() %>/absence/" class="nav-link text-dark font-italic">
                <i class="fa fa-pencil mr-3 text-primary fa-fw"></i>
                Faire un appel
            </a>
        </li>
    </ul>

</nav>
<!-- End vertical navbar -->


<!-- Page content holder -->
<div class="page-content p-5" id="content">

<!-- Toggle button -->
<button id="sidebarCollapse" type="button" class="btn btn-light bg-white rounded-pill shadow-sm px-4 mb-4"><i class="fa fa-bars mr-2"></i><small class="text-uppercase font-weight-bold">Menu</small></button>