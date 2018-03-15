<%@ page import="java.util.LinkedList" %>
<%@ page import="user.LoggedUsers" %>
<%@ page import="beans.*" %>
<%--
 Author:          Ewald Hartmann
 Created on:
 Description:     represents the current status of the vote in a pie chart
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
    <!-- Import the JavaScript of  Bootstrap -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- Import the default CSS -->
    <link rel="stylesheet" type="text/css" href="css/DefaultCSS.css">
    <!-- Set Tab picture -->
    <link rel="icon" type="image/png" href="res/Avatar.png">
    <!-- Import the C3 diagramm CSS -->
    <link href="http://cdnjs.cloudflare.com/ajax/libs/c3/0.4.21/c3.css" rel="stylesheet" type="text/css">
    <!-- Import the JavaScript for D3 diagramm  -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/d3/3.4.11/d3.min.js" charset="utf-8"></script>
    <!-- Import the JavaScript for C3 diagramm  -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.21/c3.min.js"></script>
    <!-- Import the JavaScript of  Navbar -->
    <script src="js/NavbarLogedUser.js"></script>
</head>
<body>
<!-- Implements the navigation bar in the webseite -->
<div id="navbar"></div>
<div id="container">
    <br><br>
    <%
        HttpSession httpSession = request.getSession();
        LoggedUsers loggedUser = LoggedUsers.getInstance();

        String hash = (String) httpSession.getAttribute("hash");

        if (!loggedUser.compareRights(hash, RightEnum.USER)) {
            response.sendRedirect("/LoginSL");
        }

    %>
    <!-- Title of the page -->
    <div class="titleEvaluation">
        <h1>Derzeitiger Stand
            der <%= request.getSession().getAttribute("voteObject") instanceof ElectionData ? "Wahl" : "Abstimmung"%>
        </h1>
    </div>

    <!-- Div for the Chart -->
    <div id="chart" class="chartEvaluation"></div>
    <!-- JavaScript for the Chart -->
    <script>
        var chart = c3.generate({
                data: {
                    columns: [
                        <%
                            int count = 0;
                            if(request.getSession().getAttribute("voteObject") instanceof PollData) {
                                PollData pd = (PollData) request.getSession().getAttribute("voteObject");

                                for (PollAnswer pa: pd.getAnswerList()) {
                                    if (count == pd.getAnswerList().size()) {
                                        out.println("['" + pa.getTitle() + "', " + pa.getVoteCount() + "]");
                                    } else {
                                        out.println("['" + pa.getTitle() + "', " + pa.getVoteCount() + "],");
                                    }
                                    count++;
                                }
                            } else if (request.getSession().getAttribute("voteObject") instanceof ElectionData){
                                ElectionData ed = (ElectionData) request.getSession().getAttribute("voteObject");
                                for (CandidateData cd: ed.getLiCandidates()) {
                                    if (count == ed.getLiCandidates().size()) {
                                        out.println("['" + cd.getSurname().toUpperCase() + " " + cd.getForename() + "', " + cd.getVoteCount() + "]");
                                    } else{
                                        out.println("['" + cd.getSurname().toUpperCase() + " " + cd.getForename() + "', " + cd.getVoteCount() + "],");
                                    }
                                    count++;
                                }
                            }%>
                    ],
                    type: 'donut',
                    onclick: function (d, i) {
                        console.log("onclick", d, i);
                    },
                    onmouseover: function (d, i) {
                        console.log("onmouseover", d, i);
                    },
                    onmouseout: function (d, i) {
                        console.log("onmouseout", d, i);
                    }
                },
                axis: {
                    x: {
                        label: '<%= request.getSession().getAttribute("voteObject") instanceof ElectionData ? "Name der Kandidaten" : "Antworten"%>'
                    },
                    y: {
                        label: 'Anzahl der Stimmen'

                    },
                },
                donut: {
                    title: "Prozentverteilung"
                },
                bar: {
                    width: 30
                }
            })
        ;
    </script>
    <center>
        <!-- Add a Button the change to the other chart -->
        <button id="evaluation" type="submit" class="btn btn-primary"
                onclick="window.location.href='/EvaluationBarChartUI.jsp'">
            <span class="glyphicon glyphicon-retweet"></span> Balkendiagramm
        </button>
    </center>
</div>
<footer class="footer">
    <div class="container text-center">
        <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam Democracy 2.0</p>
    </div>
</footer>
</body>
</html>