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

const formatConfig = {
	// style: "currency",
	// currency: "DOP", // CNY for Chinese Yen, EUR for Euro
	minimumFractionDigits: 2,
	currencyDisplay: "symbol",
};



function reloadtabladeInevntario(){
	worker.postMessage({'cmd': 'ventaINV'});
}


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
	worker.postMessage({'cmd': 'fecha'});


}


var timeSession = 300000-(1500*60);
var logout = 1000*60;
var renovo = false;
var preciono = false;







function extendTime(){
	preciono = true;
	worker.postMessage({'cmd': 'extendSession'});

}
function alertaTime(){
	if (renovo===false){
		$("#modalAlertaTime").modal({
			backdrop: 'static',
			keyboard: false
		})
		$("#modalAlertaTime").css("display", "block");
		document.getElementById("time").innerHTML =  '<div class="alert alert-danger alert-dismissible">\n' +
			'\t\t\t\t\t\t\t  La Sesión ha caducado\n' +
			'\t\t\t\t\t\t  </div>'
		document.getElementById("timeButton").innerHTML = '<button type="button" class="btn btn-primary" data-dismiss="modal"  onclick="extendTime()">Extender Tiempo</button>'
		setTimeout(alertLogout,logout);
	}else{
		$("#modalAlertaTime").modal({
			backdrop: 'static',
			keyboard: false
		})
		$("#modalAlertaTime").css("display", "block");
		document.getElementById("time").innerHTML =  '<div class="alert alert-danger alert-dismissible">\n' +
			'\t\t\t\t\t\t\t  Ha excedido la cantidad de intento en renovar\n' +
			'\t\t\t\t\t\t  </div>'
		document.getElementById("timeButton").innerHTML = '<form action="/logout" method="get">' +
			'<button type="submit" class="btn btn-primary"   >Salir</button>' +
			'</form>'
	}

}
function alertLogout(){
	if (preciono===false){
		$("#modalAlertaTime").css("display", "block");
		document.getElementById("time").innerHTML =  '<div class="alert alert-danger alert-dismissible">\n' +
			'\t\t\t\t\t\t\t  Ha excedido el tiempo en renovar la sesión\n' +
			'\t\t\t\t\t\t  </div>'
		document.getElementById("timeButton").innerHTML = '<form action="/logout" method="get">' +
			'<button type="submit" class="btn btn-primary"   >Salir</button>' +
			'</form>'
	}else{
		preciono = false;
	}

}


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
	
