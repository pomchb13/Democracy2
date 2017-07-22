/**
 * Created by Ewald on 22.07.2017.
 */
$(document).ready(function(){
    alert('hallo');
    $("#pollRBID").click(function(){
        alert('poll');
        $("#newVoteDivID").hide();
        $("#newPollDivID").show();

    });
    $("#voteRBID").click(function(){
        alert('vote');
        $("#newPollDivID").hide();
        $("#newVoteDivID").show();
    });
});