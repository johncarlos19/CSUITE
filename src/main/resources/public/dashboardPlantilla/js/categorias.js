/*=============================================
EDITAR CATEGORIA
=============================================*/

function reloadIMPTABLA(obj, posi){
	var lis = [];
	while(lis.length > 0) {
		lis.pop();
	}
	switch (posi) {
		case "add":
			console.log("ca a imprimir")
			// var obj = e.data.data;
			for(var key in obj) {
				var sto;
				var accion = ' <button  class="btn btn-success btnAddIMP"  data-dismiss="modal" idIMP="'+obj[key].id+'" >Agregar <i class="fa fa-plus"></i></button>'





				var employee = {
					"DT_RowId": "IMPADD_"+ obj[key].id,
					"#":    obj[key].id,
					"Nombre":   obj[key].nombre,
					"Tipo de Tributo": obj[key].operacion,
					"Valor":   obj[key].valorSumandoExtra,
					"Total":   currentyMoney(obj[key].precioNeto),
					"Acción":     accion
				}

				// c = [];
				// c.push(obj[key].id);
				// c.push(obj[key].nombre);
				// c.push(obj[key].descripcion);
				// c.push(obj[key].disponible);
				// c.push(obj[key].stock);
				// c.push(obj[key].precioVenta);
				// c.push(obj[key].precioCompra);
				lis.push(employee);
			};
			$('.tablasModal').DataTable().clear().destroy();
			$('.tablasModal').DataTable({
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
				"data": lis
				,
				"columns":[
					{"data": "#", "width": "10%"},
					{"data": "Nombre", "width": "10%"},
					{"data": "Tipo de Tributo", "width": "20%"},
					{"data": "Valor", "width": "20%"},
					{"data": "Total", "width": "20%"},
					{"data": "Acción", "width": "15%"}
				],

			} );
			break;
		case "show":
			console.log("ca a imprimir")

			for(var key in obj) {
				var sto;
				var accion = ' <button  class="btn btn-danger btnEliminar" data-toggle="modal" idIMP="'+obj[key].idUnion+'" >Eliminar <i class="fa fa-trash"></i></button>'





				var employee = {
					"DT_RowId": "IMPSHOW_"+ obj[key].id,
					"#":    obj[key].id,
					"Nombre":   obj[key].nombre,
					"Tipo de Tributo": obj[key].operacion,
					"Valor":   obj[key].valorSumandoExtra,
					"Total":   currentyMoney(obj[key].precioNeto),
					"Acción":     accion
				}

				// c = [];
				// c.push(obj[key].id);
				// c.push(obj[key].nombre);
				// c.push(obj[key].descripcion);
				// c.push(obj[key].disponible);
				// c.push(obj[key].stock);
				// c.push(obj[key].precioVenta);
				// c.push(obj[key].precioCompra);
				lis.push(employee);
			};
			$('#tablaImpuesto').DataTable().clear().destroy();
			$('#tablaImpuesto').DataTable({
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
				"data": lis
				,
				"columns":[
					{"data": "#", "width": "10%"},
					{"data": "Nombre", "width": "10%"},
					{"data": "Tipo de Tributo", "width": "20%"},
					{"data": "Valor", "width": "20%"},
					{"data": "Total", "width": "20%"},
					{"data": "Acción", "width": "15%"}
				],

			} );
			break;
		default:
			break;

	}


}

function reloadAlmacenTabla(obj){
	var lis = [];
	while(lis.length > 0) {
		lis.pop();
	}
	console.log("ca a imprimir")
	for(var key in obj) {
		var sto;
		var accion = ' <button  class="btn btn-danger btnEditarCategoria" data-toggle="modal" idIMP="'+obj[key].id+'" data-target="#modalEditarCategoria">Eliminar <i class="fa fa-trash"></i></button>'





		var employee = {
			"DT_RowId": "almacen_"+ obj[key].idAlmacen,
			"#": obj[key].idAlmacen,
			"Origen":    obj[key].proveedor,
			"Fecha De Registro":   returnDate(obj[key].fechaRegistro),
			"Costo": currentyMoney(obj[key].costo),
			"Producto Agregado":   obj[key].productoAgregado,
			"Producto Vendido":   obj[key].productoVendido,
			"Producto Descartado":   obj[key].productoDescartado
			// ,"Acción":     accion
		}

		// c = [];
		// c.push(obj[key].id);
		// c.push(obj[key].nombre);
		// c.push(obj[key].descripcion);
		// c.push(obj[key].disponible);
		// c.push(obj[key].stock);
		// c.push(obj[key].precioVenta);
		// c.push(obj[key].precioCompra);
		lis.push(employee);
	};
	$('#tablaAlmacen').DataTable().clear().destroy();
	$('#tablaAlmacen').DataTable({
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
		"data": lis,
		"columns":[
			{"data": "#", "width": "10%"},
			{"data": "Origen", "width": "10%"},
			{"data": "Fecha De Registro", "width": "20%"},
			{"data": "Costo", "width": "20%"},
			{"data": "Producto Agregado", "width": "20%"},
			{"data": "Producto Vendido", "width": "20%"},
			{"data": "Producto Descartado", "width": "20%"}
		],

	} );
}


$(".tablas").on("click", ".btnEditarCategoria", function(){

	var idCategoria = $(this).attr("idCategoria");

	var datos = new FormData();
	datos.append("idCategoria", idCategoria);

	$.ajax({
		url: "ajax/categorias.ajax.php",
		method: "POST",
      	data: datos,
      	cache: false,
     	contentType: false,
     	processData: false,
     	dataType:"json",
     	success: function(respuesta){

     		$("#editarCategoria").val(respuesta["categoria"]);
     		$("#idCategoria").val(respuesta["id"]);

     	}

	})


})
$(".tablas").on("click", ".btnEliminarIMP", function(){

	var idCategoria = $(this).attr("id");

	swal({
		title: '¿Está seguro que desea Eliminar Este Actributo?',
		text: "¡Si no lo está puede cancelar la acción!",
		type: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		cancelButtonText: 'Cancelar',
		confirmButtonText: 'Si, borrar Actributo!'
	}).then(function(result){

		if(result.value){

			var form = document.createElement("form");
			var element1 = document.createElement("input");
			var element2 = document.createElement("input");

			form.method = "POST";
			form.action = "/dashboard/impuesto";

			element1.value=idCategoria;
			element1.name="impuesto";
			form.appendChild(element1);

			element2.value="eliminar";
			element2.name="action";
			form.appendChild(element2);

			document.body.appendChild(form);

			form.submit();

		}

	})

})


/*=============================================
ELIMINAR CATEGORIA
=============================================*/
$(".tablas").on("click", ".btnEliminarCategoria", function(){

	 var idCategoria = $(this).attr("id");

	 swal({
	 	title: '¿Está seguro de borrar la categoría?',
	 	text: "¡Si no lo está puede cancelar la acción!",
	 	type: 'warning',
	 	showCancelButton: true,
	 	confirmButtonColor: '#3085d6',
	 	cancelButtonColor: '#d33',
	 	cancelButtonText: 'Cancelar',
	 	confirmButtonText: 'Si, borrar categoría!'
	 }).then(function(result){

	 	if(result.value){

			var form = document.createElement("form");
			var element1 = document.createElement("input");
			var element2 = document.createElement("input");

			form.method = "POST";
			form.action = "/dashboard/categoria";

			element1.value=idCategoria;
			element1.name="categoria";
			form.appendChild(element1);

			element2.value="eliminar";
			element2.name="action";
			form.appendChild(element2);

			document.body.appendChild(form);

			form.submit();

	 	}

	 })

})