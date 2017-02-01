$("#limpiarbtn").click(function(e) {
    $.post("purge", function(data) {
        $("#limpiarResult").html(data);
    });
});

$("#consolidarbtn").click(function(e) {
    $.post("consolidate", function(data) {
        $("#consolidateResult").html(data);
    });
});