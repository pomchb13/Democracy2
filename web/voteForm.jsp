<%--
  Created by IntelliJ IDEA.
  User: ganleb13
  Date: 20.07.2017
  Time: 10:29
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
    <script src="js/navbarAdmin.js"></script>

    <title>Neue Wahl</title>
</head>
<body>
<!-- Implements the navigation bar in the webseite -->
<div id="navbar"></div>
<div id="container">
    <br><br>

        <h1>Wahl Form</h1>

        <div class="newVote">
            <!-- Field to add a titel -->
            <div class="input-group">
                <span class="input-group-addon">Title</span>
                <input id="titel" type="text" class="form-control" name="titel" placeholder="Titel einfügen">
                <span class="input-group-addon">*</span>
            </div>
            <br>
            <!-- Field to add the startdate -->
            <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar">  Von</span></span>
                <input id="start" type="date" class="form-control" name="start" placeholder="Startdatum einfügen">
                <span class="input-group-addon">*</span>
            </div>
            <br>
            <!-- Field to add the enddate -->
            <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar">  Bis</span></span>
                <input id="end" type="date" class="form-control" name="end" placeholder="Enddatum einfügen">
                <span class="input-group-addon">*</span>
            </div>
            <br>

            <table width="100%">
                <tr>
                    <td>
                        <div class="input-group">
                            <span class="input-group-addon">Antwort #1</span>
                            <textarea id="antwort1" type="text" class="form-control" rows="3"
                                      placeholder="Antwort 1 eingeben"> </textarea>
                            <span class="input-group-addon">*</span>
                        </div>
                    </td>
                    <td>
                        <div class="input-group">
                            <span class="input-group-addon">Antwort #2</span>
                            <textarea id="antwort2" type="text" class="form-control" rows="3"
                                      placeholder="Antwort 2 eingeben"> </textarea>
                            <span class="input-group-addon">*</span>
                        </div>
                    </td>
                </tr>
            </table>
            <br>
            <button type="button" class="btn btn-primary">Wahl eröffnen</button>
        </div>


</div>
<footer class="footer">
    <div class="container text-center">
        <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam HTBLA Kaindorf</p>
    </div>
</footer>
</body>
</html>
