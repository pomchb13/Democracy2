<%@ page import="java.util.LinkedList" %>
<%@ page import="beans.Poll" %>
<%@ page import="beans.RightEnum" %>
<%@ page import="user.loggedUsers" %><%--
  Created by IntelliJ IDEA.
  User: Ewald
  Date: 11.07.2017
  Time: 20:31
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
    <!-- Import the JavaScript of  Navbar -->
    <script src="js/NavbarAdmin.js"></script>
</head>
<body>
<%
    HttpSession ses = request.getSession();
    loggedUsers lU = loggedUsers.getInstance();

    String hash = (String) ses.getAttribute("hash");

    if (!lU.compareRights(hash, RightEnum.ADMIN)) {
        response.sendRedirect("/LoginSL");
    }

%>
<!-- Implements the navigation bar in the webseite -->

<div id="navbar"></div>

<div id="container">
    <br><br>
    <!-- Add a new Referendum-->
    <div class="newRef" id="newRefDivID">
        <h1>Neue Abstimmung erstellen</h1>
        <br><br>
        <form id="form1" action="/newPollSL" method="post">
            <!-- Field to add a titel -->
            <div class="input-group">
                <span class="input-group-addon">Title</span>
                <input id="titelRef" name="input_Title" type="text" class="form-control" placeholder="Titel einfügen">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>
            <!-- Field to add the startdate and enddate -->
            <div class="data">
                <div class="input-group date" data-provide="datepicker">
                    <span class="input-group-addon">Von</span>
                    <input type="text" class="form-control" name="input_Start">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-th"></span>
                    </div>
                </div>
                <br>
                <div class="input-group date" data-provide="datepicker">
                    <span class="input-group-addon">Bis</span>
                    <input type="text" class="form-control" name="input_End">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-th"></span>
                    </div>
                </div>
            </div>
            <br>
            <!-- Add two Radio Buttons for the evaluation Picker-->
            <div class="evaluationPicker">
                <div class="radio">
                    <label><input type="radio" name="input_DiaOption" value="1" checked>Diagramm anzeigen</label>
                </div>
                <div class="radio">
                    <label><input type="radio" name="input_DiaOption" value="2">keine Diagramme anzeigen</label>
                </div>
            </div>
            <br>
            <br>
            <!-- If an error occurs it will be shown over the button -->
            <%=  request.getAttribute("PollError") != null ? request.getAttribute("PollError") : ""  %>

            <!-- Add a submit button -->
            <!-- SUBMIT OF KING -->
            <div class="submitButton">
                <button id="submitButton" type="submit" form="form1" name="actionButton" value="createReferendum"
                        class="btn btn-primary"><span
                        class="glyphicon glyphicon-floppy-disk"></span> Abstimmung eröffnen
                </button>
            </div>
        </form>
        <br>
        <hr>
        <br>
        <div>
            <h1>Kanditaten zur <%= this.getServletConfig().getServletContext().getAttribute("pollList") != null ?
                    ((Poll) this.getServletConfig().getServletContext().getAttribute("pollList")).getTitle() : ""%>
                hinzufügen</h1></div>
        <form id="form2" action="/newPollSL" method="post">
            <br>
            <br>
            <div class="answerDiv">
                <div class="input-group">
                    <span class="input-group-addon">Antworttitel</span>
                    <input id="answerTitle" type="text" class="form-control" name="input_AnswerTitle"
                           placeholder="Titel"> </input>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                </div>
                <br> <br>
                <div class="input-group">
                    <span class="input-group-addon">Antwortbeschreibung</span>
                    <textarea id="answer" type="text" class="form-control" name="input_Answer" rows="6"
                              placeholder="Beschreibung"> </textarea>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                </div>
            </div>
            <%=  request.getAttribute("pollAnswerError") != null ? request.getAttribute("pollAnswerError") : ""  %>
            <br>
            <div class="submitButton">
                <button id="addAnswerButton" type="submit" form="form2" name="actionButton" value="addAnswer"
                        class="btn btn-primary"><span
                        class="glyphicon glyphicon-floppy-disk"></span> Antwort hinzufügen
                </button>
            </div>
        </form>
        <div class="submitButton">
            <a href="AdminSettingsUI.jsp">
                <button type="button" class="btn btn-primary" name="actionButton"
                        value="forward"> Weiter zur Admin Seite <span
                        class="glyphicon glyphicon-arrow-right"></span>
                </button>
            </a>

        </div>
        <br><br><br>
    </div>
</div>
<footer class="footer">
    <div class="container text-center">
        <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam HTBLA Kaindorf</p>
    </div>
</footer>
</body>
<!--
<script>
    $("#tableMenu a").click(function (e) {
        e.preventDefault(); // cancel the link behaviour
        var selText = $(this).text();
        alert(selText);
        document.getElementById('hiddenPoll').value = selText;
        document.getElementById('tableButton').innerHTML = selText;

    });
</script>-->
</html>
