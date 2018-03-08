<%@ page import="user.LoggedUsers" %>
<%@ page import="beans.RightEnum" %>
<%@ page import="java.io.File" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.nio.file.Path" %>
<%@ page import="java.nio.file.Paths" %><%--
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
    <!-- Import the default CSS -->
    <link rel="stylesheet" type="text/css" href="css/DefaultCSS.css">
    <!-- Set Tab picture -->
    <link rel="icon" type="image/png" href="res/Avatar.png">
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
    <h1></h1>Wählerverzeichnisses</h1>
    <p>Anzahl der zugelassernen Wählerx</p>
    <center>
        <form method="POST" action="/UploadUserFileSL" enctype="multipart/form-data">
            <div class="input-group">
                <span class="input-group-addon">Anzahl</span>
                <input id="titelVote" type="text" name="input_Count_Voter" class="form-control farm-control-sm"
                       placeholder="1">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>
            <div class="submitButton">
                <button type="submit" id="upload" class="btn btn-primary" name="Upload"
                        value="uploadFile"><span
                        class="glyphicon glyphicon-floppy-disk"></span> Bestätigen
                </button>
            </div>
        </form>
        <%=  request.getAttribute("status") != null ? request.getAttribute("status") : ""  %>

        <br><br>
        <%
            if (((String) this.getServletConfig().getServletContext().getAttribute("newPath")) != null) {
                File f = new File((String) this.getServletConfig().getServletContext().getAttribute("newPath"));
                if (f != null) {
                    out.print(" <a href=\" res/userLists/userlist.xlsx\" download=\" userlist.xlsx\"> ");
                    out.print(" <button type=\"button\" id=\"download\" class=\"btn btn-primary\" name=\"download\"\n" +
                            "                        value=\"downloadFile\"><span\n" +
                            "                        class=\"glyphicon glyphicon-cloud-download\"></span> Wählerverzeichnis mit Einlogdaten runterladen\n" +
                            "                </button>");
                    out.print("</a>");
                }
            }
        %>

        <br><br>
        <form method="post" action="AdminSettingsSL">
            <button type="submit" id="forward" class="btn btn-primary" name="forward"
                    value="forwardToAdminSite"><span
                    class="glyphicon glyphicon-floppy-disk"></span> Weiter zur Admin-Seite
            </button>
        </form>
    </center>
</div>
</body>
</html>
