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
                <li><a href="adminSettingsUI.jsp"><span class="glyphicon glyphicon-home"></span> Startseite</a></li>
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
        <h1 id="voteTitle">Neue Abstimmung / Wahl erstellen</h1>
    </div>
    <div class="btn-group">
        <button type="button" class="btn btn-primary" id="pollID">Abstimmung</button>
        <button type="button" class="btn btn-primary" id="voteID">Wahl</button>
    </div>
</center>

<!-- Add a new Referendum-->
<div class="newRef" id="newRefDivID">
    <!-- Field to add a titel -->
    <div class="input-group">
        <span class="input-group-addon">Title</span>
        <input id="titelRef" type="text" class="form-control" placeholder="Titel einfügen">
        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
    </div>
    <br>
    <center>

        <!-- Field to add the startdate and enddate -->
        <div class="container">
            <div class="input-daterange" id="datepicker1">
                <span class="label label-default">von</span>
                <input type="text" class="input" name="start"/>
                <span class="label label-default">bis</span>
                <input type="text" class="input" name="end"/>
            </div>
        </div>
        <br>

        <!-- Button to add a new Answerfield -->

        <button type="button" class="add_form_field btn btn-primary">Neue Antwort hinzufügen <span
                class="glyphicon glyphicon-plus"></span>
        </button>
    </center>
    <br>

    <!-- Answerfields -->
    <div class="answerDiv">
        <div class="input-group">
            <span class="input-group-addon">Antwort #1</span>
            <textarea id="antwort1" type="text" class="form-control" rows="3"> </textarea>
            <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">Antwort #2</span>
            <textarea id="antwort2" type="text" class="form-control" rows="3"> </textarea>
            <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
        </div>
        <br>
    </div>

    <!-- Add a Dropdown Menu for the evaluation Picker-->
    <div class="evaluationPicker">
        <select class="form-control">
            <option>Balkendiagramm anzeigen</option>
            <option>Kreisdiagramm anzeigen</option>
            <option>Beide Auswertungen anzeigen</option>
            <option>keine verzeitige Auswertung anzeigen</option>
        </select>
    </div>
    <br>

    <!-- Add a submit button -->
    <center>
        <div class="submitButton">
            <button id="submitButton" type="button" class="btn btn-primary"><span
                    class="glyphicon glyphicon-floppy-disk"></span> Abstimmung eröffnen
            </button>
        </div>
    </center>
</div>

<!-- Add a new Vote-->
<div class="newVote" id="newVoteDivID" style="display:none;">
    <center>

        <!-- Field to add a titel -->
        <div class="input-group">
            <span class="input-group-addon">Title</span>
            <input id="titelVote" type="text" class="form-control farm-control-sm" placeholder="Titel einfügen">
            <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
        </div>
        <br>

        <!-- Field to add the startdate -->
        <div class="container">
            <div class="input-daterange" id="datepicker2">
                <span class="label label-default">von</span>
                <input type="text" class="input" name="start"/>
                <span class="label label-default">bis</span>
                <input type="text" class="input" name="end"/>
            </div>
        </div>
        <br>

        <!-- Show added Candidates -->
        <div class="addedCand">
            <!-- Muss mit dem JSP Java Gedöns eingedönscht werden --> <p> Bereits hinzugefügt Dr. Dr. Dr. Dr. Gerhard
            Guggerbauer</p>
        </div>

        <!-- Add a new Candidate -->
        <div class="candDiv">

            <!-- Field to add his/her titel -->
            <div class="input-group">
                <span class="input-group-addon">Titel</span>
                <input id="inputTitle" type="text" class="form-control" placeholder="Titel">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>

            <!-- Field to add his/her forename -->
            <div class="input-group">
                <span class="input-group-addon">Vorname</span>
                <input id="inputVorname" type="text" class="form-control" placeholder="Vorname">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>

            <!-- Field to add his/her last name -->
            <div class="input-group">
                <span class="input-group-addon">Nachname</span>
                <input id="inputNachname" type="text" class="form-control" placeholder="Nachname">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>

            <!-- Field to add his/her birthday -->
            <div class="input-group">
                <span class="input-group-addon">Geburtsdatum</span>
                <input id="inputBirthday" type="date" class="form-control" onkeydown="return false">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>

            <!-- Field to add his/her party -->
            <div class="input-group">
                <span class="input-group-addon">Partei</span>
                <input id="inputPartei" type="text" class="form-control" placeholder="Partei">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>

            <!-- Field to add his/her slogan -->
            <div class="input-group">
                <span class="input-group-addon">Motto</span>
                <input id="inputMotto" type="text" class="form-control" placeholder="Motto">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>

            <!-- Field to add a foto from him/her-->
            <div class="input-group">
                <span class="input-group-addon">Foto</span>
                <input id="inputFoto" type="file" class="form-control"></span>
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>

            <!-- Button to add the Candidate -->
            <div class="submitButton">
                <button id="addCand" type="button" class="btn btn-primary"><span
                        class="glyphicon glyphicon-floppy-disk"></span> Kandidaten/Partei hinzufügen
                </button>
            </div>
        </div>

        <!-- Add a Dropdown Menu for the evaluation Picker-->
        <div class="evaluationPicker">
            <select class="form-control">
                <option>Balkendiagramm anzeigen</option>
                <option>Kreisdiagramm anzeigen</option>
                <option>Beide Auswertungen anzeigen</option>
                <option>keine vorzeitige Auswertung anzeigen</option>
            </select>
        </div>
        <br>

        <!-- Button to publish the vote -->
        <div class="submitButton">
            <button id="saveVote" type="button" class="btn btn-primary"><span
                    class="glyphicon glyphicon-floppy-disk"></span> Wahl eröffnen
            </button>
        </div>
    </center>
</div>
</body>
</html>
