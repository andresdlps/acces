$(document).ready(function () {
    $.get('verificarMortalidad', function (data) {
        console.log(data)
        if(!data){
            $('#identificacion-es').attr('disabled', 'disabled');
            $('#identificacion-es').fileinput('refresh');
            $('#identificacionsvca-es').attr('disabled', 'disabled');
            $('#identificacionsvca-es').fileinput('refresh');
        }
    });
    $.get('verificarIdentificacion', function (data) {
        console.log(data)
        if(!data){
            $('#btn-ash').attr('disabled', 'disabled');
            $('#btn-cai').attr('disabled', 'disabled');
        }
    });
    $.get('verificarIdentificacionCau', function (data) {
        console.log(data)
        if(!data){
            
            $('#btn-cau').attr('disabled', 'disabled');
        }
    });
    
});



$("#identificacion-es").fileinput({
    language: "es",
    uploadUrl: 'uploadidentificacion',
    showCaption: true,
    showRemove: false,
    uploadAsync: true,
    allowedFileExtensions: ["xls", "xlsx"]
}).on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
    console.log('File 1 uploaded triggered');
    $('#btn-ash').removeAttr('disabled');
    $('#btn-cai').removeAttr('disabled');
});

$("#identificacionsvca-es").fileinput({
    language: "es",
    uploadUrl: 'uploadidentificacioncau',
    showCaption: true,
    showRemove: false,
    uploadAsync: true,
    allowedPreviewTypes: null,
    fileActionSettings: {
            showZoom: false,
            showUpload: false
    },
    allowedFileExtensions: ["xls", "xlsx"]
}).on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
    console.log('File 2 uploaded triggered');
    $('#btn-cau').removeAttr('disabled');
}).on('fileuploaderror', function(event, data, msg) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
   console.log('File upload error');
   // get message
   alert('Los departamentos no han subido por completo, Por favor suba el archivo de población y espere a que finalice la carga');
});

$("#btn-ash").click(function(e) {
    window.location.replace("insumosash");
});

$("#btn-cau").click(function(e) {
    window.location.replace("insumoscau");
});

$("#btn-cai").click(function(e) {
    window.location.replace("insumoscai");
});


