$("#svcacsv").fileinput({
    language: "es",
    uploadUrl: 'uploadpm10',
    showCaption: true,
    showRemove: false,
    uploadAsync: true,
    allowedPreviewTypes: null,
    fileActionSettings: {
            showZoom: false,
            showUpload: false
    },
    allowedFileExtensions: ["csv"]
}).on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
    console.log(response);
    if(response.message){
        alert(response.message);
    }
    $('#btncau').removeAttr('disabled');
}).on('fileuploaderror', function(event, data, msg) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
    console.log(response);
   console.log(msg);
   // get message
   if(msg){
        alert(msg);
   }
});

$("#svcaxls").fileinput({
    language: "es",
    uploadUrl: 'uploadpm10xls',
    showCaption: true,
    showRemove: false,
    uploadAsync: true,
    allowedPreviewTypes: null,
    fileActionSettings: {
            showZoom: false,
            showUpload: false
    },
    allowedFileExtensions: ["xls"]
}).on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
    $('#btncau').removeAttr('disabled');
}).on('fileuploaderror', function(event, data, msg) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
   console.log('File upload error');
   // get message
   //alert('El archivo no se pudo cargar, por favor verifique la estructura');
});

$("#btncsv").click(function(e) {
    $("#inputcsv").removeClass('hidden');
    $("#inputxls").addClass('hidden');
    $("#btncsv").addClass('active');
    $("#btnxls").removeClass('active')
});

$("#btnxls").click(function(e) {
    $("#inputxls").removeClass('hidden');
    $("#inputcsv").addClass('hidden');
    $("#btnxls").addClass('active');
    $("#btncsv").removeClass('active')
});