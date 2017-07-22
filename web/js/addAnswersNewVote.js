/**
 * Created by Ewald on 22.07.2017.
 */
$(document).ready(function () {
    var max_fields = 10;
    var wrapper = $(".container1");
    var add_button = $(".add_form_field");

    var x = 1;
    $(add_button).click(function (e) {
        e.preventDefault();
        if (x < max_fields) {
            x++;
            $(wrapper).append('<div><input type="text" name="mytext[]" placeholder="Antwort"/><a href="#" class="delete"><span class="glyphicon glyphicon-remove"></span></a></a></div>'); //add input box
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
