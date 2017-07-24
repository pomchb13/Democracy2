/**
 * Created by Ewald on 22.07.2017.
 */
$(document).ready(function () {
    var max_fields = 10;
    var wrapper = $(".answerDiv");
    var add_button = $(".add_form_field");
    var answerCount = 3;

    var anzAnswers = 1;
    $(add_button).click(function (e) {
        e.preventDefault();
        if (anzAnswers < max_fields) {
            anzAnswers++;
            $(wrapper).append('' +
                '<div class="input-group">' +
                    '<span class="input-group-addon">Antwort #' + (answerCount++) + ' </span>' +
                    '<textarea id="antwort2" type="text" class="form-control" rows="3"> </textarea>' +
                    '<span class="input-group-addon">' +
                        '<span class="glyphicon glyphicon-asterisk"></span>' +
                    '</span>' +
                '</div>' +
                '<br>');//add input box
        }
        else {
            alert('Maximum erreicht')
        }
    });

});
