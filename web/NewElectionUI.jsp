<%@ page import="java.util.LinkedList" %>
<%@ page import="beans.ElectionData" %>
<%@ page import="beans.RightEnum" %>
<%@ page import="user.LoggedUsers" %><%--
 Author:          Ewald Hartmann
 Created on:
 Description:     represents the jsp where an admin can create a new election
--%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ page pageEncoding="UTF-8"%>
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
    LoggedUsers loggesUser = LoggedUsers.getInstance();

    String hash = (String) httpSession.getAttribute("hash");

    if (!loggesUser.compareRights(hash, RightEnum.ADMIN)) {
        response.sendRedirect("/LoginSL");
    }
%>
<!-- Implements the navigation bar in the webseite -->
<div id="navbar"></div>
<br><br>
<div id="container">
    <div class="newElectionData" id="newVoteDivID">

        <h1>Neue Wahl erstellen</h1>
        <br><br>
        <form action="/NewElectionSL" id="electionForm" method="post">
            <!-- Field to add a title -->
            <div class="input-group">
                <span class="input-group-addon">Titel</span>
                <input id="titelVote" type="text" name="input_Title" class="form-control farm-control-sm"
                       placeholder="Titel einfügen">
                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
            </div>
            <br>

            <!-- Field to add the startdate and enddate -->
            <div class="date">
                <div class="input-group date" data-provide="datepicker">
                    <span class="input-group-addon">Von</span>
                    <input type="text" class="form-control" name="input_Start">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-th"></span>
                    </div>
                </div>
                <br>
                <div class="input-group date" data-provide="datepicker">
                    <span class="input-group-addon">Bis </span>
                    <input type="text" class="form-control" name="input_End">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-th"></span>
                    </div>
                </div>

            </div>
            <br>

            <!-- Add two Radio Buttons for the evaluation Picker-->
            <div class="evaluationPicker">
                <div class="evaluationPicker">
                    <div class="radio">
                        <label><input type="radio" name="input_DiaOption" value="1" checked>Diagramm
                            anzeigen</label>
                    </div>
                    <div class="radio">
                        <label><input type="radio" name="input_DiaOption" value="2">keine Diagramme anzeigen</label>
                    </div>
                </div>
            </div>
            <p id="error"><%=  request.getAttribute("statusVote") != null ? request.getAttribute("statusVote") : ""  %>
            </p>


            <!-- Button to publish the vote -->
            <div class="submitButton">
                <button id="saveVote" type="submit" name="actionButton" value="createVote"
                        class="btn btn-primary"><span
                        class="glyphicon glyphicon-floppy-disk"></span> Wahl eröffnen
                </button>
            </div>
        </form>

        <br>
        <hr>
        <br>

        <div>
            <h1>Kanditat zur Wahl "<%= request.getSession().getAttribute("newElection") != null ?
                    ((ElectionData) request.getSession().getAttribute("newElection")).getTitle() : ""%>" hinzufügen</h1></div>

        <!-- Add a new Candidate -->
        <div class="candDiv">
            <form action="/NewElectionSL" id="candidateForm" method="post">
                <!-- Default dropup button -->
                <!--<input id="ElectionDataOld" type="text" name="hiddenVote" class="form-control" hidden>-->
                <br><br>
                <!-- Field to add his/her titel -->
                <div class="input-group">
                    <span class="input-group-addon">Titel</span>
                    <input id="inputTitle" type="text" name="input_cand_Title" class="form-control"
                           placeholder="Titel">
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
                <div class="input-group date" data-provide="datepicker">
                    <span class="input-group-addon">Geburtsdatum </span>
                    <input type="text" class="form-control" name="input_cand_Birthday">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-th"></span>
                    </div>
                </div>
                <br>

                <!-- Field to add his/her party -->
                <div class="input-group">
                    <span class="input-group-addon">Partei</span>
                    <input id="inputPartei" type="text" name="input_cand_Party" class="form-control"
                           placeholder="Partei">

                </div>
                <br>

                <!-- Field to add his/her slogan -->
                <div class="input-group">
                    <span class="input-group-addon">Motto</span>
                    <input id="inputMotto" type="text" name="input_cand_Slogan" class="form-control"
                           placeholder="Motto">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                </div>
                <br>

                <!-- Field to add a picture from him/her-->
                <div class="input-group">
                    <span class="input-group-addon">Foto</span>
                    <div id="photoDropDiv" class="dropdown">
                        <button id="photoDropButton" name="photoButton" class="btn btn-default dropdown-toggle"
                                type="button" value="Bitte wählen Sie ein Foto aus"
                                data-toggle="dropdown">Bitte wählen Sie ein Foto aus
                            <span class="caret"></span>
                        </button>
                        <ul id="photoDropList" class="dropdown-menu">
                            <%
                                LinkedList<String> liFilenames = (LinkedList<String>) this.getServletConfig().getServletContext().getAttribute("liFilenames");
                                if (liFilenames != null) {
                                    for (String filename : liFilenames) {
                                        out.print("<li><a href='#'>" + filename + "</a></li>");
                                    }
                                }
                            %>
                        </ul>
                    </div>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                </div>
                <br>
                <input id="hiddenFoto" type="text" name="input_cand_Picture" class="form-control" hidden>

                <br>
                <%=  request.getAttribute("statusCand") != null ? request.getAttribute("statusCand") : ""  %>

                <!-- Button to add the Candidate -->
                <div class="submitButton">
                    <button type="submit" id="addCand" class="btn btn-primary" name="actionButton"
                            value="addPolitician"><span
                            class="glyphicon glyphicon-floppy-disk"></span> Kandidat hinzufügen
                    </button>

                </div>
            </form>
        </div>

        <br>
        <br>
        <%= request.getAttribute("errorComplete") != null ? request.getAttribute("errorComplete") : ""%>
        <p>Hierbei könnte es zu etweiligen Verzögerungen kommen</p>
        <form action="/NewElectionSL" id="CompletionForm" method="post">
            <div class="submitButton">
                <button type="submit" class="btn btn-primary" name="actionButton"
                        value="forward"> Weiter zur Generierung der Userkeys <span
                        class="glyphicon glyphicon-arrow-right"></span>
                </button>
            </div>
            <br><br><br>
        </form>
    </div>
</div>
<footer class="footer">
    <div class="container text-center">
        <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam Democracy 2.0</p>
    </div>
</footer>
<script>
    $("#photoDropList a").click(function (e) {
        e.preventDefault();
        var selText = $(this).text();
        document.getElementById('hiddenFoto').value = selText;
        document.getElementById('photoDropButton').innerText = selText;
    });
</script>
</body>
</html>
