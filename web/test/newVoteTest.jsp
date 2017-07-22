<%--
  Created by IntelliJ IDEA.
  User: Ewald
  Date: 22.07.2017
  Time: 09:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <!-- Change the Tab-title of the page -->
    <title>Democracy 2.0</title>
    <!-- Import the CSS of Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- Import the JavaScript of AJAX -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- Import the JavaScript of  Bootstrap -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- Import the JavaScript for the login -->
    <!-- <script language="javascript" type="text/javascript" src="js/loginJS.js"></script> -->
    <!-- Import toggleVote js -->
    <script lang="JavaScript" type="text/javascript" src="../js/toggleNewVote.js"></script>
    <!-- Import addAnswer js -->
    <script lang="JavaScript" type="text/javascript" src="../js/addAnswersNewVote.js"></script>
    <!-- Import the Default CSS -->
    <link rel="stylesheet" type="text/css" href="../css/defaultCSS.css">
    <!-- Set Tab picture -->
    <link rel="icon" type="image/png" href="../res/avatar.png">


</head>
<body>
<!-- Implements the navigation bar in the webseite -->
<nav class="navbar navbar-inverse">
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
                <li><a href="adminUI.jsp"><span class="glyphicon glyphicon-home"></span> Startseite</a></li>
                <li><a href="activeVotesUI.jsp"><span class="glyphicon glyphicon-th-list"></span> Aktive Wahlen</a></li>
                <li><a href="newVoteUI.jsp"><span class="glyphicon glyphicon-plus"></span> Neue Wahl erstellen</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="adminUI.jsp"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
            </ul>
        </div>
    </div>
</nav>
<!-- Title of the page -->
<center>
    <!-- Title of the page -->
    <div class="newVoteTitle">
        <h1>Neue Abstimmung erstellen</h1>
    </div>
    <div class="btn-group" role="group">
        <button type="button" checked autocomplete="off" class="btn btn-primary" id="pollRBID"> Abstimmung</button>
        <button type="button" autocomplete="off" class="btn btn-primary" id="voteRBID"> Wahl</button>
    </div>
</center>
<hr>
<br>
<center>
    <div class="newPollDiv" id="newPollDivID">
        <!-- Field to add a titel -->
        <div class="input-group">
            <span class="input-group-addon">Title</span>
            <input id="titelRef" type="text" class="form-control" placeholder="Titel einfügen">
            <span class="input-group-addon">*</span>
        </div>
        <br>
        <!-- Field to add the startdate -->
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar">  Von</span></span>
            <input id="startRef" type="date" class="form-control" placeholder="Startdatum einfügen">
            <span class="input-group-addon">*</span>
        </div>
        <br>
        <!-- Field to add the enddate -->
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar">  Bis</span></span>
            <input id="endRef" type="date" class="form-control" placeholder="Enddatum einfügen">
            <span class="input-group-addon">*</span>
        </div>
        <br>
        <button type="button" class="add_form_field btn btn-primary">Neue Antwort hinzufügen <span
                class="glyphicon glyphicon-plus"></span>
        </button>
        <br>
        <br>
        <div class="container1">
            <div><input type="text" name="mytext[]" placeholder="Antwort"><a href="#" class="delete"><span
                    class="glyphicon glyphicon-remove"></span></a></div>
            <div><input type="text" name="mytext[]" placeholder="Antwort"><a href="#" class="delete"><span
                    class="glyphicon glyphicon-remove"></span></a></div>
        </div>
    </div>
</center>

<div class="newVoteDiv" id="newVoteDivID">
    <br>
    <!-- Add a submit button -->
    <hr>
    <center>
        <div class="submitButton">
            <button id="submitButton" type="button" class="btn btn-primary"><span
                    class="glyphicon glyphicon-floppy-disk"></span> Speichern
            </button>
        </div>
    </center>

</div>
</body>
</html>
