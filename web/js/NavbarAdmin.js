/**
 * Created by Ewald on 17.09.2017.
 */
window.onload = function adminNavbar() {
    var navbar = '' +
        '<nav class="navbar navbar-inverse navbar-custom">' +
        '   <div class="container-fluid"> ' +
        '    <div class="navbar-header">' +
        ' <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navBar">' +
        '<span class="icon-bar"></span>' +
        '<span class="icon-bar"></span>' +
        '<span class="icon-bar"></span>' +
        '</button>' +
        '<a class="navbar-brand" href="">Democracy 2.0</a>' +
        '</div>' +
        '       <div class="collapse navbar-collapse" id="navBar"> ' +
        '           <ul class="nav navbar-nav"> ' +
        '               <li><a href="ActiveVotesUI.jsp"><span class="glyphicon glyphicon-th-list"></span> Aktive Wahlen</a></li>' +
        '               <li><a href="/NewElectionSL" accesskey="1"><span class="glyphicon glyphicon-plus"></span> Neue Wahl erstellen</a></li>' +
        '               <li><a href="/newPollSL" accesskey="1"><span class="glyphicon glyphicon-plus"></span> Neue Abstimmung erstellen</a></li>' +
        '               <li><a href="/UploadImageSL" accesskey="1"><span class="glyphicon glyphicon-upload"></span> Bild hochladen</a></li>' +
        '           </ul>' +
        '           <ul class="nav navbar-nav navbar-right"> ' +
        '               <li><a href="/logoutSL" accesskey="1"><span class="glyphicon glyphicon-log-out"></span> ADMIN | Logout</a></li> ' +
        '           </ul>' +
        '       </div>' +
        '   </div>' +
        '</nav>'
    document.getElementById('navbar').innerHTML = navbar;
}