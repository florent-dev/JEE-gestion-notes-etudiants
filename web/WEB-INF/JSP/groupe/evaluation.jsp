<%@ page import="classes.entity.Etudiant" %>
<%@ page import="classes.repository.EtudiantDAO" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="classes.repository.EvaluationDAO" %>
<%@ page import="classes.repository.NoteDAO" %>
<%@ page import="classes.entity.Note" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="evaluation" class="classes.entity.Evaluation" scope="request" />

<jsp:include page='<%= application.getInitParameter("entetedepage") %>' />

<% String dateStrGB = new SimpleDateFormat("yyyy-MM-dd").format(evaluation.getDate()); %>
<% String dateStrFR = new SimpleDateFormat("dd/MM/yyyy").format(evaluation.getDate()); %>

<h2 class="display-4 text-white">Évaluation <%= evaluation.getNom() %></h2>

<div class="row text-white">

    <div class="col">
        <div class="bg-white p-5 rounded my-5 shadow-sm">
            <form method="post" class="lead" action="<%= application.getContextPath() %>/groupe/updateEvaluation?id=<%= evaluation.getId() %>">

                <div class="mb-4">
                    <div><%= dateStrFR %> - <%= evaluation.getNom() %> (<%= evaluation.getDescription() %>)</div>

                    <a data-toggle="collapse" href="#infosCollapse" role="button" aria-expanded="false" aria-controls="infosCollapse">
                        <i class="fa fa-pencil mt-1">Modifier les informations</i>
                    </a>

                    <div class="collapse" id="infosCollapse">
                        <input type="hidden" value="<%= evaluation.getId() %>" name="idEvaluation" required />
                        <input class="form-control mb-1" name="nomEvaluation" required type="text" value="<%= evaluation.getNom() %>"/>
                        <input class="form-control mb-1" name="descriptionEvaluation" required type="text" value="<%= evaluation.getDescription() %>"/>
                        <input class="form-control mb-1" name="dateEvaluation" required type="date" value="<%= dateStrGB %>"/>
                    </div>
                </div>

                <table class="table table-striped table-borderless">
                    <tr>
                        <th>Étudiant</th>
                        <th>Note sur 20</th>
                    </tr>
                    <% for (Etudiant etudiant: EtudiantDAO.getAllByGroupe(evaluation.getGroupe())) { %>
                    <tr>
                        <td><%= etudiant.getNom() %> <%= etudiant.getPrenom() %></td>
                        <td>
                            <%
                                Note noteEtudiant = NoteDAO.findByEtudiantAndEvaluation(etudiant, evaluation);
                                String noteStr = (noteEtudiant != null) ? String.valueOf(noteEtudiant.getNote()) : "";
                            %>
                            <input class="form-control" min="0" max="20" name="notes[<%= etudiant %>]" type="number" placeholder="Non renseignée" value="<%= noteStr %>" />
                        </td>
                    </tr>
                    <% } %>
                </table>

                <input type="submit" class="btn btn-info" value="Mettre à jour" />
                <a href="<%= application.getContextPath() %>/groupe/view?id=<%= evaluation.getGroupe().getId() %>" class="btn btn-warning">Revenir sur le groupe</a>
            </form>
        </div>
    </div>

</div>

<jsp:include page='<%= application.getInitParameter("pieddepage") %>' />