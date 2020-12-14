/**
 * Web Worker para realizar llamadas Ajax utilizando una librería
 * que simplifica la llamada vía el objeto XMLHttpRequest.
 */
//importando el script
importScripts('/js/axios.min.js'); // JQuery trabaja con el DOM no puede ser utilizada
// ver en https://github.com/axios/axios

//eventos recuperados entre la ventana principal y el worker.
this.addEventListener('message', function(e) {

    //la información la tenemos en la propiedad data.
    var data = e.data;

    //
    switch (data.cmd) {
        case 'fecha':
            console.log("Buscando la fecha desde el servidor...");
            axios.get('/api/Producto')
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal

                    postMessage({'cmd': 'respuesta', 'data': response.data});
                    return response.data;
                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                })
                .then(function () {
                    // always executed
                });
            break;
        case 'uploadProducto':
            console.log("Buscando la fecha desde el servidor...");

            axios.post('/api/Producto', data.ProductoSaveJson)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    postMessage({'cmd': 'save', 'data': response.data});

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                })
                .then(function () {
                    // always executed
                });
            break;
        case 'userAvailable':
            console.log("Buscando la fecha desde el servidor...");

            axios.post('/api/Usuario', data.user)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    postMessage({'cmd': 'verifyUser', 'data': response.data});
                    return response.data;
                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                })
                .then(function () {
                    // always executed
                });
            break;
        default:
            this.postMessage('{"tipo": "msg","mensaje" : "Mensaje no procesado: '+data.msg+'}');
    };
}, false);