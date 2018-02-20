/**
 * Created by Ewald on 17.09.2017.
 */
window.onload = function userNavbar() {
    var navbar = '' +
        '<nav class="navbar navbar-inverse navbar-custom"> ' +
        '   <div class="container-fluid"> ' +
        '       <div class="collapse navbar-collapse" id="Nuvbar"> ' +
        '           <ul class="nav navbar-nav"> ' +
        '               <li id="titleID" class="titleID"><a href="">Democracy 2.0</a></li> ' +
        '               <li><a href="homeUI.jsp"><span class="glyphicon glyphicon-home"></span> Startseite</a></li> ' +
        '               <li><a href="aboutUI.jsp"><span class="glyphicon glyphicon-user"></span> Über Uns</a></li> ' +
        '           </ul>' +
        '           <ul class="nav navbar-nav navbar-right"> ' +
        '               <li><a href="loginUI.jsp"><span class="glyphicon glyphicon-log-in"></span> Login</a></li> ' +
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