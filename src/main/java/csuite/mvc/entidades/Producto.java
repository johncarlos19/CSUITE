package csuite.mvc.entidades;

import csuite.mvc.jsonObject.AlmacenJson;
import csuite.mvc.jsonObject.ProductoJSON;
import csuite.mvc.servicios.AlmacenServicios;
import csuite.mvc.servicios.ProductoCompradoServicios;
import csuite.mvc.servicios.ProductoEnVentaServicios;
import csuite.mvc.servicios.ProductoServicios;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

@Entity
public class Producto implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String nombre;

//    private BigDecimal precio;

    private String descripcion;
    private String codigo_local;
    private long cantProductoVendido;
    private boolean disponible = true;
    private String categoria;


    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL ,  orphanRemoval = true)
    @JoinColumn(name="idProducto")
    private List<Almacen> almacenList = new ArrayList<Almacen>();

//    @OneToMany(mappedBy = "carroCompra", cascade = CascadeType.ALL,
//            orphanRemoval = true)
//    private List<CarroCompra_Producto> carroCompra_productos = new ArrayList<CarroCompra_Producto>();


//    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL , orphanRemoval = true)
//    @JoinColumn(name="idProducto")
//    private List<Foto> fotos = new ArrayList<Foto>();


//    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL , orphanRemoval = true)
//    @JoinColumn(name="idProducto")
//    private Set<Comentario> comentarios = new HashSet<Comentario>();
//    @ManyToMany(cascade = { CascadeType.ALL }, mappedBy = "productos")
//    private List<Categoria> categorias = new ArrayList<Categoria>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idProducto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FacturaClienteProductoVendido>  facturaClienteProductoVendidos= new ArrayList<FacturaClienteProductoVendido>();


//    @ManyToOne
//    @JoinColumn( nullable=false, name="idVendedor")
//    private Vendedor idVendedor;

    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL , orphanRemoval = true,mappedBy = "idProducto")
    private ProductoEnVenta productoEnVenta;

    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL , orphanRemoval = true,mappedBy = "idProducto")
    private Foto foto;


//    private String nombreFoto;
//    private String mimeType;
//    @Lob
//    private String fotoBase64;

//        public List<Foto> getFotos() {
//        return fotos;
//    }
//
//
//    public void setFotos(List<Foto> fotos) {
//        this.fotos = fotos;
//    }
//


    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public void addFoto(Foto foto1){
            foto1.setIdProducto(this);
            this.foto = foto1;

    }

    public Producto(){

    }


    public Producto(String nombre, BigDecimal precio) {
        this.nombre = nombre;
//        this.precio = precio;
    }
    public String getPrecioLista(){
        float descuentoPorciento = 0;
        float impu = 0;
        float precioneto = productoEnVenta.getPrecioVenta();
        for (ImpuestoProductoEnVenta impuesto : productoEnVenta.getImpuestoProductoEnVentas()
        ){
            System.out.println("\n\n\nImpuesto "+impuesto.getIdImpuesto().getNombre());
            descuentoPorciento += impuesto.getIdImpuesto().getDescuento((double) productoEnVenta.getPrecioVenta());
            impu += impuesto.getIdImpuesto().getImpuesto((double) productoEnVenta.getPrecioVenta());
            precioneto += impuesto.getIdImpuesto().getPrecioNeto((double) productoEnVenta.getPrecioVenta());

        }
        double tot = 0;

        BigDecimal total = BigDecimal.valueOf(productoEnVenta.getPrecioVenta() + impu).setScale(2,BigDecimal.ROUND_HALF_UP);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        return numberFormat.format(total);
    }




    public ProductoJSON getProductoJSON(int posi){
        float descuentoPorciento = 0;
        float impu = 0;
        float precioneto = productoEnVenta.getPrecioVenta();
        for (ImpuestoProductoEnVenta impuesto : productoEnVenta.getImpuestoProductoEnVentas()
        ){
            descuentoPorciento += impuesto.getIdImpuesto().getDescuento((double) productoEnVenta.getPrecioVenta());
            impu += impuesto.getIdImpuesto().getImpuesto((double) productoEnVenta.getPrecioVenta());
            precioneto += impuesto.getIdImpuesto().getPrecioNeto((double) productoEnVenta.getPrecioVenta());

        }
        ProductoJSON productoJSON = null;
        switch (posi){
            case 1:
                productoJSON = new ProductoJSON(
                        this.getId(),
                        this.getNombre(),
                        this.getDescripcion(),
                        this.getCodigo_local(),
                        this.getCantProductoVendido(),
                        this.isDisponible(),
                        this.categoria,
                        productoEnVenta.getStock(),
                        productoEnVenta.getPrecioVenta(),
                        productoEnVenta.getPrecioCompra(),
                        productoEnVenta.getCantMaxPorVenta(),
                        descuentoPorciento,
                        impu
                        ,precioneto,
                        this.foto.getNombre(),
                        this.foto.getMimeType(),
                        this.foto.getFotoBase64()
                );
                break;
            case 2:
                productoJSON = new ProductoJSON(
                        this.getId(),
                        this.getNombre(),
                        this.getDescripcion(),
                        this.getCodigo_local(),
                        this.getCantProductoVendido(),
                        this.isDisponible(),
                        this.categoria,
                        productoEnVenta.getStock(),
                        productoEnVenta.getPrecioVenta(),
                        productoEnVenta.getPrecioCompra(),
                        productoEnVenta.getCantMaxPorVenta(),
                        descuentoPorciento,
                        impu
                        ,precioneto,
                        null,
                        null,
                        null
                );
                 break;
            default:
                break;
        }

        productoJSON.setImpuestoClientes(productoEnVenta.getImpuestoCliente());
        return productoJSON;
    }

    public FacturaClienteProductoVendido addFacturaClienteProductoVendido(FacturaClienteProductoVendido facturaClienteProductoVendido){
        facturaClienteProductoVendido.setIdProducto(this);
        return facturaClienteProductoVendido;
    }


    public void CrearProductoVenta(){
        ProductoEnVenta ux = new ProductoEnVenta();
        ux.setIdProducto(this);
        ux.setStock(0);
        this.productoEnVenta = ux;

    }
    public boolean addOnlyList(Almacen almacen){
        System.out.println("\n\n\n\nproooooooo"+productoEnVenta.getIdAlmacen().getFechaRegistro());

        productoEnVenta.setIdAlmacen(null);

        productoEnVenta = (ProductoEnVenta) new ProductoEnVentaServicios().editar(productoEnVenta);
        productoEnVenta.setCantMaxPorVenta(-1);
        productoEnVenta.setPrecioCompra(almacen.getCosto());
//        productoEnVenta.setPrecioVenta(almacen.getPrecioVentaFutura());
//        productoEnVenta.setDescuentoPorciento(0);
        productoEnVenta.addProductoStock(almacen.getProductoAgregado());

        almacen.setProductoEnVenta(productoEnVenta);
        productoEnVenta.setIdAlmacen(almacen);
//        almacen= (Almacen) AlmacenServicios.getInstancia().editar(almacen);

        productoEnVenta = (ProductoEnVenta) new ProductoEnVentaServicios().editar(productoEnVenta);

        return true;
    }


        public Almacen addAlmacen(Almacen almacen){

        if (AlmacenServicios.getInstancia().listAlmacen(0,this.id).size() == 0){



            productoEnVenta.setCantMaxPorVenta(-1);
            productoEnVenta.setPrecioCompra(almacen.getCosto());
            productoEnVenta.setPrecioVenta(almacen.getPrecioVentaFutura());
//            productoEnVenta.setDescuentoPorciento(0);
            productoEnVenta.setStock(almacen.getDisponible());

            almacen= (Almacen) AlmacenServicios.getInstancia().crear(almacen);
            productoEnVenta.setIdAlmacen(almacen);
            almacen.setProductoEnVenta(productoEnVenta);
            this.almacenList.add(almacen);

            return almacen;
        }else if(productoEnVenta.getStock()!=0){

            almacen= (Almacen) AlmacenServicios.getInstancia().crear(almacen);
            productoEnVenta.addProductoStock(almacen.getDisponible());
//            almacen= (Almacen) AlmacenServicios.getInstancia().createAndReturnObjectWithUniqueId(almacen);

            this.almacenList.add(almacen);
            return almacen;

       }else if(productoEnVenta.getStock()==0){


            almacen= (Almacen) AlmacenServicios.getInstancia().crear(almacen);
            almacenList.add(almacen);
            return almacen;
        }
return null;

    }

    public List<FacturaClienteProductoVendido> getFacturaClienteProductoVendidos() {
        return facturaClienteProductoVendidos;
    }

    public void setFacturaClienteProductoVendidos(List<FacturaClienteProductoVendido> facturaClienteProductoVendidos) {
        this.facturaClienteProductoVendidos = facturaClienteProductoVendidos;
    }

//    public List<Categoria> getCategorias() {
//        return categorias;
//    }
//
//    public void setCategorias(List<Categoria> categorias) {
//        this.categorias = categorias;
//    }

    public String getImgComplete() {
        if (foto.getMimeType() == null){
            return "../dashboardPlantilla/img/productos/default/anonymous.png";
        }else{
            return foto.getFotoBase64();
        }


    }
    public String getStock() {
        if(productoEnVenta.getStock()  == 0){
            return  "<span style='font-size: large;' class=' label label-default'>"+productoEnVenta.getStock() +"</span>";
        }else if(productoEnVenta.getStock() <=10){
            return  "<span style='font-size: large;' class=' label label-danger'>"+productoEnVenta.getStock() +"</span>";
        }else if(productoEnVenta.getStock() <=15){
            return  "<span style='font-size: large;' class=' label label-warning'>"+productoEnVenta.getStock() +"</span>";
        }else{
            return  "<span style='font-size: large;' class=' label label-success'>"+productoEnVenta.getStock() +"</span>";
        }



    }
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<Almacen> getAlmacenList() {
        return almacenList;
    }

    public void setAlmacenList(List<Almacen> almacenList) {
        this.almacenList = almacenList;
    }

    public String getCodigo_local() {
        return codigo_local;
    }

    public void setCodigo_local(String codigo_local) {
        this.codigo_local = codigo_local;
    }

    public long getCantProductoVendido() {
        return cantProductoVendido;
    }

    public void setCantProductoVendido(long cantProductoVendido) {
        this.cantProductoVendido = cantProductoVendido;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public ProductoEnVenta getProductoEnVenta() {
        return productoEnVenta;
    }

    public void setProductoEnVenta(ProductoEnVenta productoEnVenta) {
        this.productoEnVenta = productoEnVenta;
    }

//    public Vendedor getVendedor() {
//        return idVendedor;
//    }
//
//    public void setVendedor(Vendedor vendedor) {
//        this.idVendedor = vendedor;
//    }

//    public List<CarroCompra_Producto> getCarroCompra_productos() {
//        return carroCompra_productos;
//    }
//
//    public void setCarroCompra_productos(List<CarroCompra_Producto> carroCompra_productos) {
//        this.carroCompra_productos = carroCompra_productos;
//
//    }
//    public boolean deleteComentario(String id_comentario){
//        for (Comentario aux: this.comentarios
//             ) {
//            if (aux.getId() == Long.parseLong(id_comentario)){
//                Comentario borrar = aux;
//                this.comentarios.remove(aux);
//                aux=null;
//                break;
//            }
//            return true;
//        }
//
//        return true;
//    }
//    public boolean addComentario(String comentatio, String nombre, String id_cliente){
//        this.comentarios.add(new Comentario(nombre,comentatio,id_cliente));
//        return true;
//    }
//
//    public Set<Comentario> getComentarios() {
//        return comentarios;
//    }
//
//    public void setComentarios(Set<Comentario> comentarios) {
//        this.comentarios = comentarios;
//    }

    public long getId() {
        return id;
    }

//    public String getNombreFoto() {
//        return nombreFoto;
//    }
//
//    public void setNombreFoto(String nombreFoto) {
//        this.nombreFoto = nombreFoto;
//    }
//
//    public String getMimeType() {
//        return mimeType;
//    }
//
//    public void setMimeType(String mimeType) {
//        this.mimeType = mimeType;
//    }
//
//    public String getFotoBase64() {
//        return fotoBase64;
//    }
//
//    public void setFotoBase64(String fotoBase64) {
//        this.fotoBase64 = fotoBase64;
//    }

    //    private Foto buscarFoto(Long ID) {
//        Foto foto = null;
//
//        for (Foto auxFoto : this.fotos) {
//            if (auxFoto.getId() == ID) {
//                foto = auxFoto;
//            }
//        }
//
//        return foto;
//    }
//
//    public boolean eliminarFoto(Long ID) {
//        boolean ok = false;
//        Foto foto = this.buscarFoto(ID);
//
//        if (foto != null) {
//            this.fotos.remove(foto);
//            ok = true;
//        }
//
//        return ok;
//    }
    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

//    public BigDecimal getPrecio() {
//        BigDecimal produ = precio.setScale(2);
//        return produ;
//    }
//
//    public void setPrecio(BigDecimal precio) {
//        this.precio = precio;
//    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
