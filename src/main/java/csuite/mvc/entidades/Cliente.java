package csuite.mvc.entidades;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JoinColumn(name = "idCliente")
    @OneToOne()
    private Usuario idCliente;
    private Timestamp fechaUltimaCompra;
    private String idClienteLocal;
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL ,  orphanRemoval = true)
    @JoinColumn(name="idCliente")
    private List<FacturaCliente>  facturaClientes= new ArrayList<FacturaCliente>();
    private Long facturaTotal = new Long(0);

    public Cliente() {
    }
    public void addFacturaCliente(FacturaCliente facturaCliente){
        this.fechaUltimaCompra = facturaCliente.getFechaCompra();
        this.facturaTotal+=1;
        this.facturaClientes.add(facturaCliente);
    }
    public void discountFacturaCliente(){

        this.facturaTotal-=1;

    }

    public Long getFacturaTotal() {
        return facturaTotal;
    }

    public void setFacturaTotal(Long facturaTotal) {
        this.facturaTotal = facturaTotal;
    }

    public List<FacturaCliente> getFacturaClientes() {
        return facturaClientes;
    }

    public void setFacturaClientes(List<FacturaCliente> facturaClientes) {
        this.facturaClientes = facturaClientes;
    }

    public Usuario getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Usuario idCliente) {
        this.idCliente = idCliente;
    }

    public Timestamp getFechaUltimaCompra() {
        return fechaUltimaCompra;
    }

    public void setFechaUltimaCompra(Timestamp fechaUltimaCompra) {
        this.fechaUltimaCompra = fechaUltimaCompra;
    }

    public String getIdClienteLocal() {
        return idClienteLocal;
    }

    public void setIdClienteLocal(String idClienteLocal) {
        this.idClienteLocal = idClienteLocal;
    }
}
