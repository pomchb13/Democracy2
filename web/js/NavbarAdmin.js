/**
 * Created by Ewald on 17.09.2017.
 *
 * In this class we define Navigationbar for logged in Administrators. With this Navigationbar the Administrator
 * is able to easily navigate between the sites. This bar will automatically be created in the <div id="navbar"> tag.
 * In the first row the script checks if the site is loading and then adds the navigation bar. To create the navigation bar we
 * use Bootstrap classes, so the design is common with the design of the whole platform. To create the navigationbar
 * , we declare a huge String, where our bar is saved. After that we write this String to our <div> tag.
 */

//This automatically called JS Function adds the Navigation bar for logged in Administrators
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
        '               <li><a href="/NewPollSL" accesskey="1"><span class="glyphicon glyphicon-plus"></span> Neue Abstimmung erstellen</a></li>' +
        '               <li><a href="/UploadImageSL" accesskey="1"><span class="glyphicon glyphicon-upload"></span> Bild hochladen</a></li>' +
        '           </ul>' +
        '           <ul class="nav navbar-nav navbar-right"> ' +
        '               <li><a href="/LogoutSL" accesskey="1"><span class="glyphicon glyphicon-log-out"></span> ADMIN | Logout</a></li> ' +
        '           </ul>' +
        '       </div>' +
        '   </div>' +
        '</nav>'
    document.getElementById('navbar').innerHTML = navbar;
}