/**
 * Created by Ewald on 22.07.2017.
 */
$(document).ready(function(){
    $("#pollID").click(function(){
        $("#newVoteDivID").hide();
        $("#newRefDivID").show();

    });
    $("#voteID").click(function(){
        $("#newRefDivID").hide();
        $("#newVoteDivID").show();
    });
});