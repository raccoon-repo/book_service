var
    authors_container = document.getElementById("author-inputs"),
    authors = 1,
    single = ['col-xs-12'],
    double = ['col-xs-12', 'col-sm-6'],
    add_authors = null;


// returns new author container
function createFormId() {
    var author_id         = 'author-id-' + authors;
    var author            = document.createElement('div');
    var wrapper           = create_wrapper(single);
    var input             = create_input(author_id);
    var label             = create_label(author_id, 'id')
    var form_group        = create_form_group();
    var controls          = document.createElement('div');
    var add_glyph         = document.createElement('span');
    var remove_glyph      = document.createElement('span');

    author.classList.add('author');
    author.classList.add('col-xs-12');
    author.classList.add('no-padding');

    controls.classList.add('control-glyphicons-wrapper');

    add_glyph.classList.add('glyphicon');
    add_glyph.classList.add('glyphicon-plus');
    add_glyph.classList.add('add-author');

    remove_glyph.classList.add('glyphicon');
    remove_glyph.classList.add('glyphicon-remove');
    remove_glyph.classList.add('remove-author');

    appendNode(controls, add_glyph);
    appendNode(controls, remove_glyph);

    appendNode(wrapper, controls);

    appendNode(form_group, label);
    appendNode(form_group, input);

    appendNode(wrapper, form_group);

    appendNode(author, wrapper);

    return author;
}

function createFirstName(author) {
    var first_name = 'author-first-name-' + authors;

    var wrapper = create_wrapper(double);
    var form_group = create_form_group();
    var label = create_label(first_name, 'first name');
    var input = create_input(first_name);

    appendNode(form_group, label);
    appendNode(form_group, input);

    appendNode(wrapper, form_group);

    appendNode(author, wrapper);
}

function createLastName(author) {
    var last_name  = 'author-last-name-' + authors;
    var wrapper    = create_wrapper(double);
    var form_group = create_form_group();
    var label      = create_label(last_name, 'last name');
    var input      = create_input(last_name);

    appendNode(form_group, label);
    appendNode(form_group, input);
    appendNode(wrapper, form_group);
    appendNode(author, wrapper);
}

function appendNode(parent, child) {
    parent.appendChild(child);
    child.parentNode = parent;
    child.parentElement = parent;
}

function create_wrapper(classList) {
    var wrapper = document.createElement('div');

    classList.forEach(clazz => {
        wrapper.classList.add(clazz);
});
    wrapper.classList.add('no-padding');

    return wrapper;
}

function create_input(id) {
    var input = document.createElement('input');

    input.classList.add('form-control');
    input.id = id;
    input.type = 'text';

    return input;
}

function create_form_group() {
    var form_group = document.createElement('div');

    form_group.classList.add('form-group');

    return form_group;
}

function create_label(for_attr, text) {
    var label = document.createElement('label');

    label.setAttribute('for', for_attr);
    label.innerHTML = text;

    return label;
}

$(document).on('click', '.add-author', function(event) {
    if(authors === 0) {
        authors_container.removeChild(add_authors);
    }

    authors++;

    var author = createFormId();
    createFirstName(author);
    createLastName(author);

    appendNode(authors_container, author);
});

$(document).on('click', '.remove-author', function(event) {
    var author = this.parentNode.parentNode.parentNode;
    authors_container.removeChild(author);
    authors--;

    if(authors == 0) {
        var add_author = document.createElement('span');
        var wrapper = document.createElement('div');
        var glyph_wrapper = document.createElement('div');

        glyph_wrapper.style.fontSize = '1.6em';
        glyph_wrapper.style.textAlign = 'center';
        glyph_wrapper.style.margin = '5px auto';

        wrapper.classList.add('col-xs-12');
        wrapper.classList.add('no-padding');
        add_author.classList.add('glyphicon');
        add_author.classList.add('glyphicon-plus');
        add_author.classList.add('add-author');

        appendNode(glyph_wrapper, add_author);
        appendNode(wrapper, glyph_wrapper);
        appendNode(authors_container, wrapper);

        add_authors = wrapper;
    }
});