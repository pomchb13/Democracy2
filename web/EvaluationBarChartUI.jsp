<%@ page import="java.util.LinkedList" %>
<%@ page import="user.LoggedUsers" %>
<%@ page import="beans.*" %>
<%--
 Author:          Ewald Hartmann
 Created on:
 Description:     represents the current status of the vote in a bar chart
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <!-- Import the C3 diagramm CSS -->
    <link href="http://cdnjs.cloudflare.com/ajax/libs/c3/0.4.21/c3.css" rel="stylesheet" type="text/css">
    <!-- Import the JavaScript for D3 diagramm  -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/d3/3.4.11/d3.min.js" charset="utf-8"></script>
    <!-- Import the JavaScript for C3 diagramm  -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.21/c3.min.js"></script>
    <!-- Import the default CSS -->
    <link rel="stylesheet" type="text/css" href="css/DefaultCSS.css">
    <!-- Set Tab picture -->
    <link rel="icon" type="image/png" href="res/Avatar.png">
    <!-- Import the JavaScript of  Navbar -->
    <script src="js/NavbarLogedUser.js"></script>
</head>
<body>
<!-- Implements the navigation bar in the webseite -->
<div id="navbar"></div>
<div id="container">
    <br><br>
    <%
        //Check if the user is loged in
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
        // create the bar chart for user
        var chart = c3.generate({
            data: {
                columns: [
                <%
                    int count = 0;
                    if(request.getSession().getAttribute("voteObject") instanceof PollData) {
                        PollData pollData = (PollData) request.getSession().getAttribute("voteObject");
                        out.println("['Stimmen: ',");
                        for (PollAnswer pollAnswer: pollData.getAnswerList()) {
                            if (count == pollData.getAnswerList().size()-1) {
                                out.println(pollAnswer.getVoteCount() + "]");
                            } else {
                                out.println(pollAnswer.getVoteCount() + ",");
                            }
                            count++;
                        }
                    } else if (request.getSession().getAttribute("voteObject") instanceof ElectionData){
                        ElectionData electionData = (ElectionData)request.getSession().getAttribute("voteObject");
                        out.println("['Stimmen: ',");
                        for (CandidateData candidateData: electionData.getLiCandidates()) {
                            if (count == electionData.getLiCandidates().size()-1) {
                                out.println(candidateData.getVoteCount() + "]");
                            } else{
                                out.println(candidateData.getVoteCount() + ",");
                            }
                            count++;
                        }
                    }
                %>
                ],
                type: 'bar',
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
                    label: {
                        text: '<%= request.getSession().getAttribute("voteObject") instanceof ElectionData ? "Kandidaten" : "Antworten"%>',
                        position: 'outer-center',
                        type: 'category',
                        categories: [
                        <%
                            int count1 = 0;
                            if(request.getSession().getAttribute("voteObject") instanceof PollData) {
                                PollData pollData = (PollData) request.getSession().getAttribute("voteObject");
                                for (PollAnswer pollAnswer: pollData.getAnswerList()) {
                                    if (count1 == pollData.getAnswerList().size()-1) {
                                        out.println("'" + pollAnswer.getTitle() + "'");
                                    } else {
                                        out.println("'" + pollAnswer.getTitle() + "',");
                                    }
                                    count1++;
                                }
                            } else if (request.getSession().getAttribute("voteObject") instanceof ElectionData){
                                ElectionData electionData = (ElectionData)request.getSession().getAttribute("voteObject");
                                for (CandidateData candidateData: electionData.getLiCandidates()) {
                                    if (count1 == electionData.getLiCandidates().size()-1) {
                                        out.println("'" + candidateData.getTitle() + " " + candidateData.getSurname().toUpperCase() + " " + candidateData.getForename() + "'");
                                    } else {
                                        out.println("'" + candidateData.getTitle() + " " + candidateData.getSurname().toUpperCase() + " " + candidateData.getForename() + "',");
                                    }
                                    count1++;
                                }
                            }
                        %>
                        ]
                    },
                    tick: {
                        count: 1,
                        format: function () {
                            return ''
                        }
                    }
                },
                y: {
                    label: {
                        text: 'Stimmen',
                        position: 'outer-middle'
                    }
                },
            },
            bar: {
                width: 40
            },
            legend: {
                position: 'bottom'
            }
        });
    </script>
    <center>
        <!-- Add a Button the change to the other chart -->
        <button id="evaluation" type="submit" class="btn btn-primary"
                onclick="window.location.href='/EvaluationPieChartUI.jsp'">
            <span class="glyphicon glyphicon-retweet"></span> Kreisdiagramm
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
