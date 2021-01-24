/*=============================================
CARGAR LA TABLA DINÁMICA DE VENTAS
=============================================*/

// $.ajax({

// 	url: "ajax/datatable-ventas.ajax.php",
// 	success:function(respuesta){
		
// 		console.log("respuesta", respuesta);

// 	}

// })//
// var worker = new Worker('../js/webworker-ajax.js');

// $('.tablaVentas').DataTable( {
//     "ajax": "ajax/datatable-ventas.ajax.php",
//     "deferRender": true,
// 	"retrieve": true,
// 	"processing": true,
// 	 "language": {
//
// 			"sProcessing":     "Procesando...",
// 			"sLengthMenu":     "Mostrar _MENU_ registros",
// 			"sZeroRecords":    "No se encontraron resultados",
// 			"sEmptyTable":     "Ningún dato disponible en esta tabla",
// 			"sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_",
// 			"sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0",
// 			"sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
// 			"sInfoPostFix":    "",
// 			"sSearch":         "Buscar:",
// 			"sUrl":            "",
// 			"sInfoThousands":  ",",
// 			"sLoadingRecords": "Cargando...",
// 			"oPaginate": {
// 			"sFirst":    "Primero",
// 			"sLast":     "Último",
// 			"sNext":     "Siguiente",
// 			"sPrevious": "Anterior"
// 			},
// 			"oAria": {
// 				"sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
// 				"sSortDescending": ": Activar para ordenar la columna de manera descendente"
// 			}
//
// 	}
//
// } );

/*=============================================
AGREGANDO PRODUCTOS A LA VENTA DESDE LA TABLA
=============================================*/


function crearFactura(){
	var name = document.getElementById("cliente").value;
	worker.postMessage({'cmd': 'createFactura', 'id': name});
}
function reloadFacturaAjax(value){
	startLoading()
	worker.postMessage({'cmd': 'facturaLoad', 'id': value});
}





function startServerSent1(){


	console.info("Iniciando Jquery -  Ejemplo Polling");
	var evtSource = new EventSource("/api/evento-servidor");
	evtSource.onerror = function(e) {
		// const formatConfig = {
		// 	// style: "currency",
		// 	// currency: "DOP", // CNY for Chinese Yen, EUR for Euro
		// 	minimumFractionDigits: 2,
		// 	currencyDisplay: "symbol",
		// };
		// // const britishNumberFormatter = new Intl.NumberFormat("en-GB", formatConfig);
		// console.log(new Intl.NumberFormat("en-GB", formatConfig).format((500000).toFixed(2)));
		console.log("EventSource failed."+e);
		console.log("Fecha "+returnDate(1609046757407))
		console.log("Fecha1 "+returnTime(1609046757407))
		console.log("Fecha2 "+returnOnlyDate(1609046757407))
		// setTimeout(startServerSent(), 30000);
	};
	evtSource.onopen = function() {
		reloadtabladeInevntario();
		console.log("Connection to server opened.");
	};
	evtSource.addEventListener("conectado", function(e) {
		console.log("esto llego"+e.data)
	}, false);

	evtSource.addEventListener("productoload", function(e) {
		var formatConfig1 = {
			// style: "currency",
			// currency: "DOP", // CNY for Chinese Yen, EUR for Euro
			minimumFractionDigits: 2,
			currencyDisplay: "symbol",
		};
		console.log("esto llego"+e.data);
		var obj = JSON.parse(e.data);
		console.log(obj)
		var sto;
		var accion = '                                                    <div class="btn-group">\n' +
			'                                                        <button class="btn btn-primary agregarProducto recuperarBoton"\n' +
			'                                                                idproducto="'+obj.id+'" id="productoINV'+obj.id+'">Agregar\n' +
			'                                                        </button>\n' +
			'                                                    </div>'
		var onlyFaPro = document.getElementById("productoFactura"+obj.id)
		if( onlyFaPro != null){
			accion = '                                                    <div class="btn-group">\n' +
				'                                                        <button class="btn btn-default recuperarBoton"\n' +
				'                                                                idproducto="'+obj.id+'" id="productoINV'+obj.id+'" >Agregar\n' +
				'                                                        </button>\n' +
				'                                                    </div>';
		}else{

		}
		if(obj.stock === 0){
			accion = '                                                    <div class="btn-group">\n' +
				'                                                        <button class="btn btn-default recuperarBoton"\n' +
				'                                                                idproducto="'+obj.id+'" id="productoINV'+obj.id+'" >Agregar\n' +
				'                                                        </button>\n' +
				'                                                    </div>';
			sto = "<button class='btn btn-default' id='stock"+obj.id+"'>"+obj.stock+"</button>"+"                                            <input type='hidden'  id='stock"+obj.id+"'\n" +
				"                                                   value='"+obj.stock+"' >\n"
		}else if(obj.stock<=10){
			sto = "<button class='btn btn-danger' id='stock"+obj.id+"'>"+obj.stock+"</button>"+"                                            <input type='hidden'  id='stock"+obj.id+"'\n" +
				"                                                   value='"+obj.stock+"' >\n"
		}else if(obj.stock<=15){
			sto = "<button class='btn btn-warning' id='stock"+obj.id+"'>"+obj.stock+"</button>"+"                                            <input type='hidden'  id='stock"+obj.id+"'\n" +
				"                                                   value='"+obj.stock+"' >\n"
		}else{
			sto = "<button class='btn btn-success' id='stock"+obj.id+"'>"+obj.stock+"</button>"+"                                            <input type='hidden'  id='stock"+obj.id+"'\n" +
				"                                                   value='"+obj.stock+"' >\n"
		}

		var img = obj.fotoBase64
		if (img == null){
			img = "<img src='"+"../dashboardPlantilla/img/productos/default/anonymous.png"+"' width='40px' >"
		}else{
			img = "<img src='"+obj.fotoBase64+"' width='40px' >"
		}
		if (row!== null){

			$.fn.dataTable.ext.errMode = 'none';

			$('#tablaInventario').on( 'error.dt', function ( e, settings, techNote, message ) {
				console.log( 'An error has been reported by DataTables: ', message );
			} ) ;

			var table = $('#tablaInventario').DataTable();
			// Remove a row by Id:
			var index1 = table.row("#row_"+ obj.id).index()

			img = table
				.cell( index1, 1)
				.data();



		}



		var employee = {
			"DT_RowId": "row_"+ obj.id,
			"#":    obj.id,
			"Imagen":   img,
			"Codigo": obj.codigo_local,
			"Descripcion":   obj.nombre + "-"+ obj.descripcion,
			"Stock":     sto,
			"Precio de lista":    new Intl.NumberFormat("en-GB",formatConfig1).format((Math.round(obj.precioLista * 100) / 100).toFixed(2)) ,
			"Disponible":     obj.disponible,
			"Acciones":     accion
		}


		var row = document.getElementById("row_"+ obj.id)
		if (row!== null){
			var table = $('#tablaInventario').DataTable();
			// Remove a row by Id:
			table.row("#row_"+ obj.id).remove().draw();
			// Likewise to add a new row:
			table.row.add(employee).draw();
			var onlyFaPro = document.getElementById("productoFactura"+obj.id)
			if( onlyFaPro != null){
				var stock = obj.stock + Number(document.getElementById("nuevaCantidadProducto"+obj.id).value)
				var Nuevostock = obj.stock
				document.getElementById("nuevaCantidadProducto"+obj.id).setAttribute("max",stock); // set a new val
				document.getElementById("nuevaCantidadProducto"+obj.id).setAttribute("stock",stock); // set a new val
				document.getElementById("nuevaCantidadProducto"+obj.id).setAttribute("nuevoStock",Nuevostock); // set a new val
				document.getElementById("nuevaCantidadProducto"+obj.id).removeAttribute('readonly');
			}

			// row.innerHTML = '<td class="sorting_1" tabindex="0" style="">'+obj.id+'</td>' +
			// 	'<td>'+employee.Imagen+'</td>' +
			// 	'<td>'+employee.Codigo+'</td>' +
			// 	'<td>'+employee.Descripcion+'</td>' +
			// 	'<td>'+employee.Stock+'</td>' +
			// 	'<td>'+new Intl.NumberFormat("en-GB",formatConfig1).format((Math.round(obj.precioLista * 100) / 100).toFixed(2))+'</td' +
			// 	'><td>'+accion+'</td>'
		}else{
			console.log("es null")
		}




	}, false);

}

function returnDate(val){

	var milliseconds = val  // 1575909015000

	var dateObject = new Date(milliseconds)

	var humanDateFormat = dateObject.toLocaleString()
	return humanDateFormat;
}

function returnTime(val){

	var milliseconds = val  // 1575909015000

	var dateObject = new Date(milliseconds)

	var humanDateFormat = dateObject.toLocaleTimeString('en-US')
	return humanDateFormat;
}

function returnOnlyDate(val){
	const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
	var milliseconds = val  // 1575909015000

	var dateObject = new Date(milliseconds)

	var humanDateFormat = dateObject.toLocaleDateString('en-US',options)
	return humanDateFormat;
}

function imprimirFact(){
	if (document.getElementById("nuevaVenta").value === "" || document.getElementById("nuevaVenta").value === null) {
		swal({
			title: "Error al Imprimir la factura",
			text: "¡Debe crear la factura para poder imprimirla!",
			type: "error",
			confirmButtonText: "¡Cerrar!"
		});
	} else {
		worker.postMessage({'cmd': 'facturaLoadIMP', 'id': document.getElementById("nuevaVenta").value});
	}


}

function reloadFactActiva(){
	startLoading();
	worker.postMessage({'cmd': 'ventasActiva'});
}

function deleteFactura(id){
	startLoading();
	worker.postMessage({'cmd': 'deleteFactura', 'id': id});
}



$('.facturaAct').on("click" , "button.btnEliminarFactura1" ,function(){

	var idFactur = $(this).attr("id");

	swal({
		title: '¿Está seguro que desea Eliminar Esta Factura?',
		text: "¡Si borra la factura, la mayoría de los productos se agregarán de nuevo a su inventario!",
		type: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		cancelButtonText: 'Cancelar',
		confirmButtonText: 'Si, borrar Factura!'
	}).then(function(result){

		if(result.value){
			console.log("Este id "+idFactur)
			deleteFactura(idFactur);
			// var form = document.createElement("form");
			// var element1 = document.createElement("input");
			// var element2 = document.createElement("input");
			//
			// form.method = "POST";
			// form.action = "/dashboard/categoria";
			//
			// element1.value=idCategoria;
			// element1.name="categoria";
			// form.appendChild(element1);
			//
			// element2.value="eliminar";
			// element2.name="action";
			// form.appendChild(element2);
			//
			// document.body.appendChild(form);
			//
			// form.submit();

		}

	})

})





function loadFacturaListActive(lista){
	stopLoading()
	$(".facturaAct").empty();
	for (let listaKey in lista) {
		console.log("id "+lista[listaKey].idFactura)

		var ptoFa = '<div class="col-md-3">\n' +
			'                    <div class="box box-default ">\n' +
			'                        <div class="box-header with-border">\n' +
			'\n' +
			'                            <h3 class="box-title" >'+lista[listaKey].idFactura+'</h3>\n' +
			'\n' +
			'                            <div class="box-tools pull-right">\n' +
			'                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>\n' +
			'                                </button>\n' +
			'                                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>\n' +
			'                            </div>\n' +
			'                        </div>\n' +
			'                        <!-- /.box-header -->\n' +
			'                        <div class="box-body">\n' +
			'                            <div class="row">\n' +
			'                                <div class="col-md-3">\n' +
			'                                    <span class="info-box-icon bg-yellow"><i class="ion ion-ios-paper-outline"></i></span>\n' +
			'                                    <!-- ./chart-responsive -->\n' +
			'                                </div>\n' +
			'                                <!-- /.col -->\n' +
			'                                <div class="col-md-8">\n' +
			'                                    <table>\n' +
			'\n' +
			'                                    <tbody>\n' +
			'                                    <tr class="row"><td class="form-group">\n' +
			'\n' +
			'\n' +
			'                                        <div class="col-sm-6">\n' +
			'                                            <label for="inputName1" class="control-label">Fecha:</label>\n' +
			'                                        </div>\n' +
			'                                        <div class="col-sm-6">\n' +
			'                                            <a  class="form-control-" id="inputName1" placeholder="Name">'+returnDate(lista[listaKey].fechaCompra)+'</a>\n' +
			'                                        </div>\n' +
			'                                    </td></tr>\n' +
			'                                    <tr class="row"><td class="form-group">\n' +
			'\n' +
			'\n' +
			'\n' +
			'                                        <div class="col-sm-6">\n' +
			'                                            <label for="inputName" class="control-label">ID Cliente:</label>\n' +
			'                                        </div>\n' +
			'                                        <div class="col-sm-6">\n' +
			'                                            <a  class="form-control-" id="inputName" placeholder="Name">'+lista[listaKey].idCliente+'</a>\n' +
			'                                        </div>\n' +
			'                                    </td>\n' +
			'                                    </tr>\n' +
			'                                    <tr class="row"><td class="form-group">\n' +
			'\n' +
			'\n' +
			'\n' +
			'                                        <div class="col-sm-6">\n' +
			'                                            <label for="inputName2" class="control-label">Nombre:</label>\n' +
			'                                        </div>\n' +
			'                                        <div class="col-sm-6">\n' +
			'                                            <a  class="form-control-" id="inputName2" placeholder="Name">'+lista[listaKey].nombreCliente+'</a>\n' +
			'                                        </div>\n' +
			'                                    </td>\n' +
			'                                    </tr>\n' +
			'                                    <tr class="row"><td class="form-group">\n' +
			'\n' +
			'\n' +
			'\n' +
			'                                        <div class="col-sm-6">\n' +
			'                                            <label for="inputName3" class="control-label">Creador:</label>\n' +
			'                                        </div>\n' +
			'                                        <div class="col-sm-6">\n' +
			'                                            <a  class="form-control-" id="inputName3" placeholder="Name">'+lista[listaKey].idQuienLoRealizo+'</a>\n' +
			'                                        </div>\n' +
			'                                    </td>\n' +
			'                                    </tr>\n' +
			'                                    </tbody>\n' +
			'                                    </table>\n' +
			'                                </div>\n' +
			'                                <!-- /.col -->\n' +
			'                            </div>\n' +
			'                            <!-- /.row -->\n' +
			'                        </div>\n' +
			'                        <!-- /.box-body -->\n' +
			'                        <div class="box-footer no-padding">\n' +
			'                            <ul class="nav nav-pills nav-stacked">\n' +
			'                                <li><a href="#">SubTotal\n' +
			'                                    <span class="pull-right text-yellow">'+currentyMoney(lista[listaKey].total)+'</span></a></li>\n' +
			'\n' +
			'                                <li><a >Total\n' +
			'                                    <span class="pull-right text-green">'+currentyMoney(lista[listaKey].precioNeto)+'</span></a></li>\n' +
			'                                <li><a ><button class="btn  btn-danger btnEliminarFactura1" id="'+lista[listaKey].idFactura+'" type="button">Eliminar</button><div class="pull-right">\n' +
			'                                    <form method="post" action="/dashboard/ventasActivas">\n' +
			'                                        <input type="hidden" name="idFactura" value="'+lista[listaKey].idFactura+'">\n' +
			'                                        <button class="btn  btn-success" type="submit">Editar</button>\n' +
			'                                    </form>\n' +
			'                                </div></a>\n' +
			'                                </li>\n' +
			'                            </ul>\n' +
			'                        </div>\n' +
			'                        <!-- /.footer -->\n' +
			'                    </div>\n' +
			'                    </div>'
		$(".facturaAct").prepend(ptoFa);
	}


}

function getFactura(factura){
	impuestoFactura = [];
	productosList = [];
	for(var key in factura.impuestoFacturas) {
		let imp = {
			"id": factura.impuestoFacturas[key].id,
			"nombre": factura.impuestoFacturas[key].nombre,
			"operacion": factura.impuestoFacturas[key].operacion,
			"valorSumandoExtra": factura.impuestoFacturas[key].valorSumandoExtra,
			"idFacturaCliente": factura.impuestoFacturas[key].idFacturaCliente,
			"idFacturaClienteProductoVendido": factura.impuestoFacturas[key].idFacturaClienteProductoVendido,
			"impuesto": factura.impuestoFacturas[key].impuesto,
			"descuento": factura.impuestoFacturas[key].descuento,
			"interes": factura.impuestoFacturas[key].interes
		}
		impuestoFactura.push(imp);

	};
	for(var key in factura.productos) {

		impuestoProducto = [];

		for(var key12 in factura.productos[key].impuestoClientes) {

			let imp = {
				"id": factura.productos[key].impuestoClientes[key12].id,
				"nombre": factura.productos[key].impuestoClientes[key12].nombre,
				"operacion": factura.productos[key].impuestoClientes[key12].operacion,
				"valorSumandoExtra": factura.productos[key].impuestoClientes[key12].valorSumandoExtra,
				"idFacturaCliente": factura.productos[key].impuestoClientes[key12].idFacturaCliente,
				"idFacturaClienteProductoVendido": factura.productos[key].impuestoClientes[key12].idFacturaClienteProductoVendido,
				"impuesto": factura.productos[key].impuestoClientes[key12].impuesto,
				"descuento": factura.productos[key].impuestoClientes[key12].descuento,
				"interes": factura.productos[key].impuestoClientes[key12].interes
			}
			impuestoProducto.push(imp);
		};

		let produ = {
			"id": factura.productos[key].id,
			"nombre": factura.productos[key].nombre,
			"descripcion": factura.productos[key].descripcion,
			"codigo_local": factura.productos[key].codigo_local,
			"cantProductoVendido": factura.productos[key].cantProductoVendido,
			"disponible": factura.productos[key].disponible,
			"categorias": factura.productos[key].categorias,
			"stock": factura.productos[key].stock,
			"precioVenta": factura.productos[key].precioVenta,
			"precioCompra": factura.productos[key].precioCompra,
			"cantMaxPorVenta": factura.productos[key].cantMaxPorVenta,
			"descuentoPorciento": factura.productos[key].descuentoPorciento,
			"impuesto": factura.productos[key].impuesto,
			"precioLista": factura.productos[key].precioLista,
			"impuestoClientes": impuestoProducto,
			"nombreFoto": factura.productos[key].nombreFoto,
			"mimeType": factura.productos[key].mimeType,
			"fotoBase64": factura.productos[key].fotoBase64
		}
		productosList.push(produ);
	}
	let facturaComplete = {
		"idFactura": factura.idFactura,
		"total": factura.total,
		"precioNeto": factura.precioNeto,
		"idQuienLoRealizo": factura.idQuienLoRealizo,
		"metodoDePago": factura.metodoDePago,
		"fechaCompra": factura.fechaCompra,
		"nombreCliente": factura.nombreCliente,
		"idCliente": factura.idCliente,
		"compania": factura.compania,
		"direccion": factura.direccion,
		"ciudadPais": factura.ciudadPais,
		"telefono": factura.telefono,
		"impuestoFacturas": impuestoFactura,
		"productos": productosList
	}
	return facturaComplete;


}

function facturaLoadNow(factura){
	stopLoading();
	var formatConfig1 = {
		// style: "currency",
		// currency: "DOP", // CNY for Chinese Yen, EUR for Euro
		minimumFractionDigits: 2,
		currencyDisplay: "symbol",
	};

	var obj = getFactura(factura);
	document.getElementById("nuevaVenta").value = obj.idFactura;
	document.getElementById("searchClientebtn").disabled = true;
	document.getElementById("clienteAlContado").disabled = true;
	document.getElementById("cliente").readOnly  = true;
	document.getElementById("cliente").value = obj.idCliente;
	document.getElementById("nameCliente").innerHTML = '<div class="form-group">\n' +
		'\n' +
		'                                        <div class="input-group">\n' +
		'\n' +
		'                                            <span class="input-group-addon"><i class="fa fa-user"></i></span>\n' +
		'\n' +
		'                                            <input type="text" class="form-control" id="nuevoVendedor"\n' +
		'                                                   value="'+obj.nombreCliente+'" readonly="">\n' +
		'\n' +
		'                                            <input type="hidden" name="idCliente" value="'+obj.usuario+'">\n' +
		'\n' +
		'                                        </div>\n' +
		'\n' +
		'                                    </div>';

	for(var key in factura.productos) {
		var onlyFaPro = document.getElementById("productoFactura"+obj.productos[key].id)
		var idProducto = obj.productos[key].id;
		if( onlyFaPro != null){
			console.log("productoAhora:"+idProducto)
			$("button.recuperarBoton[idProducto='"+idProducto+"']").removeClass('btn-primary agregarProducto');

			$("button.recuperarBoton[idProducto='"+idProducto+"']").addClass('btn-default');
			var stock = obj.productos[key].cantMaxPorVenta + obj.productos[key].stock
			var Nuevostock = obj.productos[key].cantMaxPorVenta
			var precio = new Intl.NumberFormat("en-GB",formatConfig1).format((Math.round((obj.productos[key].precioLista - obj.productos[key].descuentoPorciento) * 100) / 100).toFixed(2))
			var precioTotal = new Intl.NumberFormat("en-GB",formatConfig1).format((Math.round(obj.productos[key].stock*(obj.productos[key].precioLista - obj.productos[key].descuentoPorciento) * 100) / 100).toFixed(2))
			$("#PrecioProducto"+obj.productos[key].id).attr("precioReal",precioTotal);
			$("#PrecioProducto"+obj.productos[key].id).val(precioTotal);
			$("#PrecioProductoOri"+obj.productos[key].id).attr("precioReal",precio);
			$("#PrecioProductoOri"+obj.productos[key].id).val(precio);
			document.getElementById("nuevaCantidadProducto"+obj.productos[key].id).setAttribute("max",stock); // set a new val
			document.getElementById("nuevaCantidadProducto"+obj.productos[key].id).setAttribute("stock",stock); // set a new val
			document.getElementById("nuevaCantidadProducto"+obj.productos[key].id).setAttribute("valueBefore",obj.productos[key].stock); // set a new val
			document.getElementById("nuevaCantidadProducto"+obj.productos[key].id).setAttribute("nuevoStock",Nuevostock); // set a new val
			document.getElementById("nuevaCantidadProducto"+obj.productos[key].id).setAttribute("value",obj.productos[key].stock); // set a new val
			document.getElementById("nuevaCantidadProducto"+obj.productos[key].id).value = obj.productos[key].stock // set a new value
			document.getElementById("nuevaCantidadProducto"+obj.productos[key].id).readOnly = false;

		}else{

			$("button.recuperarBoton[idProducto='"+idProducto+"']").removeClass('btn-primary agregarProducto');

			$("button.recuperarBoton[idProducto='"+idProducto+"']").addClass('btn-default');
			// parseInt(document.getElementById("stock"+obj.productos[key].id).value)
			var stock = obj.productos[key].cantMaxPorVenta+ obj.productos[key].stock;

			// console.log("Este es el valor "+obj.productos[key].id+" de stock"+obj.productos[key].cantMaxPorVenta +" o es este" + Number(document.getElementById("stock"+obj.productos[key].id).value) + "o este: "+document.getElementById("stock"+obj.productos[key].id).value)
			var precio = new Intl.NumberFormat("en-GB",formatConfig1).format((Math.round((obj.productos[key].precioLista - obj.productos[key].descuentoPorciento) * 100) / 100).toFixed(2))
			var precioTotal = new Intl.NumberFormat("en-GB",formatConfig1).format((Math.round(obj.productos[key].stock*(obj.productos[key].precioLista - obj.productos[key].descuentoPorciento) * 100) / 100).toFixed(2))
			var ptoFa =           	'<div class="row" id="'+"productoFactura"+obj.productos[key].id+'" style="padding:5px 15px">'+

				'<!-- Descripción del producto -->'+

				'<div class="col-xs-6" style="padding-right:0px">'+

				'<div class="input-group">'+

				'<span class="input-group-addon"><button type="button" class="btn btn-danger btn-xs quitarProducto" idProducto="'+obj.productos[key].id+'"><i class="fa fa-times"></i></button></span>'+

				'<input type="text" class="form-control nuevaDescripcionProducto" idProducto="'+obj.productos[key].id+'" name="agregarProducto" value="'+obj.productos[key].nombre +' - '+ obj.productos[key].descripcion +'" readonly required>'+

				'</div>'+

				'</div>'+

				'<!-- Cantidad del producto -->'+

				'<div class="col-xs-3">'+

				'<input type="number" class="form-control nuevaCantidadProducto" name="nuevaCantidadProducto" idPro="'+obj.productos[key].id+'" max="'+stock+'" id="nuevaCantidadProducto'+obj.productos[key].id+'" min="1" valueBefore="'+obj.productos[key].stock+'" value="'+obj.productos[key].stock+'" stock="'+stock+'" nuevoStock="'+Number(stock-obj.productos[key].stock)+'" required>'+

				'</div>' +

				// '<!-- Precio del producto -->'+
				//
				// '<div class="col-xs-3 ingresoPrecioOri" style="padding-left:0px">'+
				//
				// '<div class="input-group">'+
				//
				// '<span class="input-group-addon"><i class="ion ion-social-usd"></i></span>'+
				//
				// '<input type="text" class="form-control nuevoPrecioProductoOri" precioReal="'+precio+'" id="PrecioProductoOri'+obj.productos[key].id+'" name="nuevoPrecioProductoOri" value="'+precio+'" readonly required>'+
				//
				// '</div>'+
				//
				// '</div>'+
				'<!-- Precio del productoTotal -->'+

				'<div class="col-xs-3 ingresoPrecio" style="padding-left:0px">'+

				'<div class="input-group">'+

				'<span class="input-group-addon"><i class="ion ion-social-usd"></i></span>'+

				'<input type="text" class="form-control nuevoPrecioProducto" precioReal="'+precioTotal+'" id="PrecioProducto'+obj.productos[key].id+'" name="nuevoPrecioProducto" value="'+precioTotal+'" readonly required>'+

				'</div>'+

				'</div>'+

				'</div>';
			$(".nuevoProducto").prepend(ptoFa);
		}
	}

	$("#impuestodescuento").empty()


	for(var key1 in obj.impuestoFacturas){

		var valu = new Intl.NumberFormat("en-GB",formatConfig1).format((Math.round((obj.impuestoFacturas[key1].impuesto) * 100) / 100).toFixed(2))
		var imp = '                                                <tr>\n' +
			'\n' +
			'                                                    <td >\n' +
			'\n' +
			'                                                        <div class="input-group">\n' +
			'                                                            <span class="input-group-addon"><a>'+obj.impuestoFacturas[key1].nombre+'</a></span>\n' +
			'                                                            <input type="text" class="form-control input-lg" min="0"\n' +
			'                                                                   id="nuevoImpuestoVenta'+obj.impuestoFacturas[key1].id+'" name="nuevoImpuestoVenta"\n' +
			'                                                                   placeholder="0" readonly value="'+valu+'">\n' +
			'\n' +

			'\n' +
			'\n' +
			'\n' +
			'                                                        </div>\n' +
			'\n' +
			'                                                    </td>\n' +
			'\n' +
			'                                                </tr>'
		$("#impuestodescuento").prepend(imp);
	}


	document.getElementById("nuevoSubTotalVenta").value = currentyMoney(obj.total)
	document.getElementById("subtotalVenta").value = obj.total
	document.getElementById("nuevoTotalVenta").value= currentyMoney(obj.precioNeto)
	document.getElementById("totalVenta").value = obj.precioNeto
}

function currentyMoney(val){
	var formatConfig1 = {
		// style: "currency",
		// currency: "DOP", // CNY for Chinese Yen, EUR for Euro
		minimumFractionDigits: 2,
		currencyDisplay: "symbol",
	};
	return new Intl.NumberFormat("en-GB",formatConfig1).format((Math.round((val) * 100) / 100).toFixed(2))
}
// aqui es donde agrega
$(".tablaVentas1 tbody").on("click", "button.agregarProducto", function(){

	if(document.getElementById("nuevaVenta").value !== "") {

		var idProducto = $(this).attr("idProducto");

		$(this).removeClass("btn-primary agregarProducto");

		$(this).addClass("btn-default");

		let producto = {
			cantidad: 1,
			idProducto: parseInt(idProducto),
			idFactura: document.getElementById("nuevaVenta").value
		}
		startLoading();
		worker.postMessage({'cmd': 'addProductoFactura', 'AddDiscountProductoFacturaJSON': producto});
	}else {
		swal({
			title: "Error al agregar producto a la factura",
			text: "¡Debe seleccionar el usuario para poder agregar el producto a la factura!",
			type: "error",
			confirmButtonText: "¡Cerrar!"
		});
	}


	//
	// var datos = new FormData();
    // datos.append("idProducto", idProducto);
	//
    //  $.ajax({
	//
    //  	url:"ajax/productos.ajax.php",
    //   	method: "POST",
    //   	data: datos,
    //   	cache: false,
    //   	contentType: false,
    //   	processData: false,
    //   	dataType:"json",
    //   	success:function(respuesta){
	//
    //   	    var descripcion = respuesta["descripcion"];
    //       	var stock = respuesta["stock"];
    //       	var precio = respuesta["precio_venta"];
	//
    //       	/*=============================================
    //       	EVITAR AGREGAR PRODUTO CUANDO EL STOCK ESTÁ EN CERO
    //       	=============================================*/
	//
    //       	if(stock == 0){
	//
    //   			swal({
	// 		      title: "No hay stock disponible",
	// 		      type: "error",
	// 		      confirmButtonText: "¡Cerrar!"
	// 		    });
	//
	// 		    $("button[idProducto='"+idProducto+"']").addClass("btn-primary agregarProducto");
	//
	// 		    return;
	//
    //       	}
	//
    //       	$(".nuevoProducto").append(
	//
    //       	'<div class="row" id="'+"productoFactura"+'" style="padding:5px 15px">'+
	//
	// 		  '<!-- Descripción del producto -->'+
	//
	//           '<div class="col-xs-6" style="padding-right:0px">'+
	//
	//             '<div class="input-group">'+
	//
	//               '<span class="input-group-addon"><button type="button" class="btn btn-danger btn-xs quitarProducto" idProducto="'+idProducto+'"><i class="fa fa-times"></i></button></span>'+
	//
	//               '<input type="text" class="form-control nuevaDescripcionProducto" idProducto="'+idProducto+'" name="agregarProducto" value="'+descripcion+'" readonly required>'+
	//
	//             '</div>'+
	//
	//           '</div>'+
	//
	//           '<!-- Cantidad del producto -->'+
	//
	//           '<div class="col-xs-3">'+
	//
	//              '<input type="number" class="form-control nuevaCantidadProducto"  name="nuevaCantidadProducto" min="1" value="1" stock="'+stock+'" nuevoStock="'+Number(stock-1)+'" required>'+
	//
	//           '</div>' +
	//
	//           '<!-- Precio del producto -->'+
	//
	//           '<div class="col-xs-3 ingresoPrecio" style="padding-left:0px">'+
	//
	//             '<div class="input-group">'+
	//
	//               '<span class="input-group-addon"><i class="ion ion-social-usd"></i></span>'+
	//
	//               '<input type="text" class="form-control nuevoPrecioProducto" precioReal="'+precio+'" name="nuevoPrecioProducto" value="'+precio+'" readonly required>'+
	//
	//             '</div>'+
	//
	//           '</div>'+
	//
	//         '</div>')
	//
	//         // SUMAR TOTAL DE PRECIOS
	//
	//         sumarTotalPrecios()
	//
	//         // AGREGAR IMPUESTO
	//
	//         agregarImpuesto()
	//
	//         // AGRUPAR PRODUCTOS EN FORMATO JSON
	//
	//         listarProductos()
	//
	//         // PONER FORMATO AL PRECIO DE LOS PRODUCTOS
	//
	//         $(".nuevoPrecioProducto").number(true, 2);
	//
	//
	// 		localStorage.removeItem("quitarProducto");
	//
    //   	}
	//
    //  })

});

/*=============================================
CUANDO CARGUE LA TABLA CADA VEZ QUE NAVEGUE EN ELLA
=============================================*/



function searchCliente(id){
	var name = document.getElementById("cliente").value;
	if (id !== ""){
		worker.postMessage({'cmd': 'searchCliente', 'id': id});
	}else if (name !== ""){
		worker.postMessage({'cmd': 'searchCliente', 'id': name});
	}else {

	}
}

$(".tablaVentas1").on("draw.dt", function(){

	if(localStorage.getItem("quitarProducto") != null){

		var listaIdProductos = JSON.parse(localStorage.getItem("quitarProducto"));

		for(var i = 0; i < listaIdProductos.length; i++){

			$("button.recuperarBoton[idProducto='"+listaIdProductos[i]["idProducto"]+"']").removeClass('btn-default');
			$("button.recuperarBoton[idProducto='"+listaIdProductos[i]["idProducto"]+"']").addClass('btn-primary agregarProducto');

		}


	}


})


/*=============================================
QUITAR PRODUCTOS DE LA VENTA Y RECUPERAR BOTÓN
=============================================*/

var idQuitarProducto = [];

localStorage.removeItem("quitarProducto");

$(".formularioVenta").on("click", "button.quitarProducto", function(){

	var idProducto = $(this).attr("idProducto");
	console.log("este es el id:"+idProducto);
	var nuevaCantidadProducto = document.getElementById("nuevaCantidadProducto"+idProducto).value;
	console.log("este es el id:"+nuevaCantidadProducto);

	// var nuevoStock = Number($(this).attr("stock")) - $(this).val();

	$(this).parent().parent().parent().parent().remove();
	let producto = {
		cantidad: Number(nuevaCantidadProducto),
		idProducto: parseInt(idProducto),
		idFactura: document.getElementById("nuevaVenta").value
	}
	$("button.recuperarBoton[idProducto='"+idProducto+"']").removeClass('btn-default');

	$("button.recuperarBoton[idProducto='"+idProducto+"']").addClass('btn-primary agregarProducto');
	startLoading();
	worker.postMessage({'cmd': 'discountProductoFactura', 'AddDiscountProductoFacturaJSON': producto});




	/*=============================================
	ALMACENAR EN EL LOCALSTORAGE EL ID DEL PRODUCTO A QUITAR
	=============================================*/

	// if(localStorage.getItem("quitarProducto") == null){
	//
	// 	idQuitarProducto = [];
	//
	// }else{
	//
	// 	idQuitarProducto.concat(localStorage.getItem("quitarProducto"))
	//
	// }
	//
	// idQuitarProducto.push({"idProducto":idProducto});
	//
	// localStorage.setItem("quitarProducto", JSON.stringify(idQuitarProducto));
	//
	// $("button.recuperarBoton[idProducto='"+idProducto+"']").removeClass('btn-default');
	//
	// $("button.recuperarBoton[idProducto='"+idProducto+"']").addClass('btn-primary agregarProducto');
	//
	// if($(".nuevoProducto").children().length == 0){
	//
	// 	$("#nuevoImpuestoVenta").val(0);
	// 	$("#nuevoTotalVenta").val(0);
	// 	$("#totalVenta").val(0);
	// 	$("#nuevoTotalVenta").attr("total",0);
	//
	// }else{
	//
	// 	// SUMAR TOTAL DE PRECIOS
	//
    // 	sumarTotalPrecios()
	//
    // 	// AGREGAR IMPUESTO
	//
    //     agregarImpuesto()
	//
    //     // AGRUPAR PRODUCTOS EN FORMATO JSON
	//
    //     listarProductos()
	//
	// }

})

/*=============================================
AGREGANDO PRODUCTOS DESDE EL BOTÓN PARA DISPOSITIVOS
=============================================*/

var numProducto = 0;

$(".btnAgregarProducto").click(function(){

	numProducto ++;

	var datos = new FormData();
	datos.append("traerProductos", "ok");

	$.ajax({

		url:"ajax/productos.ajax.php",
      	method: "POST",
      	data: datos,
      	cache: false,
      	contentType: false,
      	processData: false,
      	dataType:"json",
      	success:function(respuesta){
      	    
      	    	$(".nuevoProducto").append(

          	'<div class="row" style="padding:5px 15px">'+

			  '<!-- Descripción del producto -->'+
	          
	          '<div class="col-xs-6 idProductoAlBorrar" style="padding-right:0px">'+
	          
	            '<div class="input-group">'+
	              
	              '<span class="input-group-addon"><button type="button" class="btn btn-danger btn-xs quitarProducto" idProducto><i class="fa fa-times"></i></button></span>'+

	              '<select class="form-control nuevaDescripcionProducto" id="producto'+numProducto+'" idProducto name="nuevaDescripcionProducto" required>'+

	              '<option>Seleccione el producto</option>'+

	              '</select>'+  

	            '</div>'+

	          '</div>'+

	          '<!-- Cantidad del producto -->'+

	          '<div class="col-xs-3 ingresoCantidad">'+
	            
	             '<input type="number" class="form-control nuevaCantidadProducto"  name="nuevaCantidadProducto" min="1" value="0" stock nuevoStock required>'+

	          '</div>' +

	          '<!-- Precio del producto -->'+

	          '<div class="col-xs-3 ingresoPrecio" style="padding-left:0px">'+

	            '<div class="input-group">'+

	              '<span class="input-group-addon"><i class="ion ion-social-usd"></i></span>'+
	                 
	              '<input type="text" class="form-control nuevoPrecioProducto" precioReal="" name="nuevoPrecioProducto" readonly required>'+
	 
	            '</div>'+
	             
	          '</div>'+

	        '</div>');


	        // AGREGAR LOS PRODUCTOS AL SELECT 

	         respuesta.forEach(funcionForEach);

	         function funcionForEach(item, index){

	         	if(item.stock != 0){

		         	$("#producto"+numProducto).append(

						'<option idProducto="'+item.id+'" value="'+item.descripcion+'">'+item.descripcion+'</option>'
		         	)

		         
		         }

		         

	         }

        	 // SUMAR TOTAL DE PRECIOS

    		sumarTotalPrecios()

    		// AGREGAR IMPUESTO
	        
	        agregarImpuesto()

	        // PONER FORMATO AL PRECIO DE LOS PRODUCTOS

	        $(".nuevoPrecioProducto").number(true, 2);


      	}

	})

})

/*=============================================
SELECCIONAR PRODUCTO
=============================================*/

$(".formularioVenta").on("change", "select.nuevaDescripcionProducto", function(){

	var nombreProducto = $(this).val();

	var nuevaDescripcionProducto = $(this).parent().parent().parent().children().children().children(".nuevaDescripcionProducto");

	var nuevoPrecioProducto = $(this).parent().parent().parent().children(".ingresoPrecio").children().children(".nuevoPrecioProducto");

	var nuevaCantidadProducto = $(this).parent().parent().parent().children(".ingresoCantidad").children(".nuevaCantidadProducto");

	var datos = new FormData();
    datos.append("nombreProducto", nombreProducto);


	  $.ajax({

     	url:"ajax/productos.ajax.php",
      	method: "POST",
      	data: datos,
      	cache: false,
      	contentType: false,
      	processData: false,
      	dataType:"json",
      	success:function(respuesta){

      	     $(nuevaDescripcionProducto).attr("idProducto", respuesta["id"]);
      	    $(nuevaCantidadProducto).attr("stock", respuesta["stock"]);
      	    $(nuevaCantidadProducto).attr("nuevoStock", Number(respuesta["stock"])-1);
      	    $(nuevoPrecioProducto).val(respuesta["precio_venta"]);
      	    $(nuevoPrecioProducto).attr("precioReal", respuesta["precio_venta"]);

  	      // AGRUPAR PRODUCTOS EN FORMATO JSON

	        listarProductos()

      	}

      })
})

/*=============================================
MODIFICAR LA CANTIDAD
=============================================*/

$(".formularioVenta").on("change", "input.nuevaCantidadProducto", function(){



	var precio = $(this).parent().parent().children(".ingresoPrecio").children().children(".nuevoPrecioProducto");

	var id = $(this).attr("idPro");

	var cantOri = Number($(this).attr("valueBefore")) ;

	var stockNow = Number($(this).attr("stock"));

	var stockINV = $(this).attr("nuevoStock");






	// console.log("idProducto:"+id+" valor Ante: "+prev);
	//
	// var precioFinal = $(this).val() * precio.attr("precioReal");
	//
	// precio.val(precioFinal);
	//
	// var nuevoStock = Number($(this).attr("stock")) - $(this).val();
	//
	// $(this).attr("nuevoStock", nuevoStock);

	if(Number($(this).val()) > stockNow){

		/*=============================================
		SI LA CANTIDAD ES SUPERIOR AL STOCK REGRESAR VALORES INICIALES
		=============================================*/

		$(this).val(cantOri);

		// $(this).attr("nuevoStock", $(this).attr("stock"));
		//
		// var precioFinal = $(this).val() * precio.attr("precioReal");
		//
		// precio.val(precioFinal);
		//
		// sumarTotalPrecios();

			swal({
				title: "La cantidad supera el Stock",
				text: "¡Sólo hay "+stockINV+" unidades!",
				type: "error",
				confirmButtonText: "¡Cerrar!"
			});



	    return;

	}else{
		var newValue = Number($(this).val()) - cantOri;
		console.log("valor actual:"+Number($(this).val())+"otro mas"+newValue)
		$(this).attr('readonly', true);
		if (newValue > 0){


			let producto = {
				cantidad: newValue,
				idProducto : parseInt(id),
				idFactura : document.getElementById("nuevaVenta").value
			}
			console.log("agregarProducto"+JSON.stringify(producto) )
			startLoading();
			worker.postMessage({'cmd': 'addProductoFactura', 'AddDiscountProductoFacturaJSON': producto});
		}else if (newValue < 0){

			let producto = {
				cantidad: newValue,
				idProducto : parseInt(id),
				idFactura : document.getElementById("nuevaVenta").value
			}
			console.log("descontar"+JSON.stringify(producto))
			startLoading();
			worker.postMessage({'cmd': 'discountProductoFactura', 'AddDiscountProductoFacturaJSON': producto});
		}
	}

	//
	// // SUMAR TOTAL DE PRECIOS
	//
	// sumarTotalPrecios()
	//
	// // AGREGAR IMPUESTO
	//
    // agregarImpuesto()
	//
    // // AGRUPAR PRODUCTOS EN FORMATO JSON
	//
    // listarProductos()

})

/*=============================================
SUMAR TODOS LOS PRECIOS
=============================================*/

function sumarTotalPrecios(){

	var precioItem = $(".nuevoPrecioProducto");
	
	var arraySumaPrecio = [];  

	for(var i = 0; i < precioItem.length; i++){

		 arraySumaPrecio.push(Number($(precioItem[i]).val()));
		
		 
	}

	function sumaArrayPrecios(total, numero){

		return total + numero;

	}

	var sumaTotalPrecio = arraySumaPrecio.reduce(sumaArrayPrecios);
	
	$("#nuevoTotalVenta").val(sumaTotalPrecio);
	$("#totalVenta").val(sumaTotalPrecio);
	$("#nuevoTotalVenta").attr("total",sumaTotalPrecio);


}

/*=============================================
FUNCIÓN AGREGAR IMPUESTO
=============================================*/

function agregarImpuesto(){

	var impuesto = $("#nuevoImpuestoVenta").val();
	var precioTotal = $("#nuevoTotalVenta").attr("total");

	var precioImpuesto = Number(precioTotal * impuesto/100);

	var totalConImpuesto = Number(precioImpuesto) + Number(precioTotal);
	
	$("#nuevoTotalVenta").val(totalConImpuesto);

	$("#totalVenta").val(totalConImpuesto);

	$("#nuevoPrecioImpuesto").val(precioImpuesto);

	$("#nuevoPrecioNeto").val(precioTotal);

}

/*=============================================
CUANDO CAMBIA EL IMPUESTO
=============================================*/

$("#nuevoImpuestoVenta").change(function(){

	// agregarImpuesto();


});

/*=============================================
FORMATO AL PRECIO FINAL
=============================================*/

$("#nuevoTotalVenta").number(true, 2);

/*=============================================
SELECCIONAR MÉTODO DE PAGO
=============================================*/

$("#nuevoMetodoPago").change(function(){

	var metodo = $(this).val();

	if(metodo == "Efectivo"){

		$(this).parent().parent().removeClass("col-xs-6");

		$(this).parent().parent().addClass("col-xs-4");

		$(this).parent().parent().parent().children(".cajasMetodoPago").html(

			 '<div class="col-xs-4">'+ 

			 	'<div class="input-group">'+ 

			 		'<span class="input-group-addon"><i class="ion ion-social-usd"></i></span>'+ 

			 		'<input type="text" class="form-control" id="nuevoValorEfectivo" placeholder="000000" required>'+

			 	'</div>'+

			 '</div>'+

			 '<div class="col-xs-4" id="capturarCambioEfectivo" style="padding-left:0px">'+

			 	'<div class="input-group">'+

			 		'<span class="input-group-addon"><i class="ion ion-social-usd"></i></span>'+

			 		'<input type="text" class="form-control" id="nuevoCambioEfectivo" placeholder="000000" readonly required>'+

			 	'</div>'+

			 '</div>'

		 )

		// Agregar formato al precio

		$('#nuevoValorEfectivo').number( true, 2);
      	$('#nuevoCambioEfectivo').number( true, 2);


      	// Listar método en la entrada
      	listarMetodos()

	}else{

		$(this).parent().parent().removeClass('col-xs-4');

		$(this).parent().parent().addClass('col-xs-6');

		 $(this).parent().parent().parent().children('.cajasMetodoPago').html(

		 	'<div class="col-xs-6" style="padding-left:0px">'+
                        
                '<div class="input-group">'+
                     
                  '<input type="number" min="0" class="form-control" id="nuevoCodigoTransaccion" placeholder="Código transacción"  required>'+
                       
                  '<span class="input-group-addon"><i class="fa fa-lock"></i></span>'+
                  
                '</div>'+

              '</div>')

	}

	

})

/*=============================================
CAMBIO EN EFECTIVO
=============================================*/
$(".formularioVenta").on("change", "input#nuevoValorEfectivo", function(){

	var efectivo = $(this).val();

	var cambio =  Number(efectivo) - Number($('#nuevoTotalVenta').val());

	var nuevoCambioEfectivo = $(this).parent().parent().parent().children('#capturarCambioEfectivo').children().children('#nuevoCambioEfectivo');

	nuevoCambioEfectivo.val(cambio);

})

/*=============================================
CAMBIO TRANSACCIÓN
=============================================*/
$(".formularioVenta").on("change", "input#nuevoCodigoTransaccion", function(){

	// Listar método en la entrada
     listarMetodos()


})


/*=============================================
LISTAR TODOS LOS PRODUCTOS
=============================================*/

function listarProductos(){

	var listaProductos = [];

	var descripcion = $(".nuevaDescripcionProducto");

	var cantidad = $(".nuevaCantidadProducto");

	var precio = $(".nuevoPrecioProducto");

	for(var i = 0; i < descripcion.length; i++){

		listaProductos.push({ "id" : $(descripcion[i]).attr("idProducto"), 
							  "descripcion" : $(descripcion[i]).val(),
							  "cantidad" : $(cantidad[i]).val(),
							  "stock" : $(cantidad[i]).attr("nuevoStock"),
							  "precio" : $(precio[i]).attr("precioReal"),
							  "total" : $(precio[i]).val()})

	}

	$("#listaProductos").val(JSON.stringify(listaProductos)); 

}

/*=============================================
LISTAR MÉTODO DE PAGO
=============================================*/

function listarMetodos(){

	var listaMetodos = "";

	if($("#nuevoMetodoPago").val() == "Efectivo"){

		$("#listaMetodoPago").val("Efectivo");

	}else{

		$("#listaMetodoPago").val($("#nuevoMetodoPago").val()+"-"+$("#nuevoCodigoTransaccion").val());

	}

}

/*=============================================
BOTON EDITAR VENTA
=============================================*/
$(".tablas").on("click", ".btnEditarVenta", function(){

	var idVenta = $(this).attr("idVenta");

	window.location = "index.php?ruta=editar-venta&idVenta="+idVenta;


})

/*=============================================
FUNCIÓN PARA DESACTIVAR LOS BOTONES AGREGAR CUANDO EL PRODUCTO YA HABÍA SIDO SELECCIONADO EN LA CARPETA
=============================================*/

function quitarAgregarProducto(){

	//Capturamos todos los id de productos que fueron elegidos en la venta
	var idProductos = $(".quitarProducto");

	//Capturamos todos los botones de agregar que aparecen en la tabla
	var botonesTabla = $(".tablaVentas1 tbody button.agregarProducto");

	//Recorremos en un ciclo para obtener los diferentes idProductos que fueron agregados a la venta
	for(var i = 0; i < idProductos.length; i++){

		//Capturamos los Id de los productos agregados a la venta
		var boton = $(idProductos[i]).attr("idProducto");
		
		//Hacemos un recorrido por la tabla que aparece para desactivar los botones de agregar
		for(var j = 0; j < botonesTabla.length; j ++){

			if($(botonesTabla[j]).attr("idProducto") == boton){

				$(botonesTabla[j]).removeClass("btn-primary agregarProducto");
				$(botonesTabla[j]).addClass("btn-default");

			}
		}

	}
	
}

/*=============================================
CADA VEZ QUE CARGUE LA TABLA CUANDO NAVEGAMOS EN ELLA EJECUTAR LA FUNCIÓN:
=============================================*/

$('.tablaVentas1').on( 'draw.dt', function(){

	quitarAgregarProducto();

})


/*=============================================
BORRAR VENTA
=============================================*/
$(".tablas").on("click", ".btnEliminarVenta", function(){

  var idVenta = $(this).attr("idVenta");

  swal({
        title: '¿Está seguro de borrar la venta?',
        text: "¡Si no lo está puede cancelar la accíón!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Si, borrar venta!'
      }).then(function(result){
        if (result.value) {
          
            window.location = "index.php?ruta=ventas&idVenta="+idVenta;
        }

  })

})

/*=============================================
IMPRIMIR FACTURA
=============================================*/

$(".tablas").on("click", ".btnImprimirFactura", function(){

	var codigoVenta = $(this).attr("codigoVenta");

	window.open("extensiones/tcpdf/pdf/factura.php?codigo="+codigoVenta, "_blank");

})

/*=============================================
RANGO DE FECHAS
=============================================*/

$('#daterange-btn').daterangepicker(
  {
    ranges   : {
      'Hoy'       : [moment(), moment()],
      'Ayer'   : [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
      'Últimos 7 días' : [moment().subtract(6, 'days'), moment()],
      'Últimos 30 días': [moment().subtract(29, 'days'), moment()],
      'Este mes'  : [moment().startOf('month'), moment().endOf('month')],
      'Último mes'  : [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
    },
    startDate: moment(),
    endDate  : moment()
  },
  function (start, end) {
    $('#daterange-btn span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));

    var fechaInicial = start.format('YYYY-MM-DD');

    var fechaFinal = end.format('YYYY-MM-DD');

    var capturarRango = $("#daterange-btn span").html();
   
   	localStorage.setItem("capturarRango", capturarRango);

   	window.location = "index.php?ruta=ventas&fechaInicial="+fechaInicial+"&fechaFinal="+fechaFinal;

  }

)

/*=============================================
CANCELAR RANGO DE FECHAS
=============================================*/

$(".daterangepicker.opensleft .range_inputs .cancelBtn").on("click", function(){

	localStorage.removeItem("capturarRango");
	localStorage.clear();
	window.location = "ventas";
})

/*=============================================
CAPTURAR HOY
=============================================*/

$(".daterangepicker.opensleft .ranges li").on("click", function(){

	var textoHoy = $(this).attr("data-range-key");

	if(textoHoy == "Hoy"){

		var d = new Date();
		
		var dia = d.getDate();
		var mes = d.getMonth()+1;
		var año = d.getFullYear();

		if(mes < 10){

			var fechaInicial = año+"-0"+mes+"-"+dia;
			var fechaFinal = año+"-0"+mes+"-"+dia;

		}else if(dia < 10){

			var fechaInicial = año+"-"+mes+"-0"+dia;
			var fechaFinal = año+"-"+mes+"-0"+dia;

		}else if(mes < 10 && dia < 10){

			var fechaInicial = año+"-0"+mes+"-0"+dia;
			var fechaFinal = año+"-0"+mes+"-0"+dia;

		}else{

			var fechaInicial = año+"-"+mes+"-"+dia;
	    	var fechaFinal = año+"-"+mes+"-"+dia;

		}	

    	localStorage.setItem("capturarRango", "Hoy");

    	window.location = "index.php?ruta=ventas&fechaInicial="+fechaInicial+"&fechaFinal="+fechaFinal;

	}

})





