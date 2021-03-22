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

function resizeBase64Img(base64, newWidth, newHeight) {
	return new Promise((resolve, reject)=>{


		var canvas = document.createElement("canvas");
		canvas.style.width = newWidth.toString()+"px";
		canvas.style.height = newHeight.toString()+"px";
		let context = canvas.getContext("2d");
		let img = document.createElement("img");
		img.src = base64;
		img.onload = function () {
			var iw=img.width;
			var ih=img.height;
			var scale=Math.min((newWidth/iw),(newHeight/ih));
			var iwScaled=iw*scale;
			var ihScaled=ih*scale;
			canvas.width=iwScaled;
			canvas.height=ihScaled;

			// context.scale(newWidth/img.width,  newHeight/img.height);
			context.drawImage(img, 0, 0,iwScaled,ihScaled);
			resolve(canvas.toDataURL());
		}
	});
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

			var base6412;
			console.log("Esto es lo prim" + reader.result)
			resizeBase64Img(reader.result, 150, 150).then(resized=>{

					base6412 = resized;
					console.log("sale esto"+base6412)
				let temp = {

					nombre: nombre,
					codigo: codigo,
					descripcion: descripcion,
					stock: stock,
					suplidor: suplidor,
					compra: compra,
					venta: venta,
					base64: base6412,
					mimetype: mimetype,
					nombreImg: nombreImg,
					categorias: categoria
				}
				console.log(temp)
				worker.postMessage({'cmd': 'uploadProducto', 'ProductoSaveJson': temp});

			});



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


function loadAvailableIMP(){
	startResponsible()
	var name = document.getElementById("idProducto").value;
	worker.postMessage({'cmd': 'searchImpuestoProductoAvailable', 'id': name});

}
function loadProductoIMP(){
	var name = document.getElementById("idProducto").value;
	worker.postMessage({'cmd': 'searchImpuestoProducto', 'id': name});
}
function loadProductoALMACEN(){
	var name = document.getElementById("idProducto").value;
	worker.postMessage({'cmd': 'searchAlmacenProducto', 'id': name});
}



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



function actionRelation(actionJson){
	switch (actionJson.typeClass) {
		case "Producto":
			worker.postMessage({'cmd': 'productoRelation', 'ActionJson': actionJson});
			break;
		default:
			break;


	}
}

function reloadShowProducto(obj){

	document.getElementById("nombre").innerText = obj.nombre;
	document.getElementById("descripcion").innerText = obj.descripcion;
	document.getElementById("codigo").innerText = obj.codigo_local;
	document.getElementById("categoria").innerText = obj.categorias;


	document.getElementById("codigoLocal").value =obj.codigo_local
	document.getElementById("nombreProductoEditar").value = obj.nombre
	document.getElementById("editarDescripcion").value = obj.descripcion
	document.getElementById("nuevoPrecioCompra").value = obj.precioCompra

	document.getElementById("precioVenta").innerText = (Math.round(obj.precioVenta * 100) / 100).toFixed(2);
	document.getElementById("precioLista").innerText = (Math.round(obj.precioLista * 100) / 100).toFixed(2);
	if(obj.stock  == 0){
		document.getElementById("stockActual").innerHTML =  "<span style='font-size: large;' class=' label label-default'>"+obj.stock +"</span>";
	}else if(obj.stock <=10){
		document.getElementById("stockActual").innerHTML =  "<span style='font-size: large;' class=' label label-danger'>"+obj.stock +"</span>";
	}else if(obj.stock <=15){
		document.getElementById("stockActual").innerHTML =  "<span style='font-size: large;' class=' label label-warning'>"+obj.stock +"</span>";
	}else{
		document.getElementById("stockActual").innerHTML =  "<span style='font-size: large;' class=' label label-success'>"+obj.stock +"</span>";
	}
}

function addAlmacen(){
	var employee = {
		idAlmacen: "0",
		fechaRegistro: "0",
		proveedor: document.getElementById("suplidor").value,
		productoAgregado: document.getElementById("stock").value,
		productoVendido: "0",
		productoDescartado: "0",
		costo: document.getElementById("nuevoPrecioCompraALM").value,
		precioVentaFutura: "0"
	}

	document.getElementById("suplidor").value = ""
	document.getElementById("stock").value = ""
	document.getElementById("nuevoPrecioCompraALM").value = ""
	var actionJson ={
		id: parseInt(document.getElementById("idProducto").value),
		typeClass: "Producto",
		action: "addAlmacen",
		detail: JSON.stringify(employee),
		anotherID: -1
	}
	actionRelation(actionJson);
}

function editarInfo(){
	var employee = {
		codigo: document.getElementById("codigoLocal").value,
		nombre: document.getElementById("nombreProductoEditar").value,
		descripcion: document.getElementById("editarDescripcion").value,
		categoria: document.getElementById("categoriaEditar").value
	}
	if (employee.categoria === null || employee.categoria === ""){

	}else{
		var actionJson ={
			id: parseInt(document.getElementById("idProducto").value),
			typeClass: "Producto",
			action: "editarInfoProducto",
			detail: JSON.stringify(employee),
			anotherID: -1
		}
		actionRelation(actionJson);
	}

}function editarFotoInfo(){
	var reader = new FileReader();
	var file1 = document.getElementById("imagenSave").files[0]

	if (document.getElementById("imagenSave").files.length == 0){
		let temp = {
			base64: null,
			mimetype: null,
			nombreImg: null
		}
		console.log(temp)
		document.getElementById("previsual").src = "../dashboardPlantilla/img/productos/default/anonymous.png";
		document.getElementById("imgActual").src = "../dashboardPlantilla/img/productos/default/anonymous.png";
		var actionJson ={
			id: parseInt(document.getElementById("idProducto").value),
			typeClass: "Producto",
			action: "editarFotoProducto",
			detail: JSON.stringify(temp),
			anotherID: parseInt(document.getElementById("idImagenSave").value)
		}
		actionRelation(actionJson);

	}else{
		var mimetype = file1.type
		var nombreImg = file1.name


		reader.readAsDataURL(file1);
		reader.onload = function () {

			var base6412;
			console.log("Esto es lo prim" + reader.result)
			resizeBase64Img(reader.result, 150, 150).then(resized=>{

				base6412 = resized;
				console.log("sale esto"+base6412)
				let temp = {
					base64: base6412,
					mimetype: mimetype,
					nombreImg: nombreImg
				}
				document.getElementById("previsual").src = base6412;
				document.getElementById("imgActual").src = base6412;
				console.log(temp)
				var actionJson ={
					id: parseInt(document.getElementById("idProducto").value),
					typeClass: "Producto",
					action: "editarFotoProducto",
					detail: JSON.stringify(temp),
					anotherID: parseInt(document.getElementById("idImagenSave").value)
				}
				actionRelation(actionJson);

			});



		};
		reader.onerror = function (error) {
			console.log('Error: ', error);
		};

	}


}

function editarPrecioProducto(){
	var employee = {
		venta: document.getElementById("nuevoPrecioVenta").value
	}

		var actionJson ={
			id: parseInt(document.getElementById("idProducto").value),
			typeClass: "Producto",
			action: "editarPrecioProducto",
			detail: JSON.stringify(employee),
			anotherID: -1
		}
		actionRelation(actionJson);


}

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

// add impuesto
$("#tablaIMPADD tbody").on("click", "button.btnAddIMP", function(){


	var idImp = $(this).attr("idIMP");
	console.log("Impuesto "+idImp)

	var actionJson ={
		id: parseInt(idImp),
		typeClass: "Producto",
		action: "addImpuesto",
		detail: "",
		anotherID: parseInt(document.getElementById("idProducto").value)
	}

	actionRelation(actionJson);
	//
	// var idProducto = document.getElementById("idProducto").value;
	// var idIMP = $(this).attr("idIMP");
	//
	// var form = document.createElement("form");
	// var element1 = document.createElement("input");
	// // var element2 = document.createElement("input");
	//
	//
	// let producto = {
	// 	idProducto: parseInt(idProducto),
	// 	idImpuesto: parseInt(idIMP)
	// }
	// form.method = "POST";
	// form.action = "/dashboard/showProducto";
	//
	// element1.value= idIMP;
	// element1.name="idIMP";
	// form.appendChild(element1);
	// //
	// // element2.value="eliminar";
	// // element2.name="action";
	// // form.appendChild(element2);
	//
	// document.body.appendChild(form);
	//
	// form.submit();


})



//delete impuesto
$("#tablaImpuesto tbody").on("click", "button.btnEliminar", function(){
	var idImp = $(this).attr("idIMP");
	console.log("Impuesto "+idImp)
	console.log("ImpuestoInt "+parseInt(idImp))
	swal({

		title: '¿Está seguro de borrar este Tributo?',
		text: "¡Si no lo está puede cancelar la accíón!",
		type: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		cancelButtonText: 'Cancelar',
		confirmButtonText: 'Si, borrar producto!'
	}).then(function(result) {
		if (result.value) {



			var actionJson ={
				id: parseInt(idImp),
				typeClass: "Producto",
				action: "deleteImpuesto",
				detail: "",
				anotherID: parseInt(document.getElementById("idProducto").value)
			}

			actionRelation(actionJson);
		}


	})


	//
	// var idProducto = document.getElementById("idProducto").value;
	// var idIMP = $(this).attr("idIMP");
	//
	// var form = document.createElement("form");
	// var element1 = document.createElement("input");
	// // var element2 = document.createElement("input");
	//
	//
	// let producto = {
	// 	idProducto: parseInt(idProducto),
	// 	idImpuesto: parseInt(idIMP)
	// }
	// form.method = "POST";
	// form.action = "/dashboard/showProducto";
	//
	// element1.value= idIMP;
	// element1.name="idIMP";
	// form.appendChild(element1);
	// //
	// // element2.value="eliminar";
	// // element2.name="action";
	// // form.appendChild(element2);
	//
	// document.body.appendChild(form);
	//
	// form.submit();


})
/*=============================================
EDITAR PRODUCTO
=============================================*/




$(".tablaProductos tbody").on("click", "button.btnEditarProducto", function(){

	var idProducto = $(this).attr("idProducto");

	var form = document.createElement("form");
	var element1 = document.createElement("input");
	// var element2 = document.createElement("input");

	form.method = "POST";
	form.action = "/dashboard/showProducto";

	element1.value= idProducto;
	element1.name="idProducto";
	form.appendChild(element1);
	//
	// element2.value="eliminar";
	// element2.name="action";
	// form.appendChild(element2);

	document.body.appendChild(form);

	form.submit();
	//
	// var datos = new FormData();
  //   datos.append("idProducto", idProducto);
  //
  //    $.ajax({
  //
  //     url:"ajax/productos.ajax.php",
  //     method: "POST",
  //     data: datos,
  //     cache: false,
  //     contentType: false,
  //     processData: false,
  //     dataType:"json",
  //     success:function(respuesta){
  //
  //         var datosCategoria = new FormData();
  //         datosCategoria.append("idCategoria",respuesta["id_categoria"]);
  //
  //          $.ajax({
  //
  //             url:"ajax/categorias.ajax.php",
  //             method: "POST",
  //             data: datosCategoria,
  //             cache: false,
  //             contentType: false,
  //             processData: false,
  //             dataType:"json",
  //             success:function(respuesta){
  //
  //                 $("#editarCategoria").val(respuesta["id"]);
  //                 $("#editarCategoria").html(respuesta["categoria"]);
  //
  //             }
  //
  //         })
  //
  //          $("#editarCodigo").val(respuesta["codigo"]);
  //
  //          $("#editarDescripcion").val(respuesta["descripcion"]);
  //
  //          $("#editarStock").val(respuesta["stock"]);
  //
  //          $("#editarPrecioCompra").val(respuesta["precio_compra"]);
  //
  //          $("#editarPrecioVenta").val(respuesta["precio_venta"]);
  //
  //          if(respuesta["imagen"] != ""){
  //
  //          	$("#imagenActual").val(respuesta["imagen"]);
  //
  //          	$(".previsualizar").attr("src",  respuesta["imagen"]);
  //
  //          }
  //
  //     }
  //
  // })

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



	
