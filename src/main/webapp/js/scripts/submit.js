$(document).ready(function() {
    $('form').keydown(function(event) {
        if(event.keyCode === ENTER) {
            event.preventDefault();
        }
    });

    $('#submit-button').click(function(event) {
        event.preventDefault();
        let first = true;
        tags.forEach(tag => {
            if(first) {
                hidden_input.value += tag;
                first = false;
            } else {
                hidden_input.value += ',' + tag;
            }
        });

        let authors = document.getElementById("authorsJson");
        authors.value = parseAuthors();
        $('form').submit();
    });
});

function parseAuthors() {
    let json = "{\"authors\":[";

    let id, firstName, lastName, author;

    //authors variable declared inside authors.js
    for(let i = 1; i <= authors; i++) {
        id        = document.getElementById("author-id-" + i).value;
        firstName = document.getElementById("author-first-name-" + i).value;
        lastName  = document.getElementById("author-last-name-" + i).value;

        id = id === "" ? 0: id;

        author = "{\"id\":" + id + ", " +
                 "\"firstName\":\"" + firstName + "\"," +
                 "\"lastName\":\"" + lastName + "\"}";

        if(authors !== 1 && i !== authors) {
            json += author + ","
        } else {
            json += author;
        }
    }

    json +="]}";

    return json;
}