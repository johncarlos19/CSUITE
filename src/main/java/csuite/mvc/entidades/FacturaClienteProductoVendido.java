package csuite.mvc.entidades;

import csuite.mvc.jsonObject.ProductoJSON;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FacturaClienteProductoVendido implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private long cantidad;
    private float precioVenta;
    private float precioCosto;
    private float impuestoTotal;
    @ManyToOne()
//    @MapsId("idImpuesto")
    @JoinColumn(name = "idFacturaCliente")
    private FacturaCliente idFacturaCliente;


    @ManyToOne
//    @MapsId("idProductoEnVenta")
    @JoinColumn(name = "idProducto")
    private Producto idProducto;
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL,mappedBy = "idFacturaClienteProductoVendido",  orphanRemoval = true)
    private List<ImpuestoCliente> impuestoProducto= new ArrayList<ImpuestoCliente>();

    public FacturaClienteProductoVendido() {
    }

    public float precioLista(){
        return precioVenta+impuestoTotal;
    }
    public ProductoJSON getProductoJSON(){
        ProductoJSON productoJSON = null;
        List<ImpuestoCliente> clienteList1 = new ArrayList<ImpuestoCliente>();
        float descuento = 0;
        for (ImpuestoCliente impuestoCliente : impuestoProducto) {
            ImpuestoCliente aux = new ImpuestoCliente();
            aux.setNombre(impuestoCliente.getNombre());
            aux.setOperacion(impuestoCliente.getOperacion());
            aux.setValorSumandoExtra(impuestoCliente.getValorSumandoExtra());
            aux.setId(impuestoCliente.getId());
            descuento += aux.getDescuento();
            clienteList1.add(aux);
        }
        productoJSON = new ProductoJSON(
                id,
                idProducto.getNombre(),
                idProducto.getDescripcion(),
                idProducto.getCodigo_local(),
                0,
                true,
                idProducto.getCategoria(),
                cantidad,
                precioVenta,
                precioCosto,
                idProducto.getProductoEnVenta().getCantMaxPorVenta(),
                descuento,
                impuestoTotal,
                precioLista(),
                null,
                null,
                null


        );
        productoJSON.setImpuestoClientes(clienteList1);

        return productoJSON;
    }


    public void addProducto(long cant){
        this.cantidad +=cant;
    }
    public void discountProducto(long cant){
        this.cantidad -=cant;
    }

    public FacturaCliente getIdFacturaCliente() {
        return idFacturaCliente;
    }

    public void setIdFacturaCliente(FacturaCliente idFacturaCliente) {
        this.idFacturaCliente = idFacturaCliente;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public List<ImpuestoCliente> getImpuestoProducto() {
        return impuestoProducto;
    }

    public void setImpuestoProducto(List<ImpuestoCliente> impuestoProducto) {
        this.impuestoProducto = impuestoProducto;
    }
    public void addImpuestoProducto(List<ImpuestoCliente> impuestoProducto) {
        for (ImpuestoCliente impuestoCliente : impuestoProducto) {
            impuestoCliente.setIdFacturaClienteProductoVendido(this);
            this.impuestoProducto.add(impuestoCliente);
        }

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public float getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(float precioCosto) {
        this.precioCosto = precioCosto;
    }

    public float getImpuestoTotal() {
        return impuestoTotal;
    }

    public void setImpuestoTotal(float impuestoTotal) {
        this.impuestoTotal = impuestoTotal;
    }
}
