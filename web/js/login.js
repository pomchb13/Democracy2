/**
 * Created by Leonhard on 16.07.2017.
 */

function login() {
    var user = document.getElementById('email').value;
    var password = document.getElementById('password').value;
    //Request an Blockchain, ob User vorhanden. Wenn ja, gib passwort aus Blockchain zurück

    var Blockpassword = '1234' //Testdaten

    if (password == Blockpassword) {
        alert(document.location);
        //Herausfinden, wie es mit einer Wildcardmask funktionieren könnte
        if (document.location == 'http://localhost:8080/loginUI.jsp') {
            document.location.replace('/voteUI.jsp')
        } else if (document.location = 'http://localhost:8080/adminUI.jsp') {
            document.location.replace('/adminSettingsUI.jsp')
        }
    }
}
