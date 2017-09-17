/**
 * Created by Leonhard on 16.07.2017.
 */

 $(document).ready(function () {
    // Logindata: User=admin; Password: 1234
    // $("#evaluation").click()(function () {
    $("#evaluation").click(function () {
        if ($("#email").val() == 'admin' && $("#password").val() == 'admin') {
            alert("Hallo " + $('#email').val())
            window.location = "../adminSettingsUI.jsp"
        }
        else if($("#email").val().toString() != "" && $("#password").val() == '1234'){
            alert("Hallo " + $('#email').val())
            window.location = "../voteUI.jsp"
        }
        else {
            alert("Please try again!!")
        }
    });

});
