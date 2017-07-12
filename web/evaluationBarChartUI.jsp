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
    <link href="DefaultCSS.css" rel="stylesheet" type="text/css">
    <title>Democracy 2.0</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/defaultCSS.css">
    <link href="http://cdnjs.cloudflare.com/ajax/libs/c3/0.1.29/c3.css" rel="stylesheet" type="text/css">
    <script src="http://cdnjs.cloudflare.com/ajax/libs/d3/3.4.11/d3.min.js" charset="utf-8"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/c3/0.1.29/c3.min.js"></script>
    <script language="javascript" type="text/javascript" src="js/CloseWin.js"></script>
</head>
<body onload="loaded()">
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
    <div class="titleEvaluation">
        <h1>Derzeitiger Stand der Wahl</h1>
    </div>
</center>
<div id="chart" class="chartEvaluation"></div>
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
    <button id="evaluation" type="submit" class="btn btn-primary"
            onclick="window.location.href='/evaluationPieChartUI.jsp'">Kreisdiagramm
    </button>
</center>
</body>
</html>
