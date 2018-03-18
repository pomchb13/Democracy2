/**
 * Author:          Ewald Hartmann
 * Created on:
 * Description:     In this class we define Navigationbar for Users who are not logged in. With this Navigationbar the
 *                  User is able to easily navigate between the sites. This bar will automatically be created in the
 *                  <div id="navbar"> tag. In the first row the script checks if the site is loading and then adds the
 *                  navigation bar. To create the navigation bar we use Bootstrap classes, so the design is common
 *                  with the design of the whole platform. To create the navigationbar, we declare a huge String, where
 *                  our bar is saved. After that we write this String to our <div> tag.
 */

//This automatically called JS Function adds the Navigation bar for Users who are not logged in
window.onload = function userNavbar() {
    var navbar = '' +
        '<nav class="navbar navbar-inverse"> ' +
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
        '               <li><a href="HomeUI.jsp"><span class="glyphicon glyphicon-home"></span> Startseite</a></li> ' +
        '               <li><a href="AboutUI.jsp"><span class="glyphicon glyphicon-user"></span>Team</a></li> ' +
        '           </ul>' +
        '           <ul class="nav navbar-nav navbar-right"> ' +
        '               <li><a href="/LoginSL" accesskey="1"><span class="glyphicon glyphicon-log-in"></span> Anmelden</a></li> ' +
        '           </ul> ' +
        '       </div> ' +
        '   </div> ' +
        '</nav>';
    document.getElementById('navbar').innerHTML = navbar;
}
