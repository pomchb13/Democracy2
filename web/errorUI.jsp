<%--
  Created by IntelliJ IDEA.
  User: Ewald
  Date: 12.07.2017
  Time: 18:06
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
    <!-- Import the JavaScript of  Navbar -->
    <script src="js/navbarUser.js"></script>
</head>
<body>
<!-- Implements the navigation bar in the webseite -->
<div id="navbar"></div>
<center>
    <div id="container">
        <br><br>
        <!-- Title of the page -->
        <div class="thankYouTitle">
            <h1> Ein Fehler ist aufgetreten! </h1>
        </div>
        <br>
        <br>
        <!-- Error text -->
        <div class="alert alert-danger" id="Error">
            <strong>Achtung!</strong> <br> Beim Eintragen Ihrer Stimme ist ein Fehler aufgetreten. <br><br> Bitte melden
            Sie
            sich schnellstmöglichst bei Ihrem Gemeindeamt, damit der Fehler behoben werden kann. <br> Danke für Ihr
            Verständnis!
        </div>
    </div>
</center>
<footer class="footer">
    <div class="container text-center">
        <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam HTBLA Kaindorf</p>
    </div>
</footer>
</body>
</html>

