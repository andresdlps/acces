$("#menu-toggle").click(function (e) {
    e.preventDefault();
    $("#datawrapper").toggleClass("toggled");
});

var globalData;

$(document).ready(function () {
    $.get('getdata', function (data) {
        console.log(data);
        globalData = data;
        var allRows = data.split(':');
        var table = "<table class='display responsive nowrap' cellspacing='0' width='100%' id='csv-table'>";
        for (var singleRow = 0; singleRow < allRows.length-1; singleRow++) {
            if (singleRow === 0) {
                table += '<thead>';
                table += '<tr>';
            } else {
                table += '<tr>';
            }
            var rowCells = allRows[singleRow].split(';');
            for (var rowCell = 0; rowCell < rowCells.length; rowCell++) {
                if (singleRow === 0) {
                    table += '<th>';
                    table += rowCells[rowCell];
                    table += '</th>';
                } else {
                    table += '<td>';
                    table += rowCells[rowCell];
                    table += '</td>';
                }
            }
            if (singleRow === 0) {
                table += '</tr>';
                table += '</thead>';
                table += '<tbody>';
            } else {
                table += '</tr>';
            }
        }
        table += '</tbody>';
        table += '</table>';
        $('#panel-body').append(table);
        $('#csv-table').DataTable({});
    });
});

