$(document).ready(function () {
    for(var i=2010; i<=2025; i++){
        if(i==2015){
            $('#selyear').append("<option selected>"+i+"</option>");
        }else{
            $('#selyear').append("<option>"+i+"</option>");
        }
    }
    
    
    
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
        allowedFileExtensions: ["xls"],
        uploadExtraData: function() {
            var info = {"year": $('#selyear').val()};
            return info;
        }
    }).on('filepreajax', function(event, previewId, index) {
        console.log('File pre ajax triggered');
    });
    
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
        allowedFileExtensions: ["xls"]
    });
});





