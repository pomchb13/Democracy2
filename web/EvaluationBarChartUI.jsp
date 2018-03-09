<%@ page import="java.util.LinkedList" %>
<%@ page import="user.LoggedUsers" %>
<%@ page import="beans.*" %><%--
  Created by IntelliJ IDEA.
  User: Ewald
  Date: 12.07.2017
  Time: 12:01
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
    <!-- Import the C3 diagramm CSS -->
    <link href="http://cdnjs.cloudflare.com/ajax/libs/c3/0.1.29/c3.css" rel="stylesheet" type="text/css">
    <!-- Import the JavaScript for D3 diagramm  -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/d3/3.4.11/d3.min.js" charset="utf-8"></script>
    <!-- Import the JavaScript for C3 diagramm  -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/c3/0.1.29/c3.min.js"></script>
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
        HttpSession ses = request.getSession();
        LoggedUsers lU = LoggedUsers.getInstance();

        String hash = (String) ses.getAttribute("hash");

        if (!lU.compareRights(hash, RightEnum.USER)) {
            response.sendRedirect("/LoginSL");
        }

    %>
    <!-- Title of the page -->
    <div class="titleEvaluation">
        <h1>Derzeitiger Stand der Wahl</h1>
    </div>

    <!-- Div for the Chart -->
    <div id="chart" class="chartEvaluation"></div>
    <!-- JavaScript for the Chart -->
    <script>
        var chart = c3.generate({
                data: {
                    columns: [
<%
    if(this.getServletConfig().getServletContext().getAttribute("clicked") instanceof PollData) {
       PollData pd = (PollData) this.getServletConfig().getServletContext().getAttribute("clicked");
        for (PollAnswer pa: pd.getAnswerList()) {
            out.println("['" + pa.getTitle() + "', " + pa.getVoteCount() + "],");
        }
    } else {
        ElectionData ed = (ElectionData) this.getServletConfig().getServletContext().getAttribute("clicked");
        for (CandidateData cd: ed.getLiCandidates()) {
            out.println("['" + cd.getSurname().toUpperCase() + " " + cd.getForename() + "', " + cd.getVoteCount() + "],");
        }
    }
%>
                        /* ['asdf', 50],
                         ['MUSTERFRAU Mara', 120],
                         ['KRAFT Michael', 13],
                         ['CONRAD Heike', 54],
                         ['MÜLLER Anke', 150],
                         ['AZOG Sepp', 70], */
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
                            text: 'Namen der Kandidaten',
                            position: 'outer-center'
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
                            text: 'Anzahl der Stimmen',
                            position: 'outer-middle'
                        }
                    },
                },
                legend: {
                    position: 'bottom'
                }
                    /*        colors: {
                     Max: '#ffff00',
                     Muster: '#99ccff',
                     Mann: '#cc80ff',
                     Max1: '#88cc00',
                     Muster1: '#00ffff',
                     Mann1: '#008080'
                     },
                     color: function (color, d) {
                     // d will be 'id' when called for legends
                     return d.id && d.id === 'data3' ? d3.rgb(color).darker(d.value / 150) : color;
                     }*/
            })
        ;
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
        <p class="text-muted">© 2018 Copyright by BearingPoint | Diplomarbeitsteam HTBLA Kaindorf</p>
    </div>
</footer>
</body>
</html>
