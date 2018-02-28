<%@ page import="java.util.LinkedList" %>
<%@ page import="beans.ElectionData" %>
<%@ page import="beans.RightEnum" %>
<%@ page import="user.LoggedUsers" %><%--
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
    <script type="text/javascript" src="js/AddAnswersNewVote.js"></script>
    <!-- Import defaultDate -->
    <script type="text/javascript" src="js/defaultDate.js"></script>
    <!-- Import the default CSS -->
    <link rel="stylesheet" type="text/css" href="css/DefaultCSS.css">
    <!-- Set Tab picture -->
    <link rel="icon" type="image/png" href="res/Avatar.png">
    <!-- Import datepicker -->
    <script type="text/javascript" src="js/datepicker.js"></script>
    <!-- Import Javascript function to add Candidates -->
    <script type="text/javascript" src="js/addCandidate.js"></script>
    <!-- Import JavaScript to view the selected DropDownMenuItem -->
    <!-- <script src="js/viewDropdownItem.js"></script> -->
    <!-- Import the JavaScript of  Navbar -->
    <script src="js/NavbarAdmin.js"></script>
</head>
<body>
<%
    HttpSession ses = request.getSession();
    LoggedUsers lU = LoggedUsers.getInstance();

    String hash = (String) ses.getAttribute("hash");

    if (!lU.compareRights(hash, RightEnum.ADMIN)) {
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
        <form action="/NewElectionSL" id="form1" method="post">
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
            <p id="error"><%=  request.getAttribute("errorVote") != null ? request.getAttribute("errorVote") : ""  %>
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
            <h1>Kanditaten zur <%= this.getServletConfig().getServletContext().getAttribute("newElection") != null ?
                    ((ElectionData) this.getServletConfig().getServletContext().getAttribute("newElection")).getTitle() : ""%>
                hinzufügen</h1></div>

        <!-- Add a new Candidate -->
        <div class="candDiv1">
            <form action="/NewElectionSL" id="form2" method="post">
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
                <div class="input-group">
                    <span class="input-group-addon">Geburtsdatum</span>
                    <input id="inputBirthday" type="text" name="input_cand_Birthday" class="form-control"
                           placeholder="Geburtsdatum">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
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
                    <div id="photoDropDiv" class="dropdown" style="width: 100% ">
                        <button id="photoDropButton" name="photoButton" class="btn btn-default dropdown-toggle"
                                type="button" value="Bitte wählen Sie ein Foto aus"
                                data-toggle="dropdown" onclick="<% %>" style="width: 100%">Bitte wählen Sie ein Foto aus
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
                <%=  request.getAttribute("errorPol") != null ? request.getAttribute("errorPol") : ""  %>

                <!-- Button to add the Candidate -->
                <div class="submitButton">
                    <button type="submit" id="addCand" class="btn btn-primary" name="actionButton"
                            value="addPolitician"><span
                            class="glyphicon glyphicon-floppy-disk"></span> Kandidaten/Partei hinzufügen
                    </button>

                </div>
            </form>
        </div>

        <br>
        <br>

        <div class="submitButton">
            <a href="AdminSettingsUI.jsp">
                <button type="button" class="btn btn-primary" name="forwardButton"
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
<script>
    $("#photoDropList a").click(function (e) {
        e.preventDefault();
        var selText = $(this).text();
        alert(selText);
        document.getElementById('inputFoto').value = selText;
        document.getElementById('photoDropButton').innerHTML = selText;
    });
</script>
</body>
</html>
