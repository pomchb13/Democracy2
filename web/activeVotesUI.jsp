<%--
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
<nav class="navbar navbar-inverse">
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
                <li><a href="activeVotesUI.jsp"><span class="glyphicon glyphicon-th-list"></span> Aktive Wahlen</a></li>
                <li><a href="newVoteUI.jsp"><span class="glyphicon glyphicon-plus"></span> Neue Wahl erstellen</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="adminUI.jsp"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
            </ul>
        </div>
    </div>
</nav>
<center>
    <!-- Title of the page -->
    <div class="titleActivVote">
        <h1>Alle beendeten und aktiven Wahlen auf einem Blick</h1>
    </div>
</center>
<!-- Shows all activ and finished Votes -->
<div class="activeVotes">
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Titel</th>
            <th>Startdatum</th>
            <th>Enddatum</th>
            <th>Zweck</th>
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
            <td>Ermittlung eines neunen Schulersprechers  für die Schule HTBLA Kaindorf</td>
            <td>beendet</td>
        </tr>
        </tbody>
    </table>
</div>
</div>
</body>
</html>