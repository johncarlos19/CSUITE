package csuite.mvc.jsonObject;
public class GuardarFacturaJson {

    private String idFactura;
    private String metodoDePago;

    public GuardarFacturaJson() {
    }

    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }

    public String getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }
}
