<%@ page import="beans.PollData" %>
<%@ page import="beans.PollAnswer" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="user.LoggedUsers" %>
<%@ page import="beans.RightEnum" %><%--
  Created by IntelliJ IDEA.
  User: Ewald
  Date: 28.02.2018
  Time: 16:07
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
</head>
<body>
<%
    HttpSession ses = request.getSession();
    LoggedUsers lU = LoggedUsers.getInstance();

    String hash = (String) ses.getAttribute("hash");

    if (!lU.compareRights(hash, RightEnum.USER)) {
        response.sendRedirect("/LoginSL");
    }

%>
<!-- Implements the navigation bar in the webseite -->
<div id="navbar"></div>
<div id="container">
    <br><br>
    <!--  Shows the title of the page -->
    <div class="voteTitle">
        <h1>
            <%
                PollData pd = (PollData) request.getSession().getAttribute("poll");
                out.print(pd.getTitle());
            %>
        </h1>
    </div>
    <br>
    <!--  Shows the question of the vote -->
    <div class="voteQuestion">
        <h3>Bitte wählen Sie einen der folgenen Anworten aus. </h3>
    </div>
    <br>
    <form method="post" action="/PollSL">
        <%
            int counter = 1;
            LinkedList<PollAnswer> pollAnswers = pd.getAnswerList();
            for (PollAnswer pa : pollAnswers) {
                out.println("<ul class=\"list-group\">");
                out.println("    <li class=\"list-group-item\">");
                out.println("        <div class=\"radio\">");
                out.println("            <label><input type=\"radio\" name=\"optradio\" value=\"" + counter++ + "\">"
                        + pa.getDescription() + "</label>");
                out.println("        </div>");
                out.println("    </li>");
                out.println("</ul>");
            }
        %>
        <div class="input-group">
            <button id="evaluation" type="submit" class="btn btn-primary">
                <span class="glyphicon glyphicon-ok"></span> Stimme abgeben
            </button>
        </div>
    </form>


</div>
<footer class="footer">
    <div class="container text-center">
        <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam HTBLA Kaindorf</p>
    </div>
</footer>
</body>
</html>
