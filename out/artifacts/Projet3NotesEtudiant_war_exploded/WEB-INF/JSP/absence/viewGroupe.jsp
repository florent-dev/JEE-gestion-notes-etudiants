<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="classes.entity.Appel" %>
<%@ page import="classes.repository.AppelDAO" %>
<%@ page import="java.util.Collection" %>
<%@ page import="classes.entity.Absence" %>
<%@ page import="classes.repository.AbsenceDAO" %>
<%@ page import="java.text.SimpleDateFormat" %>

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<jsp:useBean id="groupe" class="classes.entity.Groupe" scope="request"/>

<%
    // Récupération de la liste des appels du groupe donné
    Collection<Appel> listeAppels = AppelDAO.getAllByGroupe(groupe);
%>

<h2 class="display-4 text-white">Groupe <%= groupe.getNom() %></h2>

<div class="row text-white">

    <div class="col-lg-7">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <p class="lead"><b>Liste des appels</b></p>
            <table class="table">
                <% if (listeAppels.size() == 0) { %>
                <tr>
                    <td>Aucun appel trouvé.</td>
                </tr>
                <%
                } else {
                    for (Appel appel: listeAppels) {
                %>
                <tr>
                    <td>
                        <% String dateStrFR = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(appel.getDate()); %>
                        <div class="text-info"><%= dateStrFR %> - <%= appel.getModule().getNom() %></div>
                        <% for (Absence absence: AbsenceDAO.getAllByAppel(appel)) { %>
                        <div>
                            <% if (absence.isJustifie()) { %> <i class="fa fa-check-circle fa-fw text-success mt-1"></i> <small class="font-weight-bold text-uppercase text-sm-left">JUSTIFIÉE - </small>  <% } %>
                            <%= absence.getEtudiant().getNom() %> <%= absence.getEtudiant().getPrenom() %>
                        </div>
                        <% } %>
                    </td>
                    <td class="text-right">
                        <a href="<%= application.getContextPath() %>/absence/updateAppelGroupe?id=<%= appel.getId() %>"><i class="fa fa-pencil fa-fw text-primary mt-1"></i></a>
                    </td>
                </tr>
                <%
                    }
                }
                %>
            </table>
        </div>
    </div>

    <div class="col-lg-5">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <a href="<%= application.getContextPath() %>/absence/appelGroupe?id=<%= groupe.getId() %>" class="btn btn-outline-info">Faire un appel</a>
            <a href="<%= application.getContextPath() %>/groupe/view?id=<%= groupe.getId() %>" class="btn btn-outline-info">Aller sur le groupe</a>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage")%>'/>
