/**
 * Created by Ewald on 17.09.2017.
 */
window.onload = function adminNavbar() {
    var string = '' +
        '<nav class="navbar navbar-inverse">' +
        '   <div class="container-fluid"> ' +
        '       <div class="navbar-header">' +
        '           <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">' +
        '               <span class="icon-bar"></span> ' +
        '               <span class="icon-bar"></span>' +
        '               <span class="icon-bar"></span> ' +
        '           </button> ' +
        '           <a class="navbar-brand" href="">Democracy 2.0</a> ' +
        '       </div> ' +
        '       <div class="collapse navbar-collapse" id="myNavbar"> ' +
        '           <ul class="nav navbar-nav"> ' +
        '               <li><a href="adminSettingsUI.jsp"><span class="glyphicon glyphicon-home"></span> Startseite</a></li>' +
        '               <li><a href="activeVotesUI.jsp"><span class="glyphicon glyphicon-th-list"></span> Aktive Wahlen</a></li>' +
        '               <li><a href="newVoteUI.jsp"><span class="glyphicon glyphicon-plus"></span> Neue Wahl erstellen</a></li>' +
        '           </ul>' +
        '           <ul class="nav navbar-nav navbar-right"> ' +
        '               <li><a href="../homeUI.jsp"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li> ' +
        '           </ul>' +
        '       </div>' +
        '   </div>' +
        '</nav>'
        document.getElementById('navbar').innerHTML = string;
}