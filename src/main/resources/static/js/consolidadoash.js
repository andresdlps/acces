

$(document).ready(function () {
    Number.prototype.formatMoney = function(c, d, t){
        var n = this, 
        c = isNaN(c = Math.abs(c)) ? 2 : c, 
        d = d == undefined ? "." : d, 
        t = t == undefined ? "," : t, 
        s = n < 0 ? "-" : "", 
        i = String(parseInt(n = Math.abs(Number(n) || 0).toFixed(c))), 
        j = (j = i.length) > 3 ? j % 3 : 0;
        return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
     };
     
     customhover = function (index, options, content, row) {
        return "<div class='morris-hover-row-label'>"+ row.y +"</div><div class='morris-hover-point' style='color: #b52430'>" + options.labels[0] + " : " + Number(row.a).formatMoney(2, ',', '.') + "</div>";
     }
     
     $.get('hasConsolidadoCai', function (data) {
         if(!data){
             $('#panelMorbilidad').hide();
         }
     });
     
     $.get('getMorbDesnutricion', function (data) {
         console.log(data);
         if(data[1].morbilidadMenores5Costos === 0){
             $('#panelMorbilidad').hide();
         }
         
        var data2 = [];
        for(var i=0; i < data.length; i++){
            var mapa = {};
            mapa['y']=data[i].enfermedad;
            mapa['a']=data[i].morbilidadMenores5Total;
            data2.push(mapa)
        }
        
        graph51 = Morris.Bar({
            element: 'chart51',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['# casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });
        
        data2 = [];
        for(var i=0; i < data.length; i++){
            var mapa = {};
            mapa['y']=data[i].enfermedad;
            mapa['a']=data[i].morbilidadMenores5AVAD;
            data2.push(mapa)
        }
        
        graph52 = Morris.Bar({
            element: 'chart52',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['# casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });
        
        data2 = [];
        for(var i=0; i < data.length; i++){
            var mapa = {};
            mapa['y']=data[i].enfermedad;
            mapa['a']=data[i].morbilidadMenores5Costos;
            data2.push(mapa)
        }
        
        graph53 = Morris.Bar({
            element: 'chart53',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['Miles de millones de pesos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });
     });
     
     
     
    $.get('getConsolidadoAsh', function (data) {
        console.log(data);
        graph1 = Morris.Bar({
            element: 'chart11',
            data: [{
                    y: 'Caribe',
                    a: data[0].mortNum.toFixed(2)
                }, {
                    y: 'Oriental',
                    a: data[1].mortNum.toFixed(2)
                }, {
                    y: 'Bogota',
                    a: data[2].mortNum.toFixed(2)
                }, {
                    y: 'Central',
                    a: data[3].mortNum.toFixed(2)
                }, {
                    y: 'Pacífica',
                    a: data[4].mortNum.toFixed(2)
                }, {
                    y: 'Orinoquia \n\
                        y Amazonía',
                    a: data[5].mortNum.toFixed(2)
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['# de muertes'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph2 = Morris.Bar({
            element: 'chart12',
            data: [{
                    y: 'Caribe',
                    a: data[0].mortAVAD.toFixed(2)
                }, {
                    y: 'Oriental',
                    a: data[1].mortAVAD.toFixed(2)
                }, {
                    y: 'Bogota',
                    a: data[2].mortAVAD.toFixed(2)
                }, {
                    y: 'Central',
                    a: data[3].mortAVAD.toFixed(2)
                }, {
                    y: 'Pacífica',
                    a: data[4].mortAVAD.toFixed(2)
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[5].mortAVAD.toFixed(2)
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['AVAD'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph3 = Morris.Bar({
            element: 'chart13',
            data: [{
                    y: 'Caribe',
                    a: data[0].mortCostos.toFixed(2)
                }, {
                    y: 'Oriental',
                    a: data[1].mortCostos.toFixed(2)
                }, {
                    y: 'Bogota',
                    a: data[2].mortCostos.toFixed(2)
                }, {
                    y: 'Central',
                    a: data[3].mortCostos.toFixed(2)
                }, {
                    y: 'Pacifica',
                    a: data[4].mortCostos.toFixed(2)
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[5].mortCostos.toFixed(2)
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['Miles de millones de pesos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph21 = Morris.Bar({
            element: 'chart21',
            data: [{
                    y: 'Caribe',
                    a: data[0].morbMenor5Inc.toFixed(2)
                }, {
                    y: 'Oriental',
                    a: data[1].morbMenor5Inc.toFixed(2)
                }, {
                    y: 'Bogota',
                    a: data[2].morbMenor5Inc.toFixed(2)
                }, {
                    y: 'Central',
                    a: data[3].morbMenor5Inc.toFixed(2)
                }, {
                    y: 'Pacifica',
                    a: data[4].morbMenor5Inc.toFixed(2)
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[5].morbMenor5Inc.toFixed(2)
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph22 = Morris.Bar({
            element: 'chart22',
            data: [{
                    y: 'Caribe',
                    a: data[0].morbMenor5.toFixed(2)
                }, {
                    y: 'Oriental',
                    a: data[1].morbMenor5.toFixed(2)
                }, {
                    y: 'Bogota',
                    a: data[2].morbMenor5.toFixed(2)
                }, {
                    y: 'Central',
                    a: data[3].morbMenor5.toFixed(2)
                }, {
                    y: 'Pacifica',
                    a: data[4].morbMenor5.toFixed(2)
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[5].morbMenor5.toFixed(2)
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['# casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph23 = Morris.Bar({
            element: 'chart23',
            data: [{
                    y: 'Caribe',
                    a: data[0].morbMenor5Ash.toFixed(2)
                }, {
                    y: 'Oriental',
                    a: data[1].morbMenor5Ash.toFixed(2)
                }, {
                    y: 'Bogota',
                    a: data[2].morbMenor5Ash.toFixed(2)
                }, {
                    y: 'Central',
                    a: data[3].morbMenor5Ash.toFixed(2)
                }, {
                    y: 'Pacifica',
                    a: data[4].morbMenor5Ash.toFixed(2)
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[5].morbMenor5Ash.toFixed(2)
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['# casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph24 = Morris.Bar({
            element: 'chart24',
            data: [{
                    y: 'Caribe',
                    a: data[0].morbMenor5AVADAsh.toFixed(2)
                }, {
                    y: 'Oriental',
                    a: data[1].morbMenor5AVADAsh.toFixed(2)
                }, {
                    y: 'Bogota',
                    a: data[2].morbMenor5AVADAsh.toFixed(2)
                }, {
                    y: 'Central',
                    a: data[3].morbMenor5AVADAsh.toFixed(2)
                }, {
                    y: 'Pacifica',
                    a: data[4].morbMenor5AVADAsh.toFixed(2)
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[5].morbMenor5AVADAsh.toFixed(2)
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['# casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph25 = Morris.Bar({
            element: 'chart25',
            data: [{
                    y: 'Caribe',
                    a: data[0].morbMenor5CostosAsh.toFixed(2)
                }, {
                    y: 'Oriental',
                    a: data[1].morbMenor5CostosAsh.toFixed(2)
                }, {
                    y: 'Bogota',
                    a: data[2].morbMenor5CostosAsh.toFixed(2)
                }, {
                    y: 'Central',
                    a: data[3].morbMenor5CostosAsh.toFixed(2)
                }, {
                    y: 'Pacifica',
                    a: data[4].morbMenor5CostosAsh.toFixed(2)
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[5].morbMenor5CostosAsh.toFixed(2)
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['Miles de millones de pesos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph31 = Morris.Bar({
            element: 'chart31',
            data: [{
                    y: 'Caribe',
                    a: data[0].morbMayor5Inc.toFixed(2)
                }, {
                    y: 'Oriental',
                    a: data[1].morbMayor5Inc.toFixed(2)
                }, {
                    y: 'Bogota',
                    a: data[2].morbMayor5Inc.toFixed(2)
                }, {
                    y: 'Central',
                    a: data[3].morbMayor5Inc.toFixed(2)
                }, {
                    y: 'Pacifica',
                    a: data[4].morbMayor5Inc.toFixed(2)
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[5].morbMayor5Inc.toFixed(2)
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph32 = Morris.Bar({
            element: 'chart32',
            data: [{
                    y: 'Caribe',
                    a: data[0].morbMayor5.toFixed(2)
                }, {
                    y: 'Oriental',
                    a: data[1].morbMayor5.toFixed(2)
                }, {
                    y: 'Bogota',
                    a: data[2].morbMayor5.toFixed(2)
                }, {
                    y: 'Central',
                    a: data[3].morbMayor5.toFixed(2)
                }, {
                    y: 'Pacifica',
                    a: data[4].morbMayor5.toFixed(2)
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[5].morbMayor5.toFixed(2)
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['# casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph33 = Morris.Bar({
            element: 'chart33',
            data: [{
                    y: 'Caribe',
                    a: data[0].morbMayor5Ash.toFixed(2)
                }, {
                    y: 'Oriental',
                    a: data[1].morbMayor5Ash.toFixed(2)
                }, {
                    y: 'Bogota',
                    a: data[2].morbMayor5Ash.toFixed(2)
                }, {
                    y: 'Central',
                    a: data[3].morbMayor5Ash.toFixed(2)
                }, {
                    y: 'Pacifica',
                    a: data[4].morbMayor5Ash.toFixed(2)
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[5].morbMayor5Ash.toFixed(2)
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['# casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph34 = Morris.Bar({
            element: 'chart34',
            data: [{
                    y: 'Caribe',
                    a: data[0].morbMayor5AVADAsh.toFixed(2)
                }, {
                    y: 'Oriental',
                    a: data[1].morbMayor5AVADAsh.toFixed(2)
                }, {
                    y: 'Bogota',
                    a: data[2].morbMayor5AVADAsh.toFixed(2)
                }, {
                    y: 'Central',
                    a: data[3].morbMayor5AVADAsh.toFixed(2)
                }, {
                    y: 'Pacifica',
                    a: data[4].morbMayor5AVADAsh.toFixed(2)
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[5].morbMayor5AVADAsh.toFixed(2)
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['# casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph35 = Morris.Bar({
            element: 'chart35',
            data: [{
                    y: 'Caribe',
                    a: data[0].morbMayor5CostosAsh.toFixed(2)
                }, {
                    y: 'Oriental',
                    a: data[1].morbMayor5CostosAsh.toFixed(2)
                }, {
                    y: 'Bogota',
                    a: data[2].morbMayor5CostosAsh.toFixed(2)
                }, {
                    y: 'Central',
                    a: data[3].morbMayor5CostosAsh.toFixed(2)
                }, {
                    y: 'Pacifica',
                    a: data[4].morbMayor5CostosAsh.toFixed(2)
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[5].morbMayor5CostosAsh.toFixed(2)
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['Miles de millones de pesos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });


    });
    
    
    $.get('getMortDesnutricion', function (data) {
        console.log(data);
        var data2 = [];
        for(var i=0; i < data.length; i++){
            var mapa = {};
            mapa['y']=data[i].enfermedad;
            mapa['a']=data[i].muertesMenoresHombres;
            data2.push(mapa)
        }
        
        graph41 = Morris.Bar({
            element: 'chart41',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['# casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });
        
        data2 = [];
        for(var i=0; i < data.length; i++){
            var mapa = {};
            mapa['y']=data[i].enfermedad;
            mapa['a']=data[i].muertesMenoresMujeres;
            data2.push(mapa)
        }
        
        graph42 = Morris.Bar({
            element: 'chart42',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['# casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });
        
        data2 = [];
        for(var i=0; i < data.length; i++){
            var mapa = {};
            mapa['y']=data[i].enfermedad;
            mapa['a']=data[i].mortalidadMenoresCostos;
            data2.push(mapa)
        }
        
        graph43 = Morris.Bar({
            element: 'chart43',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['Miles de millones de pesos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });
    });

    $('#tab11').on('shown.bs.tab', function (e) {
        graph1.redraw();
    });

    $('#tab11').on('shown.bs.tab', function (e) {
        graph1.redraw();
    });

    $('#tab12').on('shown.bs.tab', function (e) {
        graph2.redraw();
    });

    $('#tab13').on('shown.bs.tab', function (e) {
        graph3.redraw();
    });

    $('#tab21').on('shown.bs.tab', function (e) {
        graph21.redraw();
    });

    $('#tab22').on('shown.bs.tab', function (e) {
        graph22.redraw();
    });

    $('#tab23').on('shown.bs.tab', function (e) {
        graph23.redraw();
    });

    $('#tab24').on('shown.bs.tab', function (e) {
        graph24.redraw();
    });

    $('#tab25').on('shown.bs.tab', function (e) {
        graph25.redraw();
    });

    $('#tab31').on('shown.bs.tab', function (e) {
        graph31.redraw();
    });

    $('#tab32').on('shown.bs.tab', function (e) {
        graph32.redraw();
    });

    $('#tab33').on('shown.bs.tab', function (e) {
        graph33.redraw();
    });

    $('#tab34').on('shown.bs.tab', function (e) {
        graph34.redraw();
    });

    $('#tab35').on('shown.bs.tab', function (e) {
        graph35.redraw();
    });
    
    $('#tab41').on('shown.bs.tab', function (e) {
        graph41.redraw();
    });

    $('#tab42').on('shown.bs.tab', function (e) {
        graph42.redraw();
    });

    $('#tab43').on('shown.bs.tab', function (e) {
        graph43.redraw();
    });
    
    $('#tab51').on('shown.bs.tab', function (e) {
        graph51.redraw();
    });

    $('#tab52').on('shown.bs.tab', function (e) {
        graph52.redraw();
    });

    $('#tab53').on('shown.bs.tab', function (e) {
        graph53.redraw();
    });
});






