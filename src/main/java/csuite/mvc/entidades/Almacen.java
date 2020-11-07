package csuite.mvc.entidades;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
public class Almacen implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long idAlmacen;
    @CreationTimestamp
    private Timestamp fechaRegistro;
    private String proveedor;
    private long productoAgregado;
    private long productoVendido;
    private long productoDescartado = 0;
    private float costo;
    private float precioVentaFutura;
    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL , orphanRemoval = true,mappedBy = "idAlmacen")
    private ProductoEnVenta productoEnVenta;

    public Almacen() {
    }

    public Almacen(String proveedor, long productoAgregado, long productoVendido, float costo, float precioVentaFutura) {
        this.proveedor = proveedor;
        this.productoAgregado = productoAgregado;
        this.productoVendido = productoVendido;
        this.costo = costo;
        this.precioVentaFutura = precioVentaFutura;
    }

    public long getDisponible(){
        return productoAgregado-productoDescartado-productoVendido;
    }

    public ProductoEnVenta getProductoEnVenta() {
        return productoEnVenta;
    }

    public void setProductoEnVenta(ProductoEnVenta productoEnVenta) {
        this.productoEnVenta = productoEnVenta;
    }

    public long getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(long idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public long getProductoAgregado() {
        return productoAgregado;
    }

    public void setProductoAgregado(long productoAgregado) {
        this.productoAgregado = productoAgregado;
    }


    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getPrecioVentaFutura() {
        return precioVentaFutura;
    }

    public void setPrecioVentaFutura(float precioVentaFutura) {
        this.precioVentaFutura = precioVentaFutura;
    }

    public long getProductoVendido() {
        return productoVendido;
    }

    public void setProductoVendido(long productoVendido) {
        this.productoVendido = productoVendido;
    }

    public long getProductoDescartado() {
        return productoDescartado;
    }

    public void setProductoDescartado(long productoDescartado) {
        this.productoDescartado = productoDescartado;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
}
