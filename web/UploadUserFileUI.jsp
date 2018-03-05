<%@ page import="user.LoggedUsers" %>
<%@ page import="beans.RightEnum" %><%--
  Created by IntelliJ IDEA.
  User: Leonhard
  Date: 05.03.2018
  Time: 15:13
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
    <script type="text/javascript" src="js/NavbarAdmin.js"></script>
</head>
<body>
<!-- Implements the navigation bar in the webseite -->
<div id="navbar"></div>
<br><br>
<%

    HttpSession ses = request.getSession();
    LoggedUsers lU = LoggedUsers.getInstance();

    String hash = (String) ses.getAttribute("hash");


    if (!lU.compareRights(hash, RightEnum.ADMIN)) {
        response.sendRedirect("/LoginSL");
    }

%>

<div id="container">
    <br>
    <h1>Upload des Wählerverzeichnisses</h1>
    <p>benötigter Dateityp: xls, xlsx</p>
    <center>
        <form method="POST" action="/UploadUserFileSL" enctype="multipart/form-data">
            <div class="input-group">
                <span class="input-group-addon">Wählerverzeichnis</span>
                <input id="inputExcel" type="file" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                       name="input_Excel" class="form-control" placeholder="Excel-Datei">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div><br>
            <div class="submitButton">
                <button type="submit" id="upload" class="btn btn-primary" name="Upload"
                        value="uploadFile"><span
                        class="glyphicon glyphicon-floppy-disk"></span> Wählerverzeichnis hochladen
                </button>
            </div>
        </form>
        <%=  request.getAttribute("status") != null ? request.getAttribute("status") : ""  %>

        <a href="<%= this.getServletConfig().getServletContext().getAttribute("newPath") != null
        ? this.getServletConfig().getServletContext().getAttribute("newPath"):""%>" download="userList">
            <button type="button" id="download" class="btn btn-primary" name="download">Excel-Daten herunterladen</button>
        </a>
    </center>
</div>


</body>
</html>
