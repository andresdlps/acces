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
        console.log(content);
        return "<div class='morris-hover-row-label'>"+ row.y +"</div><div class='morris-hover-point' style='color: #b52430'>" + options.labels[0] + " : " + Number(row.a).formatMoney(2, ',', '.') + "</div>";
     }
     
     customhovermort = function (index, options, content, row) {
        var hover = "<div class='morris-hover-row-label'>"+ row.y +"</div>";
        for(var i=0; i < options.ykeys.length; i++){
            hover += "<div class='morris-hover-point' style='color: "+ options.barColors[i]+ "'>"+  options.labels[i]+ " : " + Number(row[options.ykeys[i].toString()]).formatMoney(2, ',', '.') +"</div>";
        }
        return hover;
     }
       
     
    $.get('getMortConsolidadoCai', function (data) {
//        data.forEach(function (d) {
//            console.log(d.mortCostos);
//        });
        console.log(data);
        var i = 0;
        graph1 = Morris.Bar({
            element: 'chart11',
            data: [{
                    y: 'Caribe',
                    a: data[i++].morteMujeresMayores44,
                    b: data[i++].morteMujeresMayores44,
                    c: data[i++].morteMujeresMayores44,
                    d: data[i++].morteMujeresMayores44,
                    e: data[i++].morteMujeresMayores44,
                    f: data[i++].morteMujeresMayores44,
                }, {
                    y: 'Oriental',
                    a: data[i++].morteMujeresMayores44,
                    b: data[i++].morteMujeresMayores44,
                    c: data[i++].morteMujeresMayores44,
                    d: data[i++].morteMujeresMayores44,
                    e: data[i++].morteMujeresMayores44,
                    f: data[i++].morteMujeresMayores44,
                }, {
                    y: 'Bogota',
                    a: data[i++].morteMujeresMayores44,
                    b: data[i++].morteMujeresMayores44,
                    c: data[i++].morteMujeresMayores44,
                    d: data[i++].morteMujeresMayores44,
                    e: data[i++].morteMujeresMayores44,
                    f: data[i++].morteMujeresMayores44,
                }, {
                    y: 'Central',
                    a: data[i++].morteMujeresMayores44,
                    b: data[i++].morteMujeresMayores44,
                    c: data[i++].morteMujeresMayores44,
                    d: data[i++].morteMujeresMayores44,
                    e: data[i++].morteMujeresMayores44,
                    f: data[i++].morteMujeresMayores44,
                }, {
                    y: 'Pacífica',
                    a: data[i++].morteMujeresMayores44,
                    b: data[i++].morteMujeresMayores44,
                    c: data[i++].morteMujeresMayores44,
                    d: data[i++].morteMujeresMayores44,
                    e: data[i++].morteMujeresMayores44,
                    f: data[i++].morteMujeresMayores44,
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[i++].morteMujeresMayores44,
                    b: data[i++].morteMujeresMayores44,
                    c: data[i++].morteMujeresMayores44,
                    d: data[i++].morteMujeresMayores44,
                    e: data[i++].morteMujeresMayores44,
                    f: data[i++].morteMujeresMayores44,
                }],
            xkey: 'y',
            ykeys: ['a', 'b', 'c', 'd', 'e', 'f'],
            labels: ['Infecciones respiratorias agudas', 'T. M. de tráquea, bronquios y pulmón', 'Enfermedades hipertensivas', 'Enfermedades isquémicas del corazón', 'Enfermedades cerebrovasculares', 'Enf. Crónicas vías respiratorias inferiores'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        i = 0;
        graph2 = Morris.Bar({
            element: 'chart12',
            data: [{
                    y: 'Caribe',
                    a: data[i++].morteMujeresMenores5,
                    b: data[i++].morteMujeresMenores5,
                    c: data[i++].morteMujeresMenores5,
                    d: data[i++].morteMujeresMenores5,
                    e: data[i++].morteMujeresMenores5,
                    f: data[i++].morteMujeresMenores5,
                }, {
                    y: 'Oriental',
                    a: data[i++].morteMujeresMenores5,
                    b: data[i++].morteMujeresMenores5,
                    c: data[i++].morteMujeresMenores5,
                    d: data[i++].morteMujeresMenores5,
                    e: data[i++].morteMujeresMenores5,
                    f: data[i++].morteMujeresMenores5,
                }, {
                    y: 'Bogota',
                    a: data[i++].morteMujeresMenores5,
                    b: data[i++].morteMujeresMenores5,
                    c: data[i++].morteMujeresMenores5,
                    d: data[i++].morteMujeresMenores5,
                    e: data[i++].morteMujeresMenores5,
                    f: data[i++].morteMujeresMenores5,
                }, {
                    y: 'Central',
                    a: data[i++].morteMujeresMenores5,
                    b: data[i++].morteMujeresMenores5,
                    c: data[i++].morteMujeresMenores5,
                    d: data[i++].morteMujeresMenores5,
                    e: data[i++].morteMujeresMenores5,
                    f: data[i++].morteMujeresMenores5,
                }, {
                    y: 'Pacífica',
                    a: data[i++].morteMujeresMenores5,
                    b: data[i++].morteMujeresMenores5,
                    c: data[i++].morteMujeresMenores5,
                    d: data[i++].morteMujeresMenores5,
                    e: data[i++].morteMujeresMenores5,
                    f: data[i++].morteMujeresMenores5,
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[i++].morteMujeresMenores5,
                    b: data[i++].morteMujeresMenores5,
                    c: data[i++].morteMujeresMenores5,
                    d: data[i++].morteMujeresMenores5,
                    e: data[i++].morteMujeresMenores5,
                    f: data[i++].morteMujeresMenores5,
                }],
            xkey: 'y',
            ykeys: ['a', 'b', 'c', 'd', 'e', 'f'],
            labels: ['Infecciones respiratorias agudas', 'T. M. de tráquea, bronquios y pulmón', 'Enfermedades hipertensivas', 'Enfermedades isquémicas del corazón', 'Enfermedades cerebrovasculares', 'Enf. Crónicas vías respiratorias inferiores'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        i = 0;
        graph3 = Morris.Bar({
            element: 'chart13',
            data: [{
                    y: 'Caribe',
                    a: data[i++].mortAvad,
                    b: data[i++].mortAvad,
                    c: data[i++].mortAvad,
                    d: data[i++].mortAvad,
                    e: data[i++].mortAvad,
                    f: data[i++].mortAvad,
                }, {
                    y: 'Oriental',
                    a: data[i++].mortAvad,
                    b: data[i++].mortAvad,
                    c: data[i++].mortAvad,
                    d: data[i++].mortAvad,
                    e: data[i++].mortAvad,
                    f: data[i++].mortAvad,
                }, {
                    y: 'Bogota',
                    a: data[i++].mortAvad,
                    b: data[i++].mortAvad,
                    c: data[i++].mortAvad,
                    d: data[i++].mortAvad,
                    e: data[i++].mortAvad,
                    f: data[i++].mortAvad,
                }, {
                    y: 'Central',
                    a: data[i++].mortAvad,
                    b: data[i++].mortAvad,
                    c: data[i++].mortAvad,
                    d: data[i++].mortAvad,
                    e: data[i++].mortAvad,
                    f: data[i++].mortAvad,
                }, {
                    y: 'Pacífica',
                    a: data[i++].mortAvad,
                    b: data[i++].mortAvad,
                    c: data[i++].mortAvad,
                    d: data[i++].mortAvad,
                    e: data[i++].mortAvad,
                    f: data[i++].mortAvad,
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[i++].mortAvad,
                    b: data[i++].mortAvad,
                    c: data[i++].mortAvad,
                    d: data[i++].mortAvad,
                    e: data[i++].mortAvad,
                    f: data[i++].mortAvad,
                }],
            xkey: 'y',
            ykeys: ['a', 'b', 'c', 'd', 'e', 'f'],
            labels: ['Infecciones respiratorias agudas', 'T. M. de tráquea, bronquios y pulmón', 'Enfermedades hipertensivas', 'Enfermedades isquémicas del corazón', 'Enfermedades cerebrovasculares', 'Enf. Crónicas vías respiratorias inferiores'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        i = 0;
        graph4 = Morris.Bar({
            element: 'chart14',
            data: [{
                    y: 'Caribe',
                    a: data[i++].mortCostos,
                    b: data[i++].mortCostos,
                    c: data[i++].mortCostos,
                    d: data[i++].mortCostos,
                    e: data[i++].mortCostos,
                    f: data[i++].mortCostos,
                }, {
                    y: 'Oriental',
                    a: data[i++].mortCostos,
                    b: data[i++].mortCostos,
                    c: data[i++].mortCostos,
                    d: data[i++].mortCostos,
                    e: data[i++].mortCostos,
                    f: data[i++].mortCostos,
                }, {
                    y: 'Bogota',
                    a: data[i++].mortCostos,
                    b: data[i++].mortCostos,
                    c: data[i++].mortCostos,
                    d: data[i++].mortCostos,
                    e: data[i++].mortCostos,
                    f: data[i++].mortCostos,
                }, {
                    y: 'Central',
                    a: data[i++].mortCostos,
                    b: data[i++].mortCostos,
                    c: data[i++].mortCostos,
                    d: data[i++].mortCostos,
                    e: data[i++].mortCostos,
                    f: data[i++].mortCostos,
                }, {
                    y: 'Pacífica',
                    a: data[i++].mortCostos,
                    b: data[i++].mortCostos,
                    c: data[i++].mortCostos,
                    d: data[i++].mortCostos,
                    e: data[i++].mortCostos,
                    f: data[i++].mortCostos,
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[i++].mortCostos,
                    b: data[i++].mortCostos,
                    c: data[i++].mortCostos,
                    d: data[i++].mortCostos,
                    e: data[i++].mortCostos,
                    f: data[i++].mortCostos,
                }],
            xkey: 'y',
            ykeys: ['a', 'b', 'c', 'd', 'e', 'f'],
            labels: ['Infecciones respiratorias agudas', 'T. M. de tráquea, bronquios y pulmón', 'Enfermedades hipertensivas', 'Enfermedades isquémicas del corazón', 'Enfermedades cerebrovasculares', 'Enf. Crónicas vías respiratorias inferiores'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        graph21 = Morris.Bar({
            element: 'chart21',
            data: [{
                    y: 'Caribe',
                    a: data[0].cai.morbMenor5Inc
                }, {
                    y: 'Oriental',
                    a: data[6].cai.morbMenor5Inc
                }, {
                    y: 'Bogota',
                    a: data[12].cai.morbMenor5Inc
                }, {
                    y: 'Central',
                    a: data[18].cai.morbMenor5Inc
                }, {
                    y: 'Pacifica',
                    a: data[24].cai.morbMenor5Inc
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[30].cai.morbMenor5Inc
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
                    a: data[0].cai.morbMenor5
                }, {
                    y: 'Oriental',
                    a: data[6].cai.morbMenor5
                }, {
                    y: 'Bogota',
                    a: data[12].cai.morbMenor5
                }, {
                    y: 'Central',
                    a: data[18].cai.morbMenor5
                }, {
                    y: 'Pacifica',
                    a: data[24].cai.morbMenor5
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[30].cai.morbMenor5
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
                    a: data[0].cai.morbMenor5Cai
                }, {
                    y: 'Oriental',
                    a: data[6].cai.morbMenor5Cai
                }, {
                    y: 'Bogota',
                    a: data[12].cai.morbMenor5Cai
                }, {
                    y: 'Central',
                    a: data[18].cai.morbMenor5Cai
                }, {
                    y: 'Pacifica',
                    a: data[24].cai.morbMenor5Cai
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[30].cai.morbMenor5Cai
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
                    a: data[0].cai.morbMenor5AVADCai
                }, {
                    y: 'Oriental',
                    a: data[6].cai.morbMenor5AVADCai
                }, {
                    y: 'Bogota',
                    a: data[12].cai.morbMenor5AVADCai
                }, {
                    y: 'Central',
                    a: data[18].cai.morbMenor5AVADCai
                }, {
                    y: 'Pacifica',
                    a: data[24].cai.morbMenor5AVADCai
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[30].cai.morbMenor5AVADCai
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
                    a: data[0].cai.morbMenor5CostosCai
                }, {
                    y: 'Oriental',
                    a: data[6].cai.morbMenor5CostosCai
                }, {
                    y: 'Bogota',
                    a: data[12].cai.morbMenor5CostosCai
                }, {
                    y: 'Central',
                    a: data[18].cai.morbMenor5CostosCai
                }, {
                    y: 'Pacifica',
                    a: data[24].cai.morbMenor5CostosCai
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[30].cai.morbMenor5CostosCai
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
                    a: data[0].cai.morbMayor15Inc
                }, {
                    y: 'Oriental',
                    a: data[6].cai.morbMayor15Inc
                }, {
                    y: 'Bogota',
                    a: data[12].cai.morbMayor15Inc
                }, {
                    y: 'Central',
                    a: data[18].cai.morbMayor15Inc
                }, {
                    y: 'Pacifica',
                    a: data[24].cai.morbMayor15Inc
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[30].cai.morbMayor15Inc
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
                    a: data[0].cai.morbMayor15
                }, {
                    y: 'Oriental',
                    a: data[6].cai.morbMayor15
                }, {
                    y: 'Bogota',
                    a: data[12].cai.morbMayor15
                }, {
                    y: 'Central',
                    a: data[18].cai.morbMayor15
                }, {
                    y: 'Pacifica',
                    a: data[24].cai.morbMayor15
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[30].cai.morbMayor15
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
                    a: data[0].cai.morbMayor15Cai
                }, {
                    y: 'Oriental',
                    a: data[6].cai.morbMayor15Cai
                }, {
                    y: 'Bogota',
                    a: data[12].cai.morbMayor15Cai
                }, {
                    y: 'Central',
                    a: data[18].cai.morbMayor15Cai
                }, {
                    y: 'Pacifica',
                    a: data[24].cai.morbMayor15Cai
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[30].cai.morbMayor15Cai
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
                    a: data[0].cai.morbMayor15AVADCai
                }, {
                    y: 'Oriental',
                    a: data[6].cai.morbMayor15AVADCai
                }, {
                    y: 'Bogota',
                    a: data[12].cai.morbMayor15AVADCai
                }, {
                    y: 'Central',
                    a: data[18].cai.morbMayor15AVADCai
                }, {
                    y: 'Pacifica',
                    a: data[24].cai.morbMayor15AVADCai
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[30].cai.morbMayor15AVADCai
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
                    a: data[0].cai.morbMayor15CostosCai
                }, {
                    y: 'Oriental',
                    a: data[6].cai.morbMayor15CostosCai
                }, {
                    y: 'Bogota',
                    a: data[12].cai.morbMayor15CostosCai
                }, {
                    y: 'Central',
                    a: data[18].cai.morbMayor15CostosCai
                }, {
                    y: 'Pacifica',
                    a: data[24].cai.morbMayor15CostosCai
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[30].cai.morbMayor15CostosCai
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['Miles de millones de pesos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph42 = Morris.Bar({
            element: 'chart42',
            data: [{
                    y: 'Caribe',
                    a: data[0].cai.morbMayor15EPOC
                }, {
                    y: 'Oriental',
                    a: data[6].cai.morbMayor15EPOC
                }, {
                    y: 'Bogota',
                    a: data[12].cai.morbMayor15EPOC
                }, {
                    y: 'Central',
                    a: data[18].cai.morbMayor15EPOC
                }, {
                    y: 'Pacifica',
                    a: data[24].cai.morbMayor15EPOC
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[30].cai.morbMayor15EPOC
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['# casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph43 = Morris.Bar({
            element: 'chart43',
            data: [{
                    y: 'Caribe',
                    a: data[0].cai.morbMayor15CaiEPOC
                }, {
                    y: 'Oriental',
                    a: data[6].cai.morbMayor15CaiEPOC
                }, {
                    y: 'Bogota',
                    a: data[12].cai.morbMayor15CaiEPOC
                }, {
                    y: 'Central',
                    a: data[18].cai.morbMayor15CaiEPOC
                }, {
                    y: 'Pacifica',
                    a: data[24].cai.morbMayor15CaiEPOC
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[30].cai.morbMayor15CaiEPOC
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['# casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph44 = Morris.Bar({
            element: 'chart44',
            data: [{
                    y: 'Caribe',
                    a: data[0].cai.morbMayor15AVADCaiEPOC
                }, {
                    y: 'Oriental',
                    a: data[6].cai.morbMayor15AVADCaiEPOC
                }, {
                    y: 'Bogota',
                    a: data[12].cai.morbMayor15AVADCaiEPOC
                }, {
                    y: 'Central',
                    a: data[18].cai.morbMayor15AVADCaiEPOC
                }, {
                    y: 'Pacifica',
                    a: data[24].cai.morbMayor15AVADCaiEPOC
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[30].cai.morbMayor15AVADCaiEPOC
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['# casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph45 = Morris.Bar({
            element: 'chart45',
            data: [{
                    y: 'Caribe',
                    a: data[0].cai.morbMayor15CostosCaiEPOC
                }, {
                    y: 'Oriental',
                    a: data[6].cai.morbMayor15CostosCaiEPOC
                }, {
                    y: 'Bogota',
                    a: data[12].cai.morbMayor15CostosCaiEPOC
                }, {
                    y: 'Central',
                    a: data[18].cai.morbMayor15CostosCaiEPOC
                }, {
                    y: 'Pacifica',
                    a: data[24].cai.morbMayor15CostosCaiEPOC
                }, {
                    y: 'Orinoquia \n\
                y Amazonía',
                    a: data[30].cai.morbMayor15CostosCaiEPOC
                }],
            xkey: 'y',
            ykeys: ['a'],
            labels: ['Miles de millones de pesos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhover
        });

        graph1.options.labels.forEach(function (label, i) {
            var legendItem = $('<span></span>').text(label).prepend('<i>&nbsp;</i>');
            legendItem.find('i').css('backgroundColor', graph1.options.barColors[i]);
            $('#legend1').append(legendItem)
        })

        graph2.options.labels.forEach(function (label, i) {
            var legendItem = $('<span></span>').text(label).prepend('<i>&nbsp;</i>');
            legendItem.find('i').css('backgroundColor', graph2.options.barColors[i]);
            $('#legend2').append(legendItem)
        })

        graph3.options.labels.forEach(function (label, i) {
            var legendItem = $('<span></span>').text(label).prepend('<i>&nbsp;</i>');
            legendItem.find('i').css('backgroundColor', graph3.options.barColors[i]);
            $('#legend3').append(legendItem)
        })

        graph4.options.labels.forEach(function (label, i) {
            var legendItem = $('<span></span>').text(label).prepend('<i>&nbsp;</i>');
            legendItem.find('i').css('backgroundColor', graph4.options.barColors[i]);
            $('#legend4').append(legendItem)
        })
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
    
    $('#tab14').on('shown.bs.tab', function (e) {
       graph4.redraw();
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

    $('#tab42').on('shown.bs.tab', function (e) {
        graph42.redraw();
    });

    $('#tab43').on('shown.bs.tab', function (e) {
        graph43.redraw();
    });

    $('#tab44').on('shown.bs.tab', function (e) {
        graph44.redraw();
    });

    $('#tab45').on('shown.bs.tab', function (e) {
        graph45.redraw();
    });

});









