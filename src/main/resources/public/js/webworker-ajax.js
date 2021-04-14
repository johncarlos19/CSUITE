/**
 * Web Worker para realizar llamadas Ajax utilizando una librería
 * que simplifica la llamada vía el objeto XMLHttpRequest.
 */
// import br from "../dashboardPlantilla/bower_components/moment/src/locale/br";

//importando el script
importScripts('../js/axios.min.js'); // JQuery trabaja con el DOM no puede ser utilizada
// ver en https://github.com/axios/axios

//eventos recuperados entre la ventana principal y el worker.
this.addEventListener('message', function (e) {

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
                    try {
                        if (response.data === -1) {
                            postMessage({'cmd': 'timeout', 'data': response.data});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'respuesta', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'timeout'});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }


                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;
        case 'loadGraphicVenta':
            switch (data.tiempo){
                case "mes":
                    console.log("Buscando la fecha desde el servidor...");
                    axios.get('/api/Inicio/graphicVentasMes')
                        .then(function (response) {
                            // handle success
                            console.log("Respuesta:");
                            console.log(response);
                            //enviando la información a la venta principal
                            try {
                                if (response.data === -1) {
                                    postMessage({'cmd': 'timeout', 'data': response.data});
                                    return response.data;
                                } else {
                                    postMessage({'cmd': 'loadGraphicVenta', 'data': response.data});
                                    return response.data;
                                }
                            } catch (error) {
                                postMessage({'cmd': 'timeout'});
                                return response.data;
                                // expected output: ReferenceError: nonExistentFunction is not defined
                                // Note - error messages will vary depending on browser
                            }


                        })
                        .catch(function (error) {
                            // handle error
                            console.log("Error:");
                            console.log(error);
                            postMessage({'cmd': 'timeout'});

                        })
                        .then(function () {
                            // always executed
                        });
                    break;
                case "dia":
                    console.log("Buscando la fecha desde el servidor...");
                    axios.get('/api/Inicio/graphicVentasDia')
                        .then(function (response) {
                            // handle success
                            console.log("Respuesta:");
                            console.log(response);
                            //enviando la información a la venta principal
                            try {
                                if (response.data === -1) {
                                    postMessage({'cmd': 'timeout', 'data': response.data});
                                    return response.data;
                                } else {
                                    postMessage({'cmd': 'loadGraphicVenta', 'data': response.data});
                                    return response.data;
                                }
                            } catch (error) {
                                postMessage({'cmd': 'timeout'});
                                return response.data;
                                // expected output: ReferenceError: nonExistentFunction is not defined
                                // Note - error messages will vary depending on browser
                            }


                        })
                        .catch(function (error) {
                            // handle error
                            console.log("Error:");
                            console.log(error);
                            postMessage({'cmd': 'timeout'});

                        })
                        .then(function () {
                            // always executed
                        });
                    break;
            }

            break;
        case 'uploadProducto':
            console.log("Buscando la fecha desde el servidor...");

            axios.post('/api/Producto', data.ProductoSaveJson)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        if (response.data === -1) {
                            postMessage({'cmd': 'timeout'});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'save', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'timeout'});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }


                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

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
                    try {
                        if (response.data === -1) {
                            postMessage({'cmd': 'timeout'});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'verifyUser', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'timeout'});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;

        case 'passwordCorrect':
            console.log("Buscando la fecha desde el servidor...");

            axios.post('/api/Usuario/password', data.user)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        if (response.data === -1) {
                            postMessage({'cmd': 'timeout'});
                            return response.data;
                        } else {

                            postMessage({'cmd': 'verifyPassword', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'timeout'});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;
        case 'login':

            axios.post('/api/Login/', data.user)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        if (response.data === -1) {
                            postMessage({'cmd': 'timeout'});
                            return response.data;
                        } else {

                            postMessage({'cmd': 'login', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'error'});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'error'});

                })
                .then(function () {
                    // always executed
                });
            break;
        case 'deleteFactura':

            axios.post('/api/Factura/deleteFacturaActiva', data.id)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        console.log(response.data)
                        if (response.data === -1) {
                            console.log("Respuesta:" + response.data.data);
                            postMessage({'cmd': 'noFound', 'data': response.data});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'facturaBorrada', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'facturaBorrada', 'data': response.data});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;
        case 'saveFactura':

            axios.post('/api/Factura/saveFactura', data.GuardarFacturaJson)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        console.log(response.data)
                        if (response.data === "no found") {
                            console.log("Respuesta:" + response.data.data);
                            postMessage({'cmd': 'noFound', 'data': response.data});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'facturaLoadIMP', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'facturaLoadIMP', 'data': response.data});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;
        case 'facturaLoadIMP':

            axios.post('/api/Factura/loadFactura', data.id)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        console.log(response.data)
                        if (response.data === "no found") {
                            console.log("Respuesta:" + response.data.data);
                            postMessage({'cmd': 'noFound', 'data': response.data});
                            return response.data;
                        } else {
                            if (data.ubi === 'modal') {

                                postMessage({'cmd': 'facturaLoadIMPModal', 'data': response.data});
                            } else {

                                postMessage({'cmd': 'facturaLoadIMP', 'data': response.data});
                            }
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'facturaLoadIMP', 'data': response.data});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;

        case 'facturaLoad':

            axios.post('/api/Factura/loadFactura', data.id)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        console.log(response.data)
                        if (response.data === "no found") {
                            console.log("Respuesta:" + response.data.data);
                            postMessage({'cmd': 'noFound', 'data': response.data});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'facturaLoad', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'facturaLoad', 'data': response.data});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;
        case 'createFactura':

            axios.post('/api/Factura/crear', data.id)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        console.log(response.data)
                        if (response.data === "no found") {
                            console.log("Respuesta:" + response.data.data);
                            postMessage({'cmd': 'noFound', 'data': response.data});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'createFactura', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'createFactura', 'data': response.data});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;

        case 'searchCliente':

            axios.post('/api/Cliente/search', data.id)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        console.log(response.data)
                        if (response.data === "no found") {
                            console.log("Respuesta:" + response.data.data);
                            postMessage({'cmd': 'noFound', 'data': response.data});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'cliente', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'cliente', 'data': response.data});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;
        case 'searchImpuestoProducto':

            axios.post('/api/Producto/Impuesto', data.id)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        console.log(response.data)
                        if (response.data === "no found") {
                            console.log("Respuesta:" + response.data.data);
                            postMessage({'cmd': 'noFound', 'data': response.data});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'searchImpuestoProducto', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'searchImpuestoProducto', 'data': response.data});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;

        case 'searchImpuestoProductoAvailable':

            axios.post('/api/Producto/ImpuestoNoAdded', data.id)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        console.log(response.data)
                        if (response.data === "no found") {
                            console.log("Respuesta:" + response.data.data);
                            postMessage({'cmd': 'noFound', 'data': response.data});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'searchImpuestoProductoAvailable', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'searchImpuestoProductoAvailable', 'data': response.data});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;
        case 'searchImpuestoVentaAvailable':

            axios.post('/api/Factura/ImpuestoNoAdded', data.id)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        console.log(response.data)
                        if (response.data === "no found") {
                            console.log("Respuesta:" + response.data.data);
                            postMessage({'cmd': 'noFound', 'data': response.data});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'searchImpuestoFacturaAvailable', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'searchImpuestoFacturaAvailable', 'data': response.data});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;
        case 'searchAlmacenProducto':

            axios.post('/api/Producto/Almacen', data.id)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        console.log(response.data)
                        if (response.data === "no found") {
                            console.log("Respuesta:" + response.data.data);
                            postMessage({'cmd': 'noFound', 'data': response.data});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'searchAlmacenProducto', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'searchAlmacenProducto', 'data': response.data});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;
        case 'productoRelation':

            axios.post('/api/Producto/ProductoRelation', data.ActionJson)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        console.log(response.data)
                        if (response.data === "no found") {
                            console.log("Respuesta:" + response.data.data);
                            postMessage({'cmd': 'noFound', 'data': response.data});
                            return response.data;

                        } else {

                                    postMessage({'cmd': 'productoRelation','action': data.ActionJson.action, 'data': response.data});
                                    return response.data;



                        }
                    } catch (error) {
                        postMessage({'cmd': 'productoRelation','action': data.ActionJson.action, 'data': response.data});
                        return response.data;


                    }

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;
        case 'ventaRelation':

            axios.post('/api/Factura/VentaRelation', data.ActionJson)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        console.log(response.data)
                        if (response.data === "no found") {
                            console.log("Respuesta:" + response.data.data);
                            postMessage({'cmd': 'noFound', 'data': response.data});
                            return response.data;

                        } else {

                            postMessage({'cmd': 'facturaLoad', 'data': response.data});
                            return response.data;



                        }
                    } catch (error) {
                        postMessage({'cmd': 'facturaLoad', 'data': response.data});
                        return response.data;


                    }

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;
        case 'extendSession':
            console.log("Buscando la extencion desde el servidor...");

            axios.get('/api/Login/Extend')
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        if (response.data === -1) {
                            postMessage({'cmd': 'timeout'});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'time', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'time', 'data': response.data});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }

                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;
        case 'ventasActiva':
            console.log("Buscando la fecha desde el servidor...");
            axios.get('/api/Factura/ventasActivas')
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        if (response.data === -1) {
                            postMessage({'cmd': 'timeout', 'data': response.data});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'ventasActiva', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'timeout'});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }


                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    // postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;
        case 'ventaINV':
            console.log("Buscando la fecha desde el servidor...");
            axios.get('/api/Producto')
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        if (response.data === -1) {
                            postMessage({'cmd': 'timeout', 'data': response.data});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'ventaProducto', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'timeout'});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }


                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;
        case 'addProductoFactura':
            console.log("addProducto...");

            axios.post('/api/Factura/addProducto', data.AddDiscountProductoFacturaJSON)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        if (response.data === -1) {
                            postMessage({'cmd': 'timeout'});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'facturaLoad', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'timeout'});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }


                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;
        case 'discountProductoFactura':
            console.log("discountProductoFactura...");

            axios.post('/api/Factura/discountProducto', data.AddDiscountProductoFacturaJSON)
                .then(function (response) {
                    // handle success
                    console.log("Respuesta:");
                    console.log(response);
                    //enviando la información a la venta principal
                    try {
                        if (response.data === -1) {
                            postMessage({'cmd': 'timeout'});
                            return response.data;
                        } else {
                            postMessage({'cmd': 'facturaLoad', 'data': response.data});
                            return response.data;
                        }
                    } catch (error) {
                        postMessage({'cmd': 'timeout'});
                        return response.data;
                        // expected output: ReferenceError: nonExistentFunction is not defined
                        // Note - error messages will vary depending on browser
                    }


                })
                .catch(function (error) {
                    // handle error
                    console.log("Error:");
                    console.log(error);
                    postMessage({'cmd': 'timeout'});

                })
                .then(function () {
                    // always executed
                });
            break;
        default:

            this.postMessage('{"tipo": "msg","mensaje" : "Mensaje no procesado: ' + data.msg + '}');
    }
    ;
}, false);