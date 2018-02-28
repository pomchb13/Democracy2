<%--
  User: Patrick Windegger
  Date: 06.07.2017
  Time: 18:23
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
    <!-- Import login js -->
    <script lang="JavaScript" type="text/javascript" src="js/login.js"></script>
    <!-- Import the default CSS -->
    <link rel="stylesheet" type="text/css" href="css/DefaultCSS.css">
    <!-- Set Tab picture -->
    <link rel="icon" type="image/png" href="res/Avatar.png">
    <!-- Import the JavaScript of  Navbar -->
    <script src="js/NavbarNotLogedUser.js"></script>
</head>
<body>
<!-- Implements the navigation bar in the webseite -->
<div id="navbar"></div>

<div id="container">
    <br><br>

        <!-- Title of the page -->
        <div class="heading">
            <br>
            <h1>Willkommen bei Democracy 2.0</h1>
        </div>


    <form action="/loginSL" method="post">
        <!-- Add the username, the password and submit button -->
        <div class="loginText">
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input id="username" type="text" class="form-control" name="username" placeholder="Username">
            </div>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input id="password" type="password" class="form-control" name="password" placeholder="Password">
            </div>
            <br>
            <center>
                <%=  request.getAttribute("error") != null ? request.getAttribute("error") : ""  %>
                <div class="submitButton">
                    <button type="submit" id="evaluation" class="btn btn-primary">
                        <span class="glyphicon glyphicon-log-in"></span>
                        Login
                    </button>
                </div>
            </center>


        </div>
        </center>
    </form>
</div>
<footer class="footer">
    <div class="container text-center">
        <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam HTBLA Kaindorf</p>
    </div>
</footer>
</body>
</html>
