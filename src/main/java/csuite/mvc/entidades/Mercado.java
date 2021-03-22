package csuite.mvc.entidades;

import com.fasterxml.jackson.databind.ObjectMapper;
import csuite.mvc.jsonObject.ActionJson;
import csuite.mvc.jsonObject.FacturaJson;
import csuite.mvc.jsonObject.GuardarFacturaJson;
import csuite.mvc.jsonObject.ProductoJSON;
import csuite.mvc.servicios.*;
import io.jsonwebtoken.Claims;
import org.jasypt.util.text.AES256TextEncryptor;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mercado {
    //    private static ProductoServicios productoServicios = new ProductoServicios();
//    private static UsuarioServicios usuarioServicios = new UsuarioServicios();
//    private static VentasProductosServicios ventasProductosServicios = new VentasProductosServicios();
//    private static CarroCompraServicios carroCompraServicios = new CarroCompraServicios();
//    private static CarroCompra_ProductoServicios carroCompra_productoServicios = new CarroCompra_ProductoServicios();
    private static Mercado mercado;
    private AES256TextEncryptor userEncryptor = new AES256TextEncryptor();
    private AES256TextEncryptor passwordEncryptor = new AES256TextEncryptor();
    private Map<String, Login> logins = new HashMap<String, Login>();
//    private ArrayList<Login> logins = new ArrayList<Login>();
    private long timeSessionMinute = 10;
    public  List<VentasSession> listaSseUsuario = new ArrayList<VentasSession>();

    public Mercado() {
    }

    public static Mercado getInstance() {
        if (mercado == null)
            mercado = new Mercado();
        return mercado;
    }

    public void loadDataBase() throws UnsupportedEncodingException {
        userEncryptor.setPassword("admin");
        passwordEncryptor.setPassword("admin");
//        FacturaCliente facturaCliente = new FacturaCliente();
//        facturaCliente.setIdQuienLoRealizo("admin");
//        FacturaClienteServicios.getInstancia().crearFacturaCliente(facturaCliente,"admin","CLI-000001");

        try {
            if (verificar_user("admin", "admin") != null) {
//                for (Impuesto impuesto : ImpuestoServicios.getInstancia().impuestoProductoNotAdded(1)) {
//                    System.out.println("\n\nEso es:"+impuesto.getNombre());
//                }


//                ClienteServicios.getInstancia().getCliente("CLI-000001");
//                ImpuestoClienteServicios.getInstancia().ListaImpuestoFacturaCliente("FAC-00000003");
//                discountProductoInFactura("FAC-00000003", 1, 6);
//                    addProductoInFactura("FAC-00000004", 1, 4);
//                //agregando para la factura lista producto
//                long id = 1;
//                Producto va = (Producto) new ProductoServicios().find(id);
//                Almacen almacen1 = new Almacen("Flores",10,0,250,600);
//                almacen1 = va.addAlmacen(almacen1);
//                va = (Producto) new ProductoServicios().editar(va);
//
//                facturarOnlyProducto(va, 13);


//                for (Usuario usuario : UsuarioServicios.getInstancia().listaUsuario()) {
//                    if (usuario.getPerfil().equalsIgnoreCase("Admin") && !usuario.getUsuario().equalsIgnoreCase("admin") ){
//                        crearClienteAlContado(usuario.getUsuario());
//                    }
//                }

//                for (ProductoJSON productoJSON : ProductoServicios.getInstancia().getListaProductoJson("admin")) {
//                    System.out.println("\n\nImpuesto"+productoJSON.getImpuesto()+"precioNeto"+productoJSON.getPrecioLista());
//                }

//                Usuario usuario = new Usuario("admin", "Admin");
//                usuario.setEmail("admin@cashsuite.com");
//                usuario.setPerfil("Admin");
//                Usuario aux = new UsuarioServicios().crearUsuario(usuario);
//
//                Producto producto = new Producto();
//                producto.setNombre("Huevo");
//                producto.setDescripcion("Son bueno");
//                producto.setCodigo_local("144533");
//                Producto producto1 = new Producto();
//                producto1.setNombre("Pan");
//                producto1.setDescripcion("Son malo");
//                producto1.setCodigo_local("1445833");
//
//
//                Vendedor vendedor = new Vendedor();
//                vendedor.setEmail("admin@cashsuite.com");
//                vendedor.setPassword(passwordEncryptor.encrypt("admin"));
//                Vendedor otro = aux.addVendedor(vendedor);
//                VendedorServicios.getInstancia().crear(otro);
//                UsuarioServicios.getInstancia().editar(aux);
//
//                producto.CrearProductoVenta();
//                producto1.CrearProductoVenta();
//                producto = ProductoServicios.getInstancia().crearProducto(producto);
//                producto1 = ProductoServicios.getInstancia().crearProducto(producto1);
//                otro.addProducto(producto);
//                otro.addProducto(producto1);
////            VendedorServicios.getInstancia().editar(otro);
//                Producto va = ((Vendedor) VendedorServicios.getInstancia().editar(otro)).getProductoList().get(0);
//
//                Almacen almacen = new Almacen("Don Lindo",10,10,200,500);
//                va.addAlmacen(almacen);
//                va = (Producto) new ProductoServicios().editar(va);
//                almacen = new Almacen("Endy",10,1,300,600);
//                va.addAlmacen(almacen);
//                va = (Producto) new ProductoServicios().editar(va);


            }
        } catch (NullPointerException e) {
            runMuestra();
        }


    }

    public  List<VentasSession> getListaSseUsuario() {
        return listaSseUsuario;
    }



    public  void addListaSseUsuario(VentasSession ventasSession) {
        this.listaSseUsuario.add(ventasSession);
    }

    public  void setListaSseUsuario(List<VentasSession> listaSseUsuario) {
        this.listaSseUsuario = listaSseUsuario;
    }


    public void borrarSessionSSe(String id){
        int borrar = -1;
        for (int i = 0; i < listaSseUsuario.size(); i++) {
            try {
                if (id.equalsIgnoreCase(listaSseUsuario.get(i).getSseClient().ctx.req.getSession().getId())){
                    borrar = i;
                    break;
                }
            }catch (IllegalStateException e){
                e.printStackTrace();
                borrar = i;
                break;
            }

        }
        if (borrar!=-1){
            listaSseUsuario.remove(borrar);
            System.out.println("\n\nelimino");
        }
    }

    public void runMuestra() throws UnsupportedEncodingException {
        Usuario usuario = new Usuario("admin", "Admin");
        usuario.setEmail("admin@cashsuite.com");
        usuario.setPerfil("Admin");
        usuario.setMunicipio("Moca");
        usuario.setDireccion("Paso de moca");
        usuario.setTelefono("5454454");
//            Usuario aux = new UsuarioServicios().crearUsuario(usuario);
        Usuario aux = RegistrarVendedor(usuario, "admin@cashsuite.com", "admin");
        Vendedor otro = VendedorServicios.getInstancia().getVendedor(aux.getUsuario());

        Foto foto= new Foto(null,null,null);
//        foto = (Foto) FotoServices.getInstancia().crear(foto);
        Foto foto1= new Foto(null,null,null);
//        foto1 = (Foto) FotoServices.getInstancia().crear(foto1);

        Producto producto = new Producto();
        producto.setNombre("Huevo");
        producto.setDescripcion("Son bueno");
        producto.setCodigo_local("144533");

        Producto producto1 = new Producto();
        producto1.setNombre("Pan");
        producto1.setDescripcion("Son malo");
        producto1.setCodigo_local("1445833");


//            Vendedor vendedor = new Vendedor();
//            vendedor.setEmail("admin@cashsuite.com");
//            vendedor.setPassword(passwordEncryptor.encrypt("admin"));
//            Vendedor otro = aux.addVendedor(vendedor);
//            VendedorServicios.getInstancia().crear(otro);
//            UsuarioServicios.getInstancia().editar(aux);

        producto.CrearProductoVenta();
        producto1.CrearProductoVenta();
        producto = ProductoServicios.getInstancia().crearProducto(producto);
        producto1 = ProductoServicios.getInstancia().crearProducto(producto1);
        producto.addFoto(foto);
        producto1.addFoto(foto1);
        otro.addProducto(producto);
        otro.addProducto(producto1);

        Producto va = ((Vendedor) VendedorServicios.getInstancia().editar(otro)).getProductoList().get(0);

        Almacen almacen = new Almacen("Don Lindo", 10, 10, 200, 500);
        va.addAlmacen(almacen);
        va = (Producto) new ProductoServicios().editar(va);

        va = (Producto) new ProductoServicios().find(va.getId());
        Almacen almacen1 = new Almacen("Endy", 10, 0, 300, 600);
//        almacen1 = (Almacen) AlmacenServicios.getInstancia().crear(almacen1);
        almacen1 = va.addAlmacen(almacen1);
         ProductoServicios.getInstancia().update(va);
//        almacen1 = AlmacenServicios.getInstancia().buscar(almacen1.getIdAlmacen());
        va.addOnlyList(almacen1);

        va = (Producto) new ProductoServicios().find(va.getId());
        otro = VendedorServicios.getInstancia().getVendedor(otro.getIdVendedor().getUsuario());
        Categoria categoria = new Categoria();
        categoria.setDescripcion("Comida");
        Categoria categoria1 = new Categoria();
        categoria1.setDescripcion("Electronico");
        categoria = (Categoria) CategoriaServicios.getInstancia().crear(categoria);
        categoria1 = (Categoria) CategoriaServicios.getInstancia().crear(categoria1);
        otro.addCategoria(categoria);
        otro.addCategoria(categoria1);
        otro = (Vendedor) VendedorServicios.getInstancia().editar(otro);


        Usuario usuarioCliente = new Usuario();
        usuarioCliente.setNombre("Juan");
        usuarioCliente.setApellido("Perez");
        usuarioCliente.setDocumento("123456");
        usuarioCliente.setPerfil("Cliente");
        usuarioCliente.setPais("Republica Dominicana");
        usuarioCliente.setMunicipio("Moca");
        usuarioCliente.setDireccion("Paso De Moca");
        usuarioCliente.setTelefono("8095557456");
        usuarioCliente = (Usuario) new UsuarioServicios().crear(usuarioCliente);
        System.out.println("\n\n\nusua" + usuarioCliente.getUsuario());
        usuarioCliente = UsuarioServicios.getInstancia().find(usuarioCliente.getUsuario());
        Cliente cliente = new Cliente();
        cliente.setIdClienteLocal("sdsd");
        cliente = usuarioCliente.addCliente(cliente);
        cliente = (Cliente) ClienteServicios.getInstancia().crear(cliente);
        usuarioCliente = (Usuario) UsuarioServicios.getInstancia().editar(usuarioCliente);

        otro.addCliente(cliente);
        otro = (Vendedor) VendedorServicios.getInstancia().editar(otro);

        Impuesto impuesto = new Impuesto("18% De ley", "Porciento", 18);
        impuesto.setAplicarATodos(true);
        impuesto = (Impuesto) ImpuestoServicios.getInstancia().crear(impuesto);
        otro.addImpuesto(impuesto);
        otro = (Vendedor) VendedorServicios.getInstancia().editar(otro);

        long f = 1;
        impuesto = ImpuestoServicios.getInstancia().getImpuesto(impuesto.getId());
        for (ProductoEnVenta productoEnVenta : ProductoEnVentaServicios.getInstancia().listaVentaProducto(0, "admin")
        ) {
//                ImpuestoProductoEnVenta impuestoProductoEnVenta = new ImpuestoProductoEnVenta(impuesto, productoEnVenta);
//                ImpuestoProductoEnVentaServicios.getInstancia().crear(impuestoProductoEnVenta);
//                impuesto.addProducto(productoEnVenta);
            productoEnVenta.addImpuesto(impuesto);
            ProductoEnVentaServicios.getInstancia().editar(productoEnVenta);
//                ProductoEnVentaServicios.getInstancia().editar(productoEnVenta);

        }
//            ImpuestoServicios.getInstancia().editar(impuesto);


        Usuario usuario1 = new Usuario("johncarlos1943", "John");
        usuario1.setEmail("johncarlos1943@cashsuite.com");
        usuario1.setPerfil("Admin");
        Usuario aux1 = new UsuarioServicios().crearUsuario(usuario1);

        Vendedor vendedor1 = new Vendedor();
        vendedor1.setEmail("johncarlos1943@cashsuite.com");
        vendedor1.setPassword(passwordEncryptor.encrypt("admin"));
        Vendedor otro1 = aux1.addVendedor(vendedor1);
        VendedorServicios.getInstancia().crear(otro1);
        aux1 = (Usuario) UsuarioServicios.getInstancia().editar(aux1);


        Usuario usuarioCliente1 = new Usuario();
        usuarioCliente1.setNombre("Pedro");
        usuarioCliente1.setApellido("Perez");
        usuarioCliente1.setPerfil("Cliente");
        usuarioCliente1.setPais("Republica Dominicana");
        usuarioCliente1.setDocumento("654321");
        usuarioCliente1.setMunicipio("Moca");
        usuarioCliente1.setDireccion("Paso 11De Moca");
        usuarioCliente1.setTelefono("809525456");
        usuarioCliente1 = (Usuario) new UsuarioServicios().crear(usuarioCliente1);
        System.out.println("\n\n\nusua" + usuarioCliente1.getUsuario());
        usuarioCliente1 = UsuarioServicios.getInstancia().find(usuarioCliente1.getUsuario());
        Cliente cliente1 = new Cliente();
        cliente1.setIdClienteLocal("oooo");
        cliente1 = usuarioCliente1.addCliente(cliente1);
        cliente1 = (Cliente) ClienteServicios.getInstancia().crear(cliente1);
        usuarioCliente1 = (Usuario) UsuarioServicios.getInstancia().editar(usuarioCliente1);


        otro1.addCliente(cliente1);
        otro1 = (Vendedor) VendedorServicios.getInstancia().editar(otro1);
        Usuario empleado = new Usuario();
        empleado.setNombre("Pepe");
        empleado.setApellido("Puraz");
        empleado.setPerfil("Empleado");
        empleado.setPais("Republica Dominicana");
        empleado.setMunicipio("Santiago");
        empleado.setDireccion("Pasfffffe Moca");
        empleado.setTelefono("80955401221");
        empleado.setUsuario("lalameme");
        empleado.setEmail("pepe.puraz@cashsuite.com");
        registrarEmpleado("admin", empleado, "admin", 1);


        Usuario empleadoLectura = new Usuario();
        empleadoLectura.setNombre("Empleado");
        empleadoLectura.setApellido("Solo Lectura");
        empleadoLectura.setPerfil("Empleado");
        empleadoLectura.setPais("Republica Dominicana");
        empleadoLectura.setMunicipio("La vega");
        empleadoLectura.setDireccion("vegano");
        empleadoLectura.setTelefono("80955401221");
        empleadoLectura.setUsuario("empleadoLectura");
        empleadoLectura.setEmail("pepe.puraz@cashsuite.com");
        registrarEmpleado("admin", empleadoLectura, "admin", 2);

        System.out.println("\n\nEl dueno del empleado:" + EmpleadoServicios.getInstancia().getJefe("lalameme"));


        //usuario de elias


        Usuario usuario10 = new Usuario("eliasmarte", "Elias");
        usuario10.setApellido("Marte");
        usuario10.setEmail("eliasMarte@cashsuite.com");
        usuario10.setPerfil("Admin");
        Usuario aux123 = new UsuarioServicios().crearUsuario(usuario10);

        Vendedor vendedor123 = new Vendedor();
        vendedor123.setEmail("eliasmarte@cashsuite.com");
        vendedor123.setPassword(passwordEncryptor.encrypt("admin"));
        Vendedor otro123 = aux123.addVendedor(vendedor123);
        VendedorServicios.getInstancia().crear(otro123);
        aux123 = (Usuario) UsuarioServicios.getInstancia().editar(aux123);

           /* Producto pp = new Producto("Pan", BigDecimal.valueOf(50.00).setScale(2));
            pp.setDescripcion("Bueno");
            new ProductoServicios().crearProducto(pp);
            Producto dd = new Producto("Pizza", BigDecimal.valueOf(500.00).setScale(2));
            dd.setDescripcion("Malo");
            new ProductoServicios().crearProducto(dd);
            Producto ddc = new Producto("Agua", BigDecimal.valueOf(500.00).setScale(2));
            ddc.setDescripcion("Malo");
            new ProductoServicios().crearProducto(ddc);
            Producto ddr = new Producto("Leche", BigDecimal.valueOf(500.00).setScale(2));
            ddr.setDescripcion("Malo");
            new ProductoServicios().crearProducto(ddr);
            Producto dde = new Producto("Pipian", BigDecimal.valueOf(500.00).setScale(2));
            dde.setDescripcion("Malo");
            new ProductoServicios().crearProducto(dde);
            Producto ddet = new Producto("Lodo", BigDecimal.valueOf(500.00).setScale(2));
            ddet.setDescripcion("Malo");
            new ProductoServicios().crearProducto(ddet);
            Producto dd4 = new Producto("Burrito", BigDecimal.valueOf(500.00).setScale(2));
            dd4.setDescripcion("Malo");
            new ProductoServicios().crearProducto(dd4);
            Producto ddrt = new Producto("Estoy Jarto", BigDecimal.valueOf(5000.00).setScale(2));
            ddrt.setDescripcion("Malo");
            new ProductoServicios().crearProducto(ddrt);
            Producto ddtt = new Producto("Bueh", BigDecimal.valueOf(500.00).setScale(2));
            ddtt.setDescripcion("Malo");
            new ProductoServicios().crearProducto(ddtt);
            Producto ddt = new Producto("Se paso", BigDecimal.valueOf(500.00).setScale(2));
            ddt.setDescripcion("Malo");
            new ProductoServicios().crearProducto(ddt);
            Producto ddtd = new Producto("CC", BigDecimal.valueOf(500.00).setScale(2));
            ddtd.setDescripcion("Malo");
            new ProductoServicios().crearProducto(ddtd);*/
    }

    public boolean sePuedeEditarYActivate(String jefe, long producto, String user){
        boolean editar = true;
        int posi = -1;
        for (int i = 0; i < listaSseUsuario.size(); i++) {
            if (listaSseUsuario.get(i).isVaEnviar()== true && listaSseUsuario.get(i).getProducoAEnviar() == producto && listaSseUsuario.get(i).getIdJefe().equalsIgnoreCase(jefe)){
                editar = false;
                break;
            }
            if (listaSseUsuario.get(i).getIdJefe().equalsIgnoreCase(jefe) && listaSseUsuario.get(i).getUser().equalsIgnoreCase(user)){
                posi = i;
            }
        }
        if (posi != -1 && editar == true){
            listaSseUsuario.get(posi).activateVaEnviar(producto);
        }
        return editar;
    }
    public void sePuedeEditarYDesactivate(String jefe, long producto, String user){
        boolean editar = true;
        for (int i = 0; i < listaSseUsuario.size(); i++) {
            if (listaSseUsuario.get(i).getIdJefe().equalsIgnoreCase(jefe) && listaSseUsuario.get(i).getUser().equalsIgnoreCase(user)){
                listaSseUsuario.get(i).desactivateVaEnviar();
            }
        }
    }


    public Object selectActionClass(ActionJson actionJson,Claims user){
        Object aux = null;
        switch (actionJson.getTypeClass()){
            case "Producto":
                System.out.println("\n\nentro a producto");
                aux = selectActionProducto(actionJson,user);


            case "Impuesto":
                break;
            default:
                break;


        }


        return aux;
    }

    public Object selectActionProducto(ActionJson actionJson,Claims user){

        Object aux = null;
        Map<String, String> map = null;
        Producto producto = null;
        switch (actionJson.getAction()){
            case "addImpuesto":

                addNewImpuestoToOnlyProducto(actionJson.getAnotherID(),getUserJefeWithToken(user),actionJson.getId());
                aux = actionJson.getAnotherID();
            break;


            case "deleteImpuesto":

                System.out.println("\n\nentro a borrar");
                ImpuestoProductoEnVentaServicios.getInstancia().deleteImpuestoProductoEnVentaId(actionJson.getId());
                aux = actionJson.getAnotherID();

            break;

            case "addAlmacen":

                map = JsonStringToMap(actionJson.getDetail());
                System.out.println("Esto se convirtio"+map.get("proveedor")+"-"+Long.parseLong( map.get("productoAgregado")));
                Almacen almacen = new Almacen(map.get("proveedor"),Long.parseLong( map.get("productoAgregado")),Long.parseLong(map.get("productoVendido")) , Float.parseFloat(map.get("costo")), Float.parseFloat(map.get("costo")));
                addAlmacen(actionJson.getId(),almacen);
                aux = actionJson.getId();
            break;
            case "editarInfoProducto":

                map = JsonStringToMap(actionJson.getDetail());

                System.out.println("Esto se convirtio"+map.get("codigo")+"-"+ map.get("nombre"));
                producto = ProductoServicios.getInstancia().find(actionJson.getId());
                producto.setCodigo_local(map.get("codigo"));
                producto.setNombre(map.get("nombre"));
                producto.setDescripcion(map.get("descripcion"));
                producto.setCategoria(map.get("categoria"));
                ProductoServicios.getInstancia().editar(producto);

                aux = actionJson.getId();
                break;
            case "editarPrecioProducto":

                map = JsonStringToMap(actionJson.getDetail());

                System.out.println("Esto se convirtio"+map.get("codigo")+"-"+ map.get("nombre"));
                producto = ProductoServicios.getInstancia().find(actionJson.getId());
                producto.getProductoEnVenta().setPrecioVenta(Float.parseFloat(map.get("venta")));
                ProductoServicios.getInstancia().editar(producto);

                aux = actionJson.getId();
                break;
            case "editarFotoProducto":

                map = JsonStringToMap(actionJson.getDetail());
                System.out.println("Esto se convirtio"+map.get("nombreImg")+"-"+ map.get("mimetype"));
                if (map.get("base64") != null || map.get("mimetype") != null || map.get("nombreImg") != null ){
                    Foto foto= FotoServices.getInstancia().find((Long)actionJson.getAnotherID());
                    foto.setNombre(map.get("nombreImg"));
                    foto.setMimeType( map.get("mimetype"));
                    foto.setFotoBase64(map.get("base64"));
                    FotoServices.getInstancia().editar(foto);
                }else{
                    Foto foto= FotoServices.getInstancia().find((Long)actionJson.getAnotherID());
                    foto.setNombre(null);
                    foto.setMimeType(null);
                    foto.setFotoBase64(null);
                    FotoServices.getInstancia().editar(foto);

                }



                aux = actionJson.getId();
                break;



            default:
                break;


        }


        return aux;
    }
    public Map<String, String> JsonStringToMap(String json){
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = null;


        try {

            // convert JSON string to Map
            map = mapper.readValue(json, Map.class);

            // it works
            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});

            System.out.println(map);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public void addAlmacen(long id, Almacen almacen){

        Producto va = ProductoServicios.getInstancia().find(id);

        almacen = va.addAlmacen(almacen);
        ProductoServicios.getInstancia().update(va);
        if (va.getProductoEnVenta().getStock()==0){
            va.addOnlyList(almacen);
        }


    }


    public boolean borrarFactura(String idFactura){

            FacturaCliente facturaCliente = FacturaClienteServicios.getInstancia().getFacturaCliente(idFactura);
            if (facturaCliente.isFacturaGuardada()==false){
                for (FacturaClienteProductoVendido clienteProductoVendido : facturaCliente.getFacturaClienteProductoVendidos()) {
                    Producto producto = ProductoServicios.getInstancia().buscar(clienteProductoVendido.getIdProducto().getId());
                    long cantidad = clienteProductoVendido.getCantidad();
                    facturaCliente.discountPrecioProducto(clienteProductoVendido.getPrecioVenta()*cantidad);
                    facturaCliente.discountValueImpuesto(clienteProductoVendido,cantidad);
//                    facturaCliente.removeFacturaClienteProductoVendido(clienteProductoVendido.getId());

                    producto.getProductoEnVenta().addProductoStock(cantidad);
                    producto.getProductoEnVenta().discountProductoVendido(cantidad);
                    producto = (Producto) ProductoServicios.getInstancia().editar(producto);

                }
                FacturaClienteServicios.getInstancia().delete(idFactura);
                return true;
            }else{
                return false;
            }








    }
    public ProductoJSON discountProductoInFactura(String idFactura, long idProducro, long cantidad) {
        FacturaCliente facturaCliente = FacturaClienteServicios.getInstancia().getFacturaCliente(idFactura);
        FacturaClienteProductoVendido facturaClienteProductoVendido = FacturaClienteProductoVendidoServicios.getInstancia().verifyProducto(idFactura, idProducro);
        Producto producto = ProductoServicios.getInstancia().getProductoSinFoto(idProducro);
        if (facturaClienteProductoVendido != null) {
            if (facturaClienteProductoVendido.getCantidad() <= cantidad){
                cantidad = facturaClienteProductoVendido.getCantidad();
//                facturaClienteProductoVendido.discountProducto(cantidad);
                facturaCliente.discountPrecioProducto(facturaClienteProductoVendido.getPrecioVenta()*cantidad);
//            facturaCliente.discountPrecioNeto(facturaClienteProductoVendido.getImpuestoTotal()*cantidad);
//                FacturaClienteProductoVendidoServicios.getInstancia().editar(facturaClienteProductoVendido);
                facturaCliente.discountValueImpuesto(facturaClienteProductoVendido,cantidad);
                facturaCliente.removeFacturaClienteProductoVendido(facturaClienteProductoVendido.getId());
                FacturaClienteServicios.getInstancia().editar(facturaCliente);
                producto.getProductoEnVenta().addProductoStock(cantidad);
                producto.getProductoEnVenta().discountProductoVendido(cantidad);
                producto = (Producto) ProductoServicios.getInstancia().editar(producto);
                return producto.getProductoJSON(2);
            }else {
//                facturaClienteProductoVendido.discountProducto(cantidad);
                facturaCliente.discountPrecioProducto(facturaClienteProductoVendido.getPrecioVenta()*cantidad);
//            facturaCliente.discountPrecioNeto(facturaClienteProductoVendido.getImpuestoTotal()*cantidad);
//                FacturaClienteProductoVendidoServicios.getInstancia().editar(facturaClienteProductoVendido);
                for (int i = 0; i < facturaCliente.getFacturaClienteProductoVendidos().size(); i++) {
                    if(facturaCliente.getFacturaClienteProductoVendidos().get(i).getId() == facturaClienteProductoVendido.getId()){
                        facturaCliente.getFacturaClienteProductoVendidos().get(i).discountProducto(cantidad);
                        break;
                    }

                }
                facturaCliente.discountValueImpuesto(facturaClienteProductoVendido,cantidad);
                FacturaClienteServicios.getInstancia().editar(facturaCliente);
                producto.getProductoEnVenta().addProductoStock(cantidad);
                producto.getProductoEnVenta().discountProductoVendido(cantidad);
                producto = (Producto) ProductoServicios.getInstancia().editar(producto);
                return producto.getProductoJSON(2);
            }

        }else{
            return producto.getProductoJSON(2);
        }
    }

    public ProductoJSON addProductoInFactura(String idFactura, long idProducro, long cantidad) {
        FacturaCliente facturaCliente = FacturaClienteServicios.getInstancia().getFacturaCliente(idFactura);
        FacturaClienteProductoVendido facturaClienteProductoVendido = FacturaClienteProductoVendidoServicios.getInstancia().verifyProducto(idFactura, idProducro);
        Producto producto = ProductoServicios.getInstancia().buscar(idProducro);
        if (facturaClienteProductoVendido != null) {
            System.out.println("\n\nproducto"+facturaCliente.getFacturaClienteProductoVendidos().get(0).getImpuestoProducto().get(0).getNombre());


//            facturaClienteProductoVendido.addProducto(cantidad);
            facturaCliente.addPrecioProducto(facturaClienteProductoVendido.getPrecioVenta()*cantidad);
//            facturaCliente.addPrecioNeto(facturaClienteProductoVendido.getImpuestoTotal()*cantidad);
//            FacturaClienteProductoVendidoServicios.getInstancia().editar(facturaClienteProductoVendido);
            for (int i = 0; i < facturaCliente.getFacturaClienteProductoVendidos().size(); i++) {
                if(facturaCliente.getFacturaClienteProductoVendidos().get(i).getId() == facturaClienteProductoVendido.getId()){
                    facturaCliente.getFacturaClienteProductoVendidos().get(i).addProducto(cantidad);
                    break;
                }

            }
            facturaCliente.addValueImpuesto(facturaClienteProductoVendido,cantidad);
            FacturaClienteServicios.getInstancia().editar(facturaCliente);
            producto.getProductoEnVenta().discountProductoStock(cantidad);
            producto.getProductoEnVenta().addProductoVendido(cantidad);
            producto = (Producto) ProductoServicios.getInstancia().editar(producto);

            return producto.getProductoJSON(2);
        } else {
            facturaClienteProductoVendido = new FacturaClienteProductoVendido();
            facturaClienteProductoVendido.setCantidad(cantidad);
            facturaClienteProductoVendido.setPrecioCosto(producto.getProductoEnVenta().getPrecioCompra());
            facturaClienteProductoVendido.setPrecioVenta(producto.getProductoEnVenta().getPrecioVenta());
            facturaClienteProductoVendido.setImpuestoTotal(producto.getProductoEnVenta().getImpuestoTotal());
            facturaClienteProductoVendido = (FacturaClienteProductoVendido) FacturaClienteProductoVendidoServicios.getInstancia().crear(facturaClienteProductoVendido);
            facturaClienteProductoVendido.addImpuestoProducto(producto.getProductoEnVenta().getImpuestoCliente());
//            facturaClienteProductoVendido = (FacturaClienteProductoVendido) FacturaClienteProductoVendidoServicios.getInstancia().editar(facturaClienteProductoVendido);

            producto.getProductoEnVenta().discountProductoStock(cantidad);
            producto.getProductoEnVenta().addProductoVendido(cantidad);
            producto = (Producto) ProductoServicios.getInstancia().editar(producto);

            facturaClienteProductoVendido = producto.addFacturaClienteProductoVendido(facturaClienteProductoVendido);
            float pro = producto.getProductoEnVenta().getPrecioVenta()*cantidad;
            facturaCliente.addPrecioProducto(pro);
            facturaCliente.addFacturaClienteProductoVendido(facturaClienteProductoVendido);
            FacturaClienteServicios.getInstancia().editar(facturaCliente);
            return producto.getProductoJSON(2);


        }


    }

    public FacturaJson guardarFactura(GuardarFacturaJson guardarFacturaJson){
        FacturaCliente facturaCliente = FacturaClienteServicios.getInstancia().getFacturaCliente(guardarFacturaJson.getIdFactura());
        facturaCliente.setMetodoDePago(guardarFacturaJson.getMetodoDePago());
        facturaCliente.setCodigo(guardarFacturaJson.getCodigo());
        facturaCliente.setFacturaGuardada(true);
        for (FacturaClienteProductoVendido facturaClienteProductoVendido : facturaCliente.getFacturaClienteProductoVendidos()) {
            facturarOnlyProducto(facturaClienteProductoVendido.getIdProducto(), facturaClienteProductoVendido.getCantidad());
        }
        FacturaJson facturaJson = facturaCliente.getFacturaJson();
        FacturaClienteServicios.getInstancia().editar(facturaCliente);
        return facturaJson;

    }


    public void DescartarOnlyProducto(Producto producto, long cantidad) {
        long cantidadRemove = cantidad;
        boolean encontro = false;
        ProductoEnVenta productoEnVenta = ProductoEnVentaServicios.getInstancia().buscar(producto.getProductoEnVenta().getId());
//        productoEnVenta.discountProductoStock(cantidad);
        List<Almacen> lista = AlmacenServicios.getInstancia().listAlmacen(0, producto.getId());
        for (int i = lista.size() - 1; i > -1; i--) {
            if (productoEnVenta.getIdAlmacen().getIdAlmacen() == lista.get(i).getIdAlmacen() || encontro == true) {
                System.out.println("\n\nentro para cobrar" + i);
                encontro = true;
                if (i == 0) {
                    cantidadRemove = lista.get(i).agregarProductoDescartado(cantidadRemove);
                    AlmacenServicios.getInstancia().editar(lista.get(i));
                } else {
                    while (cantidadRemove != 0) {
                        if (i == -1) {
                            break;
                        }
                        System.out.println("\n\nentro para 2" + lista.get(i).getDisponible());
                        if (lista.get(i).getDisponible() >= cantidadRemove) {
                            cantidadRemove = lista.get(i).agregarProductoDescartado(cantidadRemove);
                            if (cantidadRemove == 0) {

                                Almacen almacen = (Almacen) AlmacenServicios.getInstancia().editar(lista.get(i));
                                lista.set(i, almacen);
                                if (i > 0 && lista.get(i).getDisponible() == 0) {
                                    productoEnVenta.setIdAlmacen(lista.get(i - 1));
                                    ProductoEnVentaServicios.getInstancia().editar(productoEnVenta);
                                } else {
                                    productoEnVenta.setIdAlmacen(lista.get(i));
                                    ProductoEnVentaServicios.getInstancia().editar(productoEnVenta);
                                }
                                break;
                            } else {
                                cantidadRemove = lista.get(i).agregarProductoDescartado(cantidadRemove);
                                Almacen almacen = (Almacen) AlmacenServicios.getInstancia().editar(lista.get(i));
                                lista.set(i, almacen);
                            }
                            i--;
                        } else {
                            cantidadRemove = lista.get(i).agregarProductoDescartado(cantidadRemove);
                            Almacen almacen = (Almacen) AlmacenServicios.getInstancia().editar(lista.get(i));
                            lista.set(i, almacen);
                        }
                        i--;

                    }


                }
            }
        }
    }


    public void facturarOnlyProducto(Producto producto, long cantidad) {
        long cantidadRemove = cantidad;
        boolean encontro = false;
        ProductoEnVenta productoEnVenta = ProductoEnVentaServicios.getInstancia().buscar(producto.getProductoEnVenta().getId());
//        productoEnVenta.discountProductoStock(cantidad);
        List<Almacen> lista = AlmacenServicios.getInstancia().listAlmacen(0, producto.getId());
        for (int i = lista.size() - 1; i > -1; i--) {
            if (productoEnVenta.getIdAlmacen().getIdAlmacen() == lista.get(i).getIdAlmacen() || encontro == true) {
                System.out.println("\n\nentro para cobrar" + i);
                encontro = true;
                if (i == 0) {
                    cantidadRemove = lista.get(i).agregarProductoVendido(cantidadRemove);
                    AlmacenServicios.getInstancia().editar(lista.get(i));
                } else {
                    while (cantidadRemove != 0) {
                        if (i == -1) {
                            break;
                        }
                        System.out.println("\n\nentro para 2" + lista.get(i).getDisponible());
                        if (lista.get(i).getDisponible() >= cantidadRemove) {
                            cantidadRemove = lista.get(i).agregarProductoVendido(cantidadRemove);
                            if (cantidadRemove == 0) {

                                Almacen almacen = (Almacen) AlmacenServicios.getInstancia().editar(lista.get(i));
                                lista.set(i, almacen);
                                if (i > 0 && lista.get(i).getDisponible() == 0) {
                                    productoEnVenta.setIdAlmacen(lista.get(i - 1));
                                    ProductoEnVentaServicios.getInstancia().editar(productoEnVenta);
                                } else {
                                    productoEnVenta.setIdAlmacen(lista.get(i));
                                    ProductoEnVentaServicios.getInstancia().editar(productoEnVenta);
                                }
                                break;
                            } else {
                                cantidadRemove = lista.get(i).agregarProductoVendido(cantidadRemove);
                                Almacen almacen = (Almacen) AlmacenServicios.getInstancia().editar(lista.get(i));
                                lista.set(i, almacen);
                            }
                            i--;
                        } else {
                            cantidadRemove = lista.get(i).agregarProductoVendido(cantidadRemove);
                            Almacen almacen = (Almacen) AlmacenServicios.getInstancia().editar(lista.get(i));
                            lista.set(i, almacen);
                        }
                        i--;

                    }


                }
            }
        }
    }

//agrega atributos preseleccionado a un solo producto
    public void addImpuestoToOnlyProducto(long id, String user) {
        ProductoEnVenta productoEnVenta = ProductoEnVentaServicios.getInstancia().getVentaProducto(id, user);
        List<Impuesto> lista = ImpuestoServicios.getInstancia().listaImpuestoAplicableATodos(user);
        for (Impuesto impuesto : lista) {
            productoEnVenta.addImpuesto(impuesto);
        }
        ProductoEnVentaServicios.getInstancia().editar(productoEnVenta);

    }

    public ProductoEnVenta addNewImpuestoToOnlyProducto(long id, String user, long idIMP) {
        ProductoEnVenta productoEnVenta = ProductoEnVentaServicios.getInstancia().getVentaProducto(id, user);
        Impuesto impuesto = ImpuestoServicios.getInstancia().getImpuesto(idIMP);
//        List<Impuesto> lista = ImpuestoServicios.getInstancia().listaImpuestoAplicableATodos(user);
        productoEnVenta.addImpuesto(impuesto);
        productoEnVenta = (ProductoEnVenta) ProductoEnVentaServicios.getInstancia().editar(productoEnVenta);

        return productoEnVenta;

    }

    public void addImpuestoToAllProducto(long id, String user) {
        Impuesto impuesto = ImpuestoServicios.getInstancia().getImpuesto(id);
        for (ProductoEnVenta productoEnVenta : ProductoEnVentaServicios.getInstancia().listaVentaProducto(0, user)
        ) {
//                ImpuestoProductoEnVenta impuestoProductoEnVenta = new ImpuestoProductoEnVenta(impuesto, productoEnVenta);
//                ImpuestoProductoEnVentaServicios.getInstancia().crear(impuestoProductoEnVenta);
//                impuesto.addProducto(productoEnVenta);
            productoEnVenta.addImpuesto(impuesto);
            ProductoEnVentaServicios.getInstancia().editar(productoEnVenta);
//                ProductoEnVentaServicios.getInstancia().editar(productoEnVenta);

        }
    }

    public long getTimeSessionMinute() {
        return timeSessionMinute;
    }

    public void setTimeSessionMinute(long timeSessionMinute) {
        this.timeSessionMinute = timeSessionMinute;
    }

    public Map<String, Login> getLogins() {
        return logins;
    }

    public void setLogins(Map<String, Login> logins) {
        this.logins = logins;
    }

    //    public ArrayList<Login> getLogins() {
//        return logins;
//    }
//
//    public void setLogins(ArrayList<Login> logins) {
//        this.logins = logins;
//    }

    public AES256TextEncryptor getUserEncryptor() {
        return userEncryptor;
    }

    public void setUserEncryptor(AES256TextEncryptor userEncryptor) {
        this.userEncryptor = userEncryptor;
    }

    public AES256TextEncryptor getPasswordEncryptor() {
        return passwordEncryptor;
    }

    public void setPasswordEncryptor(AES256TextEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
    }

    public Usuario RegistrarVendedor(Usuario usuario, String email, String password) throws UnsupportedEncodingException {

        usuario = new UsuarioServicios().crearUsuario(usuario);
        Vendedor vendedor = new Vendedor();
        vendedor.setEmail(email);
        vendedor.setPassword(passwordEncryptor.encrypt(password));
        Vendedor otro = usuario.addVendedor(vendedor);
        otro = (Vendedor) VendedorServicios.getInstancia().crear(otro);
        usuario = (Usuario) UsuarioServicios.getInstancia().editar(usuario);

        Usuario usuarioCliente = new Usuario();
        usuarioCliente.setNombre("Cliente Al Contado");
        usuarioCliente.setApellido("");
        usuarioCliente.setPerfil("Cliente");
        try {
            usuarioCliente.setPais(usuario.getPais());
        } catch (Exception e) {
            usuarioCliente.setPais("Republica Dominicana");

        }
        try {
            usuarioCliente.setPais(usuario.getPais());
        } catch (Exception e) {
            usuarioCliente.setPais("Republica Dominicana");

        }
        usuarioCliente.setMunicipio(usuario.getMunicipio());
        usuarioCliente.setDireccion(usuario.getDireccion());
        usuarioCliente.setTelefono(usuario.getTelefono());
        usuarioCliente.setDocumento("001");
        usuarioCliente = (Usuario) new UsuarioServicios().crear(usuarioCliente);
        usuarioCliente = UsuarioServicios.getInstancia().find(usuarioCliente.getUsuario());
        Cliente cliente = new Cliente();
        cliente.setIdClienteLocal("001");
        cliente = usuarioCliente.addCliente(cliente);
        cliente = (Cliente) ClienteServicios.getInstancia().crear(cliente);
        usuarioCliente = (Usuario) UsuarioServicios.getInstancia().editar(usuarioCliente);
        otro = VendedorServicios.getInstancia().getVendedor(otro.getIdVendedor().getUsuario());
        otro.addCliente(cliente);
        otro = (Vendedor) VendedorServicios.getInstancia().editar(otro);
        return usuario;
    }

    public void crearClienteAlContado(String userVendedor) throws UnsupportedEncodingException {

        Usuario usuario = UsuarioServicios.getInstancia().getUsuario(userVendedor);
        Vendedor otro = VendedorServicios.getInstancia().getVendedor(userVendedor);

        Usuario usuarioCliente = new Usuario();
        usuarioCliente.setNombre("Cliente Al Contado");
        usuarioCliente.setApellido("");
        usuarioCliente.setPerfil("Cliente");
        try {
            usuarioCliente.setPais(usuario.getPais());
        } catch (Exception e) {
            usuarioCliente.setPais("Republica Dominicana");

        }
        try {
            usuarioCliente.setPais(usuario.getPais());
        } catch (Exception e) {
            usuarioCliente.setPais("Republica Dominicana");

        }
        usuarioCliente.setMunicipio(usuario.getMunicipio());
        usuarioCliente.setDireccion(usuario.getDireccion());
        usuarioCliente.setTelefono(usuario.getTelefono());
        usuarioCliente.setDocumento("001");
        usuarioCliente = (Usuario) new UsuarioServicios().crear(usuarioCliente);
        usuarioCliente = UsuarioServicios.getInstancia().find(usuarioCliente.getUsuario());
        Cliente cliente = new Cliente();
        cliente.setIdClienteLocal("001");
        cliente = usuarioCliente.addCliente(cliente);
        cliente = (Cliente) ClienteServicios.getInstancia().crear(cliente);
        usuarioCliente = (Usuario) UsuarioServicios.getInstancia().editar(usuarioCliente);
        otro = VendedorServicios.getInstancia().getVendedor(otro.getIdVendedor().getUsuario());
        otro.addCliente(cliente);
        otro = (Vendedor) VendedorServicios.getInstancia().editar(otro);
    }


    public Empleado registrarEmpleado(String vendedor, Usuario usuarioEmpleado, String password, int acceso) {
        Vendedor otro = VendedorServicios.getInstancia().getVendedor(vendedor);


        usuarioEmpleado = (Usuario) new UsuarioServicios().crearUsuariEmpleado(usuarioEmpleado, acceso);
        System.out.println("\n\n\nusua" + usuarioEmpleado.getUsuario());
        usuarioEmpleado = UsuarioServicios.getInstancia().find(usuarioEmpleado.getUsuario());
        Empleado empleado = new Empleado();
        empleado.setPassword(passwordEncryptor.encrypt(password));
        empleado.setEmail(usuarioEmpleado.getEmail());
        empleado.setValidacion(true);
        empleado = usuarioEmpleado.addEmpleado(empleado);
        empleado = (Empleado) EmpleadoServicios.getInstancia().crear(empleado);
        usuarioEmpleado = (Usuario) UsuarioServicios.getInstancia().editar(usuarioEmpleado);

        otro.addEmpleado(empleado);
        otro = (Vendedor) VendedorServicios.getInstancia().editar(otro);

        return empleado;

    }

    public void addCategoria(String user, String catego) {
        Vendedor otro = null;
        otro = VendedorServicios.getInstancia().getVendedor(user);
        Categoria categoria = new Categoria();
        categoria.setDescripcion(catego);
        categoria = (Categoria) CategoriaServicios.getInstancia().crear(categoria);
        otro.addCategoria(categoria);
        otro = (Vendedor) VendedorServicios.getInstancia().editar(otro);
    }

    public Impuesto addImpuesto(String user, Impuesto impuesto) {
        Vendedor otro = null;
        otro = VendedorServicios.getInstancia().getVendedor(user);

        impuesto = (Impuesto) ImpuestoServicios.getInstancia().crear(impuesto);
        otro.addImpuesto(impuesto);
        otro = (Vendedor) VendedorServicios.getInstancia().editar(otro);
        return impuesto;
    }

    public String tipoUsuario(String user) {
        Usuario aux = UsuarioServicios.getInstancia().getUsuario(user);
        return aux.getPerfil();
    }


    public List<Politica> getListaPolitica(String perfil, int acceso, Usuario use) {
        List<Politica> politicaList = new ArrayList<Politica>();
        Politica aux = null;
        Politica aux1 = null;

        Politica aux3 = null;
        Politica aux4 = null;
        Politica aux5 = null;
        Politica aux6 = null;
        Politica aux7 = null;
        Politica aux8 = null;
        Politica aux9 = null;
        Politica aux10 = null;

        switch (perfil) {
            case "Admin":
                aux = new Politica("inventarioAdd", true);
                aux = (Politica) PoliticaServicios.getInstancia().crear(aux);
                politicaList.add(aux);
                aux1 = new Politica("inventarioEdit", true);
                aux1 = (Politica) PoliticaServicios.getInstancia().crear(aux1);
                politicaList.add(aux1);
                aux3 = new Politica("clienteAdd", true);
                aux3 = (Politica) PoliticaServicios.getInstancia().crear(aux3);
                politicaList.add(aux3);
                aux4 = new Politica("clienteEdit", true);
                aux4 = (Politica) PoliticaServicios.getInstancia().crear(aux4);
                politicaList.add(aux4);
                aux5 = new Politica("impuestoAdd", true);
                aux5 = (Politica) PoliticaServicios.getInstancia().crear(aux5);
                politicaList.add(aux5);
                aux6 = new Politica("impuestoEdit", true);
                aux6 = (Politica) PoliticaServicios.getInstancia().crear(aux6);
                politicaList.add(aux6);
                aux7 = new Politica("categoriaAdd", true);
                aux7 = (Politica) PoliticaServicios.getInstancia().crear(aux7);
                politicaList.add(aux7);
                aux8 = new Politica("categoriaEdit", true);
                aux8 = (Politica) PoliticaServicios.getInstancia().crear(aux8);
                politicaList.add(aux8);
                aux9 = new Politica("empleadoAdd", true);
                aux9 = (Politica) PoliticaServicios.getInstancia().crear(aux9);
                politicaList.add(aux9);
                aux10 = new Politica("empleadoEdit", true);
                aux10 = (Politica) PoliticaServicios.getInstancia().crear(aux10);
                politicaList.add(aux10);


                break;

            case "Empleado":
                switch (acceso) {
                    case 2:
                        aux = new Politica("inventarioAdd", false);
                        aux = (Politica) PoliticaServicios.getInstancia().crear(aux);
                        politicaList.add(aux);
                        aux1 = new Politica("inventarioEdit", false);
                        aux1 = (Politica) PoliticaServicios.getInstancia().crear(aux1);
                        politicaList.add(aux1);
                        aux3 = new Politica("clienteAdd", false);
                        aux3 = (Politica) PoliticaServicios.getInstancia().crear(aux3);
                        politicaList.add(aux3);
                        aux4 = new Politica("clienteEdit", false);
                        aux4 = (Politica) PoliticaServicios.getInstancia().crear(aux4);
                        politicaList.add(aux4);
                        aux5 = new Politica("impuestoAdd", false);
                        aux5 = (Politica) PoliticaServicios.getInstancia().crear(aux5);
                        politicaList.add(aux5);
                        aux6 = new Politica("impuestoEdit", false);
                        aux6 = (Politica) PoliticaServicios.getInstancia().crear(aux6);
                        politicaList.add(aux6);
                        aux7 = new Politica("categoriaAdd", false);
                        aux7 = (Politica) PoliticaServicios.getInstancia().crear(aux7);
                        politicaList.add(aux7);
                        aux8 = new Politica("categoriaEdit", false);
                        aux8 = (Politica) PoliticaServicios.getInstancia().crear(aux8);
                        politicaList.add(aux8);
                        aux9 = new Politica("empleadoAdd", false);
                        aux9 = (Politica) PoliticaServicios.getInstancia().crear(aux9);
                        politicaList.add(aux9);
                        aux10 = new Politica("empleadoEdit", false);
                        aux10 = (Politica) PoliticaServicios.getInstancia().crear(aux10);
                        politicaList.add(aux10);
                        break;
                    case 1:
                        aux = new Politica("inventarioAdd", true);
                        aux = (Politica) PoliticaServicios.getInstancia().crear(aux);
                        politicaList.add(aux);
                        aux1 = new Politica("inventarioEdit", true);
                        aux1 = (Politica) PoliticaServicios.getInstancia().crear(aux1);
                        politicaList.add(aux1);
                        aux3 = new Politica("clienteAdd", true);
                        aux3 = (Politica) PoliticaServicios.getInstancia().crear(aux3);
                        politicaList.add(aux3);
                        aux4 = new Politica("clienteEdit", true);
                        aux4 = (Politica) PoliticaServicios.getInstancia().crear(aux4);
                        politicaList.add(aux4);
                        aux5 = new Politica("impuestoAdd", true);
                        aux5 = (Politica) PoliticaServicios.getInstancia().crear(aux5);
                        politicaList.add(aux5);
                        aux6 = new Politica("impuestoEdit", true);
                        aux6 = (Politica) PoliticaServicios.getInstancia().crear(aux6);
                        politicaList.add(aux6);
                        aux7 = new Politica("categoriaAdd", true);
                        aux7 = (Politica) PoliticaServicios.getInstancia().crear(aux7);
                        politicaList.add(aux7);
                        aux8 = new Politica("categoriaEdit", true);
                        aux8 = (Politica) PoliticaServicios.getInstancia().crear(aux8);
                        politicaList.add(aux8);
                        aux9 = new Politica("empleadoAdd", true);
                        aux9 = (Politica) PoliticaServicios.getInstancia().crear(aux9);
                        politicaList.add(aux9);
                        aux10 = new Politica("empleadoEdit", true);
                        aux10 = (Politica) PoliticaServicios.getInstancia().crear(aux10);
                        politicaList.add(aux10);
                        break;
                    default:
                        break;
                }


                break;
            case "Vendedor":
                aux = new Politica("inventarioAdd", true);
                aux = (Politica) PoliticaServicios.getInstancia().crear(aux);
                politicaList.add(aux);
                aux1 = new Politica("inventarioEdit", true);
                aux1 = (Politica) PoliticaServicios.getInstancia().crear(aux1);
                politicaList.add(aux1);
                aux3 = new Politica("clienteAdd", true);
                aux3 = (Politica) PoliticaServicios.getInstancia().crear(aux3);
                politicaList.add(aux3);
                aux4 = new Politica("clienteEdit", true);
                aux4 = (Politica) PoliticaServicios.getInstancia().crear(aux4);
                politicaList.add(aux4);
                aux5 = new Politica("impuestoAdd", true);
                aux5 = (Politica) PoliticaServicios.getInstancia().crear(aux5);
                politicaList.add(aux5);
                aux6 = new Politica("impuestoEdit", true);
                aux6 = (Politica) PoliticaServicios.getInstancia().crear(aux6);
                politicaList.add(aux6);
                aux7 = new Politica("categoriaAdd", true);
                aux7 = (Politica) PoliticaServicios.getInstancia().crear(aux7);
                politicaList.add(aux7);
                aux8 = new Politica("categoriaEdit", true);
                aux8 = (Politica) PoliticaServicios.getInstancia().crear(aux8);
                politicaList.add(aux8);
                aux9 = new Politica("empleadoAdd", true);
                aux9 = (Politica) PoliticaServicios.getInstancia().crear(aux9);
                politicaList.add(aux9);
                aux10 = new Politica("empleadoEdit", true);
                aux10 = (Politica) PoliticaServicios.getInstancia().crear(aux10);
                politicaList.add(aux10);

                break;
            default:

                break;
        }
        return politicaList;
    }

    public String getUserJefeWithToken(Claims claims) {
//        Usuario aux = UsuarioServicios.getInstancia().getUsuario(user);
        String se = null;
//        System.out.println("\n\nPerfil" + claims.getAudience() + "-" + claims.getIssuer());

        switch (claims.getAudience()) {
            case "Admin":

                se = claims.getIssuer();
                break;

            case "Empleado":

                se = claims.getIssuer();
                break;
            case "Vendedor":
                se = claims.getIssuer();
                break;
            default:
                se = null;
                break;
        }
        return se;
    }

    public Map<String, Object> getUserJefe(String user) {
        Map<String, Object> map = new HashMap<String, Object>();
        Usuario aux = UsuarioServicios.getInstancia().getUsuario(user);
        String se = null;
        switch (aux.getPerfil()) {
            case "Admin":
                se = user;
                map.put("user",se);
                map.put("direccion",aux.getDireccion());
                map.put("telefono",aux.getTelefono());
                map.put("compania",aux.getNombreCompania());
                if (aux.getMunicipio() != null && aux.getPais()== null){
                    map.put("ciudadPais",aux.getMunicipio());
                }else if (aux.getMunicipio() == null && aux.getPais()!= null){
                    map.put("ciudadPais",aux.getPais());
                }else{
                    map.put("ciudadPais",aux.getMunicipio() + ", "+aux.getPais());
                }


                break;

            case "Empleado":

                se = EmpleadoServicios.getInstancia().getJefe(aux.getUsuario());
                map.put("user",se);
                map.put("direccion",aux.getDireccion());
                map.put("telefono",aux.getTelefono());
                map.put("compania",aux.getNombreCompania());
                if (aux.getMunicipio() != null && aux.getPais()== null){
                    map.put("ciudadPais",aux.getMunicipio());
                }else if (aux.getMunicipio() == null && aux.getPais()!= null){
                    map.put("ciudadPais",aux.getPais());
                }else{
                    map.put("ciudadPais",aux.getMunicipio() + ", "+aux.getPais());
                }
                break;
            case "Vendedor":
                se = user;
                map.put("user",se);
                map.put("direccion",aux.getDireccion());
                map.put("telefono",aux.getTelefono());
                map.put("compania",aux.getNombreCompania());
                map.put("ciudadPais",aux.getMunicipio() + ", "+aux.getPais());
                break;
            default:
                se = user;
                break;
        }
        return map;
    }


    public List<Producto> listaProductoOrdenada(String user) {
        Usuario aux = UsuarioServicios.getInstancia().getUsuario(user);
        List<Producto> productoList = null;
        switch (aux.getPerfil()) {
            case "Admin":


                productoList = new ProductoServicios().listaProducto(0, user);
                break;

            case "Empleado":

                productoList = new ProductoServicios().listaProducto(0, EmpleadoServicios.getInstancia().getJefe(aux.getUsuario()));
                break;
            case "Vendedor":
                productoList = new ProductoServicios().listaProducto(0, user);
                break;
            default:

                break;
        }
        return productoList;
    }


    public boolean send_correo_online(String correo, String mensaje, String asunto) {
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");


        Session sesion = Session.getDefaultInstance(propiedad);
        String correoEnvia = "csuite.prueba@gmail.com";
        String contrasena = "castillo30";
        String receptor = correo;


        Message mail = new MimeMessage(sesion);
        try {
            mail.setFrom(new InternetAddress(correoEnvia));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
            mail.setSubject(asunto);

            MimeMultipart multipart = new MimeMultipart("related");


            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(mensaje, "text/html");
            // add it
            multipart.addBodyPart(messageBodyPart);

            // second part (the image)
            if (asunto.equalsIgnoreCase("Error del servidor")) {
                messageBodyPart = new MimeBodyPart();
                DataSource fds = new FileDataSource(
                        "/home/ubuntu/CSUITE/build/libs/error.txt");
                messageBodyPart.setDataHandler(new DataHandler(fds));
                messageBodyPart.setFileName("error.txt");

                multipart.addBodyPart(messageBodyPart);

            }

            // put everything together
            mail.setContent(multipart);
//            mail.setContent(mensaje,"text/html");


            Transport transportar = sesion.getTransport("smtp");
            transportar.connect(correoEnvia, contrasena);
            transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transportar.close();

            return true;

        } catch (AddressException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }


    public String verificar_user(String user, String password) {


        Usuario aux = UsuarioServicios.getInstancia().getUsuario(user);
        switch (aux.getPerfil()) {
            case "Vendedor":

                System.out.println("Esto sale" + aux.getVendedor().getPassword());
                if (passwordEncryptor.decrypt(aux.getVendedor().getPassword()).equalsIgnoreCase(password)) {
                    return "Vendedor";
                }

                break;

            case "Admin":

                System.out.println("Esto sale" + aux.getVendedor().getPassword());
                if (passwordEncryptor.decrypt(aux.getVendedor().getPassword()).equalsIgnoreCase(password)) {
                    return "Admin";
                }

                break;
            case "Empleado":

                System.out.println("Esto sale" + aux.getEmpleado().getPassword());
                if (passwordEncryptor.decrypt(aux.getEmpleado().getPassword()).equals(password)) {
                    return "Empleado";
                }

                break;
            default:
                break;


        }


        return null;
    }

    public void agregar_Producto(Producto produ) {
        //int id = new ProductoServicios().getIdentityMax();
        //id+=1;
        new ProductoServicios().crearProducto(produ);

    }

//    public int cant_product(Long id) {
//        int cant = -1;
//        CarroCompra aux = carroCompraServicios.getCarroCompra(id);
//        try {
//
//            if (aux != null) {
//                cant = 0;
//                for (CarroCompra_Producto pp : aux.getListaProductos()
//                ) {
//                    cant += pp.getCantidad();
//                }
//
//                return cant;
//
//            }
//
//            return cant;
//        } catch (NullPointerException E) {
//            return -1;
//        }
//
//
//    }
//
//    public void procesar_compra(Long id_cliente, String nombre) {
//        //int id = ventasProductos.size();
//        //id+=1;
//        Calendar fecha = new GregorianCalendar();
//        Date utilDate = fecha.getTime();
//        VentasProductos ven = new VentasProductos(nombre);
//        ven.setListaProducto(carroCompraServicios.getCarroCompra(id_cliente).getListaProductos_PreComprado());
//        ventasProductosServicios.crearVentasProductos(ven);
//    }
//
//    public void agregar_producto_a_cliente(long id_cliente, int id_producto, int cant) {
//        int posi = devuelve_cliente(id_cliente);
//        CarroCompra auc = carroCompraServicios.getCarroCompra(id_cliente);
//
//        CarroCompra_Producto auxx = auc.add_producto(id_producto, cant);
//        //if (new CarroCompra_ProductoServicios().getCarroCompra_Producto(auxx.getId())!=null){
//        // new CarroCompra_ProductoServicios().getEditar(auxx);
//        // }else {
//
//        //auxx.setCarroCompra(auc);
//        //new CarroCompra_ProductoServicios().getAgregar(auxx);
//        carroCompraServicios.updateCarroCompraProducto(auc);
//        // }
//        //
//    }
//
//    public int devuelve_cliente(long id) {
//        int cant = -1;
//        if (carroCompraServicios.getCarroCompra(id).getId() == id) {
//            cant = (int) id;
//            return cant;
//        }
//
//        return cant;
//    }
//
//    public boolean borrar_producto_carro(long id_cliente, int id_producto) {
//        CarroCompra aux = carroCompraServicios.getCarroCompra(id_cliente);
//        for (CarroCompra_Producto pro : aux.getListaProductos()
//        ) {
//            if (pro.getProducto().getId() == id_producto) {
//                ;
//                carroCompraServicios.borrarProductoCarroCompra(id_cliente, pro.getProducto().getId());
//                ;
//
//                return true;
//
//            }
//        }
//        return false;
//    }
//
//    public boolean borrar_todo_carro(long id_cliente) {
//        new CarroCompraServicios().borrarTodoProductoCarroCompra(id_cliente);
//        return true;
//
//    }
//
//    public int borrar_producto(int id) {
//        int cant = -1;
//        boolean borro = carroCompra_productoServicios.getBorrarProductoATodoLosClientes(id);
//        productoServicios.borrarProducto(id);
//        if (borro == true) {
//            cant = id;
//            return cant;
//
//        }
//
//        return cant;
//    }
//
//    public Producto return_Producto(long id) {
//
//        return productoServicios.getProducto(id);
//
//    }

//    public void change_Producto(int id, String nombre, String precio) {
//        Producto aux = new ProductoServicios().getProducto(id);
//        aux.setNombre(nombre);
//        aux.setPrecio(BigDecimal.valueOf(Double.parseDouble(precio)).setScale(2));
//        new ProductoServicios().updateProducto(aux);
//
//    }

//    public static ProductoServicios getProductoServicios() {
//        return productoServicios;
//    }
//
//    public static void setProductoServicios(ProductoServicios productoServicios) {
//        Mercado.productoServicios = productoServicios;
//    }

//    public static UsuarioServicios getUsuarioServicios() {
//        return usuarioServicios;
//    }
//
//    public static void setUsuarioServicios(UsuarioServicios usuarioServicios) {
//        Mercado.usuarioServicios = usuarioServicios;
//    }

//    public static VentasProductosServicios getVentasProductosServicios() {
//        return ventasProductosServicios;
//    }
//
//    public static void setVentasProductosServicios(VentasProductosServicios ventasProductosServicios) {
//        Mercado.ventasProductosServicios = ventasProductosServicios;
//    }
//
//    public static CarroCompraServicios getCarroCompraServicios() {
//        return carroCompraServicios;
//    }
//
//    public static void setCarroCompraServicios(CarroCompraServicios carroCompraServicios) {
//        Mercado.carroCompraServicios = carroCompraServicios;
//    }
}
