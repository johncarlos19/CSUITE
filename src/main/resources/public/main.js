


//llamando el webworker
var worker = new Worker('/js/webworker-ajax.js');
var tablaUsuarios;



worker.onmessage = function (e) { //recuperando la información
    if(e.data.cmd = 'respuesta'){


        const kk = [];
        var obj = e.data.data;
        let c = [];
        for(var key in obj) {
            var employee = {
                "user_id":    obj[key].id,
                "username":   obj[key].nombre,
                "first_name": obj[key].descripcion,
                "last_name":  obj[key].disponible,
                "gender":     obj[key].stock,
                "password":   obj[key].precioVenta,
                "status":     obj[key].precioCompra
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


        tablaUsuarios = $('#tablaUsuarios').DataTable({
            // "ajax":{
            //
            //     "data":JSON.stringify(kk), //enviamos opcion 4 para que haga un SELECT
            //     "dataSrc":""
            // },
            "data": kk,
            "columns":[
                {"data": "user_id"},
                {"data": "username"},
                {"data": "first_name"},
                {"data": "last_name"},
                {"data": "gender"},
                {"data": "password"},
                {"data": "status"},
                {"defaultContent": "<div class='text-center'><div class='btn-group'><button class='btn btn-primary btn-sm btnEditar'><i class='material-icons'>edit</i></button><button class='btn btn-danger btn-sm btnBorrar'><i class='material-icons'>delete</i></button></div></div>"}
            ]
        });


    }
return JSON.stringify(kk);
};

/**
 * Función para recuperar la fecha utilizando el worker.
 * Simulamos que estamos cargando información puede tomar mucho tiempo..
 */
function recuperarFecha() {
    return worker.postMessage({'cmd': 'fecha'});
}




$(document).ready(function() {
var user_id, opcion;
opcion = 4;
console.log("sssssssssssss"+recuperarFecha())
    ;
    


var fila; //captura la fila, para editar o eliminar
//submit para el Alta y Actualización
$('#formUsuarios').submit(function(e){                         
    e.preventDefault(); //evita el comportambiento normal del submit, es decir, recarga total de la página
    username = $.trim($('#username').val());    
    first_name = $.trim($('#first_name').val());
    last_name = $.trim($('#last_name').val());    
    gender = $.trim($('#gender').val());    
    password = $.trim($('#password').val());
    status = $.trim($('#status').val());                            
        $.ajax({
          url: "bd/crud.php",
          type: "POST",
          datatype:"json",    
          data:  {user_id:user_id, username:username, first_name:first_name, last_name:last_name, gender:gender, password:password ,status:status ,opcion:opcion},    
          success: function(data) {
            tablaUsuarios.ajax.reload(null, false);
           }
        });			        
    $('#modalCRUD').modal('hide');											     			
});
        
 

//para limpiar los campos antes de dar de Alta una Persona
$("#btnNuevo").click(function(){
    opcion = 1; //alta           
    user_id=null;
    $("#formUsuarios").trigger("reset");
    $(".modal-header").css( "background-color", "#17a2b8");
    $(".modal-header").css( "color", "white" );
    $(".modal-title").text("Alta de Usuario");
    $('#modalCRUD').modal('show');	    
});

//Editar        
$(document).on("click", ".btnEditar", function(){		        
    opcion = 2;//editar
    fila = $(this).closest("tr");	        
    user_id = parseInt(fila.find('td:eq(0)').text()); //capturo el ID		            
    username = fila.find('td:eq(1)').text();
    first_name = fila.find('td:eq(2)').text();
    last_name = fila.find('td:eq(3)').text();
    gender = fila.find('td:eq(4)').text();
    password = fila.find('td:eq(5)').text();
    status = fila.find('td:eq(6)').text();
    $("#username").val(username);
    $("#first_name").val(first_name);
    $("#last_name").val(last_name);
    $("#gender").val(gender);
    $("#password").val(password);
    $("#status").val(status);
    $(".modal-header").css("background-color", "#007bff");
    $(".modal-header").css("color", "white" );
    $(".modal-title").text("Editar Usuario");		
    $('#modalCRUD').modal('show');		   
});

//Borrar
$(document).on("click", ".btnBorrar", function(){
    fila = $(this);           
    user_id = parseInt($(this).closest('tr').find('td:eq(0)').text()) ;		
    opcion = 3; //eliminar        
    var respuesta = confirm("¿Está seguro de borrar el registro "+user_id+"?");                
    if (respuesta) {            
        $.ajax({
          url: "bd/crud.php",
          type: "POST",
          datatype:"json",    
          data:  {opcion:opcion, user_id:user_id},    
          success: function() {
              tablaUsuarios.row(fila.parents('tr')).remove().draw();                  
           }
        });	
    }
 });
     
});    