<%@ page import="java.util.LinkedList" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="user.LoggedUsers" %>
<%@ page import="beans.*" %>
<%@ page import="user.LoggedUsers" %><%--
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
    <!-- Import the JavaScript of  Bootstrap -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- Import the default CSS -->
    <link rel="stylesheet" type="text/css" href="css/DefaultCSS.css">
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
        <!-- Title of the page -->
        <div class="titleActivVote">
            <h1>Alle beendeten und aktiven Wahlen auf einem Blick</h1>
            <%
                LinkedList<ElectionData> electionDataList = (LinkedList<ElectionData>) this.getServletConfig().getServletContext().getAttribute("electionDataList");
                LinkedList<PollData> pollDataList = (LinkedList<PollData>) this.getServletConfig().getServletContext().getAttribute("pollDataList");
                if (electionDataList != null && electionDataList.size() != 0) {
                    out.println("<h3> Alle Wahlen </h3>");
                    out.print("<table class=\"table table-hover\">\n" +
                            "    <thead>\n" +
                            "    <tr>\n" +
                            "        <th>Titel</th>\n" +
                            "        <th>Startdatum</th>\n" +
                            "        <th>Enddatum</th>\n" +
                            "        <th>Politiker</th>\n" +
                            "        <th>Status</th>\n" +
                            "    </tr>\n" +
                            "    </thead>\n" +
                            "    <tbody>");

                    for (ElectionData v : electionDataList) {
                        if ((LocalDate.now().isEqual(v.getDate_due()) || LocalDate.now().equals(v.getDate_from())) ||
                                (LocalDate.now().isBefore(v.getDate_due()) && LocalDate.now().isAfter(v.getDate_from()))) {
                            out.print("<tr class='active'>");
                            out.print("<td>" + v.getTitle() + "</td>\n" +
                                    "<td>" + v.getDate_from() + "</td>\n" +
                                    "<td>" + v.getDate_due() + "</td>\n" +
                                    "<td>");
                            for (CandidateData p : v.getLiCandidates()) {
                                out.print(p.getForename() + " " + p.getSurname() + " | ");
                            }
                            out.print("</td>" +
                                    "<td>aktiv</td>\n" +
                                    "</tr>");
                        } else if (LocalDate.now().isBefore(v.getDate_from())) {
                            out.print("<tr class='inactive'>");
                            out.print("<td>" + v.getTitle() + "</td>\n" +
                                    "<td>" + v.getDate_from() + "</td>\n" +
                                    "<td>" + v.getDate_due() + "</td>\n" +
                                    "<td>");
                            for (CandidateData p : v.getLiCandidates()) {
                                out.print(p.getForename() + " " + p.getSurname() + " | ");
                            }
                            out.print("</td>" +
                                    "<td>inaktiv</td>\n" +
                                    "</tr>");
                        } else {
                            out.print("<tr class='success'>");
                            out.print("<td>" + v.getTitle() + "</td>\n" +
                                    "<td>" + v.getDate_from() + "</td>\n" +
                                    "<td>" + v.getDate_due() + "</td>\n" +
                                    "<td>");
                            for (CandidateData p : v.getLiCandidates()) {
                                out.print(p.getForename() + " " + p.getSurname() + " | ");
                            }
                            out.print("</td>" +
                                    "<td>abgehalten</td>\n" +
                                    "</tr>");
                        }

                    }
                }

                if (pollDataList != null && pollDataList.size() != 0) {
                    out.println("<h3> Alle Abstimmungen </h3>");
                    out.print("<table class=\"table table-hover\">\n" +
                            "    <thead>\n" +
                            "    <tr>\n" +
                            "        <th>Titel</th>\n" +
                            "        <th>Startdatum</th>\n" +
                            "        <th>Enddatum</th>\n" +
                            "        <th>Wahlmöglichkeiten</th>\n" +
                            "        <th>Status</th>\n" +
                            "    </tr>\n" +
                            "    </thead>\n" +
                            "    <tbody>");

                    for (PollData v : pollDataList) {
                        if ((LocalDate.now().isEqual(v.getDate_due()) || LocalDate.now().equals(v.getDate_from())) ||
                                (LocalDate.now().isBefore(v.getDate_due()) && LocalDate.now().isAfter(v.getDate_from()))) {
                            out.print("<tr class='active'>");
                            out.print("<td>" + v.getTitle() + "</td>\n" +
                                    "<td>" + v.getDate_from() + "</td>\n" +
                                    "<td>" + v.getDate_due() + "</td>\n" +
                                    "<td>");
                            for (PollAnswer p : v.getAnswerList()) {
                                out.print(p.getTitle() + " | ");
                            }
                            out.print("</td>" +
                                    "<td>aktiv</td>\n" +
                                    "</tr>");
                        } else if (LocalDate.now().isBefore(v.getDate_from())) {
                            out.print("<tr class='inactive'>");
                            out.print("<td>" + v.getTitle() + "</td>\n" +
                                    "<td>" + v.getDate_from() + "</td>\n" +
                                    "<td>" + v.getDate_due() + "</td>\n" +
                                    "<td>");
                            for (PollAnswer p : v.getAnswerList()) {
                                out.print(p.getTitle() + " | ");
                            }
                            out.print("</td>" +
                                    "<td>inaktiv</td>\n" +
                                    "</tr>");
                        } else {
                            out.print("<tr class='success'>");
                            out.print("<td>" + v.getTitle() + "</td>\n" +
                                    "<td>" + v.getDate_from() + "</td>\n" +
                                    "<td>" + v.getDate_due() + "</td>\n" +
                                    "<td>");
                            for (PollAnswer p : v.getAnswerList()) {
                                out.print(p.getTitle() + " | ");
                            }
                            out.print("</td>" +
                                    "<td>abgehalten</td>\n" +
                                    "</tr>");
                        }

                    }


                }
            %>
        </div>
    <!-- Implement the footer -->
    <footer class="footer">
        <div class="container text-center">
            <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam HTBLA Kaindorf</p>
        </div>
    </footer>
</div>
</body>
</html>
