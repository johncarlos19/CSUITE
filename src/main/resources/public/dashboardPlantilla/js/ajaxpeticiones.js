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
            var employee = {
                "#":    obj[key].id,
                "Imagen":   "<img src='"+img+"' width='40px' >",
                "Nombre":   obj[key].nombre,
                "Codigo": obj[key].codigo_local,
                "Descripcion":  obj[key].descripcion,
                "Categoria":     obj[key].categorias,
                "Precio de compra":   (Math.round(obj[key].precioCompra * 100) / 100).toFixed(2),
                "Precio de venta":     (Math.round(obj[key].precioVenta * 100) / 100).toFixed(2),
                "Precio de lista":     (Math.round(obj[key].precioLista * 100) / 100).toFixed(2),
                "Stock":     sto,
                "Disponible":     obj[key].disponible,
                "Acciones":     "<div class='btn-group' style = ' "+document.getElementById("inventa").value+"'><button class='btn btn-warning btnEditarProducto' idproducto='"+obj[key].id+"'  data-toggle='modal' data-target=''#modalEditarProducto'><i  class='fa fa-eye'></i></button> <button class='btn btn-danger btnEliminarProducto' idproducto='"+obj[key].id+"'  codigo='118'  imagen='vistas/img/productos/default/anonymous.png'><i  class='fa fa-times'></i></button> </div>"
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

        } );


        document.getElementById("loading").innerHTML = "";



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
                '                                                                idproducto="'+obj[key].id+'" id="productoINV'+obj[key].id+'">Agregar\n' +
                '                                                        </button>\n' +
                '                                                    </div>'
            var onlyFaPro = document.getElementById("productoFactura"+obj[key].id)
            if( onlyFaPro != null){
                accion = '                                                    <div class="btn-group">\n' +
                    '                                                        <button class="btn btn-default recuperarBoton"\n' +
                    '                                                                idproducto="'+obj[key].id+'" id="productoINV'+obj[key].id+'" >Agregar\n' +
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
                    '                                                                idproducto="'+obj[key].id+'" id="productoINV'+obj[key].id+'" disabled>Agregar\n' +
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
                "Imagen":   "<img src='"+img+"' width='40px' >",
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

        $('.tablaVentas1').DataTable().clear().destroy();
        $('.tablaVentas1').DataTable( {
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
            ]

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

    if(e.data.cmd === 'verifyUser'){
        console.log("entro")
        var pw1 = document.getElementById("password").value;
        var pw2 = document.getElementById("passwordRetry").value;
        if (e.data.data === false){
            if(pw1 !==pw2)
            {

                document.getElementById("alertPassword").innerHTML = '<div class="alert alert-danger alert-dismissible">\n' +
                    '\t\t\t\t\t\t\t  <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>\n' +
                    '\t\t\t\t\t\t\t  <h4><i class="icon fa fa-ban"></i> Alerta!</h4>\n' +
                    '\t\t\t\t\t\t\t  La contraseña no coincide\n' +
                    '\t\t\t\t\t\t  </div>'

            } else {
                document.getElementById("RegisterEmpleado").submit()

            }
        }else{
            document.getElementById("alertID").innerHTML = '<div class="alert alert-danger alert-dismissible">\n' +
                '\t\t\t\t\t\t\t  <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>\n' +
                '\t\t\t\t\t\t\t  <h4><i class="icon fa fa-ban"></i> Alerta!</h4>\n' +
                '\t\t\t\t\t\t\t  El usuario escrito ha sido registrado\n' +
                '\t\t\t\t\t\t  </div>'
            if(pw1 !== pw2)
            {
                document.getElementById("alertPassword").innerHTML = '<div class="alert alert-danger alert-dismissible">\n' +
                    '\t\t\t\t\t\t\t  <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>\n' +
                    '\t\t\t\t\t\t\t  <h4><i class="icon fa fa-ban"></i> Alerta!</h4>\n' +
                    '\t\t\t\t\t\t\t  La contraseña no coincide\n' +
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
    $("#modalLoading").css("display", "block");
}
function stopLoading(){
    $('#modalLoading').modal('toggle');
    $("#modalLoading").css("display", "none");
}