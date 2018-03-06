<%@ page import="java.util.LinkedList" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="user.LoggedUsers" %>
<%@ page import="beans.*" %>
<%@ page import="user.LoggedUsers" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: Ewald
  Date: 11.07.2017
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Change the Tab-title of the page -->
    <title>Democracy 2.0</title>
    <!-- Import the CSS of Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- Import the JavaScript of JQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- Import the JAvascript to use DropDownMenus in Bootstrap -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <!-- Import the JavaScript of  Bootstrap -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- Import the default CSS -->
    <link rel="stylesheet" type="text/css" href="css/DefaultCSS.css">
    <!-- Set Tab picture -->
    <link rel="icon" type="image/png" href="res/Avatar.png">
    <!-- Set Tab picture -->
    <link rel="icon" type="image/png" href="res/Avatar.png">
    <!-- Import the JavaScript of Navbar -->
    <script src="js/NavbarAdmin.js"></script>

</head>
<body>
<%
    HttpSession ses = request.getSession();
    LoggedUsers lU = LoggedUsers.getInstance();

    String hash = (String) ses.getAttribute("hash");

    if (!lU.compareRights(hash, RightEnum.ADMIN)) {
        response.sendRedirect("/LoginSL");
    }

%>
<!-- Implements the navigation bar in the webseite -->
<div id="navbar"></div>
<div class="container">
    <br>
    <br>
    <%
        LinkedList<PollData> liPollList = new LinkedList<>();
        LinkedList<ElectionData> liElectionList = new LinkedList<>();


    %>

    <!-- Title of the page -->
    <div class="titleActivVote">
        <h1>Alle beendeten und aktiven Wahlen auf einem Blick</h1>
    </div>
    <div class="showTable">
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Von</th>
                    <th>Bis</th>
                    <th>Abgegebene Stimmen</th>
                    <th>Zum Diagramm</th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (PollData pd:liPollList) {
                        out.println("<tr>");
                        out.println("<td>"+pd.getTitle()+"</td>");
                        out.println("<td>"+pd.getDate_from().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))+"</td>");
                        out.println("<td>"+pd.getDate_due().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))+"</td>");
                        int count = 0;
                        for (PollAnswer pa:pd.getAnswerList()) {
                            count += pa.getVoteCount();
                        }
                        out.println("<td>"+count+"</td>");
                        out.println("");

                    }
                %>


                </tbody>
            </table>
        </div>
    </div>


</div>

<br><br>
<!-- Implement the footer -->
<footer class="footer">
    <div class="container text-center">
        <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam HTBLA Kaindorf</p>
    </div>
</footer>

</body>
</html>

