<%@ page import="user.LoggedUsers" %>
<%@ page import="beans.RightEnum" %>
<%@ page import="user.LoggedUsers" %>
<%--
 Author:          Ewald Hartmann
 Created on:
 Description:     represents the image upload
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
    <!-- Import the JAvascript to use DropDownMenus in Bootstrap -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <!-- Import the JavaScript of  Bootstrap -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- Import the default CSS -->
    <link rel="stylesheet" type="text/css" href="css/DefaultCSS.css">
    <!-- Set Tab picture -->
    <link rel="icon" type="image/png" href="res/Avatar.png">
    <!-- Import the JavaScript of  Navbar -->
    <script src="js/NavbarAdmin.js"></script>
</head>
<body>
<%

    HttpSession httpSession = request.getSession();
    LoggedUsers loggedUsers = LoggedUsers.getInstance();

    String hash = (String) httpSession.getAttribute("hash");

    if (!loggedUsers.compareRights(hash, RightEnum.ADMIN)) {
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
        <form method="POST" action="/UploadImageSL" enctype="multipart/form-data" onsubmit="return validation(this)">
            <div class="input-group">
                <span class="input-group-addon">Portrait</span>
                <input id="inputPicture" type="file" name="input_Picture" class="form-control"
                       placeholder="Bild" accept="image/*">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>
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
<footer class="footer">
    <div class="container text-center">
        <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam Democracy 2.0</p>
    </div>
</footer>
</body>
<script>
    function validation(thisform) {
        with (thisform) {
            if (validateFileExtension(file, "valid_msg", "pdf/office/image files are only allowed!",
                    new Array("jpg", "jpeg", "png")) == false) {
                return false;
            }
            if (validateFileSize(file, 1048576, "valid_msg", "Document size should be less than 1MB !") == false) {
                return false;
            }
        }
    }

    function validateFileExtension(component, msg_id, msg, extns) {
        var flag = 0;
        with (component) {
            var ext = value.substring(value.lastIndexOf('.') + 1);
            for (i = 0; i < extns.length; i++) {
                if (ext == extns[i]) {
                    flag = 0;
                    break;
                }
                else {
                    flag = 1;
                }
            }
            if (flag != 0) {
                document.getElementById(msg_id).innerHTML = msg;
                component.value = "";
                component.style.backgroundColor = "#eab1b1";
                component.style.border = "thin solid #000000";
                component.focus();
                return false;
            }
            else {
                return true;
            }
        }
    }

    function validateFileSize(component, maxSize, msg_id, msg) {
        if (navigator.appName == "Microsoft Internet Explorer") {
            if (component.value) {
                var oas = new ActiveXObject("Scripting.FileSystemObject");
                var e = oas.getFile(component.value);
                var size = e.size;
            }
        }
        else {
            if (component.files[0] != undefined) {
                size = component.files[0].size;
            }
        }
        if (size != undefined && size > maxSize) {
            document.getElementById(msg_id).innerHTML = msg;
            component.value = "";
            component.style.backgroundColor = "#eab1b1";
            component.style.border = "thin solid #000000";
            component.focus();
            return false;
        }
        else {
            return true;
        }
    }
</script>
</html>
