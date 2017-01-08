$(function () {
    Highcharts.chart('chart1', {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Porcentaje nacional CAI, CAU y ASH'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            name: 'Causa',
            colorByPoint: true,
            data: [{
                name: 'CAU',
                y: 56.33
            }, {
                name: 'CAI',
                y: 21.03,
                sliced: true,
                selected: true
            }, {
                name: 'ASH',
                y: 22.64
            }]
        }]
    });
});