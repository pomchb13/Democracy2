<%@ page import="user.LoggedUsers" %>
<%@ page import="beans.RightEnum" %>
<%@ page import="user.LoggedUsers" %>
<%--
  Created by IntelliJ IDEA.
  User: Leonhard
  Date: 23.02.2018
  Time: 14:00
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
    <!-- Import the JAvascript to use DropDownMenus in Bootstrap -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <!-- Import the JavaScript of  Bootstrap -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- Import the CSS of  Bootstrap Datepicker -->
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.min.css"/>
    <!-- Import the CSS of  Bootstrap Datepicker 3-->
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css"/>
    <!-- Import the JavaScript of  Bootstrap Datepicker -->
    <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>
    <!-- Import toggleNewVote -->
    <script type="text/javascript" src="js/toggleNewVote.js"></script>
    <!-- Import addAnswersNewVote -->
    <script type="text/javascript" src="js/AddAnswersNewVote.js"></script>
    <!-- Import defaultDate -->
    <script type="text/javascript" src="js/defaultDate.js"></script>
    <!-- Import the default CSS -->
    <link rel="stylesheet" type="text/css" href="css/DefaultCSS.css">
    <!-- Set Tab picture -->
    <link rel="icon" type="image/png" href="res/Avatar.png">
    <!-- Import datepicker -->
    <script type="text/javascript" src="js/datepicker.js"></script>
    <!-- Import Javascript function to add Candidates -->
    <script type="text/javascript" src="js/addCandidate.js"></script>
    <!-- Import JavaScript to view the selected DropDownMenuItem -->
    <!-- <script src="js/viewDropdownItem.js"></script> -->
    <!-- Import the JavaScript of  Navbar -->
    <script src="js/NavbarAdmin.js"></script>
</head>
<body>
<%

    HttpSession ses = request.getSession();
    LoggedUsers lU = LoggedUsers.getInstance();

    String hash = (String) ses.getAttribute("hash");


    if (!lU.compareRights(hash, RightEnum.ADMIN)) {
        response.sendRedirect("/LoginSL");
    }

%>
<!-- Implements the navigation bar in the webseite -->
<div id="navbar"></div>
<br><br>
<div id="container">
    <br>
    <h1>Upload von Dateien</h1>
    <p>bevorzugestes Dateiformat: 35*45</p>
    <center>
        <form method="POST" action="/UploadImageSL" enctype="multipart/form-data">
            <div class="input-group">
                <span class="input-group-addon">Portrait</span>
                <input id="inputPicture" type="file" name="input_Picture" class="form-control"
                       placeholder="Bild">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div><br>
            <div class="submitButton">
                <button type="submit" id="upload" class="btn btn-primary" name="Upload"
                        value="uploadPicture"><span
                        class="glyphicon glyphicon-floppy-disk"></span> Bild hochladen
                </button>
            </div>
        </form>
        <%=  request.getAttribute("status") != null ? request.getAttribute("status") : ""  %>
    </center>
</div>

</body>
</html>
