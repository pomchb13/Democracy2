/**
 * Created by Leonhard on 28.11.2017.
 */
function addCand() {

    alert('HALLO i bims deim Javascript');
    var candTitle = document.getElementsByName('input_cand_Title')[0].value;
    var candFirstname = document.getElementsByName('input_cand_Firstname')[0].value;
    var candLastname = document.getElementsByName('input_cand_Lastname')[0].value;
    var candBirthday = document.getElementsByName('input_cand_Birthday')[0].value;
    var candParty = document.getElementsByName('input_cand_Party')[0].value;
    var candSlogan = document.getElementsByName('input_cand_Slogan')[0].value;
    var candPicture = 'TestPicture';
    // var candPicture = document.getElementsByName('input_cand_Picture')[0];

    var test = document.getElementsByName("input_Cands")[0].value;
    var all = test + "; <br>" + candTitle + ", " + candFirstname + ", " + candLastname + ", " + candBirthday + ", " +
        candParty + ", " + candSlogan + ", " + candPicture ;
    alert(all);
    document.getElementsByName("input_Cands")[0].innerHTML = all;
}