package csuite.mvc.entidades;

import csuite.mvc.jsonObject.ProductoJSON;
import csuite.mvc.servicios.ImpuestoClienteServicios;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
public class ProductoEnVenta implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @JoinColumn(name = "idProducto")
    @OneToOne()
    private Producto idProducto;
    private long stock;
    private long vendido = 0;
    private float precioVenta;
    private float precioCompra;
    private long cantMaxPorVenta;
    @JoinColumn(name = "idAlmacen")
    @OneToOne(cascade=CascadeType.ALL)
    private Almacen idAlmacen;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "idProductoEnVenta", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name="idproductoEnVenta")
    private List<ImpuestoProductoEnVenta> impuestoProductoEnVentas = new ArrayList<ImpuestoProductoEnVenta>();

    public ProductoEnVenta() {
    }




    public ImpuestoProductoEnVenta addImpuesto(Impuesto impuesto){


        ImpuestoProductoEnVenta can = new ImpuestoProductoEnVenta(impuesto,this);
        impuesto.getImpuestoProductoEnVentas().add(can);
        impuestoProductoEnVentas.add(can);
        return can;
//        impuesto.getProductoEnVentas().add(this);
//
//        impuestos.add(impuesto);
    }

    public Almacen getIdAlmacen() {
        return idAlmacen;
    }
    public float getImpuestoTotal() {
        float impuestoTotal = 0;
        for (ImpuestoProductoEnVenta impuesto : impuestoProductoEnVentas
        ){

            impuestoTotal += impuesto.getIdImpuesto().getPrecioNeto((double) precioVenta);

        }
        return impuestoTotal;
    }

    public List<ImpuestoCliente> getImpuestoCliente(){
        List<ImpuestoCliente> impuestoClientes = new ArrayList<ImpuestoCliente>();
        for (ImpuestoProductoEnVenta auxe : impuestoProductoEnVentas) {

            ImpuestoCliente aux = new ImpuestoCliente();
            aux.setNombre(auxe.getIdImpuesto().getNombre());
            aux.setOperacion(auxe.getIdImpuesto().getOperacion());
            aux.setValorSumandoExtra(auxe.getIdImpuesto().getPrecioNeto((double) precioVenta));
//            aux = (ImpuestoCliente) ImpuestoClienteServicios.getInstancia().crear(aux);
            impuestoClientes.add(aux);
        }
        return impuestoClientes;
    }

    public void addProductoStock(long prod){
        stock+=prod;

    }
    public void addProductoVendido(long prod){
        vendido+=prod;

    }
    public void discountProductoVendido(long prod){
        vendido-=prod;

    }

    public void discountProductoStock(long prod){
        stock-=prod;
    }

    public void setIdAlmacen(Almacen idAlmacen) {
        this.idAlmacen = idAlmacen;
    }
    public void addIdAlmacen(Almacen idAlmacen) {
        idAlmacen.setProductoEnVenta(this);
        this.idAlmacen = idAlmacen;
    }

//    public List<Impuesto> getImpuestos() {
//        return impuestos;
//    }
//
//    public void setImpuestos(List<Impuesto> impuestos) {
//        this.impuestos = impuestos;
//    }


    public List<ImpuestoProductoEnVenta> getImpuestoProductoEnVentas() {
        return impuestoProductoEnVentas;
    }

    public void setImpuestoProductoEnVentas(List<ImpuestoProductoEnVenta> impuestoProductoEnVentas) {
        this.impuestoProductoEnVentas = impuestoProductoEnVentas;
    }

    public long getVendido() {
        return vendido;
    }

    public void setVendido(long vendido) {
        this.vendido = vendido;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProductoJSON getProductoJSON(int posi){
        float descuentoPorciento = 0;
        float impu = 0;
        float precioneto = precioVenta;
        for (ImpuestoProductoEnVenta impuesto : impuestoProductoEnVentas
        ){
            descuentoPorciento += impuesto.getIdImpuesto().getDescuento((double) precioVenta);
            impu += impuesto.getIdImpuesto().getImpuesto((double) precioVenta);
            precioneto += impuesto.getIdImpuesto().getPrecioNeto((double) precioVenta);

        }
        ProductoJSON productoJSON = null;
        switch (posi){
            case 1:
                 productoJSON = new ProductoJSON(
                        idProducto.getId(),
                        idProducto.getNombre(),
                        idProducto.getDescripcion(),
                        idProducto.getCodigo_local(),
                        idProducto.getCantProductoVendido(),
                        idProducto.isDisponible(),
                        "Comida",
                        stock,
                        precioVenta,
                        precioCompra,
                        cantMaxPorVenta,
                        descuentoPorciento,
                        impu
                        ,precioneto,
                        idProducto.getFoto().getNombre(),
                        idProducto.getFoto().getMimeType(),
                        idProducto.getFoto().getFotoBase64()
                );
                break;
            case 2:
                productoJSON = new ProductoJSON(
                        idProducto.getId(),
                        idProducto.getNombre(),
                        idProducto.getDescripcion(),
                        idProducto.getCodigo_local(),
                        idProducto.getCantProductoVendido(),
                        idProducto.isDisponible(),
                        "Comida",
                        stock,
                        precioVenta,
                        precioCompra,
                        cantMaxPorVenta,
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
        return productoJSON;
    }


    public float getPrecioVenta() {

        return precioVenta;
    }

    public BigDecimal getTotal(){
        double tot = 0;

        BigDecimal total = BigDecimal.valueOf(precioVenta).setScale(2,BigDecimal.ROUND_HALF_UP);
        return total;
    }
    public String getTotalString(){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        return numberFormat.format(getTotal());
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public long getCantMaxPorVenta() {
        return cantMaxPorVenta;
    }

    public void setCantMaxPorVenta(long cantMaxPorVenta) {
        this.cantMaxPorVenta = cantMaxPorVenta;
    }
}
