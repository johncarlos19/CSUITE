const worker = new Worker('../js/webworker-ajax.js');
var tablaUsuarios;
const kk = [];



worker.onmessage = function (e) { //recuperando la información
    if(e.data.cmd === 'respuesta'){
        while(kk.length > 0) {
            kk.pop();
        }

        var obj = e.data.data;
        for(var key in obj) {
            var sto
            if(obj[key].stock === 0){
                sto = "<button class='btn btn-default'>"+obj[key].stock+"</button>"
            }else if(obj[key].stock<=10){
                sto = "<button class='btn btn-danger'>"+obj[key].stock+"</button>"
            }else if(obj[key].stock<=15){
                sto = "<button class='btn btn-warning'>"+obj[key].stock+"</button>"
            }else{
                sto = "<button class='btn btn-success'>"+obj[key].stock+"</button>"
            }
            var img = obj[key].fotoBase64
            if (img == null){
                img = "../dashboardPlantilla/img/productos/default/anonymous.png"
            }
            var accionn = "<div class='btn-group' style = ' "+document.getElementById("inventa").value+"'> " +
                // " <form method='post' action='/dashboard/showProducto'><input type='hidden' name='idProducto' value='"+obj[key].id+"'><button class='btn btn-warning btnEditarProducto' type='button' ><i  class='fa fa-eye'></i></button></form>" +
                " <button class='btn btn-warning btnEditarProducto' type='button' idProducto='"+obj[key].id+"' ><i  class='fa fa-eye'></i></button>"

            if (obj[key].cantProductoVendido ===0){
                accionn += "   <button class='btn btn-danger btnEliminarProducto' idproducto='"+obj[key].id+"'  codigo='118'  imagen='vistas/img/productos/default/anonymous.png'><i  class='fa fa-times'></i></button> </div>"
            }else{
                accionn +="   </div>"
            }

            var employee = {
                "#":    obj[key].id,
                "Imagen":   "<img src='"+img+"' width='64' height='64' >",
                "Nombre":   obj[key].nombre,
                "Codigo": obj[key].codigo_local,
                "Descripcion":  obj[key].descripcion,
                "Categoria":     obj[key].categorias,
                "Precio de compra":   (Math.round(obj[key].precioCompra * 100) / 100).toFixed(2),
                "Precio de venta":     (Math.round(obj[key].precioVenta * 100) / 100).toFixed(2),
                "Precio de lista":     (Math.round(obj[key].precioLista * 100) / 100).toFixed(2),
                "Stock":     sto,
                "Disponible":     obj[key].disponible,
                "Acciones":     accionn
            }

            // c = [];
            // c.push(obj[key].id);
            // c.push(obj[key].nombre);
            // c.push(obj[key].descripcion);
            // c.push(obj[key].disponible);
            // c.push(obj[key].stock);
            // c.push(obj[key].precioVenta);
            // c.push(obj[key].precioCompra);
            kk.push(employee);
        };

        console.log("valorr"+JSON.stringify(kk))

        $('.tablaProductos').DataTable().clear().destroy();
        // $('.tablaProductos tfoot th').each( function () {
        //     var title = $(this).text();
        //     if (title === "Imagen" || title === "Acciones"){
        //
        //     }else{
        //         $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
        //     }
        // } );



        $('.tablaProductos').DataTable( {
            // "ajax": "ajax/datatable-productos.ajax.php?perfilOculto="+perfilOculto,

            "deferRender": true,
            "retrieve": true,
            "processing": true,

            "language": {

                "sProcessing":     "Procesando...",
                "sLengthMenu":     "Mostrar _MENU_ registros",
                "sZeroRecords":    "No se encontraron resultados",
                "sEmptyTable":     "Ningún dato disponible en esta tabla",
                "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_",
                "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0",
                "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
                "sInfoPostFix":    "",
                "sSearch":         "Buscar:",
                "sUrl":            "",
                "sInfoThousands":  ",",
                "sLoadingRecords": "Cargando...",
                "oPaginate": {
                    "sFirst":    "Primero",
                    "sLast":     "Último",
                    "sNext":     "Siguiente",
                    "sPrevious": "Anterior"
                },
                "oAria": {
                    "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
                    "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                }

            },
            "data": kk
            ,
            "columns":[
                {"data": "#"},
                {"data": "Imagen"},
                {"data": "Nombre"},
                {"data": "Codigo"},
                {"data": "Descripcion"},
                {"data": "Categoria"},
                {"data": "Precio de compra"},
                {"data": "Precio de venta"},
                {"data": "Precio de lista"},
                {"data": "Stock"},
                {"data": "Disponible"},
                {"data": "Acciones"}
            ]
            // ,initComplete: function () {
            //     this.api().columns().every( function () {
            //         var column = this;
            //         // $(column.hea)
            //
            //         var select = $('<select><option value=""></option></select>')
            //             .appendTo(  $(".tablaProductos thead tr:eq(1) th").eq(column.index()).empty()  )
            //             .on( 'change', function () {
            //                 var val = $.fn.dataTable.util.escapeRegex(
            //                     $(this).val()
            //                 );
            //
            //                 column
            //                     .search( val ? '^'+val+'$' : '', true, false )
            //                     .draw();
            //             } );
            //
            //         column.data().unique().sort().each( function ( d, j ) {
            //             select.append( '<option value="'+d+'">'+d+'</option>' )
            //         } );
            //         try {
            //             console.log("titulo"+column().title())
            //         }catch (e){
            //
            //         }
            //     } );
            // }
            // , "initComplete": function () {
            //     // Apply the search
            //     this.api().columns().every( function () {
            //         var that = this;
            //
            //         $( 'input', this.footer() ).on( 'keyup change clear', function () {
            //             if ( that.search() !== this.value ) {
            //                 that
            //                     .search( this.value )
            //                     .draw();
            //             }
            //         } );
            //     } );
            // },"searchPanes": {
            //     "viewTotal": true,
            //     "columns": [2,3,4,5]
            // }, "dom": 'Plfrtip'

        } );
        //     .columns().every( function() {
        //     var that = this;
        //
        //     $('input', this.footer()).on('keyup change', function() {
        //         if (that.search() !== this.value) {
        //             that
        //                 .search(this.value)
        //                 .draw();
        //         }
        //     });
        // });
        // dataFilter();



        document.getElementById("loading").innerHTML = "";



    }
    if(e.data.cmd === 'loadGraphicVenta'){


        $("#line-chart-ventas").empty();

        var obj = e.data.data;
        var lis = []


        for(var key in obj) {
            var emplo = {
                y: obj[key].key,
                ventas: obj[key].value
            }
            lis.push(emplo)
            }




         var line = new Morris.Line({
            element: 'line-chart-ventas',
            resize: true,
            data: lis,
            xkey: 'y',
            ykeys: ['ventas'],
            labels: ['ventas'],
            lineColors: ['#efefef'],
            lineWidth: 2,
            hideHover: 'auto',
            gridTextColor: '#fff',
            gridStrokeWidth: 0.4,
            pointSize: 4,
            pointStrokeColors: ['#efefef'],
            gridLineColor: '#efefef',
            gridTextFamily: 'Open Sans',
            preUnits: '$',
            gridTextSize: 10
        });


    }
    if(e.data.cmd === 'searchImpuestoProducto'){
        reloadIMPTABLA(e.data.data,"show");
        dataFilter()
        stopLoading()
    }
    if(e.data.cmd === 'searchImpuestoProductoAvailable'){
        reloadIMPTABLA(e.data.data,"add");
        reloadResponsibleTable()
        dataFilter()
        stopLoading()
    }

    if(e.data.cmd === 'searchAlmacenProducto'){
        reloadAlmacenTabla(e.data.data,"show");
        stopLoading()
    }

    if(e.data.cmd === 'save'){

        // document.getElementById('spinner').style.display =
        document.getElementById('salir').click()
        document.getElementById('ale').innerHTML = "<div class=\"alert alert-success alert-dismissible\">\n" +
            "                            <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">X</button>\n" +
            "                            <h4><i class=\"icon fa fa-check\"></i> Alert!</h4>\n" +
            "                            Se ha guardado correctamente\n" +
            "                        </div>"
        $(".tablaProductos tbody").remove();

        // var form = document.createElement("form");
        //
        //
        // form.method = "GET";
        // form.action = "/inventario";
        //
        //
        // document.body.appendChild(form);
        //
        // form.submit();
        reloadTabla();
    }

    if(e.data.cmd === 'cliente'){
        document.getElementById("searchClientebtn").disabled = true;
        document.getElementById("clienteAlContado").disabled = true;
        document.getElementById("cliente").readOnly  = true;
        var obj = e.data.data;
        document.getElementById("cliente").value = obj.usuario;
        document.getElementById("nameCliente").innerHTML = '<div class="form-group">\n' +
            '\n' +
            '                                        <div class="input-group">\n' +
            '\n' +
            '                                            <span class="input-group-addon"><i class="fa fa-user"></i></span>\n' +
            '\n' +
            '                                            <input type="text" class="form-control" id="nuevoVendedor"\n' +
            '                                                   value="'+obj.nombre+' '+obj.apellido+'" readonly="">\n' +
            '\n' +
            '                                            <input type="hidden" name="idCliente" value="'+obj.usuario+'">\n' +
            '\n' +
            '                                        </div>\n' +
            '\n' +
            '                                    </div>';


        crearFactura();
        // renovo = true;
        // setTimeout(alertaTime,e.data.data - (1500*60));
    }

    if(e.data.cmd === 'createFactura'){


        facturaLoadNow(e.data.data);
    }

    if(e.data.cmd === 'noFound'){
        document.getElementById("nameCliente").innerHTML =  '<div class="alert alert-danger alert-dismissible">\n' +
            '\t\t\t\t\t\t\t  Cliente No Encontrado\n' +
            '\t\t\t\t\t\t  </div>'
    }


    if(e.data.cmd === 'time'){
        renovo = true;
        setTimeout(alertaTime,e.data.data - (1500*60));
    }

    if(e.data.cmd === 'timeout'){

        preciono = false;
        alertLogout();
        try {
            stopLoading()
        }catch (e){

        }

    }

    if(e.data.cmd === 'ventaProducto'){
        while(kk.length > 0) {
            kk.pop();
        }

        console.log("ca a imprimir")
        var obj = e.data.data;
        for(var key in obj) {
            var sto;
            var accion = '                                                    <div class="btn-group">\n' +
                '                                                        <button class="btn btn-primary agregarProducto recuperarBoton"\n' +
                '                                                                idproducto="'+obj[key].id+'" data-dismiss="modal" id="productoINV'+obj[key].id+'">Agregar\n' +
                '                                                        </button>\n' +
                '                                                    </div>'
            var onlyFaPro = document.getElementById("productoFactura"+obj[key].id)
            if( onlyFaPro != null){
                accion = '                                                    <div class="btn-group">\n' +
                    '                                                        <button class="btn btn-default recuperarBoton"\n' +
                    '                                                                idproducto="'+obj[key].id+'" data-dismiss="modal" id="productoINV'+obj[key].id+'" >Agregar\n' +
                    '                                                        </button>\n' +
                    '                                                    </div>';
            }else{

            }



            // var accion = '                                                    <div class="btn-group">\n' +
            // 	'                                                        <button class="btn btn-primary agregarProducto recuperarBoton"\n' +
            // 	'                                                                idproducto="'+obj[key].id+'" id="productoINV'+obj[key].id+'">Agregar\n' +
            // 	'                                                        </button>\n' +
            // 	'                                                    </div>'
            if(obj[key].stock === 0){
                accion = '                                                    <div class="btn-group">\n' +
                    '                                                        <button class="btn btn-default recuperarBoton"\n' +
                    '                                                                idproducto="'+obj[key].id+'" data-dismiss="modal" id="productoINV'+obj[key].id+'" disabled>Agregar\n' +
                    '                                                        </button>\n' +
                    '                                                    </div>';
                sto = "<button class='btn btn-default' >"+obj[key].stock+"</button> "+"                                            <input type='hidden'  id='stock"+obj[key].id+"'\n" +
                    "                                                   value='"+obj[key].stock+"' >\n"
            }else if(obj[key].stock <=10){
                sto = "<button class='btn btn-danger' >"+obj[key].stock+"</button>"+"                                            <input type='hidden'  id='stock"+obj[key].id+"'\n" +
                    "                                                   value='"+obj[key].stock+"' >\n"
            }else if(obj[key].stock <=15){
                sto = "<button class='btn btn-warning' >"+obj[key].stock+"</button>"+"                                            <input type='hidden'  id='stock"+obj[key].id+"'\n" +
                    "                                                   value='"+obj[key].stock+"' >\n"
            }else{
                sto = "<button class='btn btn-success' >"+obj[key].stock+"</button>"+"                                            <input type='hidden'  id='stock"+obj[key].id+"'\n" +
                    "                                                   value='"+obj[key].stock+"' >\n"
            }
            var img = obj[key].fotoBase64
            if (img == null){
                img = "../dashboardPlantilla/img/productos/default/anonymous.png"
            }
            var employee = {
                "DT_RowId": "row_"+ obj[key].id,
                "#":    obj[key].id,
                "Imagen":   "<img src='"+img+"' width='64' height='64' >",
                "Codigo": obj[key].codigo_local,
                "Descripcion":   obj[key].nombre + "-"+ obj[key].descripcion,
                "Stock":     sto,
                "Precio de lista":    new Intl.NumberFormat("en-GB",formatConfig).format((Math.round(obj[key].precioLista * 100) / 100).toFixed(2)) ,
                "Disponible":     obj[key].disponible,
                "Acciones":     accion
            }

            // c = [];
            // c.push(obj[key].id);
            // c.push(obj[key].nombre);
            // c.push(obj[key].descripcion);
            // c.push(obj[key].disponible);
            // c.push(obj[key].stock);
            // c.push(obj[key].precioVenta);
            // c.push(obj[key].precioCompra);
            kk.push(employee);
        };

        console.log("valorr"+JSON.stringify(kk))

        $('#tablaInventario').DataTable().clear().destroy();
        $('#tablaInventario').DataTable( {
            // "ajax": "ajax/datatable-productos.ajax.php?perfilOculto="+perfilOculto,
            "deferRender": true,
            "retrieve": true,
            "processing": true,

            "language": {

                "sProcessing":     "Procesando...",
                "sLengthMenu":     "Mostrar _MENU_ registros",
                "sZeroRecords":    "No se encontraron resultados",
                "sEmptyTable":     "Ningún dato disponible en esta tabla",
                "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_",
                "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0",
                "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
                "sInfoPostFix":    "",
                "sSearch":         "Buscar:",
                "sUrl":            "",
                "sInfoThousands":  ",",
                "sLoadingRecords": "Cargando...",
                "oPaginate": {
                    "sFirst":    "Primero",
                    "sLast":     "Último",
                    "sNext":     "Siguiente",
                    "sPrevious": "Anterior"
                },
                "oAria": {
                    "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
                    "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                }

            },
            "data": kk
            ,
            "columns":[
                {"data": "#", "width": "10%"},
                {"data": "Imagen", "width": "10%"},
                {"data": "Codigo", "width": "15%"},
                {"data": "Descripcion", "width": "20%"},
                {"data": "Stock", "width": "10%"},
                {"data": "Precio de lista", "width": "20%"},
                {"data": "Acciones", "width": "15%"}
            ],

        } );
        $('#tablaInventario1').DataTable().clear().destroy();
        $('#tablaInventario1').DataTable( {
            // "ajax": "ajax/datatable-productos.ajax.php?perfilOculto="+perfilOculto,
            "deferRender": true,
            "retrieve": true,
            "processing": true,

            "language": {

                "sProcessing":     "Procesando...",
                "sLengthMenu":     "Mostrar _MENU_ registros",
                "sZeroRecords":    "No se encontraron resultados",
                "sEmptyTable":     "Ningún dato disponible en esta tabla",
                "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_",
                "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0",
                "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
                "sInfoPostFix":    "",
                "sSearch":         "Buscar:",
                "sUrl":            "",
                "sInfoThousands":  ",",
                "sLoadingRecords": "Cargando...",
                "oPaginate": {
                    "sFirst":    "Primero",
                    "sLast":     "Último",
                    "sNext":     "Siguiente",
                    "sPrevious": "Anterior"
                },
                "oAria": {
                    "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
                    "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                }

            },
            "data": kk,
            // ,"responsive": {
            //     "details": {
            //         "type": 'column',
            //         "target": 'tr'
            //     }
            // },
            // "columnDefs": [ {
            //     "className": 'control',
            //     "orderable": "false",
            //     "targets": "1"
            // } ],
            "columns":[
                {"data": "#"},
                {"data": "Imagen"},
                {"data": "Codigo"},
                {"data": "Descripcion"},
                {"data": "Stock"},
                {"data": "Precio de lista"},
                {"data": "Acciones"}
            ],

        } );
        console.log("ca a imprimir2")


        // document.getElementById("loading").innerHTML = "";



    }

    if(e.data.cmd === 'facturaLoad'){
        facturaLoadNow(e.data.data);
    }

    if(e.data.cmd === 'ventasActiva'){
        loadFacturaListActive(e.data.data);
    }

    if(e.data.cmd === 'facturaBorrada'){
        console.log("entro aqui")
        reloadFactActiva();
    }
    if(e.data.cmd === 'facturaLoadIMPModal'){
        console.log("entro aqui")
        stopLoading()
        var invo = returnInvoice(e.data.data)
        document.getElementById("includedContent").innerHTML = invo;
        generateBarcode();
        // $("#includedContent").html(invo);
    }
    if(e.data.cmd === 'facturaLoadIMP'){
        console.log("entro aqui")
        stopLoading()
        $('#guardarFactura').prop('disabled', false);
        $("#guardarFactura").html('Guardar venta');
        createInvoice(e.data.data)


        for (var key in e.data.data.productos){

            $("button.recuperarBoton[idProducto='"+e.data.data.productos[key].id+"']").removeClass('btn-default');
            $("button.recuperarBoton[idProducto='"+e.data.data.productos[key].id+"']").addClass(' btn-primary agregarProducto');
        }


        limpiarFactura()
    }
    if(e.data.cmd === 'productoRelation'){
        switch (e.data.action) {
            case "addAlmacen":
                reloadShowProducto(e.data.data)
                console.log("\n\nVan lo que es el load Producto")
                loadProductoALMACEN();
                dataFilter()
                stopLoading()
                break;
            case "addImpuesto":
                reloadShowProducto(e.data.data)
                loadProductoIMP();
                dataFilter()
                stopLoading()
                break;
            case "deleteImpuesto":
                reloadShowProducto(e.data.data)
                loadProductoIMP();
                dataFilter()
                stopLoading()
                break;
            case "editarInfoProducto":
                reloadShowProducto(e.data.data)
                stopLoading()
                // loadProductoIMP();
                // dataFilter()
                break;
            case "editarPrecioProducto":
                reloadShowProducto(e.data.data)
                loadProductoIMP();
                stopLoading()
                // dataFilter()
                break;
            case "editarFotoProducto":
                reloadShowProducto(e.data.data)
                stopLoading()
                // loadProductoIMP();
                // dataFilter()
                break;
            default:
                break;
        }
    }

    if(e.data.cmd === 'error'){
        stopLoading()
        swal({
            type:"error",
            title: "¡ERROR!",
            text: "¡Ha ocurrido un error, vuelva a intentarlo mas tarde!",
            showConfirmButton: true,
            confirmButtonText: "Cerrar"

        }).then(function(result){

            if(result.value){


            }

        });


    }

    if(e.data.cmd === 'login'){
        stopLoading()

        switch (e.data.data) {
            case 200:
                var form = document.createElement("form");


                form.method = "GET";
                form.action = "/dashboard/home";

                document.body.appendChild(form);

                form.submit();
                break;
            case 420:
                swal({
                    type:"error",
                    title: "¡ERROR!",
                    text: "¡Esta cuenta ha iniciado sesión, debe salir de la sesión para poder entrar!",
                    showConfirmButton: true,
                    confirmButtonText: "Cerrar"

                }).then(function(result){

                    if(result.value){


                    }

                });
                break;
            case 430:
                swal({
                    type:"error",
                    title: "¡ERROR!",
                    text: "¡Usuario o contraseña no coinciden!",
                    showConfirmButton: true,
                    confirmButtonText: "Cerrar"

                }).then(function(result){

                    if(result.value){


                    }

                });
                break;
            case 440:
                swal({
                    type:"error",
                    title: "¡ERROR!",
                    text: "¡Usuario o contraseña no coinciden!",
                    confirmButtonText: "Cerrar"

                }).then(function(result){

                    if(result.value){


                    }

                });
                break;

        }
    }

    if(e.data.cmd === 'verifyPassword'){
        console.log("entro")
        var pw1 = document.getElementById("password").value;
        var pw2 = document.getElementById("passwordRetry").value;
        if (e.data.data === true){
            if(pw1 !==pw2)
            {

                document.getElementById("alertPassword").innerHTML = '<div class="alert alert-danger alert-dismissible">\n' +
                    '\t\t\t\t\t\t\t  <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>\n' +
                    '\t\t\t\t\t\t\t  <h4><i class="icon fa fa-ban"></i> Alerta!</h4>\n' +
                    '\t\t\t\t\t\t\t  La contraseña no coincide\n' +
                    '\t\t\t\t\t\t  </div>'

            } else {
                document.getElementById("cambiarPassowrd").submit()

            }
        }else{
            document.getElementById("alertPassword").innerHTML = '<div class="alert alert-danger alert-dismissible">\n' +
                '\t\t\t\t\t\t\t  <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>\n' +
                '\t\t\t\t\t\t\t  <h4><i class="icon fa fa-ban"></i> Alerta!</h4>\n' +
                '\t\t\t\t\t\t\t  La contraseña vieja no es correcta\n' +
                '\t\t\t\t\t\t  </div>'
            if(pw1 !== pw2)
            {
                document.getElementById("alertPassword").innerHTML = '<div class="alert alert-danger alert-dismissible">\n' +
                    '\t\t\t\t\t\t\t  <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>\n' +
                    '\t\t\t\t\t\t\t  <h4><i class="icon fa fa-ban"></i> Alerta!</h4>\n' +
                    '\t\t\t\t\t\t\t  La contraseña vieja no es correcta y La contraseña nueva no coincide\n' +
                    '\t\t\t\t\t\t  </div>'

            }

        }

    }

};

function startLoading(){
    $("#modalLoading").modal({
        backdrop: 'static',
        keyboard: false
    })
    $('#modalLoading').modal('show');
    $("#modalLoading").css("display", "block");
}
function stopLoading(){
    setTimeout(stopNowLoading,1000);

}

function verifyUser(){
    var employee = {
        user: document.getElementById("user").value,
        password: document.getElementById("password").value
    }
    startLoading()
    worker.postMessage({'cmd': 'login', 'user': JSON.stringify(employee)});
}
function stopNowLoading(){
    $('#modalLoading').modal('hide');
    $("#modalLoading").css("display", "none");
}

function setCookie(cname,cvalue,exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

