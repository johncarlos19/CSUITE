package csuite.mvc.entidades;


import csuite.mvc.jsonObject.ImpuestoJson;

import javax.persistence.*;
import javax.persistence.GenerationType;

import java.io.Serializable;

@Entity
public class ImpuestoCliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String operacion;
    private double valorSumandoExtra;
    @ManyToOne()
    private FacturaCliente idFacturaCliente = null;
    @ManyToOne()
    private FacturaClienteProductoVendido idFacturaClienteProductoVendido = null;

    public ImpuestoCliente(String nombre, String operacion, double valorSumandoExtra) {
        this.nombre = nombre;
        this.operacion = operacion;
        this.valorSumandoExtra = valorSumandoExtra;
    }

    public ImpuestoCliente() {

    }

    public ImpuestoCliente dameImpuestoSinDeoendy(){
        ImpuestoCliente aux = new ImpuestoCliente();
        aux.setId(id);
        aux.setNombre(nombre);
        aux.setOperacion(operacion);
        aux.setValorSumandoExtra(valorSumandoExtra);
        return aux;
    }

    public double getInteres(){
        double neto = 0;
        if (operacion.equalsIgnoreCase("Porciento")){
            neto = ((double)valorSumandoExtra);
        }else if (operacion.equalsIgnoreCase("Suma de Cantidad")){
            neto = valorSumandoExtra;
        }else if (operacion.equalsIgnoreCase("Descuento Absoluto")){
            neto = -1*valorSumandoExtra;
        }else if (operacion.equalsIgnoreCase("Descuento Porcentual")){
            neto = -1*((double)valorSumandoExtra);
        }else{
            neto = 0;
        }
        return neto;
    }

    public double getImpuesto(){
        double neto = 0;
        if (operacion.equalsIgnoreCase("Porciento")){
            neto = ((double)valorSumandoExtra);
        }else if (operacion.equalsIgnoreCase("Suma de Cantidad")){
            neto = valorSumandoExtra;
        }else{
            neto = 0;
        }
        return neto;
    }
    public double getDescuento(){
        double neto = 0;
        if (operacion.equalsIgnoreCase("Descuento Absoluto")){
            neto = valorSumandoExtra;
        }else if (operacion.equalsIgnoreCase("Descuento Porcentual")){
            neto = ((double)valorSumandoExtra);
        }else{
            neto = 0;
        }
        return neto;
    }

    public void addImpuestoValue(double prod){
        valorSumandoExtra+=prod;
    }

    public void discountImpuestoValue(double prod){
        valorSumandoExtra-=prod;
    }

    public FacturaCliente getIdFacturaCliente() {
        return idFacturaCliente;
    }

    public void setIdFacturaCliente(FacturaCliente idFacturaCliente) {
        this.idFacturaCliente = idFacturaCliente;
    }

    public FacturaClienteProductoVendido getIdFacturaClienteProductoVendido() {
        return idFacturaClienteProductoVendido;
    }

    public void setIdFacturaClienteProductoVendido(FacturaClienteProductoVendido idFacturaClienteProductoVendido) {
        this.idFacturaClienteProductoVendido = idFacturaClienteProductoVendido;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public double getValorSumandoExtra() {
        return valorSumandoExtra;
    }

    public void setValorSumandoExtra(double valorSumandoExtra) {
        this.valorSumandoExtra = valorSumandoExtra;
    }
}
