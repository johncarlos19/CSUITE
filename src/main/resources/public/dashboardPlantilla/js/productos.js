/*=============================================
CARGAR LA TABLA DINÁMICA DE PRODUCTOS
=============================================*/

// $.ajax({

// 	url: "ajax/datatable-productos.ajax.php",
// 	success:function(respuesta){
		
// 		console.log("respuesta", respuesta);

// 	}

// })
//llamando el webworker
var worker = new Worker('/js/webworker-ajax.js');
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
				"Precio de compra":   obj[key].precioCompra,
				"Precio de venta":     obj[key].precioVenta,
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




};



function uploadPorduct(){
	var nombre = document.getElementById("nombreProducto").value;
	var codigo = document.getElementById("nuevoCodigo").value;
	var descripcion = document.getElementById("nuevaDescripcion").value;
	var stock = document.getElementById("stock").value;
	var suplidor = document.getElementById("suplidor").value;
	var compra = document.getElementById("nuevoPrecioCompra").value;
	var venta = document.getElementById("nuevoPrecioVenta").value;
	var reader = new FileReader();
	var categoria = document.getElementById("nuevaCategoria").value;
	var file1 = document.getElementById("imagen").files[0]
	if (document.getElementById("imagen").files.length == 0){
		let temp = {

			nombre: nombre,
			codigo: codigo,
			descripcion: descripcion,
			stock: stock,
			suplidor: suplidor,
			compra: compra,
			venta: venta,
			base64: null,
			mimetype: null,
			nombreImg: null,
			categorias: categoria
		}
		console.log(temp)
		worker.postMessage({'cmd': 'uploadProducto', 'ProductoSaveJson': temp});

	}else{
		var mimetype = file1.type
		var nombreImg = file1.name


		reader.readAsDataURL(file1);
		reader.onload = function () {



			let temp = {

				nombre: nombre,
				codigo: codigo,
				descripcion: descripcion,
				stock: stock,
				suplidor: suplidor,
				compra: compra,
				venta: venta,
				base64: reader.result,
				mimetype: mimetype,
				nombreImg: nombreImg,
				categorias: categoria
			}
			console.log(temp)
			worker.postMessage({'cmd': 'uploadProducto', 'ProductoSaveJson': temp});
		};
		reader.onerror = function (error) {
			console.log('Error: ', error);
		};

	}











}


var perfilOculto = $("#perfilOculto").val();
function reloadTabla() {
	document.getElementById("loading").innerHTML = '<i class="fa fa-refresh fa-spin"></i>';
	worker.postMessage({'cmd': 'producto'});


}

$(document).ready(function() {
reloadTabla();
});

var valll = "<div className='btn-group'><button className='btn btn-warning btnEditarProducto' idproducto='61'  data-toggle='modal' data-target=''#modalEditarProducto'><i  className='fa fa-pencil'></i></button> <button className='btn btn-danger btnEliminarProducto' idproducto='61'  codigo='118'  imagen='vistas/img/productos/default/anonymous.png'><i  className='fa fa-times'></i></button> </div>";


/*=============================================
CAPTURANDO LA CATEGORIA PARA ASIGNAR CÓDIGO
=============================================*/
$("#nuevaCategoria").change(function(){

	var idCategoria = $(this).val();

	var datos = new FormData();
  	datos.append("idCategoria", idCategoria);

  	$.ajax({

      url:"ajax/productos.ajax.php",
      method: "POST",
      data: datos,
      cache: false,
      contentType: false,
      processData: false,
      dataType:"json",
      success:function(respuesta){

      	if(!respuesta){

      		var nuevoCodigo = idCategoria+"01";
      		$("#nuevoCodigo").val(nuevoCodigo);

      	}else{

      		var nuevoCodigo = Number(respuesta["codigo"]) + 1;
          	$("#nuevoCodigo").val(nuevoCodigo);

      	}
                
      }

  	})

})

/*=============================================
AGREGANDO PRECIO DE VENTA
=============================================*/
$("#nuevoPrecioCompra, #editarPrecioCompra").change(function(){

	if($(".porcentaje").prop("checked")){

		var valorPorcentaje = $(".nuevoPorcentaje").val();
		
		var porcentaje = Number(($("#nuevoPrecioCompra").val()*valorPorcentaje/100))+Number($("#nuevoPrecioCompra").val());

		var editarPorcentaje = Number(($("#editarPrecioCompra").val()*valorPorcentaje/100))+Number($("#editarPrecioCompra").val());

		$("#nuevoPrecioVenta").val(porcentaje);
		$("#nuevoPrecioVenta").prop("readonly",true);

		$("#editarPrecioVenta").val(editarPorcentaje);
		$("#editarPrecioVenta").prop("readonly",true);

	}

})

/*=============================================
CAMBIO DE PORCENTAJE
=============================================*/
$(".nuevoPorcentaje").change(function(){

	if($(".porcentaje").prop("checked")){

		var valorPorcentaje = $(this).val();
		
		var porcentaje = Number(($("#nuevoPrecioCompra").val()*valorPorcentaje/100))+Number($("#nuevoPrecioCompra").val());

		var editarPorcentaje = Number(($("#editarPrecioCompra").val()*valorPorcentaje/100))+Number($("#editarPrecioCompra").val());

		$("#nuevoPrecioVenta").val(porcentaje);
		$("#nuevoPrecioVenta").prop("readonly",true);

		$("#editarPrecioVenta").val(editarPorcentaje);
		$("#editarPrecioVenta").prop("readonly",true);

	}

})

$(".porcentaje").on("ifUnchecked",function(){

	$("#nuevoPrecioVenta").prop("readonly",false);
	$("#editarPrecioVenta").prop("readonly",false);

})

$(".porcentaje").on("ifChecked",function(){

	$("#nuevoPrecioVenta").prop("readonly",true);
	$("#editarPrecioVenta").prop("readonly",true);

})

/*=============================================
SUBIENDO LA FOTO DEL PRODUCTO
=============================================*/

$(".nuevaImagen").change(function(){

	var imagen = this.files[0];
	
	/*=============================================
  	VALIDAMOS EL FORMATO DE LA IMAGEN SEA JPG O PNG
  	=============================================*/

  	if(imagen["type"] != "image/jpeg" && imagen["type"] != "image/png"){

  		$(".nuevaImagen").val("");

  		 swal({
		      title: "Error al subir la imagen",
		      text: "¡La imagen debe estar en formato JPG o PNG!",
		      type: "error",
		      confirmButtonText: "¡Cerrar!"
		    });

  	}else if(imagen["size"] > 2000000){

  		$(".nuevaImagen").val("");

  		 swal({
		      title: "Error al subir la imagen",
		      text: "¡La imagen no debe pesar más de 2MB!",
		      type: "error",
		      confirmButtonText: "¡Cerrar!"
		    });

  	}else{

  		var datosImagen = new FileReader;
  		datosImagen.readAsDataURL(imagen);

  		$(datosImagen).on("load", function(event){

  			var rutaImagen = event.target.result;

  			$(".previsualizar").attr("src", rutaImagen);

  		})

  	}
})

/*=============================================
EDITAR PRODUCTO
=============================================*/


$(".tablaProductos tbody").on("click", "button.btnEditarProducto", function(){

	var idProducto = $(this).attr("idProducto");
	
	var datos = new FormData();
    datos.append("idProducto", idProducto);

     $.ajax({

      url:"ajax/productos.ajax.php",
      method: "POST",
      data: datos,
      cache: false,
      contentType: false,
      processData: false,
      dataType:"json",
      success:function(respuesta){
          
          var datosCategoria = new FormData();
          datosCategoria.append("idCategoria",respuesta["id_categoria"]);

           $.ajax({

              url:"ajax/categorias.ajax.php",
              method: "POST",
              data: datosCategoria,
              cache: false,
              contentType: false,
              processData: false,
              dataType:"json",
              success:function(respuesta){
                  
                  $("#editarCategoria").val(respuesta["id"]);
                  $("#editarCategoria").html(respuesta["categoria"]);

              }

          })

           $("#editarCodigo").val(respuesta["codigo"]);

           $("#editarDescripcion").val(respuesta["descripcion"]);

           $("#editarStock").val(respuesta["stock"]);

           $("#editarPrecioCompra").val(respuesta["precio_compra"]);

           $("#editarPrecioVenta").val(respuesta["precio_venta"]);

           if(respuesta["imagen"] != ""){

           	$("#imagenActual").val(respuesta["imagen"]);

           	$(".previsualizar").attr("src",  respuesta["imagen"]);

           }

      }

  })

})

/*=============================================
ELIMINAR PRODUCTO
=============================================*/

$(".tablaProductos tbody").on("click", "button.btnEliminarProducto", function(){

	var idProducto = $(this).attr("idProducto");
	var codigo = $(this).attr("codigo");
	var imagen = $(this).attr("imagen");
	
	swal({

		title: '¿Está seguro de borrar el producto?',
		text: "¡Si no lo está puede cancelar la accíón!",
		type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Si, borrar producto!'
        }).then(function(result) {
        if (result.value) {

        	window.location = "index.php?ruta=productos&idProducto="+idProducto+"&imagen="+imagen+"&codigo="+codigo;

        }


	})

})
	
