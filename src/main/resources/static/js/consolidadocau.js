$(document).ready(function () {
    $.get('getMortConsolidadoCau', function (data) {
        console.log(data)
    });
    graph1 = Morris.Bar({
            element: 'chart11',
            data: [{
                    y: 'AMVA',
                    a: 102,
                    b: 32,
                }, {
                    y: 'CAM',
                    a: 421,
                    b: 324,
                }, {
                    y: 'CAR',
                    a: 213,
                    b: 98,
                }, {
                    y: 'CARDER',
                    a: 132,
                    b: 222,
                }, {
                    y: 'CDMB',
                    a: 321,
                    b: 298,
                }, {
                    y: 'CORANTIOQUIA',
                    a: 765,
                    b: 543,
                }, {
                    y: 'CAM',
                    a: 421,
                    b: 324,
                }, {
                    y: 'CAR',
                    a: 213,
                    b: 98,
                }, {
                    y: 'CARDER',
                    a: 132,
                    b: 222,
                }, {
                    y: 'CDMB',
                    a: 321,
                    b: 298,
                }, {
                    y: 'CORANTIOQUIA',
                    a: 765,
                    b: 543,
                }, {
                    y: 'CAM',
                    a: 421,
                    b: 324,
                }, {
                    y: 'CAR',
                    a: 213,
                    b: 98,
                }, {
                    y: 'CARDER',
                    a: 132,
                    b: 222,
                }, {
                    y: 'CDMB',
                    a: 321,
                    b: 298,
                }, {
                    y: 'CORANTIOQUIA',
                    a: 765,
                    b: 543,
                }],
            xkey: 'y',
            ykeys: ['a', 'b'],
            labels: ['Cáncer de pulmón', 'Cardiopulmonares'],
            hideHover: 'auto',
            resize: true
        });
});









