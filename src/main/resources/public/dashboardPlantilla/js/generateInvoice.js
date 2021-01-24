function imprimir() {
    window.print();
}

function generateBarcode() {
    var value = $("#facturaPrint").html();
    var btype = "code39";
    var renderer = "bmp";

    var settings = {
        output: renderer,
        bgColor: "#FFFFFF",
        color: "#000000",
        barWidth: "1",
        barHeight: "50",
        moduleSize: "5",
        posX: "10",
        posY: "20",
        addQuietZone: "1"
    };
    $("#barcodeTarget").html("").show().barcode(value, btype, settings);

}

function showConfig1D() {
    $('.config .barcode1D').show();
    $('.config .barcode2D').hide();
}

function showConfig2D() {
    $('.config .barcode1D').hide();
    $('.config .barcode2D').show();
}

function clearCanvas() {
    var canvas = $('#canvasTarget').get(0);
    var ctx = canvas.getContext('2d');
    ctx.lineWidth = 1;
    ctx.lineCap = 'butt';
    ctx.fillStyle = '#FFFFFF';
    ctx.strokeStyle = '#000000';
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.strokeRect(0, 0, canvas.width, canvas.height);
}

function addProductoInInvoice(producto){
    if (producto.impuestoClientes.length ===0){

        var string = '<tr class="item ">\n' +
            '            <td style="text-align: left; padding-top: 0px; padding-bottom: 0px; font-weight: bold;">\n' +
            '                '+producto[key].stock+'\n' +
            '\n' +
            '            </td>\n' +
            '\n' +
            '            <td style="text-align: left; padding-top: 0px; padding-bottom: 0px; font-weight: bold;">\n' +
            '                '+producto.nombre +' - '+ producto.descripcion+'\n' +
            '            </td>\n' +
            '\n' +
            '\n' +
            '            <td style="text-align: left; padding-top: 0px; padding-bottom: 0px; font-weight: bold;">\n' +
            '\n' +
            '                '+currentyMoney(producto.precioVenta)+'\n' +
            '\n' +
            '            </td>\n' +
            '            <td style="text-align: right; font-weight: bold; padding-top: 0px; padding-bottom: 0px;">\n' +
            '\n' +
            '                '+currentyMoney(producto.precioLista*producto[key].stock)+'\n' +
            '\n' +
            '            </td>\n' +
            '        </tr>'
        return string;
    }else{
        var string = '<tr class="item last">\n' +
            '            <td style="text-align: left; padding-top: 0px; padding-bottom: 0px; font-weight: bold;">\n' +
            '                '+producto[key].stock+'\n' +
            '\n' +
            '            </td>\n' +
            '\n' +
            '            <td style="text-align: left; padding-top: 0px; padding-bottom: 0px; font-weight: bold;">\n' +
            '                '+producto.nombre +' - '+ producto.descripcion+'\n' +
            '            </td>\n' +
            '\n' +
            '\n' +
            '            <td style="text-align: left; padding-top: 0px; padding-bottom: 0px; font-weight: bold;">\n' +
            '\n' +
            '                '+currentyMoney(producto.precioVenta)+'\n' +
            '\n' +
            '            </td>\n' +
            '            <td style="text-align: right; font-weight: bold; padding-top: 0px; padding-bottom: 0px;">\n' +
            '\n' +
            '                '+currentyMoney(producto.precioLista*producto[key].stock)+'\n' +
            '\n' +
            '            </td>\n' +
            '        </tr>'
        for (var key in producto.impuestoClientes){
            if (key === (producto.impuestoClientes.length-1)){
                var impString = '<tr class="item">\n' +
                    '            <td style="text-align: left; padding-top: 0px; padding-bottom: 0px;">\n' +
                    '                <br>\n' +
                    '\n' +
                    '            </td>\n' +
                    '            <td style="text-align: left; padding-top: 0px; padding-bottom: 0px;">\n' +
                    '\n' +
                    '                '+producto.impuestoClientes[key].nombre+'\n' +
                    '\n' +
                    '            </td>\n' +
                    '\n' +
                    '\n' +
                    '            <td style="text-align: left; padding-top: 0px; padding-bottom: 0px;">\n' +
                    '                '+currentyMoney(producto.impuestoClientes[key].valorSumandoExtra)+'\n' +
                    '            </td>\n' +
                    '\n' +
                    '            <td style="text-align: right; padding-top: 0px; padding-bottom: 0px;">\n' +
                    '                <br>\n' +
                    '            </td>\n' +
                    '        </tr>'
                string.concat("\n",impString);
            }else{
                var impString = '<tr class="item last">\n' +
                    '            <td style="text-align: left; padding-top: 0px; padding-bottom: 0px;">\n' +
                    '                <br>\n' +
                    '\n' +
                    '            </td>\n' +
                    '            <td style="text-align: left; padding-top: 0px; padding-bottom: 0px;">\n' +
                    '\n' +
                    '                '+producto.impuestoClientes[key].nombre+'\n' +
                    '\n' +
                    '            </td>\n' +
                    '\n' +
                    '\n' +
                    '            <td style="text-align: left; padding-top: 0px; padding-bottom: 0px;">\n' +
                    '                '+currentyMoney(producto.impuestoClientes[key].valorSumandoExtra)+'\n' +
                    '            </td>\n' +
                    '\n' +
                    '            <td style="text-align: right; padding-top: 0px; padding-bottom: 0px;">\n' +
                    '                <br>\n' +
                    '            </td>\n' +
                    '        </tr>'
            string.concat("\n",impString)
            }


        }
        return string;
    }
}

function addImpuestoInInvoice(impuestoFactura){
    var string = "";
    if (impuestoFactura.length ===0){
        return string;
    }else{
        for (var key in impuestoFactura){
            var stingIMP =         '        <tr class="item last">\n' +
                '\n' +
                '            <td>\n' +
                '                <br>\n' +
                '            </td>\n' +
                '            <td style="text-align: right;">\n' +
                '                '+impuestoFactura[key].nombre+'\n' +
                '            </td>\n' +
                '\n' +
                '            <td style="text-align: right;">\n' +
                '                '+currentyMoney(impuestoFactura[key].impuesto)+'\n' +
                '            </td>\n' +
                '        </tr>\n'
            string.concat("\n",stingIMP);


        }
        return string

    }
}

function createInvoice(factura){
    var divContents = $("#dvContainer").html();
    var printWindow = window.open('', '', 'height=400,width=800');
    printWindow.document.write('<html>\n' +
        '<head>\n' +
        '    <meta charset="utf-8">\n' +
        '    <title>A simple, clean, and responsive HTML invoice template</title>\n' +
        '    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"\n' +
        '          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"\n' +
        '          alt="../dashboardPlantilla/bower_components/bootstrap/dist/css/bootstrap.min.css">\n' +
        '\n' +
        '    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"\n' +
        '          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">\n' +
        '    <link rel="stylesheet" href="../dashboardPlantilla/css/invoice.css">\n' +
        '\n' +
        '    <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>\n' +
        '    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"\n' +
        '            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"\n' +
        '            crossorigin="anonymous"></script>\n' +
        '    <script type="text/javascript" src="../js/jquery-barcode.js"></script>\n' +
        '    <script type="text/javascript" src="../dashboardPlantilla/js/generateInvoice.js"></script>\n' +
        '\n' +
        '\n' +
        '</head>');


    printWindow.document.write('<body class="invoice-box">\n' +
        '<div >\n' +
        '    <table cellpadding="0" cellspacing="0">\n' +
        '        <tr class="information">\n' +
        '            <td colspan="2">\n' +
        '                <table>\n' +
        '                    <tr>\n' +
        '                        <td class=" " style="max-width:180px;  margin-left: auto; margin-right: auto;">\n' +
        '                            <img src="../dashboardPlantilla/img/plantilla/cashsuite_logo.png"\n' +
        '                                 style="width:100%; max-width:180px; ">\n' +
        '\n' +
        '                        </td>\n' +
        '                        <td>\n' +
        '                            '+factura.compania+'<br>\n' +
        '                            '+factura.direccion+'<br>\n' +
        '                            '+factura.ciudadPais+'<br>\n' +
        '                            '+factura.telefono+'<br>\n' +
        '                        </td>\n' +
        '\n' +
        '\n' +
        '                    </tr>\n' +
        '                </table>\n' +
        '            </td>\n' +
        '        </tr>\n' +
        '        <tr class="information">\n' +
        '            <td colspan="2">\n' +
        '                <table>\n' +
        '                    <tr>\n' +
        '                        <td>\n' +
        '                            <table>\n' +
        '                                <tr class="item last">\n' +
        '                                    <td id="barcodeTarget" style=" margin: 0px; padding: 5px;\n' +
        '            vertical-align: top; font-weight: bold; margin-left: auto; margin-right: auto;">\n' +
        '                                        <div></div>\n' +
        '                                    </td>\n' +
        '                                </tr>\n' +
        '                                <tr class="item last ">\n' +
        '                                    <td style="padding: 0px;">\n' +
        '                                        <span id="facturaPrint">'+factura.idFactura+'</span>\n' +
        '                                    </td>\n' +
        '                                </tr>\n' +
        '                                <tr class="item last ">\n' +
        '                                    <td style="padding: 0px;">\n' +
        '                                        <span>ID Cliente: </span><span>'+factura.idCliente+'</span>\n' +
        '                                    </td>\n' +
        '                                </tr>\n' +
        '                                <tr class="item last">\n' +
        '                                    <td style="padding: 0px;">\n' +
        '                                        Nombre Completo: '+factura.nombreCliente+'\n' +
        '                                    </td>\n' +
        '\n' +
        '                                </tr>\n' )
    if(factura.metodoDePago !== null){
        printWindow.document.write('                                <tr class="item last">\n' +
            '                                    <td style="padding: 0px;">\n' +
            '                                        Metodo De Pago: '+factura.metodoDePago+'\n' +
            '                                    </td>\n' +
            '\n' +
            '                                </tr>\n' )
    }

        printWindow.document.write(
        '                            </table>\n' +
        '\n' +
        '                        </td>\n' +
        '\n' +
        '\n' +
        '                        <td>\n' +
        '\n' +
        '                            <span>Fecha: '+returnOnlyDate(factura.fechaCompra)+'</span><br>\n' +
        '                            <span>Hora: '+returnTime(factura.fechaCompra)+'</span>\n' +
        '                        </td>\n' +
        '\n' +
        '\n' +
        '                    </tr>\n' +
        '                </table>\n' +
        '            </td>\n' +
        '        </tr>\n' +
        '    </table>\n' +
        '    <table cellpadding="0" cellspacing="0">\n' +
        '        <tr class="heading">\n' +
        '            <td class="">\n' +
        '                UDS\n' +
        '            </td>\n' +
        '            <td class=" " style="text-align: left;">\n' +
        '                Descripci&#243;n\n' +
        '            </td>\n' +
        '            <td>\n' +
        '                <br>\n' +
        '            </td>\n' +
        '\n' +
        '            <td style="text-align: right;">\n' +
        '                Valor\n' +
        '            </td>\n' +
        '        </tr>\n'
    );
    for (var key in factura.productos){
        printWindow.document.write(addProductoInInvoice(factura.productos[key]));
    }
    printWindow.document.write(
        '\n' +
        '    </table>\n' +
        '    <table cellpadding="0" cellspacing="0">\n' +
        '\n' +
        '\n' +
        '        <tr class="heading">\n' +
        '\n' +
        '            <td>\n' +
        '                <br>\n' +
        '            </td>\n' +
        '            <td style="text-align: right;">\n' +
        '                Informaci&#243;n De Pago\n' +
        '            </td>\n' +
        '\n' +
        '            <td style="text-align: right;">\n' +
        '                <br>\n' +
        '            </td>\n' +
        '        </tr>\n' +
        '\n' );


    printWindow.document.write(addImpuestoInInvoice(factura.impuestoFacturas));

    printWindow.document.write(
        '\n' +
        '        <tr class="item ">\n' +
        '\n' +
        '            <td>\n' +
        '                <br>\n' +
        '            </td>\n' +
        '            <td style="text-align: right;">\n' +
        '                SubTotal\n' +
        '            </td>\n' +
        '\n' +
        '            <td style="text-align: right;">\n' +
        '                '+currentyMoney(obj.total)+'\n' +
        '            </td>\n' +
        '        </tr>\n' +
        '\n' +
        '        <tr class="total">\n' +
        '\n' +
        '            <td>\n' +
        '                <br>\n' +
        '            </td>\n' +
        '            <td>\n' +
        '                <br>\n' +
        '            </td>\n' +
        '\n' +
        '            <td style="text-align: right; font-weight: bold">\n' +
        '                Total: '+currentyMoney(obj.precioNeto)+'\n' +
        '            </td>\n' +
        '        </tr>\n' +
        '    </table>\n' +
        '\n' +
        '    <button class="oculto-impresion" onclick="imprimir()">Imprimir</button>\n' +
        '\n' +
        '</div>\n' +
        '\n' +
        '</body>\n' +
        '</html>');

    // printWindow.document.write(divContents);
    // printWindow.document.write('</body></html>');
    printWindow.document.close();
    printWindow.print();
}

$(document).ready(function () {

    generateBarcode();
});