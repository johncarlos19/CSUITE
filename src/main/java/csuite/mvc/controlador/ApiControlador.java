package csuite.mvc.controlador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import csuite.mvc.entidades.*;
import csuite.mvc.jsonObject.*;
import csuite.mvc.servicios.*;

import csuite.mvc.util.NoExisteEstudianteException;
import io.javalin.Javalin;
import io.javalin.http.sse.SseClient;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ApiControlador extends JavalinControlador {
    public final static String SECRET_KEY = "ghQaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";
//    public static List<VentasSession> listaSseUsuario = new ArrayList<VentasSession>();



    public ApiControlador(Javalin app) {
        super(app);
//        iniStart();
    }

    @Override
    public void aplicarRutas() {
        app.routes(() -> {
            path("/api", () -> {
                /**
                 * Ejemplo de una API REST, implementando el CRUD
                 * ir a
                 */
                path("/Login", () -> {
                    after(ctx -> {
                        ctx.header("Content-Type", "application/json");
                    });
                    get("/Extend", ctx -> {
                        String headerAuth = ctx.req.getHeader("Authorization");
                        System.out.println(headerAuth);
                        String user = ctx.cookie("User");
                        String session = ctx.sessionAttribute("User");
                        if (user==null || session == null){

                            System.out.println(Mercado.getInstance().getUserEncryptor().decrypt(user));
                            ctx.json(-1);

                        }else{
                            System.out.println("\n\n\n\nverifico");

                            if(Mercado.getInstance().getUserEncryptor().decrypt(user).equalsIgnoreCase(session)){
                                try {
                                    if(isExpirate(decodeJWT(session))==false){
                                        String header = "Authorization";
                                        Claims claims = decodeJWT(session);
                                        String use = claims.getId();
                                        Map<String, Object> map = new HashMap<String, Object>();
                                        map.put("user",claims.get("user"));
                                        map.put("direccion",claims.get("direccion"));
                                        map.put("telefono",claims.get("telefono"));
                                        map.put("compania",claims.get("compania"));
                                        map.put("ciudadPais",claims.get("ciudadPais"));
                                        String jwt = createJWT(use,claims.getAudience(), map);
                                        ctx.header(header,jwt);
                                        ctx.sessionAttribute("User",jwt);
                                        ctx.cookie("User",Mercado.getInstance().getUserEncryptor().encrypt(jwt),2147483647);
                                        String mensaje = String.format("Manejador before aplicando a todas las llamadas: %s, Contexto: %s, Metodo: %s",
                                                ctx.req.getRemoteHost(),
                                                ctx.req.getServletPath(),
                                                ctx.req.getMethod());
                                        //
                                        System.out.println(mensaje);

                                        Mercado.getInstance().getLogins().get(use).setJwt(claims);
                                        Mercado.getInstance().getLogins().get(use).setSession(ctx.req.getSession());
//                                        for (int i = 0; i < Mercado.getInstance().getLogins().size(); i++) {
//                                            if (Mercado.getInstance().getLogins().get(i).getId().equalsIgnoreCase(use)){
//
//                                            }
//                                        }
                                        String tmp = ctx.body();
                                        ctx.json(Mercado.getInstance().getTimeSessionMinute()*60*1000);




                                    }else{
                                        ctx.json(-1);
                                    }

                                }catch (ExpiredJwtException e){
                                    ctx.json(-1);
                                }
                            }else{
                                ctx.json(-1);
                            }


                        }



                    });

                });
                ws("/mensajeServidor", ws -> {

                    ws.onConnect(ctx -> {
                        Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
                        if (user!= null){
                            VentasSession ventasSession = new VentasSession(ctx.session,Mercado.getInstance().getUserJefeWithToken(user), user.getId());
                            ventasSession.setIdSession(ctx.getSessionId());
                            Mercado.getInstance().addListaSseUsuario(ventasSession);

                        }else{

//                            sseClient.sendEvent("statuServer", "error");
                        }



                        System.out.println("Conexion Iniciada - "+ctx.getSessionId());
//                        usuariosConectados.add(ctx.session);
                    });

                    ws.onMessage(ctx -> {
                        //Puedo leer los header, parametros entre otros.
//                        ctx.headerMap();
//                        ctx.pathParamMap();
//                        ctx.queryParamMap();
//                        //
//                        System.out.println("Mensaje Recibido de "+ctx.getSessionId()+" ====== ");
//                        System.out.println("Mensaje: "+ctx.message());
//                        System.out.println("================================");
                        //
//                        enviarMensajeAClientesConectados(ctx.message(), "azul");
                    });

                    ws.onBinaryMessage(ctx -> {
//                        System.out.println("Mensaje Recibido Binario "+ctx.getSessionId()+" ====== ");
//                        System.out.println("Mensaje: "+ctx.data().length);
//                        System.out.println("================================");
                    });

                    ws.onClose(ctx -> {
                        System.out.println("Conexion Cerrada - "+ctx.getSessionId());
                        int borra = -1;
                        for (int i = 0; i < Mercado.getInstance().listaSseUsuario.size(); i++) {
                            try {
                                if (Mercado.getInstance().listaSseUsuario.get(i).getIdSession().equalsIgnoreCase(ctx.getSessionId())){
                                    System.out.println("\n\neste es el id"+Mercado.getInstance().listaSseUsuario.get(i).getIdSession()+" el otro "+ctx.getSessionId());
                                    borra = i;
//                                    Mercado.getInstance().listaSseUsuario.remove(Mercado.getInstance().listaSseUsuario.get(i));
                                    System.out.println("\n\neliminar12");
                                    break;
                                }
                            }catch (IllegalStateException e){

                            }
                        }
                        System.out.println("\n\neliminar12");
                        if (borra!=-1){
                            Mercado.getInstance().listaSseUsuario.remove(borra);

                        }

//                        usuariosConectados.remove(ctx.session);
                    });

                    ws.onError(ctx -> {
                        System.out.println("Ocurrio un error en el WS");
                    });
                });

                /**
                 * Filtro para activarse despues de la llamadas al contexto.
                 */
                app.wsAfter("/mensajeServidor", wsHandler -> {
                    System.out.println("Filtro para WS despues de la llamada al WS");
                    //ejecutar cualquier evento antes...
                });

//                sse("/evento-servidor", sseClient -> {
//
//                    System.out.println("Agregando cliente para evento del servidor: ");
//                    sseClient.sendEvent("conectado","ready");
//
//                    Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(sseClient.ctx.cookie("User")));
//                    if (user!= null){
//                        VentasSession ventasSession = new VentasSession(sseClient,Mercado.getInstance().getUserJefeWithToken(user), user.getId());
//
//                        Mercado.getInstance().addListaSseUsuario(ventasSession);
//                        sseClient.onClose(() -> {
//                            System.out.println("\n\neliminar");
//                            int borra = -1;
//                            for (int i = 0; i < Mercado.getInstance().listaSseUsuario.size(); i++) {
//                                try {
//                                    if (Mercado.getInstance().listaSseUsuario.get(i).getSseClient().ctx.req.getSession().getId().equalsIgnoreCase(sseClient.ctx.req.getSession().getId())){
//                                        System.out.println("\n\neste es el id"+Mercado.getInstance().listaSseUsuario.get(i).getSseClient().ctx.req.getSession().getId()+" el otro "+sseClient.ctx.req.getSession().getId());
//                                        borra = i;
////                                    Mercado.getInstance().listaSseUsuario.remove(Mercado.getInstance().listaSseUsuario.get(i));
//                                        System.out.println("\n\neliminar12");
//                                        break;
//                                    }
//                                }catch (IllegalStateException e){
//
//                                }
//                            }
//                            System.out.println("\n\neliminar12");
//                            if (borra!=-1){
//                                Mercado.getInstance().listaSseUsuario.remove(borra);
//
//                            }
//
////                        listaSseUsuario.remove(sseClient);
//                        });
//                    }else{
//
//                        sseClient.sendEvent("statuServer", "error");
//                    }
//
//                });



                path("/Cliente", () -> {
                    after(ctx -> {
                        ctx.header("Content-Type", "application/json");
                    });

                    post("/", ctx -> {

                        Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
                        System.out.println("\n\n\nEste es el headerr ne" + decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());

                        String tmp = ctx.body();
                        ctx.json(UsuarioServicios.getInstancia().existe(tmp));
                    });
                    post("/search", ctx -> {

                        Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
//                        System.out.println("\n\n\nEste es el headerr ne" + decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());

                        String tmp =  ctx.body().toString();
                        System.out.println("Esto llego:"+tmp);
//                        ctx.json(ClienteServicios.getInstancia().getClienteUsuario(Mercado.getInstance().getUserJefeWithToken(user),tmp).getUsuarioJson());
                        Usuario clienteJson = ClienteServicios.getInstancia().getClienteUsuario(Mercado.getInstance().getUserJefeWithToken(user),tmp);
                        if (clienteJson != null){
                            ctx.json(clienteJson.getUsuarioJson());
                        }else {
                            ctx.json("no found");
                        }

                    });
                });
                path("/Inicio", () -> {
                    after(ctx -> {
                        ctx.header("Content-Type", "application/json");
                    });


                    get("/graphicVentasMes", ctx -> {

                        Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
//                        System.out.println("\n\n\nEste es el headerr ne" + decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());
                        ;
                        ctx.json(Mercado.getInstance().returnFechaMesJson(Mercado.getInstance().getUserJefeWithToken(user)));
//                        String tmp =  ctx.body().toString();
//                        System.out.println("Esto llego:"+tmp);
//                        ctx.json(ClienteServicios.getInstancia().getClienteUsuario(Mercado.getInstance().getUserJefeWithToken(user),tmp).getUsuarioJson());
//                        Usuario clienteJson = ClienteServicios.getInstancia().getClienteUsuario(Mercado.getInstance().getUserJefeWithToken(user),tmp);
//                        if (clienteJson != null){
//                            ctx.json(clienteJson.getUsuarioJson());
//                        }else {
//                            ctx.json("no found");
//                        }

                    });
                    get("/graphicVentasDia", ctx -> {

                        Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
//                        System.out.println("\n\n\nEste es el headerr ne" + decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());
                        ;
                        ctx.json(Mercado.getInstance().returnFechaDiaJson(Mercado.getInstance().getUserJefeWithToken(user)));
//                        String tmp =  ctx.body().toString();
//                        System.out.println("Esto llego:"+tmp);
//                        ctx.json(ClienteServicios.getInstancia().getClienteUsuario(Mercado.getInstance().getUserJefeWithToken(user),tmp).getUsuarioJson());
//                        Usuario clienteJson = ClienteServicios.getInstancia().getClienteUsuario(Mercado.getInstance().getUserJefeWithToken(user),tmp);
//                        if (clienteJson != null){
//                            ctx.json(clienteJson.getUsuarioJson());
//                        }else {
//                            ctx.json("no found");
//                        }

                    });
                });

                path("/Factura", () -> {
                    after(ctx -> {
                        ctx.header("Content-Type", "application/json");
                    });
                    get("/ventasActivas", ctx -> {
                        List<FacturaJson> facturaJsonList = new ArrayList<FacturaJson>();
                        Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
                        System.out.println("\n\n\nEste es el headerr ne" + decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());
                        if (user.getId().equalsIgnoreCase(Mercado.getInstance().getUserJefeWithToken(user))){
                            List<FacturaCliente> facturaClienteList= FacturaClienteServicios.getInstancia().ListFacturaClienteActivaVendedor(user.getId());
                            for (FacturaCliente facturaCliente : facturaClienteList) {
                                facturaJsonList.add(facturaCliente.getFacturaJson());
                            }
                        }else{
                            List<FacturaCliente> facturaClienteList= FacturaClienteServicios.getInstancia().ListFacturaClienteActivaEmpleado(user.getId());
                            for (FacturaCliente facturaCliente : facturaClienteList) {
                                facturaJsonList.add(facturaCliente.getFacturaJson());
                            }
                        }
                        ctx.json(facturaJsonList);
                    });
                    post("/loadProducto", ctx -> {

                        Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
                        System.out.println("\n\n\nEste es el headerr ne" + decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());

                        String tmp = ctx.body();
                        ctx.json(UsuarioServicios.getInstancia().existe(tmp));
                    });
                    post("/saveFactura", ctx -> {

                        Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
                        System.out.println("\n\n\nEste es el headerr ne" + decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());

                        GuardarFacturaJson tmp = ctx.bodyAsClass(GuardarFacturaJson.class);
                        FacturaJson facturaJson = Mercado.getInstance().guardarFactura(tmp);

                        ctx.json(facturaJson);
                    });
                    post("/crear", ctx -> {

                        Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
//                        System.out.println("\n\n\nEste es el headerr ne" + decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());

                        String tmp =  ctx.body().toString();
                        System.out.println("Esto llego:"+tmp);

//                      ctx.json(ClienteServicios.getInstancia().getClienteUsuario(Mercado.getInstance().getUserJefeWithToken(user),tmp).getUsuarioJson());
//                        Usuario clienteJson = ClienteServicios.getInstancia().getClienteUsuario("admin",tmp);
                        FacturaCliente facturaCliente = new FacturaCliente();
                        facturaCliente.setDireccion((String) user.get("direccion"));
                        facturaCliente.setTelefono((String) user.get("telefono"));
                        facturaCliente.setCompania((String) user.get("compania"));
                        facturaCliente.setCiudadPais((String) user.get("ciudadPais"));
                        facturaCliente.setIdQuienLoRealizo(user.getId());

                        facturaCliente.setIdVendedor(Mercado.getInstance().getUserJefeWithToken(user));

                        facturaCliente = FacturaClienteServicios.getInstancia().crearFacturaCliente(facturaCliente,"admin",tmp);
                        ctx.json(facturaCliente.getFacturaJson());
//                        if (clienteJson != null){
//
//                        }else {
//                            ctx.json("no found");
//                        }

                    });

                    post("/addProducto", ctx -> {

                        Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
//                        System.out.println("\n\n\nEste es el headerr ne" + decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());
                        AddDiscountProductoFacturaJSON addDiscountProductoFacturaJSON = ctx.bodyAsClass(AddDiscountProductoFacturaJSON.class);
//                        String tmp =  ctx.body().toString();
//                        System.out.println("Esto llego:"+tmp);
                        boolean editar = true;
                        ProductoJSON productoJSON = null;
                        do {
                            editar = Mercado.getInstance().sePuedeEditarYActivate(Mercado.getInstance().getUserJefeWithToken(user),addDiscountProductoFacturaJSON.getIdProducto(), user.getId() );
                            if (editar== true){

                                productoJSON = Mercado.getInstance().addProductoInFactura(addDiscountProductoFacturaJSON.getIdFactura(),addDiscountProductoFacturaJSON.getIdProducto(),addDiscountProductoFacturaJSON.getCantidad());
                                Mercado.getInstance().sePuedeEditarYDesactivate(Mercado.getInstance().getUserJefeWithToken(user),addDiscountProductoFacturaJSON.getIdProducto(), user.getId() );
                                sendJsonProductoUpdate(productoJSON,Mercado.getInstance().getUserJefeWithToken(user));
                            }
                        }while (editar==false);
                        FacturaJson facturaJson = FacturaClienteServicios.getInstancia().getFacturaCliente(addDiscountProductoFacturaJSON.getIdFactura()).getFacturaJson();
                        ctx.json(facturaJson);
//                        ctx.json(ClienteServicios.getInstancia().getClienteUsuario(Mercado.getInstance().getUserJefeWithToken(user),tmp).getUsuarioJson());
//                        Usuario clienteJson = ClienteServicios.getInstancia().getClienteUsuario("admin",tmp);
//                        if (clienteJson != null){
//                            ctx.json(clienteJson.getUsuarioJson());
//                        }else {
//                            ctx.json("no found");
//                        }

                    });
                    post("/discountProducto", ctx -> {

                        Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
//                        System.out.println("\n\n\nEste es el headerr ne" + decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());
                        AddDiscountProductoFacturaJSON addDiscountProductoFacturaJSON = ctx.bodyAsClass(AddDiscountProductoFacturaJSON.class);
//                        String tmp =  ctx.body().toString();
//                        System.out.println("Esto llego:"+tmp);
                        boolean editar = true;
                        ProductoJSON productoJSON = null;
                        do {
                            editar = Mercado.getInstance().sePuedeEditarYActivate(Mercado.getInstance().getUserJefeWithToken(user),addDiscountProductoFacturaJSON.getIdProducto(), user.getId() );
                            if (editar== true){


                                productoJSON = Mercado.getInstance().discountProductoInFactura(addDiscountProductoFacturaJSON.getIdFactura(),addDiscountProductoFacturaJSON.getIdProducto(),addDiscountProductoFacturaJSON.getCantidad());
                                Mercado.getInstance().sePuedeEditarYDesactivate(Mercado.getInstance().getUserJefeWithToken(user),addDiscountProductoFacturaJSON.getIdProducto(), user.getId() );
                                sendJsonProductoUpdate(productoJSON,Mercado.getInstance().getUserJefeWithToken(user));
                            }
                        }while (editar==false);

                        FacturaJson facturaJson = FacturaClienteServicios.getInstancia().getFacturaCliente(addDiscountProductoFacturaJSON.getIdFactura()).getFacturaJson();
                        ctx.json(facturaJson);

                    });
                    post("/deleteFacturaActiva", ctx -> {

//                        Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
//                        System.out.println("\n\n\nEste es el headerr ne" + decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());
                        Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
                        if (user != null){
                            String tmp =  ctx.body().toString();
                            System.out.println("Esto llego:"+tmp);
                            boolean dele = Mercado.getInstance().borrarFactura(tmp);
                            if (dele == true){

                                ctx.json("deleted");
                            }else {
                                ctx.json("no deleted");

                            }
                        }else{
                            ctx.json(-1);
                        }

//                        if (clienteJson != null){
//
//                        }else {
//                            ctx.json("no found");
//                        }

                    });
                    post("/loadFactura", ctx -> {

//                        Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
//                        System.out.println("\n\n\nEste es el headerr ne" + decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());

                        String tmp =  ctx.body().toString();
                        System.out.println("Esto llego:"+tmp);
//                        ctx.json(ClienteServicios.getInstancia().getClienteUsuario(Mercado.getInstance().getUserJefeWithToken(user),tmp).getUsuarioJson());
//                        Usuario clienteJson = ClienteServicios.getInstancia().getClienteUsuario("admin",tmp);
                        FacturaJson facturaJson = FacturaClienteServicios.getInstancia().getFacturaCliente(tmp).getFacturaJson();
                        ctx.json(facturaJson);
//                        if (clienteJson != null){
//
//                        }else {
//                            ctx.json("no found");
//                        }

                    });

                });

                path("/Usuario", () -> {
                            after(ctx -> {
                                ctx.header("Content-Type", "application/json");
                            });

                            post("/", ctx -> {

                                Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
                                System.out.println("\n\n\nEste es el headerr ne" + decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());

                                String tmp = ctx.body();
                                ctx.json(UsuarioServicios.getInstancia().existe(tmp));
                            });
                        });

                path("/Producto", () -> {
                    after(ctx -> {
                        ctx.header("Content-Type", "application/json");
                    });
                    post("/Impuesto", ctx -> {
                        String session = ctx.sessionAttribute("User");
                        if (session == null){
                            ctx.json(-1);

                        }else{



                            try {
                                if(isExpirate(decodeJWT(session))==false){

                                    Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
                                    String idProducto =  ctx.body().toString();
                                    Producto producto = ProductoServicios.getInstancia().getProductoSinFoto(Long.parseLong(idProducto));

                                    List<ImpuestoJson> listaJson = new ArrayList<ImpuestoJson>();
                                    for (int i = 0; i < producto.getProductoEnVenta().getImpuestoProductoEnVentas().size(); i++) {
                                        listaJson.add(producto.getProductoEnVenta().getImpuestoProductoEnVentas().get(i).getIdImpuesto().damImpuestoJson((double) producto.getProductoEnVenta().getPrecioVenta(),producto.getProductoEnVenta().getImpuestoProductoEnVentas().get(i).getId()));
                                    }
//

                                    System.out.println("\n\n\nEste es el headerr ne"+decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());
                                    ctx.json(listaJson);
                                }else{
                                    ctx.json(null);
                                }
                            }catch (ExpiredJwtException e){
                                ctx.json(null);
                            }
                        }

                    });


                    post("/ImpuestoNoAdded", ctx -> {
                        String session = ctx.sessionAttribute("User");
                        if (session == null){
                            ctx.json(-1);

                        }else{



                            try {
                                if(isExpirate(decodeJWT(session))==false){

                                    Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
                                    String idProducto =  ctx.body().toString();
                                    Producto producto = ProductoServicios.getInstancia().getProductoSinFoto(Long.parseLong(idProducto));
                                    long cant = ImpuestoServicios.getInstancia().getCantImpuesto(Mercado.getInstance().getUserJefeWithToken(user));
                                    List<Impuesto> imp = ImpuestoServicios.getInstancia().impuestoProductoNotAdded(Long.parseLong(idProducto),Mercado.getInstance().getUserJefeWithToken(user));


                                    List<ImpuestoJson> listaJson = new ArrayList<ImpuestoJson>();
                                    if (cant != imp.size()){

                                        for (int i = 0; i < imp.size(); i++) {
                                            listaJson.add(imp.get(i).damImpuestoJson((double) producto.getProductoEnVenta().getPrecioVenta(),-1));
                                        }
                                    }
//

                                    System.out.println("\n\n\nEste es el headerr ne"+decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());
                                    ctx.json(listaJson);
                                }else{
                                    ctx.json(null);
                                }
                            }catch (ExpiredJwtException e){
                                ctx.json(null);
                            }
                        }

                    });
                    post("/ProductoRelation", ctx -> {
                        String session = ctx.sessionAttribute("User");
                        if (session == null){
                            ctx.json(-1);

                        }else{



                            try {
                                if(isExpirate(decodeJWT(session))==false){
                                    Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
                                    ActionJson actionJson =  ctx.bodyAsClass(ActionJson.class);
                                    long idProdu = (long) Mercado.getInstance().selectActionClass(actionJson,user);
//                                    List<Almacen> lista = AlmacenServicios.getInstancia().listAlmacen(0, Long.parseLong(idProducto));
//                                    List<AlmacenJson> listaJson = new ArrayList<AlmacenJson>();
//                                    for (int i = 0; i < lista.size(); i++) {
//                                        listaJson.add(lista.get(i).getAlmacenJson());
//                                    }
//                                    Producto producto = ProductoServicios.getInstancia().getProductoSinFoto();
                                    Producto producto = ProductoServicios.getInstancia().getProductoSinFoto(idProdu);
                                    System.out.println("\n\n\nEste es el headerr ne"+decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());
                                    ctx.json(producto.getProductoJSON(2));
                                }else{
                                    ctx.json(null);
                                }
                            }catch (ExpiredJwtException e){
                                ctx.json(null);
                            }
                        }

                    });
                    post("/Almacen", ctx -> {
                        String session = ctx.sessionAttribute("User");
                        if (session == null){
                            ctx.json(-1);

                        }else{



                            try {
                                if(isExpirate(decodeJWT(session))==false){
                                    Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
                                    String idProducto =  ctx.body().toString();
                                    List<Almacen> lista = AlmacenServicios.getInstancia().listAlmacen(0, Long.parseLong(idProducto));
                                    List<AlmacenJson> listaJson = new ArrayList<AlmacenJson>();
                                    for (int i = 0; i < lista.size(); i++) {
                                        listaJson.add(lista.get(i).getAlmacenJson());
                                    }
//                                    Producto producto = ProductoServicios.getInstancia().getProductoSinFoto();

                                    System.out.println("\n\n\nEste es el headerr ne"+decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());
                                    ctx.json(listaJson);
                                }else{
                                    ctx.json(null);
                                }
                            }catch (ExpiredJwtException e){
                                ctx.json(null);
                            }
                        }

                    });

                    get("/", ctx -> {
                        String session = ctx.sessionAttribute("User");
                        if (session == null){
                            ctx.json(-1);

                        }else{



                                try {
                                    if(isExpirate(decodeJWT(session))==false){
                                        Claims user = decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")));
                                        System.out.println("\n\n\nEste es el headerr ne"+decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());
                                        ctx.json(ProductoServicios.getInstancia().getListaProductoJson(Mercado.getInstance().getUserJefeWithToken(user)));
                                    }else{
                                        ctx.json(null);
                                    }
                        }catch (ExpiredJwtException e){
                            ctx.json(null);
                        }catch (NullPointerException e){
                                    e.printStackTrace();
                                    ctx.json(null);
                                }
                    }

                    });

//                    get("/:matricula", ctx -> {
//                        ctx.json(fakeServices.getEstudiantePorMatricula(ctx.pathParam("matricula", Integer.class).get()));
//                    });
//
                    post("/", ctx -> {
                        //parseando la informacion del POJO debe venir en formato json.

                        ProductoSaveJson tmp = ctx.bodyAsClass(ProductoSaveJson.class);


                        Producto producto = new Producto();
                        producto.setNombre(tmp.getNombre());
                        producto.setDescripcion(tmp.getDescripcion());
                        producto.setCodigo_local(tmp.getCodigo());
//                        producto.setMimeType();
//                        producto.setNombreFoto();
//                        producto.setFotoBase64();
                        producto.setCategoria(tmp.getCategorias());


                        Vendedor otro = new VendedorServicios().find(Mercado.getInstance().getUserJefeWithToken(decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")))));

                        producto.CrearProductoVenta();
                        producto = ProductoServicios.getInstancia().crearProducto(producto);
                        if (tmp.getNombreImg() != null || tmp.getMimetype() != null || tmp.getBase64() != null ){

                            Foto foto= new Foto(tmp.getNombreImg(), tmp.getMimetype(), tmp.getBase64());
                            foto = (Foto) FotoServices.getInstancia().crear(foto);
                            producto.addFoto(foto);
                            producto = (Producto) ProductoServicios.getInstancia().editar(producto);
                        }else{
                            Foto foto= new Foto(null,null,null);
                            foto = (Foto) FotoServices.getInstancia().crear(foto);
                            producto.addFoto(foto);
                            producto = (Producto) ProductoServicios.getInstancia().editar(producto);
                        }
                        otro.addProducto(producto);
//            VendedorServicios.getInstancia().editar(otro);
                        Producto va = ((Vendedor) VendedorServicios.getInstancia().editar(otro)).getProductoEspecif(producto.getId());

                        Almacen almacen = new Almacen(tmp.getSuplidor(),Long.parseLong(tmp.getStock()),0,(float) Double.parseDouble(tmp.getCompra()), (float)Double.parseDouble(tmp.getVenta()));
                        va.addAlmacen(almacen);
                        va = (Producto) new ProductoServicios().editar(va);
                        Mercado.getInstance().addImpuestoToOnlyProducto(va.getId(), Mercado.getInstance().getUserJefeWithToken(decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")))));

//                        va = (Producto) new ProductoServicios().find(va.getId());
//                        Almacen almacen1 = new Almacen("Endy",10,1,300,600);
//                        almacen1 = va.addAlmacen(almacen1);
//                        new ProductoServicios().update(va);

//                        va.addOnlyList(almacen1);
//                        va = (Producto) new ProductoServicios().find(va.getId());
                        //creando.

                        ctx.json(true);
                    });
//
//                    put("/", ctx -> {
//                        //parseando la informacion del POJO.
//                        Estudiante tmp = ctx.bodyAsClass(Estudiante.class);
//                        //creando.
//                        ctx.json(fakeServices.actualizarEstudiante(tmp));
//
//                    });
//
//                    delete("/:matricula", ctx -> {
//                        //creando.
//                        ctx.json(fakeServices.eliminandoEstudiante(ctx.pathParam("matricula", Integer.class).get()));
//                    });
                });
            });
        });

        app.exception(NoExisteEstudianteException.class, (exception, ctx) -> {
            ctx.status(404);
            ctx.json(""+exception.getLocalizedMessage());
        });
    }
    public static void removeInSession(String id){
        for (int i = 0; i < Mercado.getInstance().listaSseUsuario.size(); i++) {

        }
    }
    public static void sendJsonProductoUpdate(ProductoJSON productoJSON, String idJefe){
        List<VentasSession> listaTmp = new CopyOnWriteArrayList<>(Mercado.getInstance().getListaSseUsuario());
        String jsonStr = null;
        ObjectMapper Obj = new ObjectMapper();

        try {

            // get Oraganisation object as a json string
             jsonStr = Obj.writeValueAsString(productoJSON);

            // Displaying JSON String
            System.out.println(jsonStr);
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        String finalJsonStr = jsonStr;
        listaTmp.forEach(sseClient -> {
            System.out.println("Enviando informacion...");
            if (idJefe.equalsIgnoreCase(sseClient.getIdJefe())){
                try {
                    sseClient.getSseClient().getRemote().sendString( finalJsonStr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }
    public void iniStart(){
        new Thread(() -> {
            long id = 1;
            ProductoJSON productoJSON = ProductoServicios.getInstancia().buscar(id).getProductoJSON(1);
            while(true){
                productoJSON.setStock(productoJSON.getStock()+1);
//                List<VentasSession> listaTmp = new CopyOnWriteArrayList<>(Mercado.getInstance().getListaSseUsuario());
//                listaTmp.forEach(sseClient -> {
//                    System.out.println("Enviando informacion...");
//                    if ("admin".equalsIgnoreCase(sseClient.getIdJefe())){
//                        sseClient.getSseClient().sendEvent("productoload", ""+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
//                    }
//
//                });
                sendJsonProductoUpdate(productoJSON,"admin");
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    public static String createJWT(String username, String perfil, Map<String, Object> map) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder;
        builder = Jwts.builder().setClaims(map).setId(username)
                .setIssuedAt(now)
                .setSubject("CashSuite")
                .setIssuer((String) map.get("user"))
                .setAudience(perfil)
                .signWith(signatureAlgorithm, signingKey);

        // 5 minutes para expiracion
        long expMillis = nowMillis + Mercado.getInstance().getTimeSessionMinute() * 1000 * 60;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);
        System.out.println("fecha" +now +exp);
        System.out.println("Expiro: "+isExpirate(decodeJWT(builder.compact())));
        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static boolean isExpirate(Claims jwt){
        boolean expire = true;
        if (jwt != null){
            if (new  Date(System.currentTimeMillis()).after(jwt.getExpiration())){
                return expire;
            }

        }
        return false;
    }

    public static Claims decodeJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        try {

            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(jwt).getBody();

            return claims;
        }catch (ExpiredJwtException e){
            return null;
        }    }
}
