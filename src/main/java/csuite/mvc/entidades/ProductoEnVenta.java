package csuite.mvc.entidades;

import csuite.mvc.jsonObject.ProductoJSON;

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
    private float precioVenta;
    private float precioCompra;
    private long cantMaxPorVenta;
    @JoinColumn(name = "idAlmacen")
    @OneToOne()
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

    public void addProductoStock(long prod){
        stock+=prod;
    }

    public void setIdAlmacen(Almacen idAlmacen) {
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

    public ProductoJSON getProductoJSON(){
        float descuentoPorciento = 0;
        float impu = 0;
        float precioneto = 0;
        for (ImpuestoProductoEnVenta impuesto : impuestoProductoEnVentas
        ){
            descuentoPorciento += impuesto.getIdImpuesto().getDescuento((double) precioVenta);
            impu += impuesto.getIdImpuesto().getImpuesto((double) precioVenta);
            precioneto += impuesto.getIdImpuesto().getPrecioNeto((double) precioVenta);

        }

        return new ProductoJSON(
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
                idProducto.getNombreFoto(),
                idProducto.getMimeType(),
                idProducto.getFotoBase64()
        );
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
