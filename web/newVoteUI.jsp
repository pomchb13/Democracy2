<%--
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
    <!-- Import toggleNewVote -->
    <script type="text/javascript" src="js/toggleNewVote.js"></script>
    <!-- Import addAnswersNewVote -->
    <script type="text/javascript" src="js/addAnswersNewVote.js"></script>
    <!-- Import the default CSS -->
    <link rel="stylesheet" type="text/css" href="css/defaultCSS.css">
    <!-- Set Tab picture -->
    <link rel="icon" type="image/png" href="res/avatar.png">
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
<center>
    <!-- Title of the page -->
    <div class="newVoteTitle">
        <h1>Neue Abstimmung erstellen</h1>
    </div>
    <div class="btn-group" data-toggle="buttons">
        <label class="btn btn-primary active">
            <input type="radio" checked autocomplete="off"> Abstimmung
        </label>
        <label class="btn btn-primary">
            <input type="radio" autocomplete="off"> Wahl
        </label>
    </div>
</center>
<div class="newVote" id="referendum">
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
    <center>
        <button type="button" class="add_form_field btn btn-primary">Neue Antwort hinzufügen <span
                class="glyphicon glyphicon-plus"></span>
        </button>
    </center>
    <br>

    <div class="answerDiv">
        <div class="input-group">
            <span class="input-group-addon">Antwort #1</span>
            <textarea id="antwort1" type="text" class="form-control" rows="3"> </textarea>
            <span class="input-group-addon">*</span>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">Antwort #2</span>
            <textarea id="antwort2" type="text" class="form-control" rows="3"> </textarea>
            <span class="input-group-addon">*</span>
        </div>
        <br>
    </div>
    <!-- Add a submit button -->
    <center>
        <div class="submitButton">
            <button id="submitButton" type="button" class="btn btn-primary"><span
                    class="glyphicon glyphicon-floppy-disk"></span> Speichern
            </button>
        </div>
    </center>
</div>
<center>
    <div class="newVote" id="vote">
        <!-- Field to add a titel -->
        <div class="input-group">
            <span class="input-group-addon">Title</span>
            <input id="titelVote" type="text" class="form-control farm-control-sm" placeholder="Titel einfügen">
            <span class="input-group-addon">*</span>
        </div>
        <br>
        <!-- Field to add the startdate -->
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar">  Von</span></span>
            <input id="startVote" type="date" class="form-control farm-control-sm" placeholder="Startdatum einfügen">
            <span class="input-group-addon">*</span>
        </div>
        <br>
        <!-- Field to add the enddate -->
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar">  Bis</span></span>
            <input id="endVote" type="date" class="form-control farm-control-sm" placeholder="Enddatum einfügen">
            <span class="input-group-addon">*</span>
        </div>
        <br>
        <div class="addedCand">
            <!-- Muss mit dem JSP Java Gedöns eingedönscht werden --> <p> Bereits hinzugefügt Dr. Dr. Dr. Dr. Gerhard
            Guggerbauer</p>
        </div>
        <div class="candDiv">
            <div class="input-group">
                <span class="input-group-addon">Titel</span>
                <input id="inputTitle" type="text" class="form-control" placeholder="Titel">
                <span class="input-group-addon">*</span>
            </div>
            <br>
            <div class="input-group">
                <span class="input-group-addon">Vorname</span>
                <input id="inputVorname" type="text" class="form-control" placeholder="Vorname">
                <span class="input-group-addon">*</span>
            </div>
            <br>
            <div class="input-group">
                <span class="input-group-addon">Nachname</span>
                <input id="inputNachname" type="text" class="form-control" placeholder="Nachname">
                <span class="input-group-addon">*</span>
            </div>
            <br>
            <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar">  Geburtsdatum</span></span>
                <input id="inputBirthday" type="date" class="form-control" placeholder="tt.mm.jjjj">
                <span class="input-group-addon">*</span>
            </div>
            <br>
            <div class="input-group">
                <span class="input-group-addon">Partei</span>
                <input id="inputPartei" type="text" class="form-control" placeholder="Partei">
                <span class="input-group-addon">*</span>
            </div>
            <br>
            <div class="input-group">
                <span class="input-group-addon">Motto</span>
                <input id="inputMotto" type="text" class="form-control" placeholder="Motto">
                <span class="input-group-addon">*</span>
            </div>
            <br>
            <div class="submitButton">
                <button id="addCand" type="button" class="btn btn-primary"><span
                        class="glyphicon glyphicon-floppy-disk"></span> Kandidaten/Partei hinzufügen
                </button>
            </div>
            <br>

        </div>

        <div class="submitButton">
            <button id="saveVote" type="button" class="btn btn-primary"><span
                    class="glyphicon glyphicon-floppy-disk"></span> Wahl eröffnen
            </button>
        </div>

    </div>
</center>

</body>
</html>
