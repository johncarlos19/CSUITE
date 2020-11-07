package csuite.mvc.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Impuesto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String operacion;
    private double valorSumandoExtra;
    private boolean utilizar = true;

    @ManyToMany()
    private List<FacturaCliente> facturaClientes = new ArrayList<FacturaCliente>();


    public Impuesto() {
    }

    public Impuesto(String nombre, String operacion, double valorSumandoExtra) {
        this.nombre = nombre;
        this.operacion = operacion;
        this.valorSumandoExtra = valorSumandoExtra;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOperacion() {
        return operacion;
    }

    public BigDecimal getValorSumandoExtraBig(){
        if (operacion.equalsIgnoreCase("Porciento")){
            return BigDecimal.valueOf(valorSumandoExtra).setScale(1);
        }else{
            return BigDecimal.valueOf(valorSumandoExtra).setScale(2);
        }
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public double getValorSumandoExtra() {
        return valorSumandoExtra;
    }

    public void setValorSumandoExtra(double valorSumandoExtra) {
        this.valorSumandoExtra = valorSumandoExtra;
    }

    public boolean isUtilizar() {
        return utilizar;
    }

    public void setUtilizar(boolean utilizar) {
        this.utilizar = utilizar;
    }
}
