<%@ page import="user.LoggedUsers" %>
<%@ page import="beans.RightEnum" %>
<%--
 Author:          Ewald Hartmann
 Created on:
 Description:     represents the time if an election or poll did not start yet
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
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
    <link rel="stylesheet" type="text/css" href="css/DefaultCSS.css">
    <!-- Set Tab picture -->
    <link rel="icon" type="image/png" href="res/Avatar.png">
    <!-- Import the JavaScript of  Navbar -->
    <script src="js/NavbarLogedUser.js"></script>
</head>
<body>
<%
    HttpSession httpSession = request.getSession();
    LoggedUsers loggedUsers = LoggedUsers.getInstance();

    String hash = (String) httpSession.getAttribute("hash");

    if (!loggedUsers.compareRights(hash, RightEnum.USER)) {
        response.sendRedirect("/LoginSL");
    }
%>
<div id="container">
    <div id="title">Die ausgewählte Wahl von Ihnen hat noch nicht begonnen.</div>
</div>
<center>
    <footer class="footer">
        <div class="container text-center">
            <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam Democracy 2.0</p>
        </div>
    </footer>
</center>

</body>
</html>
