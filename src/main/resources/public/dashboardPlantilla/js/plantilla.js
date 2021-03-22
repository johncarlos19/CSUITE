/*=============================================
SideBar Menu
=============================================*/

$('.sidebar-menu').tree()

/*=============================================
Data Table
=============================================*/

$(".tablas").DataTable({

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

	}

});



// DataTable


function dataFilter(){



	// $('.table').DataTable( {
	// 	initComplete: function () {
	// 		this.api().columns().every( function () {
	// 			var column = this;
	// 			var select = $('<select><option value=""></option></select>')
	// 				.appendTo( $(column.footer()).empty() )
	// 				.on( 'change', function () {
	// 					var val = $.fn.dataTable.util.escapeRegex(
	// 						$(this).val()
	// 					);
	//
	// 					column
	// 						.search( val ? '^'+val+'$' : '', true, false )
	// 						.draw();
	// 				} );
	//
	// 			column.data().unique().sort().each( function ( d, j ) {
	// 				select.append( '<option value="'+d+'">'+d+'</option>' )
	// 			} );
	// 		} );
	// 	}
	// } );
	// $('.table').DataTable( {
	// 	initComplete: function () {
	// 		this.api().columns().every( function () {
	// 			var column = this;
	// 			var select = $('<select><option value=""></option></select>')
	// 				.appendTo( $(column.footer()).empty() )
	// 				.on( 'change', function () {
	// 					var val = $.fn.dataTable.util.escapeRegex(
	// 						$(this).val()
	// 					);
	//
	// 					column
	// 						.search( val ? '^'+val+'$' : '', true, false )
	// 						.draw();
	// 				} );
	//
	// 			column.data().unique().sort().each( function ( d, j ) {
	// 				select.append( '<option value="'+d+'">'+d+'</option>' )
	// 			} );
	// 		} );
	// 	}
	// } );
}
function startResponsible(){
	$('.tablasModal').DataTable()
		.columns.adjust()
		.responsive.recalc();
}


function reloadResponsibleTable(){
	setTimeout(startResponsible, 300);
}


/*=============================================
 //iCheck for checkbox and radio inputs
=============================================*/

$('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
  checkboxClass: 'icheckbox_minimal-blue',
  radioClass   : 'iradio_minimal-blue'
})

/*=============================================
 //input Mask
=============================================*/

//Datemask dd/mm/yyyy
$('#datemask').inputmask('dd/mm/yyyy', { 'placeholder': 'dd/mm/yyyy' })
//Datemask2 mm/dd/yyyy
$('#datemask2').inputmask('mm/dd/yyyy', { 'placeholder': 'mm/dd/yyyy' })
//Money Euro
$('[data-mask]').inputmask()

/*=============================================
CORRECCIÓN BOTONERAS OCULTAS BACKEND	
=============================================*/

if(window.matchMedia("(max-width:767px)").matches){
	
	$("body").removeClass('sidebar-collapse');

}else{

	$("body").addClass('sidebar-collapse');
}
