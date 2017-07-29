/**
 * Created by Leonhard on 16.07.2017.
 */

$(document).ready(function () {
    // Logindata: User=admin; Password: 1234
    // $("#evaluation").click()(function () {
        $("#evaluation").click(function(){
        if ($("#email").val() == 'admin' && $("#password").val() == '1234') {
            if (window.location.href == 'http://localhost:8080/adminUI.jsp') {
                window.location.replace("http://localhost:8080/adminSettingsUI.jsp");
            }
            else {
                window.location.replace("http://localhost:8080/voteUI.jsp");
            }
        }
        else {
            alert("Please try again!!");
        }
    })
});

