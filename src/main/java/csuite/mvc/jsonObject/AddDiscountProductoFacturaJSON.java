package csuite.mvc.jsonObject;

public class AddDiscountProductoFacturaJSON {
    private long cantidad;
    private long idProducto;
    private String idFactura;


    public AddDiscountProductoFacturaJSON() {
    }
    public AddDiscountProductoFacturaJSON(long cantidad, long idProducto, String idFactura) {
        this.cantidad = cantidad;
        this.idProducto = idProducto;
        this.idFactura = idFactura;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }
}
