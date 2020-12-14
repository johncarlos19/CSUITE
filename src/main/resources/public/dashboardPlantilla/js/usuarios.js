
/*=============================================
LISTADO DE PAISES
=============================================*/

var worker = new Worker('/js/webworker-ajax.js');




worker.onmessage = function (e) { //recuperando la información

	console.log("entro11")
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


function saveEmpleado(){
	var user = document.getElementById("id").value;

	worker.postMessage({'cmd': 'userAvailable', 'user': user});
}







$.ajax({

	url:"vistas/js/plugins/paises.json",
	type: "GET",
	success: function(respuesta){
		
		respuesta.forEach(seleccionarPais);

		function seleccionarPais(item, index){
			
			var pais =  item.name;
			var codPais =  item.code;
			var dial = item.dial_code;

			$("#inputPais").append(

				`<option value="`+pais+`,`+codPais+`,`+dial+`">`+pais+`</option>`

			)


		}

	}

})




/*=============================================
AGREGAR DIAL CODE DEL PAIS
=============================================*/

$("#inputPais").change(function(){

	$(".dialCode").html($(this).val().split(",")[2])

})

/*=============================================
INPUT MASK
=============================================*/

$('[data-mask]').inputmask();

/*=============================================
VALIDAR FORMULARIO SUSCRIPCIÓN
=============================================*/

$(".suscribirse").click(function(){

	$(".alert").remove();

	var nombre = $("#inputName").val();
	var email = $("#inputEmail").val();
	var pais = $("#inputPais").val().split(",")[0];
	var codigo_pais = $("#inputPais").val().split(",")[1];
	var telefono_movil = $("#inputPais").val().split(",")[2]+" "+$("#inputMovil").val();
	var aceptarTerminos = $("#aceptarTerminos:checked").val();

	/*=============================================
	VALIDAR
	=============================================*/
	if( nombre == "" ||
		email == "" ||
		pais == "" ||
		codigo_pais == "" ||
		telefono_movil == "" ||
		aceptarTerminos != "on" ||



		console.log("formulario listo"));
	
	

})
/*=============================================
EDITAR USUARIO
=============================================*/
$(".tablas").on("click", ".btnEditarUsuario", function(){

	var idUsuario = $(this).attr("idUsuario");
	
	var datos = new FormData();
	datos.append("idUsuario", idUsuario);

	$.ajax({

		url:"ajax/usuarios.ajax.php",
		method: "POST",
		data: datos,
		cache: false,
		contentType: false,
		processData: false,
		dataType: "json",
		success: function(respuesta){
			
			$("#editarNombre").val(respuesta["nombre"]);
			$("#editarUsuario").val(respuesta["usuario"]);
			$("#editarPerfil").html(respuesta["perfil"]);
			$("#editarPerfil").val(respuesta["perfil"]);
			$("#fotoActual").val(respuesta["foto"]);

			$("#passwordActual").val(respuesta["password"]);

			if(respuesta["foto"] != ""){

				$(".previsualizarEditar").attr("src", respuesta["foto"]);

			}else{

				$(".previsualizarEditar").attr("src", "vistas/img/usuarios/default/anonymous.png");

			}

		}

	});

})

/*=============================================
ACTIVAR USUARIO
=============================================*/
$(".tablas").on("click", ".btnActivar", function(){

	var idUsuario = $(this).attr("idUsuario");
	var estadoUsuario = $(this).attr("estadoUsuario");

	var datos = new FormData();
 	datos.append("activarId", idUsuario);
  	datos.append("activarUsuario", estadoUsuario);

  	$.ajax({

	  url:"ajax/usuarios.ajax.php",
	  method: "POST",
	  data: datos,
	  cache: false,
      contentType: false,
      processData: false,
      success: function(respuesta){

      		if(window.matchMedia("(max-width:767px)").matches){

	      		 swal({
			      title: "El usuario ha sido actualizado",
			      type: "success",
			      confirmButtonText: "¡Cerrar!"
			    }).then(function(result) {
			        if (result.value) {

			        	window.location = "usuarios";

			        }


				});

	      	}

      }

  	})

  	if(estadoUsuario == 0){

  		$(this).removeClass('btn-success');
  		$(this).addClass('btn-danger');
  		$(this).html('Desactivado');
  		$(this).attr('estadoUsuario',1);

  	}else{

  		$(this).addClass('btn-success');
  		$(this).removeClass('btn-danger');
  		$(this).html('Activado');
  		$(this).attr('estadoUsuario',0);

  	}

})

/*=============================================
REVISAR SI EL USUARIO YA ESTÁ REGISTRADO
=============================================*/

$("#nuevoUsuario").change(function(){

	$(".alert").remove();

	var usuario = $(this).val();

	var datos = new FormData();
	datos.append("validarUsuario", usuario);

	 $.ajax({
	    url:"ajax/usuarios.ajax.php",
	    method:"POST",
	    data: datos,
	    cache: false,
	    contentType: false,
	    processData: false,
	    dataType: "json",
	    success:function(respuesta){
	    	
	    	if(respuesta){

	    		$("#nuevoUsuario").parent().after('<div class="alert alert-warning">Este usuario ya existe en la base de datos</div>');

	    		$("#nuevoUsuario").val("");

	    	}

	    }

	})
})

/*=============================================
ELIMINAR USUARIO
=============================================*/
$(".tablas").on("click", ".btnEliminarUsuario", function(){

  var idUsuario = $(this).attr("idUsuario");
  var fotoUsuario = $(this).attr("fotoUsuario");
  var usuario = $(this).attr("usuario");

  swal({
    title: '¿Está seguro de borrar el usuario?',
    text: "¡Si no lo está puede cancelar la accíón!",
    type: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      cancelButtonText: 'Cancelar',
      confirmButtonText: 'Si, borrar usuario!'
  }).then(function(result){

    if(result.value){

      window.location = "index.php?ruta=usuarios&idUsuario="+idUsuario+"&usuario="+usuario+"&fotoUsuario="+fotoUsuario;

    }

  })

})