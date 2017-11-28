/**
 * Created by Leonhard on 28.11.2017.
 */
function addCand() {

    var candTitle = document.getElementsByName('input_cand_Title');
    var candFirstname = document.getElementsByName('input_cand_Firstname');
    var candLastname = document.getElementsByName('input_cand_Lastname');
    var candBirthday = document.getElementsByName('input_cand_Birthday');
    var candParty = document.getElementsByName('input_cand_Party');
    var candSlogan = document.getElementsByName('input_cand_Slogan');
    var candPicture = document.getElementsByName('input_cand_Picture').src;

    var test = document.getElementsByName("input_Cands");
    var all = test + ";/n" + candTitle + ", " + candFirstname + ", " + candLastname + ", " + candBirthday + ", " +
        candParty + ", " + candSlogan + ", " + candPicture
    document.getElementsByName("input_Cands").innerHTML = all;
}