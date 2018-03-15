<%@ page import="java.util.LinkedList" %>
<%@ page import="beans.ElectionData" %>
<%@ page import="beans.CandidateData" %>
<%@ page import="user.LoggedUsers" %>
<%@ page import="beans.RightEnum" %>
<%@ page import="util.ServletUtil" %>
<%--
 Author:          Ewald Hartmann
 Created on:
 Description:     represents an election
--%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <!-- Change the Tab-title of the page -->
    <title>Democracy 2.0</title>
    <!-- Import the CSS of Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- Import the JavaScript of AJAX -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- Import the JavaScript of  Bootstrap -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- Import the default CSS -->
    <link rel="stylesheet" type="text/css" href="css/DefaultCSS.css">
    <!-- Set Tab picture -->
    <link rel="icon" type="image/png" href="res/Avatar.png">
    <!-- Import the JavaScript of  Navbar -->
    <script src="js/NavbarLogedUser.js"></script>
    <!-- Import the JavaScript for showing the candidateinfo -->
    <script src="js/CandidateInfo.js"></script>

</head>
<body>
<%
    HttpSession httpSession = request.getSession();
    LoggedUsers loggesUser = LoggedUsers.getInstance();

    String hash = (String) httpSession.getAttribute("hash");

    if (!loggesUser.compareRights(hash, RightEnum.USER)) {
        response.sendRedirect("/LoginSL");
    }

%>
<!-- Implements the navigation bar in the webseite -->
<div id="navbar"></div>
<div id="container">
    <br><br>
    <!--  Shows the title of the page -->
    <div class="voteTitle">
        <%
            ElectionData electionData = (ElectionData) request.getSession().getAttribute("voteObject");
        %>
        <h1>
            <%= electionData.getTitle()%>
        </h1>
    </div>
    <br>
    <!--  Shows the question of the vote -->
    <div class="voteQuestion">
        <h3>Bitte wählen Sie einen der folgenden Kanditaten aus. </h3>
    </div>
    <br>
    <form method="post" action="/ElectionSL">
        <!--  Adds the answer options of the vote -->
        <div class="voteDiv">
            <%
                LinkedList<CandidateData> candidateDataList = electionData.getLiCandidates();
                int count = 0;
                for (CandidateData candidateData : candidateDataList) {
                    System.out.println(candidateData.toString());
                    out.println("<ul class=\"list-group\">");
                    out.println("    <li class=\"list-group-item\">");
                    out.println("        <div class=\"radio\">");
                    out.println("            <label><input type=\"radio\" class=\"votingButton\" name=\"optradio\" value=\" " + count + " \" >"
                            + candidateData.getTitle() + " "
                            + candidateData.getSurname().toUpperCase() + " "
                            + candidateData.getForename() + "</label>");
                    out.println("            <button type=\"button\" class=\"btn btn-link\" data-toggle=\"modal\" data-target=\"#infoModal\" name=\"" + count++ + "\" onClick=\"reply_click(this.name)\" style=\"height 100px; width: 100px \"><span");
                    out.println("                    class=\"glyphicon glyphicon-info-sign\"");
                    out.println("                      ></span></button>");
                    out.println("        </div>");
                    out.println("    </li>");
                    out.println("</ul>");
                }
            %>


        </div>
        <!-- Adds a button the submit the choice -->
        <center>
            <div class="input-group">
                    <button id="evaluation" type="submit" class="btn btn-primary">
                        <span class="glyphicon glyphicon-ok"></span> Stimme abgeben
                    </button>
            </div>
            <br>
            <%=  request.getAttribute("error") != null ? request.getAttribute("error") : ""  %>
        </center>
    </form>

    <!-- Implements the Infodialog -->
    <div class="modal fade" id="infoModal" role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <!-- The head of the Infodialog -->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" id="cand_titleName"></h4>
                </div>
                <!-- The Body of the Infodialog -->
                <div class="modal-footer">
                    <div class="infoDiv">
                        <div class="col-sm-2">
                            <img src="" id="cand_pic" class="img-responsive" alt="" width="150"
                                 height="300">
                        </div>
                        <div class="col-sm-10">
                            <ul class="list-group" style="text-align: left"> <!-- STYLE TAG MUSS INS CSS -->
                                <li class="list-group-item"><span class="listHeader">Vorname: </span>
                                    <p class="candAttribute" id="cand_forename"></p></li>
                                <li class="list-group-item"><span class="listHeader">Nachname: </span>
                                    <p class="candAttribute" id="cand_surname"></p></li>
                                <li class="list-group-item"><span class="listHeader">Geburtsdatum: </span>
                                    <p class="candAttribute" id="cand_birthday"></p></li>
                                <li class="list-group-item"><span class="listHeader">Partei: </span>
                                    <p class="candAttribute" id="cand_party"></p></li>
                                <li class="list-group-item"><span class="listHeader">Wahlmotto: </span>
                                    <p class="candAttribute" id="cand_slogan"></p></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <br>
    <p>Für alle Angaben ist der Ersteller der Wahl verantwortlich!</p>
</div>
<footer class="footer">
    <div class="container text-center">
        <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam Democracy 2.0</p>
    </div>
</footer>
</body>
</html>
