<%@ page import="java.util.LinkedList" %>
<%@ page import="beans.Poll" %><%--
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
    <script type="text/javascript" src="js/addAnswersNewVote.js"></script>
    <!-- Import defaultDate -->
    <script type="text/javascript" src="js/defaultDate.js"></script>
    <!-- Import the default CSS -->
    <link rel="stylesheet" type="text/css" href="css/defaultCSS.css">
    <!-- Set Tab picture -->
    <link rel="icon" type="image/png" href="res/avatar.png">
    <!-- Import datepicker -->
    <script type="text/javascript" src="js/datepicker.js"></script>
</head>
<body>
<!-- Implements the navigation bar in the webseite -->
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="">Democracy 2.0</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li><a href="adminSettingsUI.jsp"><span class="glyphicon glyphicon-home"></span> Startseite</a></li>
                <li><a href="activeVotesUI.jsp"><span class="glyphicon glyphicon-th-list"></span> Aktive
                    Wahlen/Abstimmung</a></li>
                <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href=""><span
                        class="glyphicon glyphicon-plus"></span> Neue Wahl/Abstimmung erstellen</a>
                    <ul class="dropdown-menu">
                        <li><a href="newPollUI.jsp">Neue Abstimmung</a></li>
                        <li><a href="newVoteUI.jsp">Neue Wahl</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="adminUI.jsp"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
            </ul>
        </div>
    </div>
</nav>
<br>
<br>
<br>

<!-- Add a new Referendum-->
<div class="newRef" id="newRefDivID">
    <h1>Neue Abstimmung erstellen</h1>
    <br><br>
    <form id="newRef" action="/newPollSL" method="post">
        <!-- Field to add a titel -->
        <div class="input-group">
            <span class="input-group-addon">Title</span>
            <input id="titelRef" name="input_Title" type="text" class="form-control" placeholder="Titel einfügen">
            <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
        </div>
        <br>
        <center>

            <!-- Field to add the startdate and enddate -->
            <div class="container">
                <div class="input-daterange" id="datepicker1">
                    <span class="label label-default">von</span>
                    <input type="text" name="input_Start" class="input" name="start"/>
                    <span class="label label-default">bis</span>
                    <input type="text" name="input_End" class="input" name="end"/>
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
        </center>
        <br>
        <br>
        <center>
            <!-- If an error occurs it will be shown over the button -->
            <%=  request.getAttribute("PollError") != null ? request.getAttribute("PollError") : ""  %>

            <!-- Add a submit button -->
            <!-- SUBMIT OF KING -->
            <div class="submitButton">
                <button id="submitButton" type="submit" form="newRef" name="actionButton" value="createReferendum"
                        class="btn btn-primary"><span
                        class="glyphicon glyphicon-floppy-disk"></span> Abstimmung eröffnen
                </button>
            </div>
        </center>
    </form>

    <br>
    <hr>
    <br>

    <h1>Antworten hinzufügen</h1>
    <form action="/addAnswerToPollSL" method="post">

        <div id="tableDiv" class="dropdown">
            <center>
                <button id="tableButton" name="pollTitle" class="btn btn-primary dropdown-toggle" type="button"
                        data-toggle="dropdown">
                    Bitte Abstimmung auswählen
                    <span class="caret"></span>
                </button>
                <ul id="tableMenu" class="dropdown-menu">
                    <%
                        LinkedList<Poll> liListe = (LinkedList<Poll>) this.getServletConfig().getServletContext().getAttribute("pollList");
                        if (liListe != null) {
                            for (Poll p : liListe) {
                                out.print("<li><a href='#'>" + p.getTitle() + "</a></li>");
                            }
                        }
                    %>
                </ul>
            </center>
        </div>
        <br>
        <br>
        <center>
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
                <button id="addAnswerButton" type="submit" form="newRef" name="actionButton" value="addAnswer"
                        class="btn btn-primary"><span
                        class="glyphicon glyphicon-floppy-disk"></span> Antwort hinzufügen
                </button>
            </div>

        </center>
    </form>
    <center>
        <div class="submitButton">
            <a href="adminSettingsUI.jsp">
                <button type="button" class="btn btn-primary" name="forwardButton"
                        value="forward"> Weiter zur Admin Seite <span class="glyphicon glyphicon-arrow-right"></span>
                </button>
            </a>

        </div>
    </center>

</div>
</body>

<script>
    $("#tableMenu a").click(function (e) {
        e.preventDefault(); // cancel the link behaviour
        var selText = $(this).text();
        $("#tableButton").text(selText);
        $("#tableButton").value(selText);
    });
</script>
</html>
