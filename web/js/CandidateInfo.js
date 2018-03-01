var xmlhttp;
onload = init;

function init() {
    if(window.XMLHttpRequest)
    {
        xmlhttp = new XMLHttpRequest();
    }
}

function reply_click(buttonStr) {
    url = "../ElectionSL?"+buttonStr;
    xmlhttp.open("GET", url, true);
    xmlhttp.onreadystatechange = processRequest();
    xmlhttp.send(null);
}

function processRequest()
{
    if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
    {
        wholeString = xmlhttp.responseText;
        stringField = wholeString.split(";");
        document.getElementById("cand_titleName").innerHTML = stringField[0];
        document.getElementById("cand_pic").setAttribute("src", stringField[6]);
        document.getElementById("cand_forename").innerHTML = stringField[1];
        document.getElementById("cand_surname").innerHTML = stringField[2];
        document.getElementById("cand_birthday").innerHTML = stringField[3];
        document.getElementById("cand_party").innerHTML = stringField[4];
        document.getElementById("cand_slogan").innerHTML = stringField[5];
    }
}