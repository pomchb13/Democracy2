/**
 * Created by Ewald on 17.09.2017.
 */
window.onload = function userNavbar() {
    var navbar = '' +
        '<nav class="navbar navbar-inverse navbar-custom"> ' +
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
        '               <li><a href="homeUI.jsp"><span class="glyphicon glyphicon-home"></span> Startseite</a></li> ' +
        '               <li><a href="aboutUI.jsp"><span class="glyphicon glyphicon-user"></span> Über Uns</a></li> ' +
        '           </ul>' +
        '           <ul class="nav navbar-nav navbar-right"> ' +
        '               <li><a href="/logoutSL" accesskey="1"><span class="glyphicon glyphicon-log-out"></span> USER | Logout</a></li> ' +
        '           </ul> ' +
        '       </div> ' +
        '   </div> ' +
        '</nav>';
    document.getElementById('navbar').innerHTML = navbar;
}

/* '           <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">' +
 '               <span class="icon-bar"></span> ' +
 '               <span class="icon-bar"></span> ' +
 '               <span class="icon-bar"></span> ' +
 '           </button> ' +; */