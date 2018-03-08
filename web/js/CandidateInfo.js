/**
 * Author:          Leonhard Gangl
 * Created on:
 * Description:     This javascript class is responsible for displaying the whole candidate information if the voter
 *                  presses the an info button in the VoteUI. Every candidate has it's own info button. To ensure that
 *                  the information will be displayed we use java ajax. Ajax the information will appear on the website
 *                  without reloading the whole site.
 */

//Ajax Object
var xmlhttp;
//Defines that the init() method will be called if the js will be loaded.
onload = init;

/**
 * In this Method we initialise the xmlhttp ajax object.
 */
function init() {
    if(window.XMLHttpRequest)
    {
        alert('init');
        xmlhttp = new XMLHttpRequest();
        alert('end init ' + xmlhttp)
    }
}

/**
 * @param buttonStr
 * This Button sends a part of the information from the candidate and gets the rest back. If the if the xmlhttp object
 * receives a result it calls the processRequest() method.
 */
function reply_click(buttonStr) {
    alert('I am in reply_click:'+buttonStr);
    url = "../ElectionSL?candidateID="+buttonStr;
    xmlhttp.open("GET", url, true);
    try {
        xmlhttp.onreadystatechange = processRequest();
    }catch (e){
        alert(e.toString());
    }
    //xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    xmlhttp.send(null);
    alert('did all your work');
}

/**
 * The processRequest() method firstly checks if the xmlhttp request was successfully. If that is true, it will write all
 * the information from the candidate to the given HTML elements.
 */
function processRequest()
{
    alert('pr');
    alert(xmlhttp.readyState);
    alert(xmlhttp.status);
    if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
    {
        alert('request successfull');
        wholeString = xmlhttp.responseText;
        stringField = wholeString.split(";");
        document.getElementById("cand_titleName").innerHTML = stringField[0];
        document.getElementById("cand_pic").setAttribute("src", stringField[6]);
        document.getElementById("cand_forename").innerHTML = stringField[1];
        document.getElementById("cand_surname").innerHTML = stringField[2];
        document.getElementById("cand_birthday").innerHTML = stringField[3];
        document.getElementById("cand_party").innerHTML = stringField[4];
        document.getElementById("cand_slogan").innerHTML = stringField[5];
        alert('written successfull');
    }
}