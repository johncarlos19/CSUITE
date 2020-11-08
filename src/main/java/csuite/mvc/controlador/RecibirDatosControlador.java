package csuite.mvc.controlador;


import csuite.mvc.entidades.*;
import csuite.mvc.servicios.*;
import io.javalin.Javalin;

import io.jsonwebtoken.*;
import org.hibernate.Session;
import org.jasypt.util.text.AES256TextEncryptor;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RecibirDatosControlador extends JavalinControlador {


    public final static String SECRET_KEY = "ghQaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";
    AES256TextEncryptor userEncryptor = new AES256TextEncryptor();
    AES256TextEncryptor passwordEncryptor = new AES256TextEncryptor();
    static ArrayList<Login>  logins = new ArrayList<Login>();


    public RecibirDatosControlador(Javalin app) {
        super(app);
        userEncryptor.setPassword("admin");
        passwordEncryptor.setPassword("admin");

    }

    /**
     * Registrando los sistemas de plantillas utilizados.
     */


    @Override
    public void aplicarRutas() {

        app.before(ctx -> {






        });


//        app.get("/login", ctx -> {
//
//
//        });
//
//        app.get("/carrito_process", ctx -> {
//
//        });
//
//
//        app.get("/carrito", ctx -> {
//
//
//        });


        /**
         * Ejemplo para leer por parametros de consulta (query param)
         * http://localhost:7000/parametros?matricula=20011126&nombre=Carlos%20Camacho
         */




//        app.get("/doItChange", ctx -> {
//
//
//
//        });
//
//        app.post("/new_product", ctx -> {
//
//
//        });
//        app.get("/editar/:id", ctx -> {
//
//
//        });
//        app.get("/editar/eliminar/:id_foto/:id_producto", ctx -> {
//
//
//        });
//        app.get("/comentar", ctx -> {
//
//
//        });
//        app.get("/EliminarComentario", ctx -> {
//
//
//        });
//        app.post("/edit_now", ctx -> {
//
//
//
//        });
//        app.get("/add", ctx -> {
//
//        });
//
//
//
//        app.post("/login", ctx -> {
//
//
//        });

        app.routes(() -> {

            path("/", () -> {
                get(ctx -> {
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("listaPaqueteProducto", "");
                    ctx.render("/public/webPage/CashSuite.html", contexto);





                });
            });

            path("/logout", () -> {


                get(ctx -> {

                    for (int i = 0; i < logins.size(); i++) {
                        if (logins.get(i).getId().equalsIgnoreCase(decodeJWT(ctx.sessionAttribute("User")).getId())){
                            logins.remove(i);
                            break;
                        }
                    }


                    ctx.removeCookie("User");
                    ctx.req.getSession().invalidate();
                    ctx.redirect("/login");

                });

            });



                path("/login", () -> {
                    get(ctx -> {
                        Map<String, Object> contexto = new HashMap<>();
                        contexto.put("listaPaqueteProducto", "");
                        ctx.render("/public/webPage/login.html", contexto);

                    });
                    post(ctx -> {




                        if (Mercado.getInstance().verificar_user(ctx.formParam("ingresoEmail"),ctx.formParam("ingresoPassword"))!=null){

                            if (isSessionAvailable(ctx.formParam("ingresoEmail"))){
                                String user = ctx.formParam("ingresoEmail");

                                String header = "Authorization";
                                String jwt = createJWT(user);
                                logins.add(new Login(user,decodeJWT(jwt)));

                                ctx.sessionAttribute("User",jwt);
                                ctx.cookie("User",Mercado.getInstance().getUserEncryptor().encrypt(jwt),2147483647);
                                ctx.redirect("/dashboard/home");
                            }else {
                                Map<String, Object> contexto1 = new HashMap<>();
                                contexto1.put("texto1",  "¡Esta cuenta ha iniciado sesión, debe salir de la sesión para poder entrar!");
                                ctx.render("/public/webPage/login_error.html",contexto1);
                            }


                        }else{
                            Map<String, Object> contexto2 = new HashMap<>();
                            contexto2.put("texto1", "¡Email o contraseña no coinciden!");
                            ctx.render("/public/webPage/login_error.html",contexto2 );
                        }


                    });
            });

                });



        path("/dashboard", () -> {





            app.routes(() -> {
                before(ctx -> {


                    System.out.println("\n\n\n\n"+ctx.req.getPathInfo());

                    String headerAuth = ctx.req.getHeader("Authorization");
                    System.out.println(headerAuth);
                    String user = ctx.cookie("User");
                    String session = ctx.sessionAttribute("User");
                    System.out.println("este es e; [add"+ctx.path());
//                    if (ctx.path().contains("/dashboardPlantilla/")==true  || ctx.path().contains("/webPage")==true ||ctx.path().contains("/js")==true ||ctx.path().contains("/bd")==true || ctx.path().contains("/assets")==true ){

                    if (ctx.path().contains("/home")==true  || ctx.path().contains("/inventario")==true
                            || ctx.path().contains("/cliente")==true
                            || ctx.path().contains("/categoria")==true
                            || ctx.path().contains("/impuesto")==true
                            || ctx.path().contains("/empleado")==true
                    ){
                        if (user==null || session == null){

                            System.out.println(Mercado.getInstance().getUserEncryptor().decrypt(user));
                            ctx.redirect("/login");

                        }else{
                            System.out.println("\n\n\n\nverifico");

                            if(Mercado.getInstance().getUserEncryptor().decrypt(user).equalsIgnoreCase(session)){
                                try {
                                    if(isExpirate(decodeJWT(session))==false){
                                        String header = "Authorization";
                                        String use = decodeJWT(session).getId();
                                        String jwt = createJWT(decodeJWT(session).getId());
                                        ctx.header(header,jwt);
                                        for (int i = 0; i < logins.size(); i++) {
                                            if (logins.get(i).getId().equalsIgnoreCase(use)){
                                                logins.get(i).setJwt(decodeJWT(jwt));
                                            }
                                        }

                                        ctx.sessionAttribute("User",jwt);
                                        ctx.cookie("User",Mercado.getInstance().getUserEncryptor().encrypt(jwt),2147483647);
                                        String mensaje = String.format("Manejador before aplicando a todas las llamadas: %s, Contexto: %s, Metodo: %s",
                                                ctx.req.getRemoteHost(),
                                                ctx.req.getServletPath(),
                                                ctx.req.getMethod());
                                        //
                                        System.out.println(mensaje);

                                    }else{
                                        ctx.req.getSession().invalidate();
                                        ctx.redirect("/login");
                                    }

                                }catch (ExpiredJwtException e){
                                    ctx.req.getSession().invalidate();
                                    ctx.redirect("/login");
                                }
                            }else{
                                ctx.req.getSession().invalidate();
                                ctx.redirect("/login");
                            }


                        }

                    }else{
                        System.out.println("entro");

                    }
                });




                path("/home", () -> {

                    get(ctx -> {
                        String user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId();
                        System.out.println("\n\n\nusuario"+user);
                        ctx.res.addHeader("Authorization",ctx.cookie("User"));
                        Map<String, Object> contexto = new HashMap<>();
                        contexto.put("listaProducto", Mercado.getInstance().listaProductoOrdenada(user));

                        ctx.render("/public/dashboardPlantilla/home.html", contexto);




                    });
                });


                path("/inventario", () -> {

                    get(ctx -> {
                        String user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId();
                        System.out.println("\n\n\nusuario"+user);
                        ctx.res.addHeader("Authorization",ctx.cookie("User"));
                        Map<String, Object> contexto = new HashMap<>();
                        contexto.put("categoria", CategoriaServicios.getInstancia().ListaCategoria(user));
                        ctx.render("/public/dashboardPlantilla/inventario.html",contexto);




                    });
                });
                path("/categoria", () -> {

                    get(ctx -> {
                        String user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId();
                        System.out.println("\n\n\nusuario"+user);
                        ctx.res.addHeader("Authorization",ctx.cookie("User"));
                        Map<String, Object> contexto = new HashMap<>();
                        contexto.put("categoria", CategoriaServicios.getInstancia().ListaCategoria(user));

                        ctx.render("/public/dashboardPlantilla/categoria.html",contexto);




                    });
                    post(ctx -> {
                        String user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId();
                        System.out.println("\n\n\nusuario"+user);
                        ctx.res.addHeader("Authorization",ctx.cookie("User"));
                        String categoria = ctx.formParam("nuevaCategoria");
                        Mercado.getInstance().addCategoria(user,categoria);


                        ctx.redirect("/dashboard/categoria");




                    });
                });

                path("/impuesto", () -> {

                    get(ctx -> {
                        String user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId();
                        System.out.println("\n\n\nusuario"+user);
                        ctx.res.addHeader("Authorization",ctx.cookie("User"));
                        Map<String, Object> contexto = new HashMap<>();
                        contexto.put("impuesto", ImpuestoServicios.getInstancia().listaImpuesto(user));

                        ctx.render("/public/dashboardPlantilla/impuesto.html",contexto);




                    });
                    post(ctx -> {
                        String user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId();
                        System.out.println("\n\n\nusuario"+user);
                        ctx.res.addHeader("Authorization",ctx.cookie("User"));
                        Impuesto impuesto = new Impuesto(ctx.formParam("nombre"),ctx.formParam("nuevoTributo"),Double.parseDouble(ctx.formParam("valor")));
                        Mercado.getInstance().addImpuesto(user,impuesto);


                        ctx.redirect("/dashboard/impuesto");




                    });
                });

                path("/cliente", () -> {

                    get(ctx -> {

                        String user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId();
                        System.out.println("\n\n\nusuario"+user);
                        ctx.res.addHeader("Authorization",ctx.cookie("User"));
                        Map<String, Object> contexto = new HashMap<>();

//                    for (Cliente aux : ClienteServicios.getInstancia().listaCliente("admin")
//                         ) {
//                        list.add(UsuarioServicios.getInstancia().find(aux.getIdCliente().getUsuario()));
//
//                    }
                        contexto.put("cliente",ClienteServicios.getInstancia().listaCliente(user));

                        ctx.render("/public/dashboardPlantilla/Cliente.html",contexto);




                    });
                    post(ctx -> {
                        String user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId();
                        System.out.println("\n\n\nusuario"+user);
                        ctx.res.addHeader("Authorization",ctx.cookie("User"));


                        String nombre = ctx.formParam("nombre");
                        String apellido = ctx.formParam("apellido");
                        String tipoDocumento = ctx.formParam("tipoDocumento");
                        String documentoID = ctx.formParam("documentoID");
                        String nuevoEmail = ctx.formParam("nuevoEmail");
                        String nuevoTelefono = ctx.formParam("nuevoTelefono");
                        String pais = ctx.formParam("pais");
                        String Municipio = ctx.formParam("Municipio");
                        String nuevaDireccion = ctx.formParam("nuevaDireccion");


                        Vendedor otro = VendedorServicios.getInstancia().getVendedor(user);

                        Usuario usuarioCliente = new Usuario();
                        usuarioCliente.setNombre(nombre);
                        usuarioCliente.setApellido(apellido);
                        usuarioCliente.setPerfil("Cliente");
                        usuarioCliente.setPais(pais);
                        usuarioCliente.setMunicipio(Municipio);
                        usuarioCliente.setDireccion(nuevaDireccion);
                        usuarioCliente.setTelefono(nuevoTelefono);
                        usuarioCliente.setDocumento(documentoID);
                        usuarioCliente.setTipoDocumento(tipoDocumento);
                        usuarioCliente.setEmail(nuevoEmail);
                        usuarioCliente = (Usuario) new UsuarioServicios().crear(usuarioCliente);
                        System.out.println("\n\n\nusua"+usuarioCliente.getUsuario());
                        usuarioCliente = UsuarioServicios.getInstancia().buscar(usuarioCliente.getUsuario());
                        Cliente cliente = new Cliente();
                        cliente = usuarioCliente.addCliente(cliente);
                        cliente = (Cliente) ClienteServicios.getInstancia().crear(cliente);
                        usuarioCliente = (Usuario) UsuarioServicios.getInstancia().editar(usuarioCliente);

                        otro.addCliente(cliente);
                        otro = (Vendedor) VendedorServicios.getInstancia().editar(otro);


                        ctx.redirect("/dashboard/cliente");




                    });
                });









                path("/empleado", () -> {

                    get(ctx -> {
                        String user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId();
                        System.out.println("\n\n\nusuario"+user);
                        ctx.res.addHeader("Authorization",ctx.cookie("User"));
                        Map<String, Object> contexto = new HashMap<>();
                        List<Usuario> list = new ArrayList<Usuario>();
//                    for (Cliente aux : ClienteServicios.getInstancia().listaCliente("admin")
//                         ) {
//                        list.add(UsuarioServicios.getInstancia().find(aux.getIdCliente().getUsuario()));
//
//                    }
                        contexto.put("empleado", EmpleadoServicios.getInstancia().listaEmpleado(user));

                        ctx.render("/public/dashboardPlantilla/usuarioDashboard.html",contexto);




                    });
                    post(ctx -> {

                        String user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId();
                        System.out.println("\n\n\nusuario"+user);
                        ctx.res.addHeader("Authorization",ctx.cookie("User"));
                        String nombre = ctx.formParam("nombre");
                        String apellido = ctx.formParam("apellido");
                        String tipoDocumento = ctx.formParam("tipoDocumento");
                        String documentoID = ctx.formParam("documentoID");
                        String nuevoEmail = ctx.formParam("nuevoEmail");
                        String nuevoTelefono = ctx.formParam("nuevoTelefono");
                        String pais = ctx.formParam("pais");
                        String Municipio = ctx.formParam("Municipio");
                        String nuevaDireccion = ctx.formParam("nuevaDireccion");


                        Vendedor otro = VendedorServicios.getInstancia().getVendedor(user);

                        Usuario usuarioCliente = new Usuario();
                        usuarioCliente.setNombre(nombre);
                        usuarioCliente.setApellido(apellido);
                        usuarioCliente.setPerfil("Cliente");
                        usuarioCliente.setPais(pais);
                        usuarioCliente.setMunicipio(Municipio);
                        usuarioCliente.setDireccion(nuevaDireccion);
                        usuarioCliente.setTelefono(nuevoTelefono);
                        usuarioCliente.setDocumento(documentoID);
                        usuarioCliente.setTipoDocumento(tipoDocumento);
                        usuarioCliente.setEmail(nuevoEmail);
                        usuarioCliente = (Usuario) new UsuarioServicios().crear(usuarioCliente);
                        System.out.println("\n\n\nusua"+usuarioCliente.getUsuario());
                        usuarioCliente = UsuarioServicios.getInstancia().buscar(usuarioCliente.getUsuario());
                        Cliente cliente = new Cliente();
                        cliente = usuarioCliente.addCliente(cliente);
                        cliente = (Cliente) ClienteServicios.getInstancia().crear(cliente);
                        usuarioCliente = (Usuario) UsuarioServicios.getInstancia().editar(usuarioCliente);

                        otro.addCliente(cliente);
                        otro = (Vendedor) VendedorServicios.getInstancia().editar(otro);


                        ctx.redirect("/dashboard/empleado");




                    });
                });



            });


        });

        app.routes(() -> {
            /*
            path("/editar/:id", () -> {

                get(ctx -> {


                    String id_cliente = ctx.sessionAttribute("carroCompra");

                    Producto producto = Mercado.getInstance().getProductoServicios().getProducto(ctx.pathParam("id", Integer.class).getOrNull());
                    int aux = Mercado.getInstance().cant_product(Long.parseLong(id_cliente));
                    Map<String, Object> modelo = new HashMap<>();
                    String userenc = userEncryptor.decrypt(ctx.cookie("Login"));
                    if (userenc != null) {
                        modelo.put("user", userenc + " - Salir");
                        modelo.put("log", "logout");
                    } else {
                        userenc = "Iniciar Seccion";
                        modelo.put("user", userenc);
                        modelo.put("log", "login");
                    }
                    modelo.put("cant", aux);
                    modelo.put("producto", producto);
                    ctx.render("/publico/EditarProducto.html",modelo);
                });
        });*/
//            path("/producto", () -> {
//                get(ctx -> {
//
//
//
//                });
//            });
//
//            path("/venta_producto", () -> {
//
//                get(ctx -> {
//                    if (ctx.cookie("Login") != null) {
//
//
//
//                    } else {
//                        ctx.redirect("/");
//                    }
//                });
//            });




//
//
//
//
//            path("/process", () -> {
//
//                get(ctx -> {
//                    ctx.render("/publico/successfully.html");
//                });
//                post(ctx -> {
//                    if(ctx.formParam("status").equalsIgnoreCase("COMPLETED")){
//                        Factura factura = new Factura(ctx.formParam("id_order"),ctx.formParam("status"),ctx.formParam("name"),ctx.formParam("last"),ctx.formParam("email"),ctx.formParam("payer_id"),ctx.formParam("address_line_1"),ctx.formParam("address_line_2"),
//                                ctx.formParam("admin_area_2"),ctx.formParam("admin_area_1"),ctx.formParam("postal_code"),ctx.formParam("country_code"));
//                        if(ctx.formParam("id_paquete")!=null){
//                            factura.setPack(true);
//                            factura.setIdPaqueteProducto(Long.parseLong(ctx.formParam("id_paquete")));
//                            PaqueteProducto aux = PaqueteProductoServicios.getInstance().find(Long.parseLong(ctx.formParam("id_paquete")));
//                            for (Producto ax: aux.getProductoList()
//                                 ) {
//                                factura.addProducto(ax);
//                            }
//
//                        }
//                        FacturaServicios.getInstance().crear(factura);
//                        ctx.redirect("/process");
//
//                    }
//                });
//            });
//            path("/checkout", () -> {
//
//                /**
//                 * http://localhost:7000/thymeleaf/
//                 */
//                post(ctx -> {
//                    PaqueteProducto aux = PaqueteProductoServicios.getInstance().find(Long.parseLong(ctx.formParam("id")));
//                    System.out.println(aux.getFees());
//
//
//                    Map<String, Object> contexto = new HashMap<>();
//                    contexto.put("aux", aux);
//                    ctx.render("/publico/checkout.html", contexto);
//                });
//            });
//            path("/", () -> {
//                get(ctx -> {
//
//                    Map<String, Object> contexto = new HashMap<>();
//                    contexto.put("listaPaqueteProducto", PaqueteProductoServicios.getInstance().ListadoCompleto());
//                    ctx.render("/publico/index.html", contexto);
//
//
//                });
//            });




        });

    }

    public static boolean isSessionAvailable(String user){
        boolean available = true;
        for (Login aux: logins
             ) {
            if (aux.getId().equals(user)){
                if (isExpirate(aux.getJwt())==true){
                    logins.remove(aux);
                    return true;
                }else{
                    return false;
                }
            }
        }
        return available;
    }

    public static String createJWT(String username) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(username)
                .setIssuedAt(now)
                .setSubject("CashSuite")
                .setIssuer("JLC")
                .signWith(signatureAlgorithm, signingKey);

        // 5 minutes para expiracion
        long expMillis = nowMillis + 300000;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);
        System.out.println("fecha" +now +exp);
        System.out.println("Expiro: "+isExpirate(decodeJWT(builder.compact())));
        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static boolean isExpirate(Claims jwt){
        boolean expire = true;

        if (new  Date(System.currentTimeMillis()).after(jwt.getExpiration())){
            return expire;
        }
        return false;
    }

    public static Claims decodeJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();

        return claims;
    }
}
