var
    hidden_input     = document.getElementById("tags"),
    tags_input_field = document.getElementById("tags-input-field"),
    pill_wrapper     = document.getElementById("tag-pills-wrapper"),
    tags             = new Set();
const
    ENTER = 13,
    glyphicon_remove = 'glyphicon-remove-circle';

tags_input_field.onkeydown = function(e) {
    if(e.keyCode === ENTER) {
        var value = tags_input_field.value.trim();

        if(!tags.has(value) && value != "" && value.indexOf(',') === -1) {
            add_tag(value);
            add_pill(pill_wrapper, value);
            tags_input_field.value = "";
        }
    }
};


$(document).on('click', '.glyphicon-remove-circle', function() {
    var pill        = this.parentNode;
    var tag         = pill.firstChild;


    remove_tag(tag);
    remove_pill(pill_wrapper, pill);
});

$(document).ready(function() {
    $('.li-tag-field').click(function() {
        // get the value of the text node
        var value = this.firstChild.nodeValue;

        if(!tags.has(value)) {
            add_tag(value);
            add_pill(pill_wrapper, value);
        }
    });
});

function add_pill(wrapper, value) {

    var pill = document.createElement('div');
    var icon = document.createElement('span');
    var text = document.createTextNode(value + " ");

    pill.classList.add('tag-pill');

    icon.classList.add('glyphicon');
    icon.classList.add('glyphicon-remove-circle');

    pill.appendChild(text);
    pill.appendChild(icon);

    pill.parentNode    = wrapper;
    pill.parentElement = wrapper;

    wrapper.appendChild(pill);
}

function remove_pill(wrapper, pill) {

}

function add_tag(tag) {
    tags.add(tag);
    print_set(tags);
}

function remove_tag(tag) {
    tags.delete(tag.nodeValue);
    print_set(tags);
}

function print_set(set) {
    set.forEach(element => console.log(element));
}