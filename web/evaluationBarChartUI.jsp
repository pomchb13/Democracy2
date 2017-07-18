<%--
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
    <!-- <script language="javascript" type="text/javascript" src="js/CloseWin.js"></script> -->
</head>
<body onload="loaded()">
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
                <li><a href="homeUI.jsp"><span class="glyphicon glyphicon-home"></span> Startseite</a></li>
                <li><a href="loginUI.jsp"><span class="glyphicon glyphicon-list-alt"></span> Wahl</a></li>
                <li><a href="aboutUI.jsp"><span class="glyphicon glyphicon-user"></span> Über Uns</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="adminUI.jsp"><span class="glyphicon glyphicon-log-in"></span> Login für Admins</a></li>
            </ul>
        </div>
    </div>
</nav>
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
                label: 'Namen der Kandidaten'
            },
            y: {
                label: 'Anzahl der Stimmen'
            },
        },
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
    });
</script>
<center>
    <!-- Add a Button the change to the other chart -->
    <button id="evaluation" type="submit" class="btn btn-primary"
            onclick="window.location.href='/evaluationPieChartUI.jsp'">
        <span class="glyphicon glyphicon-retweet"></span> Kreisdiagramm
    </button>
</center>
</body>
</html>