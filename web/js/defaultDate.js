/**
 * Created by Ewald on 24.07.2017.
 */
window.onload = function () {
    var today = new Date();
    var day = today.getDate();
    var month = today.getMonth() + 1;
    var year = today.getFullYear();
    if (day < 10) {
        day = '0' + day;
    }
    if (month < 10) {
        month = '0' + month;
    }

    var today2 = year + '.' + month + '.' + day;
    var date = new Date();
    var maxMonth = date.getMonth() + 2;
    var maxDate = year + '.' + maxMonth + '.' + day;

    document.getElementById("startRef").value = today2;
    document.getElementById("startRef").min = today2;
    document.getElementById("startRef").max = maxDate;
};