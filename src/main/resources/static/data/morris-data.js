$(function() {
    Morris.Bar({
        element: 'morris-bar-chart4',
        data: [{
            y: 'Region 1',
            a: 100
        }, {
            y: 'Region 2',
            a: 75
        }, {
            y: 'Region 3',
            a: 50
        }, {
            y: 'Region 4',
            a: 75
        }, {
            y: 'Region 5',
            a: 50
        }, {
            y: 'Region 6',
            a: 75
        }],
        xkey: 'y',
        ykeys: ['a'],
        labels: ['Series A'],  
        barColors: ['#00a65a'],
        hideHover: 'auto',
        resize: true
    });
    
   
    
});
