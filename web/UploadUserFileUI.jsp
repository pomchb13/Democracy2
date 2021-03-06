<%@ page import="user.LoggedUsers" %>
<%@ page import="beans.RightEnum" %>
<%@ page import="java.io.File" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.nio.file.Path" %>
<%@ page import="java.nio.file.Paths" %>
<%--
 Author:          Ewald Hartmann
 Created on:
 Description:     represents user upload
--%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
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
    <script type="text/javascript" src="js/NavbarAdminDownload.js"></script>
</head>
<body>
<!-- Implements the navigation bar in the webseite -->
<div id="navbar"></div>
<br><br>
<%
    HttpSession httpSession = request.getSession();
    LoggedUsers loggedUsers = LoggedUsers.getInstance();

    String hash = (String) httpSession.getAttribute("hash");

    if (!loggedUsers.compareRights(hash, RightEnum.ADMIN)) {
        response.sendRedirect("/LoginSL");
    }

%>

<div id="container">
    <br>
    <h1>Generator der Userkeys</h1>
    <p>Anzahl der zugelassenen Wähler</p>
    <center>
        <form method="POST" action="/UploadUserFileSL">
            <!-- input field for typing in the number of eligible voters -->
            <div class="input-group">
                <span class="input-group-addon">Anzahl</span>
                <input id="titelVote" type="text" name="input_Count_Voter" class="form-control farm-control-sm"
                       placeholder="1">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>
            <!-- Button to create the .xlsx file -->
            <div class="submitButton">
                <button type="submit" id="upload" class="btn btn-primary" name="Upload"
                        value="uploadFile"><span
                        class="glyphicon glyphicon-floppy-disk"></span> Bestätigen
                </button>
            </div>
        </form>
        <!-- Shows the status after trying to create the .xlsx file. -->
        <%=  request.getAttribute("status") != null ? request.getAttribute("status") : ""  %>

        <br><br>
        <%
            //Inline code which shows a download button and a forward button, if the .xlsx file is generated successfully
            if (request.getAttribute("newPath") != null) {
                File file = new File((String) request.getAttribute("newPath"));
                if (file != null) {

                    out.print(" <a href=\" res/userLists/userlist.xlsx\" download=\"userlist.xlsx\"> ");
                    out.print(" <button type=\"button\" id=\"download\" class=\"btn btn-primary\" name=\"download\"\n" +
                            "                        value=\"downloadFile\"><span\n" +
                            "                        class=\"glyphicon glyphicon-cloud-download\"></span> Usernames mit Passwörter herunterladen\n" +
                            "   </button>");
                    out.print("</a>");
                    out.print("<br><br>\n" +
                            "        <form method=\"post\" action=\"/AdminSettingsSL\">\n" +
                            "            <button type=\"submit\" id=\"forward\" class=\"btn btn-primary\" name=\"forward\"\n" +
                            "                    value=\"forwardToAdminSite\"> Weiter zur Admin-Seite\n" +
                            " <span class=\"glyphicon glyphicon-arrow-right\"></span>"+
                            "            </button>\n" +
                            "        </form>");
                }
            }
        %>
    </center>
</div>
<footer class="footer">
    <div class="container text-center">
        <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam Democracy 2.0</p>
    </div>
</footer>

</body>
</html>
