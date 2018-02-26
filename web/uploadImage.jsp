<%@ page import="user.loggedUsers" %>
<%@ page import="beans.RightEnum" %><%--
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
    <script type="text/javascript" src="js/addAnswersNewVote.js"></script>
    <!-- Import defaultDate -->
    <script type="text/javascript" src="js/defaultDate.js"></script>
    <!-- Import the default CSS -->
    <link rel="stylesheet" type="text/css" href="css/defaultCSS.css">
    <!-- Set Tab picture -->
    <link rel="icon" type="image/png" href="res/avatar.png">
    <!-- Import datepicker -->
    <script type="text/javascript" src="js/datepicker.js"></script>
    <!-- Import Javascript function to add Candidates -->
    <script type="text/javascript" src="js/addCandidate.js"></script>
    <!-- Import JavaScript to view the selected DropDownMenuItem -->
    <!-- <script src="js/viewDropdownItem.js"></script> -->
    <!-- Import the JavaScript of  Navbar -->
    <script src="js/navbarAdmin.js"></script>

    <?php include 'php/dateiupload.php'; ?>


</head>
<body>
<%

    HttpSession ses = request.getSession();
    loggedUsers lU = loggedUsers.getInstance();

    String hash = (String) ses.getAttribute("hash");


    if (!lU.compareRights(hash, RightEnum.ADMIN)) {
        response.sendRedirect("/loginSL");
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
            <input type="file" name="file" id="file"/> <br/>
            <input type="submit" value="Upload" name="upload" id="upload"/>
        </form>
        <%=  request.getAttribute("status") != null ? request.getAttribute("status") : ""  %>
    </center>
</div>

</body>
</html>
