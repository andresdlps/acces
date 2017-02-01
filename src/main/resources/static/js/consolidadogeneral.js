var consolidadoash, consolidadocai, consolidadocau, consolidadomortcai, mortdesnutricion, morbdesnutricion;

function updatecomparaciones() {
    var costos = $("#costosTotales").text().split('.').join("").replace(/,/g, '.').split('$').join("");
    var compgeneral = (Number(costos) / Number($('#PIB').val())) * 100;
    var comparacionPIB = document.getElementById('comparacionPIB');
    comparacionPIB.removeChild(comparacionPIB.firstChild);
    document.getElementById('comparacionPIB').appendChild(document.createTextNode(Number(compgeneral).formatMoney(2, ',', '.') + "%"));

    costos = $("#costosTotalesCau").text().split('.').join("").replace(/,/g, '.').split('$').join("");
    compgeneral = (Number(costos) / Number($('#PIB').val())) * 100;
    var comparacionPIBCau = document.getElementById('comparacionPIBCau');
    comparacionPIBCau.removeChild(comparacionPIBCau.firstChild);
    document.getElementById('comparacionPIBCau').appendChild(document.createTextNode(Number(compgeneral).formatMoney(2, ',', '.') + "%"));

    costos = $("#costosTotalesCai").text().split('.').join("").replace(/,/g, '.').split('$').join("");
    compgeneral = (Number(costos) / Number($('#PIB').val())) * 100;
    comparacionPIB = document.getElementById('comparacionPIBCai');
    comparacionPIB.removeChild(comparacionPIB.firstChild);
    comparacionPIB.appendChild(document.createTextNode(Number(compgeneral).formatMoney(2, ',', '.') + "%"));

    costos = $("#costosTotalesAsh").text().split('.').join("").replace(/,/g, '.').split('$').join("");
    compgeneral = (Number(costos) / Number($('#PIB').val())) * 100;
    comparacionPIB = document.getElementById('comparacionPIBAsh');
    comparacionPIB.removeChild(comparacionPIB.firstChild);
    comparacionPIB.appendChild(document.createTextNode(Number(compgeneral).formatMoney(2, ',', '.') + "%"));


}

function updatedata() {

//    console.log(consolidadoash);
//    console.log(consolidadocai);
//    console.log(consolidadocau);
//    console.log(consolidadomortcai);
//    console.log(mortdesnutricion);
    console.log(morbdesnutricion);

    var labels = ["Muerte por exposición (corto y largo plazo)", "Morbilidad Bronquitis Crónica", "Morbilidad Sintomas Respiratorios", "Morbilidad Dias de actividad restringida", "Morbilidad Vias respiratorias inferiores", "Morbilidad Admisiones hosp. por causas respiratorias", "Morbilidad visitas a urgencias"]
    var keys = ["costosMortalidad", "morbBCCostos", "srcostos", "darcostos", "morbEVRICostos", "admCostos", "visitasCostos"]
    var data2 = [];

    var sumacau = 0;
    for (var i = 0; i < 7; i++) {
        var mapa = {};
        mapa['label'] = labels[i];
        var sum = 0;
        for (var j = 0; j < consolidadocau.length; j++) {
            sum += consolidadocau[j][keys[i]];
        }
        sumacau += sum;
        mapa['value'] = sum;
        data2.push(mapa);
    }

    var costosTotalesCau = document.getElementById('costosTotalesCau');
    costosTotalesCau.removeChild(costosTotalesCau.firstChild);
    costosTotalesCau.appendChild(document.createTextNode("$" + Number(sumacau).formatMoney(2, ',', '.')));

    graph2 = Morris.Donut({
        element: 'chart2',
        data: data2,
        formatter: customhovermoney
    });

    labels = ["Mortalidad Mujeres mayores a 44 años", "Morbilidad menores de 5 años por IRA", "Morbilidad mujeres mayores de 45 años por IRA", "Morbilidad mujeres mayores de 45 años por EPOC"]
    keys = ["mortCostos", "morbMenor5CostosCai", "morbMayor15CostosCai", "morbMayor15CostosCaiEPOC"]
    data2 = [];
    var sumacai = 0;
    for (var i = 0; i < 4; i++) {
        var mapa = {};
        mapa['label'] = labels[i];
        var sum = 0;
        if (i == 0) {
            for (var j = 0; j < consolidadomortcai.length; j++) {
                sum += consolidadomortcai[j][keys[i]];
            }
        } else {
            for (var j = 0; j < consolidadocai.length; j++) {
                sum += consolidadocai[j][keys[i]];
            }
        }
        sumacai += sum;
        mapa['value'] = sum;
        data2.push(mapa);
    }

    console.log(data2);

    var costosTotalesCai = document.getElementById('costosTotalesCai');
    costosTotalesCai.removeChild(costosTotalesCai.firstChild);
    costosTotalesCai.appendChild(document.createTextNode("$" + Number(sumacai).formatMoney(2, ',', '.')));

    graph3 = Morris.Donut({
        element: 'chart3',
        data: data2,
        formatter: customhovermoney
    });

    labels = ["Mortalidad Menores por enfermedades intestinales", "Morbilidad Diarrea en menores de 5 años", "Morbilidad Diarrea en mayores de 5 años"]
    keys = ["mortCostos", "morbMenor5CostosAsh", "morbMayor5CostosAsh"]
    data2 = [];
    var sumaash = 0;
    for (var i = 0; i < 3; i++) {
        var mapa = {};
        mapa['label'] = labels[i];
        var sum = 0;
        for (var j = 0; j < consolidadoash.length; j++) {
            sum += consolidadoash[j][keys[i]];
        }
        sumaash += sum;
        mapa['value'] = sum;
        data2.push(mapa);
    }

    var mapa2 = {};
    mapa2['label'] = 'Mortalidad desnutrición';
    var sum2 = 0;
    for (var j = 0; j < mortdesnutricion.length; j++) {
        sum2 += mortdesnutricion[j].mortalidadMenoresCostos;
    }
    sumaash += sum2;
    mapa2['value'] = sum2;
    data2.push(mapa2);

    mapa2 = {};
    mapa2['label'] = 'Morbilidad desnutrición';
    sum2 = 0;
    for (var j = 0; j < morbdesnutricion.length; j++) {
        sum2 += morbdesnutricion[j].morbilidadMenores5Costos;
    }
    sumaash += sum2;
    if(sum2 == 0.0){
        document.getElementById("desnutricion-warning").appendChild(document.createTextNode("El cálculo de morbilidad a causa de desnutrición no se encuentra actualizado. Para actualizarlo ingrese nuevamente los insumos para el calculo de ASH."));
    }else{
        $('.alert-warning').remove();
    }   
    mapa2['value'] = sum2;
    data2.push(mapa2);

    console.log(data2);



    var costosTotalesAsh = document.getElementById('costosTotalesAsh');
    costosTotalesAsh.removeChild(costosTotalesAsh.firstChild);
    costosTotalesAsh.appendChild(document.createTextNode("$" + Number(sumaash).formatMoney(2, ',', '.')));

    graph4 = Morris.Donut({
        element: 'chart4',
        data: data2,
        formatter: customhovermoney
    });

    var sumatotal = sumaash + sumacai + sumacau;
    var costosTotales = document.getElementById('costosTotales');
    costosTotales.removeChild(costosTotales.firstChild);
    costosTotales.appendChild(document.createTextNode("$" + Number(sumatotal).formatMoney(2, ',', '.')));

    graph1 = Morris.Donut({
        element: 'chart1',
        data: [
            {label: "Calidad de aire intramuros", value: sumacai},
            {label: "Calidad de aire urbano", value: sumacau},
            {label: "Agua, Saneamiento e Higiene", value: sumaash}
        ],
        formatter: customhovermoney
    });


    var indexmayor = 0;
    var mayor = 0;
    for (i = 0; i < graph1.segments.length; i++) {
        graph1.segments[i].handlers['hover'].push(function (i) {
            $('#chartinfo1').text(graph1.data[i].label);
        });
        if (graph1.data[i].value > mayor) {
            mayor = graph1.data[i].value;
            indexmayor = i;
        }
    }
    $('#chartinfo1').text(graph1.data[indexmayor].label);


    indexmayor = 0;
    mayor = 0;
    for (i = 0; i < graph2.segments.length; i++) {
        graph2.segments[i].handlers['hover'].push(function (i) {
            $('#chartinfo2').text(graph2.data[i].label);
        });
        if (graph2.data[i].value > mayor) {
            mayor = graph2.data[i].value;
            indexmayor = i;
        }
    }
    $('#chartinfo2').text(graph2.data[indexmayor].label);

    indexmayor = 0;
    mayor = 0;
    for (i = 0; i < graph3.segments.length; i++) {
        graph3.segments[i].handlers['hover'].push(function (i) {
            $('#chartinfo3').text(graph3.data[i].label);
        });
        if (graph3.data[i].value > mayor) {
            mayor = graph3.data[i].value;
            indexmayor = i;
        }
    }
    $('#chartinfo3').text(graph3.data[indexmayor].label);

    indexmayor = 0;
    mayor = 0;
    for (i = 0; i < graph4.segments.length; i++) {
        graph4.segments[i].handlers['hover'].push(function (i) {
            $('#chartinfo4').text(graph4.data[i].label);
        });
        if (graph4.data[i].value > mayor) {
            mayor = graph4.data[i].value;
            indexmayor = i;
        }
    }
    $('#chartinfo4').text(graph4.data[indexmayor].label);




    $('div svg text').each(function (index) {
        if (index % 2 == 0) {
            $(this).remove();
        }
    });


    updatecomparaciones()
}

$(document).ready(function () {

    jQuery.ajax({
        url: 'getConsolidadoAsh',
        success: function (data) {
            consolidadoash = data;
        },
        async: false
    });

    jQuery.ajax({
        url: 'getConsolidadoCai',
        success: function (data) {
            consolidadocai = data;
        },
        async: false
    });

    jQuery.ajax({
        url: 'getMortConsolidadoCai',
        success: function (data) {
            consolidadomortcai = data;
        },
        async: false
    });

    jQuery.ajax({
        url: 'getConsolidadoCau',
        success: function (data) {
            consolidadocau = data;
        },
        async: false
    });

    jQuery.ajax({
        url: 'getMortDesnutricion',
        success: function (data) {
            mortdesnutricion = data;
        },
        async: false
    });

    jQuery.ajax({
        url: 'getMorbDesnutricion',
        success: function (data) {
            morbdesnutricion = data;
        },
        async: false
    });


    Number.prototype.formatMoney = function (c, d, t) {
        var n = this,
                c = isNaN(c = Math.abs(c)) ? 2 : c,
                d = d == undefined ? "." : d,
                t = t == undefined ? "," : t,
                s = n < 0 ? "-" : "",
                i = String(parseInt(n = Math.abs(Number(n) || 0).toFixed(c))),
                j = (j = i.length) > 3 ? j % 3 : 0;
        return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
    };


    customhoverdonut = function (x) {
        return Number(x).formatMoney(2, ',', '.') + '%';
    }

    customhovermoney = function (x) {
        return "$" + Number(x).formatMoney(2, ',', '.');
    }

    $('#PIB').change(function () {
        updatecomparaciones();
    });

    updatedata();

    $(window).resize(function () {
        console.log("WINDOW RESIZING")
        graph1.redraw();
        graph2.redraw();
        graph3.redraw();
        graph4.redraw();
        $('div svg text').each(function (index) {
            if (index % 2 == 0) {
                $(this).remove();
            }
        });


        var indexmayor = 0;
        var mayor = 0;
        for (i = 0; i < graph1.segments.length; i++) {
            graph1.segments[i].handlers['hover'].push(function (i) {
                $('#chartinfo1').text(graph1.data[i].label);
            });
            if (graph1.data[i].value > mayor) {
                mayor = graph1.data[i].value;
                indexmayor = i;
            }
        }
        $('#chartinfo1').text(graph1.data[indexmayor].label);


        indexmayor = 0;
        mayor = 0;
        for (i = 0; i < graph2.segments.length; i++) {
            graph2.segments[i].handlers['hover'].push(function (i) {
                $('#chartinfo2').text(graph2.data[i].label);
            });
            if (graph2.data[i].value > mayor) {
                mayor = graph2.data[i].value;
                indexmayor = i;
            }
        }
        $('#chartinfo2').text(graph2.data[indexmayor].label);

        indexmayor = 0;
        mayor = 0;
        for (i = 0; i < graph3.segments.length; i++) {
            graph3.segments[i].handlers['hover'].push(function (i) {
                $('#chartinfo3').text(graph3.data[i].label);
            });
            if (graph3.data[i].value > mayor) {
                mayor = graph3.data[i].value;
                indexmayor = i;
            }
        }
        $('#chartinfo3').text(graph3.data[indexmayor].label);

        indexmayor = 0;
        mayor = 0;
        for (i = 0; i < graph4.segments.length; i++) {
            graph4.segments[i].handlers['hover'].push(function (i) {
                $('#chartinfo4').text(graph4.data[i].label);
            });
            if (graph4.data[i].value > mayor) {
                mayor = graph4.data[i].value;
                indexmayor = i;
            }
        }
        $('#chartinfo4').text(graph4.data[indexmayor].label);
    });

});

