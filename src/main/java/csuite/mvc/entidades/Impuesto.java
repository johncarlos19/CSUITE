package csuite.mvc.entidades;

import com.sun.istack.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Impuesto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String operacion;
    private double valorSumandoExtra;

    private boolean aplicarATodos = false;
    private boolean utilizar = true;

//    @ManyToMany(cascade =  CascadeType.ALL , mappedBy = "impuestos",fetch = FetchType.LAZY)
//    private List<FacturaCliente> facturaClientes = new ArrayList<FacturaCliente>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "idImpuesto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImpuestoProductoEnVenta> impuestoProductoEnVentas = new ArrayList<ImpuestoProductoEnVenta>();



    public Impuesto() {
    }

    public Impuesto(String nombre, String operacion, double valorSumandoExtra) {
        this.nombre = nombre;
        this.operacion = operacion;
        this.valorSumandoExtra = valorSumandoExtra;
    }

    public double getPrecioNeto(Double aux){
        double neto = 0;
        if (operacion.equalsIgnoreCase("Porciento")){
            neto = aux*((double)valorSumandoExtra/100);
        }else if (operacion.equalsIgnoreCase("Suma de Cantidad")){
            neto = valorSumandoExtra;
        }else if (operacion.equalsIgnoreCase("Descuento Absoluto")){
            neto = -1*valorSumandoExtra;
        }else if (operacion.equalsIgnoreCase("Descuento Porcentual")){
            neto = -1*aux*((double)valorSumandoExtra/100);
        }else{
            neto = 0;
        }
        return neto;
    }
//    public void addProducto(ProductoEnVenta producto){
//        producto.addImpuesto(this);
//        this.productoEnVentas.add(producto);
//    }
    public double getImpuesto(Double aux){
        double neto = 0;
        if (operacion.equalsIgnoreCase("Porciento")){
            neto = aux*((double)valorSumandoExtra/100);
        }else if (operacion.equalsIgnoreCase("Suma de Cantidad")){
            neto = valorSumandoExtra;
        }else{
            neto = 0;
        }
        return neto;
    }
    public double getDescuento(Double aux){
        double neto = 0;
        if (operacion.equalsIgnoreCase("Descuento Absoluto")){
            neto = valorSumandoExtra;
        }else if (operacion.equalsIgnoreCase("Descuento Porcentual")){
            neto = aux*((double)valorSumandoExtra/100);
        }else{
            neto = 0;
        }
        return neto;
    }

    public List<ImpuestoProductoEnVenta> getImpuestoProductoEnVentas() {
        return impuestoProductoEnVentas;
    }

    public void setImpuestoProductoEnVentas(List<ImpuestoProductoEnVenta> impuestoProductoEnVentas) {
        this.impuestoProductoEnVentas = impuestoProductoEnVentas;
    }
    //    public List<ProductoEnVenta> getProductoEnVentas() {
//        return productoEnVentas;
//    }
//
//    public void setProductoEnVentas(List<ProductoEnVenta> productoEnVentas) {
//        this.productoEnVentas = productoEnVentas;
//    }

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

    public boolean isAplicarATodos() {
        return aplicarATodos;
    }

    public void setAplicarATodos(boolean aplicarATodos) {
        this.aplicarATodos = aplicarATodos;
    }

//    public List<FacturaCliente> getFacturaClientes() {
//        return facturaClientes;
//    }
//
//    public void setFacturaClientes(List<FacturaCliente> facturaClientes) {
//        this.facturaClientes = facturaClientes;
//    }

    public BigDecimal getValorSumandoExtraBig(){
        if (operacion.equalsIgnoreCase("Porciento")){
            return BigDecimal.valueOf(valorSumandoExtra).setScale(1);
        }else if (operacion.equalsIgnoreCase("Suma de Cantidad")){
            return BigDecimal.valueOf(valorSumandoExtra).setScale(2);
        }else if (operacion.equalsIgnoreCase("Descuento Absoluto")){
            return BigDecimal.valueOf(valorSumandoExtra).setScale(2);
        }else if (operacion.equalsIgnoreCase("Descuento Porcentual")){
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
