<%--
  Created by IntelliJ IDEA.
  User: Ewald
  Date: 12.07.2017
  Time: 19:03
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
    <!-- Import the default CSS -->
    <link rel="stylesheet" type="text/css" href="css/defaultCSS.css">
    <!-- Set Tab picture -->
    <link rel="icon" type="image/png" href="res/avatar.png">
    <!-- Import the C3 diagramm CSS -->
    <link href="http://cdnjs.cloudflare.com/ajax/libs/c3/0.1.29/c3.css" rel="stylesheet" type="text/css">
    <!-- Import the JavaScript for D3 diagramm  -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/d3/3.4.11/d3.min.js" charset="utf-8"></script>
    <!-- Import the JavaScript for C3 diagramm  -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/c3/0.1.29/c3.min.js"></script>
    <!-- Import the JavaScript of  Navbar -->
    <script src="js/navbarUser.js"></script>

</head>
<body onload="loaded()">
<!-- Implements the navigation bar in the webseite -->
<div id="navbar"></div>
<center>
    <!-- Title of the page -->
    <div class="titleEvaluation">
        <h1>Derzeitiger Stand der Wahl</h1>
    </div>
</center>
<!-- Div for the Chart -->
<div id="chart" class="chartEvaluation"></div>
<!-- JavaScript for the Chart -->
<script>
    var chart = c3.generate({
        data: {
            columns: [
                ['Max', 50],
                ['Muster', 120],
                ['Mann', 13],
                ['Max1', 54],
                ['Muster1', 150],
                ['Mann1', 70],
            ],
            type: 'pie',
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
                label: 'Namen der Kandidaten'
            },
            y: {
                label: 'Anzahl der Stimmen'

            },
        }
    });
</script>
<center>
    <!-- Add a Button the change to the other chart -->
    <button id="evaluation" type="submit" class="btn btn-primary"
            onclick="window.location.href='/evaluationBarChartUI.jsp'">
        <span class="glyphicon glyphicon-retweet"></span> Balkendiagramm
    </button>
</center>
</body>
</html>