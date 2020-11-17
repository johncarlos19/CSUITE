package csuite.mvc.entidades;

import  csuite.mvc.entidades.*;
import csuite.mvc.servicios.*;
import org.jasypt.util.text.AES256TextEncryptor;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Mercado {
//    private static ProductoServicios productoServicios = new ProductoServicios();
//    private static UsuarioServicios usuarioServicios = new UsuarioServicios();
//    private static VentasProductosServicios ventasProductosServicios = new VentasProductosServicios();
//    private static CarroCompraServicios carroCompraServicios = new CarroCompraServicios();
//    private static CarroCompra_ProductoServicios carroCompra_productoServicios = new CarroCompra_ProductoServicios();
    private static Mercado mercado;
    AES256TextEncryptor userEncryptor = new AES256TextEncryptor();
    AES256TextEncryptor passwordEncryptor = new AES256TextEncryptor();

    public Mercado() {
    }

    public static Mercado getInstance() {
        if (mercado == null)
            mercado = new Mercado();
        return mercado;
    }

    public void loadDataBase() {
        userEncryptor.setPassword("admin");
        passwordEncryptor.setPassword("admin");


        try {
            if (verificar_user("admin", "admin") !=null) {
                Usuario usuario = new Usuario("admin", "Admin");
                usuario.setEmail("admin@cashsuite.com");
                usuario.setPerfil("Admin");
                Usuario aux = new UsuarioServicios().crearUsuario(usuario);

                Producto producto = new Producto();
                producto.setNombre("Huevo");
                producto.setDescripcion("Son bueno");
                producto.setCodigo_local("144533");
                Producto producto1 = new Producto();
                producto1.setNombre("Pan");
                producto1.setDescripcion("Son malo");
                producto1.setCodigo_local("1445833");


                Vendedor vendedor = new Vendedor();
                vendedor.setEmail("admin@cashsuite.com");
                vendedor.setPassword(passwordEncryptor.encrypt("admin"));
                Vendedor otro = aux.addVendedor(vendedor);
                VendedorServicios.getInstancia().crear(otro);
                UsuarioServicios.getInstancia().editar(aux);

                producto.CrearProductoVenta();
                producto1.CrearProductoVenta();
                producto = ProductoServicios.getInstancia().crearProducto(producto);
                producto1 = ProductoServicios.getInstancia().crearProducto(producto1);
                otro.addProducto(producto);
                otro.addProducto(producto1);
//            VendedorServicios.getInstancia().editar(otro);
                Producto va = ((Vendedor) VendedorServicios.getInstancia().editar(otro)).getProductoList().get(0);

                Almacen almacen = new Almacen("Don Lindo",10,10,200,500);
                va.addAlmacen(almacen);
                va = (Producto) new ProductoServicios().editar(va);
                almacen = new Almacen("Endy",10,1,300,600);
                va.addAlmacen(almacen);
                va = (Producto) new ProductoServicios().editar(va);




            }
        } catch (NullPointerException e) {
            Usuario usuario = new Usuario("admin", "Admin");
            usuario.setEmail("admin@cashsuite.com");
            usuario.setPerfil("Admin");
            Usuario aux = new UsuarioServicios().crearUsuario(usuario);

            Producto producto = new Producto();
            producto.setNombre("Huevo");
            producto.setDescripcion("Son bueno");
            producto.setCodigo_local("144533");
            Producto producto1 = new Producto();
            producto1.setNombre("Pan");
            producto1.setDescripcion("Son malo");
            producto1.setCodigo_local("1445833");


            Vendedor vendedor = new Vendedor();
            vendedor.setEmail("admin@cashsuite.com");
            vendedor.setPassword(passwordEncryptor.encrypt("admin"));
            Vendedor otro = aux.addVendedor(vendedor);
            VendedorServicios.getInstancia().crear(otro);
            UsuarioServicios.getInstancia().editar(aux);

            producto.CrearProductoVenta();
            producto1.CrearProductoVenta();
            producto = ProductoServicios.getInstancia().crearProducto(producto);
            producto1 = ProductoServicios.getInstancia().crearProducto(producto1);
            otro.addProducto(producto);
            otro.addProducto(producto1);

            Producto va = ((Vendedor) VendedorServicios.getInstancia().editar(otro)).getProductoList().get(0);

            Almacen almacen = new Almacen("Don Lindo",10,10,200,500);
            va.addAlmacen(almacen);
            va = (Producto) new ProductoServicios().editar(va);

            va = (Producto) new ProductoServicios().find(va.getId());
            Almacen almacen1 = new Almacen("Endy",10,1,300,600);
            almacen1 = va.addAlmacen(almacen1);
             new ProductoServicios().update(va);

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
            usuarioCliente.setPerfil("Cliente");
            usuarioCliente.setPais("Republica Dominicana");
            usuarioCliente.setMunicipio("Moca");
            usuarioCliente.setDireccion("Paso De Moca");
            usuarioCliente.setTelefono("8095557456");
            usuarioCliente = (Usuario) new UsuarioServicios().crear(usuarioCliente);
            System.out.println("\n\n\nusua"+usuarioCliente.getUsuario());
            usuarioCliente = UsuarioServicios.getInstancia().find(usuarioCliente.getUsuario());
            Cliente cliente = new Cliente();
            cliente.setIdClienteLocal("sdsd");
            cliente = usuarioCliente.addCliente(cliente);
            cliente = (Cliente) ClienteServicios.getInstancia().crear(cliente);
            usuarioCliente = (Usuario) UsuarioServicios.getInstancia().editar(usuarioCliente);

            otro.addCliente(cliente);
            otro = (Vendedor) VendedorServicios.getInstancia().editar(otro);

            Impuesto impuesto = new Impuesto("18% De ley","Porciento",18);
            impuesto = (Impuesto) ImpuestoServicios.getInstancia().crear(impuesto);
            otro.addImpuesto(impuesto);
            otro = (Vendedor) VendedorServicios.getInstancia().editar(otro);











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
            usuarioCliente1.setMunicipio("Moca");
            usuarioCliente1.setDireccion("Paso 11De Moca");
            usuarioCliente1.setTelefono("809525456");
            usuarioCliente1 = (Usuario) new UsuarioServicios().crear(usuarioCliente1);
            System.out.println("\n\n\nusua"+usuarioCliente1.getUsuario());
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
            registrarEmpleado("admin",empleado,"admin");

            System.out.println("\n\nEl dueno del empleado:"+EmpleadoServicios.getInstancia().getJefe("lalameme"));






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


    }

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

    public Empleado registrarEmpleado(String vendedor, Usuario usuarioEmpleado, String password){
        Vendedor otro = VendedorServicios.getInstancia().getVendedor(vendedor);


        usuarioEmpleado = (Usuario) new UsuarioServicios().crearUsuario(usuarioEmpleado);
        System.out.println("\n\n\nusua"+usuarioEmpleado.getUsuario());
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

    public void addCategoria(String user, String catego){
        Vendedor otro = null;
        otro = VendedorServicios.getInstancia().getVendedor(user);
        Categoria categoria = new Categoria();
        categoria.setDescripcion(catego);
        categoria = (Categoria) CategoriaServicios.getInstancia().crear(categoria);
        otro.addCategoria(categoria);
        otro = (Vendedor) VendedorServicios.getInstancia().editar(otro);
    }
    public void addImpuesto(String user, Impuesto impuesto){
        Vendedor otro = null;
        otro = VendedorServicios.getInstancia().getVendedor(user);

        impuesto = (Impuesto) ImpuestoServicios.getInstancia().crear(impuesto);
        otro.addImpuesto(impuesto);
        otro = (Vendedor) VendedorServicios.getInstancia().editar(otro);
    }

    public String tipoUsuario(String user){
        Usuario aux = UsuarioServicios.getInstancia().getUsuario(user);
        return aux.getPerfil();
    }


    public String getUserJefe(String user){
        Usuario aux = UsuarioServicios.getInstancia().getUsuario(user);
        String se = null;
        switch (aux.getPerfil()){
            case "Admin":

                se =  user;
                break;

            case "Empleado":

                se =  EmpleadoServicios.getInstancia().getJefe(aux.getUsuario());
                break;
            case "Vendedor":
                se =  user;
                break;
            default:
se  = user;
                break;
        }
        return se;
    }



    public List<Producto> listaProductoOrdenada(String user){
        Usuario aux = UsuarioServicios.getInstancia().getUsuario(user);
        List<Producto> productoList = null;
        switch (aux.getPerfil()){
            case "Admin":


                productoList = new ProductoServicios().listaProducto(1,user);
            break;

            case "Empleado":

                productoList =  new ProductoServicios().listaProducto(1,EmpleadoServicios.getInstancia().getJefe(aux.getUsuario()));
                break;
            case "Vendedor":
                productoList =  new ProductoServicios().listaProducto(1,user);
                break;
            default:

                break;
        }
        return productoList;
    }


    public String verificar_user(String user, String password) {
        Usuario aux = new UsuarioServicios().getUsuario(user);
        try {
            if(aux.getPerfil()==null){
                return null;
            }
        }catch (NullPointerException E){
            return null;
        }


        switch (aux.getPerfil()){
            case "Vendedor":

                    System.out.println("Esto sale"+aux.getVendedor().getPassword());
                    if (passwordEncryptor.decrypt(aux.getVendedor().getPassword()).equalsIgnoreCase(password)) {
                        return "Vendedor";
                    }

                break;

            case "Admin":

                System.out.println("Esto sale"+aux.getVendedor().getPassword());
                if (passwordEncryptor.decrypt(aux.getVendedor().getPassword()).equalsIgnoreCase(password)) {
                    return "Admin";
                }

                break;
            case "Empleado":

                System.out.println("Esto sale"+aux.getEmpleado().getPassword());
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
