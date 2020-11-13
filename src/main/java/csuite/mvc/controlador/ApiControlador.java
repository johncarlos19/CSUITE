package csuite.mvc.controlador;

import csuite.mvc.entidades.*;
import csuite.mvc.jsonObject.ProductoSaveJson;
import csuite.mvc.servicios.ProductoEnVentaServicios;
import csuite.mvc.servicios.ProductoServicios;

import csuite.mvc.servicios.UsuarioServicios;
import csuite.mvc.servicios.VendedorServicios;
import csuite.mvc.util.NoExisteEstudianteException;
import io.javalin.Javalin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
                path("/Producto", () -> {
                    after(ctx -> {
                        ctx.header("Content-Type", "application/json");
                    });

                    get("/", ctx -> {
                        System.out.println("\n\n\nEste es el headerr ne"+decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId());
                        ctx.json(ProductoServicios.getInstancia().getListaProductoJson(decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId()));
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


                        Vendedor otro = new VendedorServicios().find(Mercado.getInstance().getUserJefe(decodeJWT(Mercado.getInstance().getUserEncryptor().decrypt(ctx.cookie("User"))).getId()));

                        producto.CrearProductoVenta();
                        producto = ProductoServicios.getInstancia().crearProducto(producto);
                        otro.addProducto(producto);
//            VendedorServicios.getInstancia().editar(otro);
                        Producto va = ((Vendedor) VendedorServicios.getInstancia().editar(otro)).getProductoEspecif(producto.getId());

                        Almacen almacen = new Almacen(tmp.getSuplidor(),Long.parseLong(tmp.getStock()),0,(float) Double.parseDouble(tmp.getCompra()), (float)Double.parseDouble(tmp.getVenta()));
                        va.addAlmacen(almacen);
                        va = (Producto) new ProductoServicios().editar(va);

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
