<%@ page import="java.util.LinkedList" %>
<%@ page import="beans.Vote" %><%--
  Created by IntelliJ IDEA.
  User: Leonhard
  Date: 28.11.2017
  Time: 18:40
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
    <script type="text/javascript" src="js/viewDropdownItem.js"></script>

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
<div class="newVote" id="newVoteDivID">
    <center>
        <h1>Neue Wahl erstllen</h1>
        <br><br>
        <form id="form1" method="post">
            <!-- Field to add a title -->
            <div class="input-group">
                <span class="input-group-addon">Title</span>
                <input id="titelVote" type="text" name="input_Title" class="form-control farm-control-sm"
                       placeholder="Titel einfügen">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>

            <!-- Field to add the startdate and enddate -->
            <div class="container">
                <div class="input-daterange" id="datepicker2">
                    <span class="label label-default">von</span>
                    <input type="text" name="input_Start" class="input"/>
                    <span class="label label-default">bis</span>
                    <input type="text" name="input_End" class="input"/>
                </div>
            </div>
            <br>

            <!-- Add two Radio Buttons for the evaluation Picker-->
            <div class="evaluationPicker">
                <div class="evaluationPicker">
                    <div class="radio">
                        <label><input type="radio" name="input_DiaOption" value="1" checked>Diagramm anzeigen</label>
                    </div>
                    <div class="radio">
                        <label><input type="radio" name="input_DiaOption" value="2">keine Diagramme anzeigen</label>
                    </div>
                </div>
            </div>


            <!-- Button to publish the vote -->
            <div class="submitButton">
                <button id="saveVote" type="submit" name="actionButton" value="createVote" class="btn btn-primary"><span
                        class="glyphicon glyphicon-floppy-disk"></span> Wahl eröffnen
                </button>
            </div>
        </form>
        <!-- Show added Candidates
        <div class="addedCand">
            <p name="input_Cands"></p>
        </div> -->

        <br>
        <hr>
        <br>

        <!-- Default dropup button -->
        <div id="tableDiv" class="dropdown">
            <button id="tableButton" class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                Table
                <span class="caret"></span>
            </button>
            <ul id="tableMenu" class="dropdown-menu">
                <li><a href="#">Att1</a></li>
                <li><a href="#">Att2</a></li>
                <li><a href="#">Att3</a></li>
            </ul>
        </div>
        <!-- Add a new Candidate -->
        <div class="candDiv1">

            <!-- Show votes to whom the candidate should be added -->

            <%
                /*LinkedList<Vote> liListe = (LinkedList<Vote>) this.getServletConfig().getServletContext().getAttribute("voteList");
                if (liListe != null)
                {
                    for (Vote v:liListe) {
                        out.print("<li><a href='#'>"+v.getTitle()+"</a></li>");
                    }
                }*/
            %>




            <!-- Field to add his/her titel -->
            <div class="input-group">
                <span class="input-group-addon">Titel</span>
                <input id="inputTitle" type="text" name="input_cand_Title" class="form-control" placeholder="Titel">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>

            <!-- Field to add his/her forename -->
            <div class="input-group">
                <span class="input-group-addon">Vorname</span>
                <input id="inputVorname" type="text" name="input_cand_Firstname" class="form-control"
                       placeholder="Vorname">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>

            <!-- Field to add his/her last name -->
            <div class="input-group">
                <span class="input-group-addon">Nachname</span>
                <input id="inputNachname" type="text" name="input_cand_Lastname" class="form-control"
                       placeholder="Nachname">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>

            <!-- Field to add his/her birthday -->
            <div class="input-group">
                <span class="input-group-addon">Geburtsdatum</span>
                <input id="inputBirthday" type="date" name="input_cand_Birthday" class="form-control">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>

            <!-- Field to add his/her party -->
            <div class="input-group">
                <span class="input-group-addon">Partei</span>
                <input id="inputPartei" type="text" name="input_cand_Party" class="form-control" placeholder="Partei">

            </div>
            <br>

            <!-- Field to add his/her slogan -->
            <div class="input-group">
                <span class="input-group-addon">Motto</span>
                <input id="inputMotto" type="text" name="input_cand_Slogan" class="form-control" placeholder="Motto">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>

            <!-- Field to add a picture from him/her-->
            <div class="input-group">
                <span class="input-group-addon">Foto</span>
                <input id="inputFoto" type="file" name="input_cand_Picture" class="form-control"
                       accept="image/*" enctype="multipart/form-data"></span>
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>

            <!-- Button to add the Candidate -->
            <div class="submitButton">
                <button id="addCand" class="btn btn-primary" name="actionButton"
                        value="addPolitician" onclick="addCand()"><span
                        class="glyphicon glyphicon-floppy-disk"></span> Kandidaten/Partei hinzufügen
                </button>

            </div>
        </div>

        <br>

    </center>
</div>


</body>
</html>
