/**
 * Created by Ewald on 22.07.2017.
 *
 * With this Javascript method we are able to dynamically add Answers to new Polls. The new Textfield appears on the
 * webpage, when the Administrator presses the Button to add an answer. The script is added automatically to the jsp with
 * the <script type="text/javascript" src="js/AddAnswersNewVote.js"></script> tag.
 */
//In the method header we define, that the function will be created when the javascript is integrated successfully to the jsp
$(document).ready(function () {
    //Defines that a maximum of 10 added answers are allowed
    var max_fields = 10;
    //Save the <div> tag to a variable to eddit it and later write it to the page
    var wrapper = $(".answerDiv");
    //Save the <button> to check if it's pressed
    var add_button = $(".add_form_field");
    //Variable which defines the actual ID Number od the <textarea>
    var answerCount = 3;
    //Variable which defines added fields
    var anzAnswers = 0;
    //In this function header the function will be calles if the Button addAnswer is pressed
    $(add_button).click(function (e) {
        e.preventDefault();
        //Checks if the maximum off added field is reached
        if (anzAnswers < max_fields) {
            //Increases the number of added fields with 1
            anzAnswers++;
            //Adds the <textarea> to the saved <div>. Also created with Bootstrap
            $(wrapper).append('' +
                '<div class="input-group">' +
                    '<span class="input-group-addon">Antwort #' + (answerCount) + ' </span>' +
                    '<textarea id="antwort' +answerCount++ + '" type="text" class="form-control" rows="3"> </textarea>' +
                    '<span class="input-group-addon">' +
                        '<span class="glyphicon glyphicon-asterisk"></span>' +
                    '</span>' +
                '</div>' +
                '<br>');
        }
        //If the maximum of added inputs is reached it will Alert the Administrator
        else {
            alert('Maximum erreicht')
        }
    });

});
