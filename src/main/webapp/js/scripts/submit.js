$(document).ready(function() {
    $('form').keydown(function(event) {
        if(event.keyCode === ENTER) {
            event.preventDefault();
        }
    });

    $('#submit-button').click(function(event) {
        event.preventDefault();
        var first = true;
        tags.forEach(tag => {
            if(first) {
                hidden_input.value += tag;
                first = false;
            } else {
                hidden_input.value += ',' + tag;
            }
        });

        collect_authors_info();
        var hidden_authors_input = document.getElementById('hidden-authors-input');
        console.log(hidden_authors_input.value);
        $('form').submit();
    });
});

function collect_authors_info() {
    var hidden_authors_input = document.getElementById('hidden-authors-input');
    var first = true;
    for(var i = 1; i <= authors; i++) {
        var id        = document.getElementById('author-id-' + i).value;
        id = (id === "") ? "0": id;
        var lastName  = document.getElementById('author-last-name-' + i).value;
        var firstName = document.getElementById('author-first-name-' + i).value;

        var json = "{\"id\":" + '\"' + id + '\"' +
            ",\"firstName\":" + '\"' + firstName + '\"' +
            ",\"lastName\":" + '\"' + lastName + '\"' + "}";
        if(first) {
            hidden_authors_input.value += json;
            first = false;
        } else {
            hidden_authors_input.value += (';' + json);
        }
    }
}