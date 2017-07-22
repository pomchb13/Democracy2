/**
 * Created by Ewald on 22.07.2017.
 */
$(document).ready(function () {
    var max_fields = 10;
    //var wrapper = $(".container1");
    var wrapper = $(".answerDiv");
    var add_button = $(".add_form_field");
    var answerCount = 3;

    var x = 1;
    $(add_button).click(function (e) {
        e.preventDefault();
        if (x < max_fields) {
            x++;
            //$(wrapper).append('<div><input type="text" name="mytext[]" placeholder="Antwort"/><a href="#" class="delete"><span class="glyphicon glyphicon-remove"></span></a></a></div>');
            $(wrapper).append('<div class="input-group"><span class="input-group-addon">Antwort #'+ (answerCount++) +' </span><textarea id="antwort2" type="text" class="form-control" rows="3"> </textarea><span class="input-group-addon">*</span> </div> <br>');//add input box
        }
        else {
            alert('Maximum erreicht')
        }
    });

    $(wrapper).on("click", ".delete", function (e) {
        if (x > 1) {
            e.preventDefault();
            $(this).parent('div').remove();
            x--;
        }

    })
});
