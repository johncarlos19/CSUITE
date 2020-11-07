package csuite.mvc.entidades;

import csuite.mvc.jsonObject.ProductoJSON;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Locale;

@Entity
public class ProductoEnVenta implements Serializable {

    @Id
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
    private float descuentoPorciento;

    public ProductoEnVenta() {
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

    public float getDescuentoPorciento() {
        return descuentoPorciento;
    }

    public void setDescuentoPorciento(float descuentoPorciento) {
        this.descuentoPorciento = descuentoPorciento;
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


    public ProductoJSON getProductoJSON(){
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
