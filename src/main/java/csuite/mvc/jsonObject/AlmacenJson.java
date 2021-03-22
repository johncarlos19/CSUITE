package csuite.mvc.jsonObject;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

public class AlmacenJson {
    private long idAlmacen;
    private Timestamp fechaRegistro;
    private String proveedor;
    private long productoAgregado;
    private long productoVendido;
    private long productoDescartado;
    private float costo;
    private float precioVentaFutura;


    public AlmacenJson() {
    }

    public AlmacenJson(long idAlmacen, Timestamp fechaRegistro, String proveedor, long productoAgregado, long productoVendido, long productoDescartado, float costo, float precioVentaFutura) {
        this.idAlmacen = idAlmacen;
        this.fechaRegistro = fechaRegistro;
        this.proveedor = proveedor;
        this.productoAgregado = productoAgregado;
        this.productoVendido = productoVendido;
        this.productoDescartado = productoDescartado;
        this.costo = costo;
        this.precioVentaFutura = precioVentaFutura;
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

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public long getProductoAgregado() {
        return productoAgregado;
    }

    public void setProductoAgregado(long productoAgregado) {
        this.productoAgregado = productoAgregado;
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
}
