package csuite.mvc.controlador;

import csuite.mvc.entidades.*;
import csuite.mvc.jsonObject.ProductoSaveJson;
import csuite.mvc.servicios.*;

import csuite.mvc.util.NoExisteEstudianteException;
import io.javalin.Javalin;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ApiControlador extends JavalinControlador {
    public final static String SECRET_KEY = "ghQaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";




    public ApiControlador(Javalin app) {
        super(app);
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
                            ctx.json(1);

                        }else{
                            System.out.println("\n\n\n\nverifico");

                            if(Mercado.getInstance().getUserEncryptor().decrypt(user).equalsIgnoreCase(session)){
                                try {
                                    if(isExpirate(decodeJWT(session))==false){
                                        String header = "Authorization";
                                        Claims claims = decodeJWT(session);
                                        String use = claims.getId();
                                        String jwt = createJWT(use,claims.getAudience(), claims.getIssuer());
                                        ctx.header(header,jwt);
                                        ctx.sessionAttribute("User",jwt);
                                        ctx.cookie("User",Mercado.getInstance().getUserEncryptor().encrypt(jwt),2147483647);
                                        String mensaje = String.format("Manejador before aplicando a todas las llamadas: %s, Contexto: %s, Metodo: %s",
                                                ctx.req.getRemoteHost(),
                                                ctx.req.getServletPath(),
                                                ctx.req.getMethod());
                                        //
                                        System.out.println(mensaje);

                                        for (int i = 0; i < Mercado.getInstance().getLogins().size(); i++) {
                                            if (Mercado.getInstance().getLogins().get(i).getId().equalsIgnoreCase(use)){
                                                Mercado.getInstance().getLogins().get(i).setJwt(claims);
                                                Mercado.getInstance().getLogins().get(i).setSession(ctx.req.getSession());
                                            }
                                        }
                                        String tmp = ctx.body();
                                        ctx.json(Mercado.getInstance().getTimeSessionMinute()*60*1000);




                                    }else{
                                        ctx.json(1);
                                    }

                                }catch (ExpiredJwtException e){
                                    ctx.json(1);
                                }
                            }else{
                                ctx.json(1);
                            }


                        }



                    });

                });
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
                        System.out.println("\n\n\nEste es el headerr ne" + decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());

                        String tmp = ctx.body();
                        ctx.json(ClienteServicios.getInstancia().getClienteUsuario(Mercado.getInstance().getUserJefeWithToken(user),tmp).getUsuarioJson());
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
                        producto.setMimeType(tmp.getMimetype());
                        producto.setNombreFoto(tmp.getNombreImg());
                        producto.setFotoBase64(tmp.getBase64());
                        producto.setCategoria(tmp.getCategorias());


                        Vendedor otro = new VendedorServicios().find(Mercado.getInstance().getUserJefeWithToken(decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User")))));

                        producto.CrearProductoVenta();
                        producto = ProductoServicios.getInstancia().crearProducto(producto);
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
    public static String createJWT(String username, String perfil, String dueno) {

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
                .setIssuer(dueno)
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
