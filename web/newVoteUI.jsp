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
    <!-- Change the Tab-title of the page -->
    <title>Democracy 2.0</title>
    <!-- Import the CSS of Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- Import the JavaScript of AJAX -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- Import the JavaScript of  Bootstrap -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- Import the default CSS -->
    <link rel="stylesheet" type="text/css" href="css/defaultCSS.css">
    <!-- Set Tab picture -->
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
                <li><a href="homeUI.jsp"><span class="glyphicon glyphicon-home"></span> Startseite</a></li>
                <li><a href="loginUI.jsp"><span class="glyphicon glyphicon-list-alt"></span> Wahl</a></li>
                <li><a href="aboutUI.jsp"><span class="glyphicon glyphicon-user"></span> Über Uns</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="adminUI.jsp"><span class="glyphicon glyphicon-log-in"></span> Login für Admins</a></li>
            </ul>
        </div>
    </div>
</nav>
<center>
    <!-- Title of the page -->
    <div class="newVoteTitle">
        <h1>Neue Wahl erstellen</h1>
    </div>
</center>
<div class="newVote">
    <!-- Feld to add a titel -->
    <div class="input-group">
        <span class="input-group-addon">Title</span>
        <input id="titel" type="text" class="form-control" name="titel" placeholder="Titel einfügen">
        <span class="input-group-addon">*</span>
    </div>
    <br>
    <!-- Feld to add the startdate -->
    <div class="input-group">
        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
        <input id="start" type="date" class="form-control" name="start" placeholder="Startdatum einfügen">
        <span class="input-group-addon">*</span>
    </div>
    <br>
    <!-- Feld to add the enddate -->
    <div class="input-group">
        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
        <input id="end" type="date" class="form-control" name="end" placeholder="Enddatum einfügen">
        <span class="input-group-addon">*</span>
    </div>
    <br>
    <!-- Feld to add a description -->
    <div class="input-group">
        <span class="input-group-addon">Beschreibung</span>
        <textarea id="description" type="text" class="form-control" name="description" placeholder="Beschreibung einfügen" rows="5"></textarea>
    </div>
    <br>
    <!-- Add a submit button -->
    <div class="submitButton">
        <button id="submitButton" type="button" class="btn btn-primary">Speichern</button>
    </div>
</div>

</body>
</html>
