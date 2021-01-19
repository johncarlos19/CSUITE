package csuite.mvc.jsonObject;

import csuite.mvc.entidades.ImpuestoCliente;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FacturaJson {
    private String idFactura;
    private float total;//subtotal
    private float precioNeto;
    private String idQuienLoRealizo;
    private String metodoDePago;
    private Timestamp fechaCompra;
    private String nombreCliente;
    private String idCliente;
    private List<ImpuestoCliente> impuestoFacturas= new ArrayList<ImpuestoCliente>();
    private List<ProductoJSON> productos= new ArrayList<ProductoJSON>();

    public FacturaJson(String idFactura, float total, float precioNeto, String idQuienLoRealizo, String metodoDePago, Timestamp fechaCompra, List<ImpuestoCliente> impuestoFacturas) {
        this.idFactura = idFactura;
        this.total = total;
        this.precioNeto = precioNeto;
        this.idQuienLoRealizo = idQuienLoRealizo;
        this.metodoDePago = metodoDePago;
        this.fechaCompra = fechaCompra;
        this.impuestoFacturas = impuestoFacturas;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public List<ProductoJSON> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoJSON> productos) {
        this.productos = productos;
    }

    public void addProductoJSON(ProductoJSON productoJSON){
        productos.add(productoJSON);
    }


    public List<ImpuestoCliente> getImpuestoFacturas() {
        return impuestoFacturas;
    }

    public void setImpuestoFacturas(List<ImpuestoCliente> impuestoFacturas) {
        this.impuestoFacturas = impuestoFacturas;
    }

    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getPrecioNeto() {
        return precioNeto;
    }

    public void setPrecioNeto(float precioNeto) {
        this.precioNeto = precioNeto;
    }

    public String getIdQuienLoRealizo() {
        return idQuienLoRealizo;
    }

    public void setIdQuienLoRealizo(String idQuienLoRealizo) {
        this.idQuienLoRealizo = idQuienLoRealizo;
    }

    public String getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public Timestamp getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Timestamp fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
}
