<%--
  Created by IntelliJ IDEA.
  User: Ewald
  Date: 12.07.2017
  Time: 17:45
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
    <!-- Import the JavaScript for the login -->
    <script language="javascript" type="text/javascript" src="js/loginJS.js"></script>
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
                <li><a href="loginUI.jsp"><span class="glyphicon glyphicon-list-alt"></span> Volksabstimmung / Wahl</a></li>
                <li><a href="aboutUI.jsp"><span class="glyphicon glyphicon-user"></span> Über Uns</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="adminUI.jsp"><span class="glyphicon glyphicon-log-in"></span> Login für Admins</a></li>
            </ul>
        </div>
    </div>
</nav>
<center>
    <br>
    <br>
    <br>
    <!-- Title of the page -->
    <div class="thankYouTitle">
        <h1>Danke für Ihre Beteiligung an der Wahl!</h1>
    </div>
    <br>
    <br>
    <!-- Success Text -->
    <div class="alert alert-success" id="Success">
        <strong>Erfolgreich!</strong> <br> Ihre Stimme wurde in unser System aufgenommen.
    </div>
    <!-- Button for the evaluationpage -->
    <div class="input-group">
        <button id="evaluation" type="submit" class="btn btn-primary"
                onclick="window.location.href='/evaluationBarChartUI.jsp'">
            <span class="glyphicon glyphicon-time"></span> Derzeitiger Stand
        </button>
    </div>
</center>
</body>
</html>