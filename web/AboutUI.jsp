<%--
  Created by IntelliJ IDEA.
  User: Ewald
  Date: 11.07.2017
  Time: 17:55
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
    <script src="js/NavbarNotLogedUser.js"></script>
</head>
<body>
<!-- Implements the navigation bar in the webseite -->
<div id="navbar"></div>
<div class="container">
    <br>
    <br>
    <!-- Title of the page -->
    <div class="aboutTitle">
        <h1>Über uns</h1>
    </div>

    <!-- Over text of the page -->
    <p>Im folgenden Abschnitt wird die Diplomarbeitsgruppe vorgestellt, die diese Wahlplattform erstellt haben.
        <br> Der Auftraggeber für diese Wahlplattform ist die Firma BearingPoint.
    </p>
    <br>
    <!-- Info about the teammembers-->
    <div class="media">
        <div class="media-left">
            <!-- Add a picture -->
            <img src="res/Windegger.jpg" class="media-object" style="width:150px">
        </div>
        <div class="media-body">
            <!-- Name and description of the member  -->
            <h4 class="media-heading"><b>Windegger</b> Patrick </h4>
            <p>90BHIF</p><br>
            <p>Projektleiter</p>
            <p>Blockchain/Backend</p>
        </div>
    </div>
    <hr>
    <!-- Info about the teammembers -->
    <div class="media">
        <div class="media-left media-middle">
            <!-- Add a picture -->
            <img src="res/Pommer.jpg" class="media-object" style="width:150px">
        </div>
        <div class="media-body" >
            <!-- Name and description of the member  -->
            <h4 class="media-heading"><b>Pommer</b> Christoph</h4>
            <p>90BHIF</p><br>
            <p>Blockchain/Backend</p>
        </div>
    </div>
    <hr>
    <!-- Info about the teammembers -->
    <div class="media">
        <div class="media-left media-middle">
            <!-- Add a picture -->
            <img src="res/Hartmann.jpg" class="media-object" style="width:150px">
        </div>
        <div class="media-body">
            <!-- Name and description of the member  -->
            <h4 class="media-heading"><b>Hartmann</b> Ewald</h4>
            <p>90BHIF</p><br>
            <p>Java/Frontend</p>
        </div>
    </div>
    <hr>
    <!-- Info about the teammembers -->
    <div class="media">
        <div class="media-left media-middle">
            <!-- Add a picture -->
            <img src="res/Gangl.jpg" class="media-object" style="width:150px">
        </div>
        <div class="media-body">
            <!-- Name and description of the member  -->
            <h4 class="media-heading"><b>Gangl</b> Leonhard</h4>
            <p>90BHIF</p><br>
            <p>Java/Frontend</p>
        </div>
    </div>
</div>
<br>
<br>
<br>
<br>
<footer class="footer">
    <div class="container text-center">
        <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam HTBLA Kaindorf</p>
    </div>
</footer>
</body>
</html>
