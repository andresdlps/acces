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
     
    customhovermort = function (index, options, content, row) {
       var hover = "<div class='morris-hover-row-label'>"+ row.y +"</div>";
       for(var i=0; i < options.ykeys.length; i++){
           hover += "<div class='morris-hover-point' style='color: "+ options.barColors[i]+ "'>"+  options.labels[i]+ " : " + Number(row[options.ykeys[i].toString()]).formatMoney(2, ',', '.') +"</div>";
       }
       return hover;
    }
     
    var svs;
    
    $.get('getSVs', function (data) {
        svs = data;
    });
    
    $.get('getMortConsolidadoCau', function (data) {
        console.log(svs);
        console.log(data);
        
        var data2 = [];
        
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].mortMayores45;
            mapa['b']=data[i+1].mortMayores45;
            data2.push(mapa)
        }
        console.log(data2);
        
        chart11 = Morris.Bar({
            element: 'chart11',
            data: data2,
            xkey: 'y',
            ykeys: ['a', 'b'],
            labels: ['Cáncer de pulmón', 'Cardiopulmonares'],
            hideHover: 'auto',
            barColors: ['#b52430', '#c32030'],
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].mortAvadMayor45;
            mapa['b']=data[i+1].mortAvadMayor45;
            data2.push(mapa)
        }
        
        graph12 = Morris.Bar({
            element: 'chart12',
            data: data2,
            xkey: 'y',
            ykeys: ['a', 'b'],
            labels: ['Cáncer de pulmón', 'Cardiopulmonares'],
            barColors: ['#b52430', '#c32030'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.mortTodasMenores;
            data2.push(mapa)
        }
        
        graph13 = Morris.Bar({
            element: 'chart13',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.mortTodasMenoresAVAD;
            data2.push(mapa)
        }
        
        graph14 = Morris.Bar({
            element: 'chart14',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.mortTodas;
            data2.push(mapa)
        }
        
        graph15 = Morris.Bar({
            element: 'chart15',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['#casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.mortTodasAVAD;
            data2.push(mapa)
        }
        
        graph16 = Morris.Bar({
            element: 'chart16',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['#casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.costosMortalidad;
            data2.push(mapa)
        }
        
        graph17 = Morris.Bar({
            element: 'chart17',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['Miles de Millones de Pesos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.morbBC;
            data2.push(mapa)
        }
        
        graph21 = Morris.Bar({
            element: 'chart21',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.morbBCAVAD;
            data2.push(mapa)
            
        }
        
        graph22 = Morris.Bar({
            element: 'chart22',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.morbBCCostos;
            data2.push(mapa)
        }
        
        graph23 = Morris.Bar({
            element: 'chart23',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['Miles de Millones de Pesos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.sr;
            data2.push(mapa)
        }
        
        graph31 = Morris.Bar({
            element: 'chart31',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.sravad;
            data2.push(mapa)
        }
        
        graph32 = Morris.Bar({
            element: 'chart32',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.srcostos;
            data2.push(mapa)
        }
        
        graph33 = Morris.Bar({
            element: 'chart33',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['Miles de Millones de Pesos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.morbEVRI;
            data2.push(mapa)
        }
        
        graph41 = Morris.Bar({
            element: 'chart41',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.morbEVRIAVAD;
            data2.push(mapa)
        }
        
        graph42 = Morris.Bar({
            element: 'chart42',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.morbEVRICostos;
            data2.push(mapa)
        }
        
        graph43 = Morris.Bar({
            element: 'chart43',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['Miles de Millones de Pesos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.dar;
            data2.push(mapa)
        }
        
        graph51 = Morris.Bar({
            element: 'chart51',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.daravad;
            data2.push(mapa)
        }
        
        graph52 = Morris.Bar({
            element: 'chart52',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.darcostos;
            data2.push(mapa)
        }
        
        graph53 = Morris.Bar({
            element: 'chart53',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.adm;
            data2.push(mapa)
        }
        
        graph61 = Morris.Bar({
            element: 'chart61',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.admAVAD;
            data2.push(mapa)
        }
        
        graph62 = Morris.Bar({
            element: 'chart62',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.admCostos;
            data2.push(mapa)
        }
        
        graph63 = Morris.Bar({
            element: 'chart63',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.visitas;
            data2.push(mapa)
        }
        
        graph71 = Morris.Bar({
            element: 'chart71',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.visitasAVAD;
            data2.push(mapa)
        }
        
        graph72 = Morris.Bar({
            element: 'chart72',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        data2 = [];
        for(var i=0; i < data.length; i+=2){
            var mapa = {};
            mapa['y']=svs[i/2].nombre;
            mapa['a']=data[i].cau.visitasCostos;
            data2.push(mapa)
        }
        
        graph73 = Morris.Bar({
            element: 'chart73',
            data: data2,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['casos'],
            barColors: ['#b52430'],
            hideHover: 'auto',
            resize: true,
            hoverCallback: customhovermort
        });
        
        
        
    });
    
        
    $('#tab11').on('shown.bs.tab', function (e) {
        graph11.redraw();
    });

    $('#tab12').on('shown.bs.tab', function (e) {
        graph12.redraw();
    });

    $('#tab13').on('shown.bs.tab', function (e) {
        graph13.redraw();
    });
    
    $('#tab14').on('shown.bs.tab', function (e) {
        graph14.redraw();
    });
    
    $('#tab15').on('shown.bs.tab', function (e) {
        graph15.redraw();
    });
    
    $('#tab16').on('shown.bs.tab', function (e) {
        graph16.redraw();
    });
    
    $('#tab17').on('shown.bs.tab', function (e) {
        graph17.redraw();
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
    
    $('#tab31').on('shown.bs.tab', function (e) {
        graph31.redraw();
    });

    $('#tab32').on('shown.bs.tab', function (e) {
        graph32.redraw();
    });

    $('#tab33').on('shown.bs.tab', function (e) {
        graph33.redraw();
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
    
    $('#tab61').on('shown.bs.tab', function (e) {
        graph61.redraw();
    });

    $('#tab62').on('shown.bs.tab', function (e) {
        graph62.redraw();
    });

    $('#tab63').on('shown.bs.tab', function (e) {
        graph63.redraw();
    });
    
    $('#tab71').on('shown.bs.tab', function (e) {
        graph71.redraw();
    });

    $('#tab72').on('shown.bs.tab', function (e) {
        graph72.redraw();
    });

    $('#tab73').on('shown.bs.tab', function (e) {
        graph73.redraw();
    });
});









