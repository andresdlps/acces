$(document).ready(function () {
    $.get('hasConsolidadoAsh', function (data) {
        if(!data){
            $("#verASH").attr('disabled', 'disabled');
        }
    });
    
    $.get('hasConsolidadoCai', function (data) {
        if(!data){
            $("#verCAI").attr('disabled', 'disabled');
        }
    });
    
    $.get('hasConsolidadoCau', function (data) {
        if(!data){
            $("#verCAU").attr('disabled', 'disabled');
        }
    });
    
    $.get('hasConsolidadoGeneral', function (data) {
        if(!data){
            $("#btnreporte").attr('disabled', 'disabled');
        }
    });
    
    
});

$("#verASH").click(function(e) {
    window.location.replace("consolidadoash.html");
});

$("#verCAI").click(function(e) {
    window.location.replace("consolidadocai.html");
});

$("#verCAU").click(function(e) {
    window.location.replace("consolidadocau.html");
});

$("#btnreporte").click(function(e) {
    window.location.replace("consolidadogeneral.html");
});

