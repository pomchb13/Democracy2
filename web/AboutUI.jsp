<%--
 Author:          Ewald Hartmann
 Created on:
 Description:     represents the creator
--%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ page pageEncoding="UTF-8" %>
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
    <div class="table-responsive">
        <table class="table" style="text-align: center">
            <tbody>
            <tr>
                <td>
                    <div style="width: 200px;margin: 0 auto;">
                        <img src="res/Gangl.jpg" class="img-responsive" alt="Cinque Terre" width="250" height="250">
                    </div>
                </td>
                <td>
                    <div style="width: 200px;margin: 0 auto;">
                        <img src="res/Hartmann.jpg" class="img-responsive" alt="Cinque Terre" width="250" height="250">
                    </div>
                </td>
                <td>
                    <div style="width: 200px;margin: 0 auto;">
                        <img src="res/Pommer.jpg" class="img-responsive" alt="Cinque Terre" width="250" height="250">
                    </div>
                </td>
                <td>
                    <div style="width:  200px;margin: 0 auto;">
                        <img src="res/Windegger.jpg" class="img-responsive" alt="Cinque Terre" width="250" height="250">
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <!-- Name and description of the member  -->
                    <h4 class="media-heading"><b>Gangl</b> Leonhard</h4>
                    <p>90BHIF</p>
                    <p>Java/Frontend</p>
                </td>
                <td>
                    <!-- Name and description of the member  -->
                    <h4 class="media-heading"><b>Hartmann</b> Ewald</h4>
                    <p>90BHIF</p>
                    <p>Java/Frontend</p>
                </td>
                <td>
                    <!-- Name and description of the member  -->
                    <h4 class="media-heading"><b>Pommer</b> Christoph</h4>
                    <p>90BHIF</p>
                    <p>Blockchain/Backend</p>
                </td>
                <td>
                    <!-- Name and description of the member  -->
                    <h4 class="media-heading"><b>Windegger</b> Patrick </h4>
                    <p>90BHIF</p>
                    <p>Projektleiter</p>
                    <p>Blockchain/Backend</p>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<br>
<br>
<br>
<br>
<footer class="footer">
    <div class="container text-center">
        <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam Democracy 2.0</p>
    </div>
</footer>
</body>
</html>
