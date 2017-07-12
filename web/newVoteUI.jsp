<%--
  Created by IntelliJ IDEA.
  User: Ewald
  Date: 11.07.2017
  Time: 20:31
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
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Democracy 2.0</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li><a href="adminSettingsUI.jsp"><span class="glyphicon glyphicon-home"></span> Startseite</a></li>
                <li><a href="activeVotesUI.jsp"><span class="glyphicon glyphicon-time"></span> Aktive Wahlen</a></li>
                <li><a href="newVoteUI.jsp"><span class="glyphicon glyphicon-plus"></span> Neue Wahl</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="homeUI.jsp"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
            </ul>
        </div>
    </div>
</nav>
<center>
    <div class="newVoteTitle">
        <h1>Neue Wahl erstellen</h1>
    </div>
</center>
<div class="newVote">
    <div class="input-group">
        <span class="input-group-addon">Title</span>
        <input id="titel" type="text" class="form-control" name="titel" placeholder="Titel einfügen">
        <span class="input-group-addon">*</span>
    </div>
    <br>
    <div class="input-group">
        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
        <input id="start" type="date" class="form-control" name="start" placeholder="Startdatum einfügen">
        <span class="input-group-addon">*</span>
    </div>
    <br>
    <div class="input-group">
        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
        <input id="end" type="date" class="form-control" name="end" placeholder="Enddatum einfügen">
        <span class="input-group-addon">*</span>
    </div>
    <br>
    <div class="input-group">
        <span class="input-group-addon">Beschreibung</span>
        <textarea id="description" type="text" class="form-control" name="description" placeholder="Beschreibung einfügen" rows="5"></textarea>
    </div>
    <br>
    <div class="submitButton">
        <button id="submitButton" type="button" class="btn btn-primary">Speichern</button>
    </div>
</div>

</body>
</html>
