$("#mortalidadxls-es").fileinput({
    language: "es",
    uploadUrl: '/acces/uploadmortalidad',
    browseClass: "btn btn-primary btn-block",
    uploadClass: "btn btn-primary btn-block",
    showCaption: false,
    showRemove: false,
    uploadAsync: true,
    previewFileIcon: '<i class="fa fa-file"></i>',
    allowedPreviewTypes: null,
    fileActionSettings: {
            showZoom: false,
            showUpload: false
    },
    allowedFileExtensions: ["xls", "xlsx"]
});

$("#poblacionxls-es").fileinput({
    language: "es",
    uploadUrl: '/acces/uploadpoblacion',
    browseClass: "btn btn-primary btn-block",
    uploadClass: "btn btn-primary btn-block",
    showCaption: false,
    showRemove: false,
    uploadAsync: true,
    previewFileIcon: '<i class="fa fa-file"></i>',
    allowedPreviewTypes: null,
    fileActionSettings: {
            showZoom: false,
            showUpload: false
    },
    allowedFileExtensions: ["xls", "xlsx"],
    uploadExtraData: {
        year: $('#selyear').val()
    }
});

