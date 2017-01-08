$("#svca").fileinput({
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
    allowedFileExtensions: ["xls", "xlsx"]
}).on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
    $('#btncau').removeAttr('disabled');
}).on('fileuploaderror', function(event, data, msg) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
   console.log('File upload error');
   // get message
   alert('El archivo no se pudo cargar, por favor verifique la estructura');
});