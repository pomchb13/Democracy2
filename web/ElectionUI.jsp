<%@ page import="java.util.LinkedList" %>
<%@ page import="beans.ElectionData" %>
<%@ page import="beans.CandidateData" %>
<%@ page import="user.LoggedUsers" %>
<%@ page import="beans.RightEnum" %>
<%@ page import="util.ServletUtil" %><%--
  Created by IntelliJ IDEA.
  User: Ewald
  Date: 11.07.2017
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<%/*
    HttpSession ses = request.getSession();
    LoggedUsers lU = LoggedUsers.getInstance();

    String hash = (String) ses.getAttribute("hash");

    if (!lU.compareRights(hash, RightEnum.ADMIN)) {
        response.sendRedirect("/LoginSL");
    }
*/
%>
<!-- Implements the navigation bar in the webseite -->
<div id="navbar"></div>
<div id="container">
    <br><br>
    <!--  Shows the title of the page -->
    <div class="voteTitle">
        <%
            ElectionData ed = (ElectionData) request.getSession().getAttribute("election");
        %>
        <h1>
            <%= ed.getTitle()%>
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
                LinkedList<CandidateData> canDat = ed.getLiCandidates();
                int count = 0;
                for (CandidateData cd : canDat) {
                    System.out.println(cd.toString());
                    out.println("<ul class=\"list-group\">");
                    out.println("    <li class=\"list-group-item\">");
                    out.println("        <div class=\"radio\">");
                    out.println("            <label><input type=\"radio\" name=\"optradio\" value=\" " + count + " \" >"
                            + cd.getTitle() + " "
                            + cd.getSurname().toUpperCase() + " "
                            + cd.getForename() + "</label>");
                    out.println("            <button id=\"info\" type=\"button\" class=\"btn btn-link\" data-toggle=\"modal\" data-target=\"#infoModal\" name=\""+count++ +"\" onClick=\"reply_click(this.name)\" ><span");
                    out.println("                    class=\"glyphicon glyphicon-info-sign\"");
                    out.println("                      ></span></button>");
                    out.println("        </div>");
                    out.println("    </li>");
                    out.println("</ul>");
                }
            %>


        </div>
        <!-- Adds a button the submit the choise -->
        <div class="input-group">
            <button id="evaluation" type="submit" class="btn btn-primary">
                <span class="glyphicon glyphicon-ok"></span> Stimme abgeben
            </button>
        </div>

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
                            <img src="res/images/user.png" id="cand_pic" class="img-responsive" alt="" width="150"
                                 height="300">
                        </div>
                        <div class="col-sm-10">
                            <ul class="list-group">
                                <li class="list-group-item"><span class="listHeader">Vorname: </span><p class="candAttribute" id="cand_forename"></p></li>
                                <li class="list-group-item"><span class="listHeader">Nachname: </span><p class="candAttribute" id="cand_surname"></p></li>
                                <li class="list-group-item"><span class="listHeader">Geburtsdatum: </span><p class="candAttribute" id="cand_birthday"></p></li>
                                <li class="list-group-item"><span class="listHeader">Partei: </span><p class="candAttribute" id="cand_party"></p></li>
                                <li class="list-group-item"><span class="listHeader">Wahlmotto: </span><p class="candAttribute" id="cand_slogan"></p></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <br>
    <p>Für Alle Angaben ist der Ersteller der Wahl verantwortlich!</p>
</div>
<footer class="footer">
    <div class="container text-center">
        <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam HTBLA Kaindorf</p>
    </div>
</footer>
</body>
</html>
