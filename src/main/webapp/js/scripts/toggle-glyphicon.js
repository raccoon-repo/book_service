function toggle_glyph_class(toggler) {
    var childNodes = toggler.childNodes;

    for(var i = 0; i < childNodes.length; i++) {
        var node = childNodes[i];
        if(node.tagName === 'SPAN') {
            var classes = node.classList;
            if(classes.contains("glyphicon-menu-down")) {
                classes.remove("glyphicon-menu-down");
                classes.add("glyphicon-menu-up");
            } else if(classes.contains("glyphicon-menu-up")) {
                classes.remove("glyphicon-menu-up")
                classes.add("glyphicon-menu-down");
            }
        }
    }
}

$(document).ready(function() {
    $('a.toggle-bar').click(function() {
        toggle_glyph_class(this);
    });
});