package csuite.mvc.entidades;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FacturaCliente implements Identifiable<String> {
    @Id
    @GeneratedValue(generator = "cli-generator",strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "cli-generator", parameters = {@org.hibernate.annotations.Parameter(name = "prefix", value = "FAC"),@org.hibernate.annotations.Parameter(name = "longitud", value = "12")}
            , strategy = "csuite.mvc.entidades.MyGenerator")
    private String idFactura;
    private float total;
    private float precioNeto;
    private String idQuienLoRealizo;
    private String metodoDePago;
    @ManyToMany(cascade = { CascadeType.ALL }, mappedBy = "facturaClientes")
    private List<Impuesto> impuestos = new ArrayList<Impuesto>();
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL ,  orphanRemoval = true)
    @JoinColumn(name="idFactura")
    private List<FacturaClienteProductoVendido>  facturaClienteProductoVendidos= new ArrayList<FacturaClienteProductoVendido>();

    public FacturaCliente() {
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

    @Override
    public String getId() {
        return idFactura;
    }
}
