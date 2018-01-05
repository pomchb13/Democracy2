<%@ page import="java.util.LinkedList" %>
<%@ page import="beans.Vote" %>
<%@ page import="beans.Poll" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="beans.Politician" %>
<%@ page import="beans.PollAnswer" %><%--
  Created by IntelliJ IDEA.
  User: Ewald
  Date: 11.07.2017
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="DefaultCSS.css" rel="stylesheet" type="text/css">
    <title>Democracy 2.0</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/defaultCSS.css">
    <link rel="icon" type="image/png" href="res/avatar.png">
</head>
<body>
<!-- Implements the navigation bar in the webseite -->
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="">Democracy 2.0</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li><a href="adminSettingsUI.jsp"><span class="glyphicon glyphicon-home"></span> Startseite</a></li>
                <li><a href="activeVotesUI.jsp"><span class="glyphicon glyphicon-th-list"></span> Aktive
                    Wahlen/Abstimmung</a></li>
                <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href=""><span
                        class="glyphicon glyphicon-plus"></span> Neue Wahl/Abstimmung erstellen</a>
                    <ul class="dropdown-menu">
                        <li><a href="newPollUI.jsp">Neue Abstimmung</a></li>
                        <li><a href="newVoteUI.jsp">Neue Wahl</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="adminUI.jsp"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
            </ul>
        </div>
    </div>
</nav>
<br>
<br>
<br>
<center>
    <!-- Title of the page -->
    <div class="titleActivVote">
        <h1>Alle beendeten und aktiven Wahlen auf einem Blick</h1>
        <%
            LinkedList<Vote> voteList = (LinkedList<Vote>) this.getServletConfig().getServletContext().getAttribute("voteList");
            LinkedList<Poll> pollList = (LinkedList<Poll>) this.getServletConfig().getServletContext().getAttribute("pollList");
            if (voteList != null && voteList.size() != 0) {
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

                for (Vote v : voteList) {
                    if ((LocalDate.now().isEqual(v.getDate_due()) || LocalDate.now().equals(v.getDate_from())) ||
                            (LocalDate.now().isBefore(v.getDate_due()) && LocalDate.now().isAfter(v.getDate_from()))) {
                        out.print("<tr class='active'>");
                        out.print("<td>" + v.getTitle() + "</td>\n" +
                                "<td>" + v.getDate_from() + "</td>\n" +
                                "<td>" + v.getDate_due() + "</td>\n" +
                                "<td>");
                        for (Politician p : v.getLiCandidates()) {
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
                        for (Politician p : v.getLiCandidates()) {
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
                        for (Politician p : v.getLiCandidates()) {
                            out.print(p.getForename() + " " + p.getSurname() + " | ");
                        }
                        out.print("</td>" +
                                "<td>abgehalten</td>\n" +
                                "</tr>");
                    }

                }
            }

                if (pollList != null && pollList.size() != 0) {
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

                    for (Poll v : pollList) {
                        if ((LocalDate.now().isEqual(v.getDate_due()) || LocalDate.now().equals(v.getDate_from())) ||
                                (LocalDate.now().isBefore(v.getDate_due()) && LocalDate.now().isAfter(v.getDate_from()))) {
                            out.print("<tr class='active'>");
                            out.print("<td>"+v.getTitle()+"</td>\n" +
                                    "<td>"+v.getDate_from()+"</td>\n" +
                                    "<td>"+v.getDate_due()+"</td>\n"+
                                    "<td>");
                            for (PollAnswer p:v.getAnswerList()) {
                                out.print(p.getTitle() +" | ");
                            }
                            out.print("</td>" +
                                    "<td>aktiv</td>\n" +
                                    "</tr>");
                        } else if (LocalDate.now().isBefore(v.getDate_from())) {
                            out.print("<tr class='inactive'>");
                            out.print("<td>"+v.getTitle()+"</td>\n" +
                                    "<td>"+v.getDate_from()+"</td>\n" +
                                    "<td>"+v.getDate_due()+"</td>\n"+
                                    "<td>");
                            for (PollAnswer p:v.getAnswerList()) {
                                out.print(p.getTitle() + " | ");
                            }
                            out.print("</td>" +
                                    "<td>inaktiv</td>\n" +
                                    "</tr>");
                        } else {
                            out.print("<tr class='success'>");
                            out.print("<td>"+v.getTitle()+"</td>\n" +
                                    "<td>"+v.getDate_from()+"</td>\n" +
                                    "<td>"+v.getDate_due()+"</td>\n"+
                                    "<td>");
                            for (PollAnswer p:v.getAnswerList()) {
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
</center>
<!-- Shows all activ and finished Votes -->
<div class="activeVotes">


</div>
</div>
</body>
<!--
<table class="table table-hover">
<thead>
<tr>
<th>Titel</th>
<th>Startdatum</th>
<th>Enddatum</th>
<th>Antworten</th>
<th>Status</th>
</tr>
</thead>
<tbody>
<tr class="active">
<td>Bundespräsidentenwahl</td>
<td>11.07.2017</td>
<td>13.07.2017</td>
<td>Ermittlung eines neunen Präsidenten für Österreich</td>
<td>aktiv</td>
</tr>
<tr class="success">
<td>Schulsprecher</td>
<td>03.02.2017</td>
<td>04.02.2017</td>
<td>Ermittlung eines neunen Schulersprechers für die Schule HTBLA Kaindorf</td>
<td>beendet</td>
</tr>
</tbody>
</table> -->
</html>

