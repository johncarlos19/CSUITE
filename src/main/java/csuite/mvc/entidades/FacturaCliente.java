package csuite.mvc.entidades;

import csuite.mvc.jsonObject.FacturaJson;
import csuite.mvc.jsonObject.ProductoJSON;
import csuite.mvc.servicios.ImpuestoClienteServicios;
import org.hibernate.LazyInitializationException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FacturaCliente implements Identifiable<String> {
    @Id
    @GeneratedValue(generator = "cli-generator",strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "cli-generator", parameters = {@org.hibernate.annotations.Parameter(name = "prefix", value = "FAC"),@org.hibernate.annotations.Parameter(name = "longitud", value = "12")}
            , strategy = "csuite.mvc.entidades.MyGenerator")
    private String idFactura;
    private float total = 0;//subtotal
    private float precioNeto = 0;
    private String compania;
    private String direccion;
    private String ciudadPais;
    private String telefono;
    private String idQuienLoRealizo;
    private String metodoDePago;
    private String idVendedor;
    private String nombreCliente;
    private String idCliente;
    private boolean facturaGuardada = false;
    private String codigo; //este codigo es de trans o monto que pago en efectivo
    @CreationTimestamp
    private Timestamp fechaCompra;
//    @ManyToMany()
//    private List<Impuesto> impuestos = new ArrayList<Impuesto>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "idFacturaCliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FacturaClienteProductoVendido>  facturaClienteProductoVendidos= new ArrayList<FacturaClienteProductoVendido>();
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL,mappedBy = "idFacturaCliente",  orphanRemoval = true)
    private List<ImpuestoCliente>  impuestoClientes= new ArrayList<ImpuestoCliente>();



    public FacturaCliente() {
    }


    public FacturaJson getFacturaJson(){
        List<ImpuestoCliente> list = new ArrayList<ImpuestoCliente>();
        try {
            for (ImpuestoCliente impuestoCliente : impuestoClientes) {
                list.add(impuestoCliente.dameImpuestoSinDeoendy());
            }
        }catch (LazyInitializationException e){
            e.printStackTrace();
        }


        FacturaJson facturaJson = new FacturaJson(idFactura,total,precioNeto,idQuienLoRealizo,metodoDePago,fechaCompra,list);
        facturaJson.setIdCliente(idCliente);
        facturaJson.setNombreCliente(nombreCliente);
        facturaJson.setDireccion(direccion);
        facturaJson.setTelefono(telefono);
        facturaJson.setCompania(compania);
        facturaJson.setCiudadPais(ciudadPais);
        for (FacturaClienteProductoVendido vendido : facturaClienteProductoVendidos) {
            facturaJson.addProductoJSON(vendido.getProductoJSON());
        }
        return facturaJson;

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCiudadPais() {
        return ciudadPais;
    }

    public void setCiudadPais(String ciudadPais) {
        this.ciudadPais = ciudadPais;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public void addFacturaClienteProductoVendido(FacturaClienteProductoVendido facturaClienteProductoVendido){

//        impuestoClientes = ImpuestoClienteServicios.getInstancia().ListaImpuestoFacturaCliente(idFactura);
        List<ImpuestoCliente> listafacturaClienteProductoVendido = new ArrayList<>(facturaClienteProductoVendido.getImpuestoProducto());
        boolean vaEliminar = false;
        int eli = -1;
        float totalImpuesto = 0;
        try {
            for (ImpuestoCliente impuestoClienteFacturaCliente : impuestoClientes) {

                for (int i = 0; i < listafacturaClienteProductoVendido.size(); i++) {
                    if (impuestoClienteFacturaCliente.getNombre().equalsIgnoreCase(listafacturaClienteProductoVendido.get(i).getNombre()) && impuestoClienteFacturaCliente.getOperacion().equalsIgnoreCase(listafacturaClienteProductoVendido.get(i).getOperacion())){
                        System.out.println("\n\nSe va a guardar uno solo");
                        impuestoClienteFacturaCliente.addImpuestoValue(listafacturaClienteProductoVendido.get(i).getValorSumandoExtra()* facturaClienteProductoVendido.getCantidad());
                        ImpuestoClienteServicios.getInstancia().editar(impuestoClienteFacturaCliente);

                        eli = i;
                        vaEliminar = true;
                    }else if(listafacturaClienteProductoVendido.get(i).getValorSumandoExtra() == 0){
                        eli = i;
                        vaEliminar = true;

                    }
                }


                if (vaEliminar == true){
                    listafacturaClienteProductoVendido.remove(eli);
                    vaEliminar = false;
                }
                totalImpuesto+=impuestoClienteFacturaCliente.getInteres();

            }
        }catch (LazyInitializationException D){
            D.printStackTrace();
        }
        System.out.println("\n\ntotal"+listafacturaClienteProductoVendido.size());
        for (ImpuestoCliente impuestoCliente : listafacturaClienteProductoVendido) {
            ImpuestoCliente aux = new ImpuestoCliente();
            aux.setNombre(impuestoCliente.getNombre());
            aux.setOperacion(impuestoCliente.getOperacion());
            aux.setValorSumandoExtra(impuestoCliente.getValorSumandoExtra()* facturaClienteProductoVendido.getCantidad());
            aux.setIdFacturaCliente(this);
            ImpuestoClienteServicios.getInstancia().crear(aux);
            totalImpuesto+=aux.getInteres();
        }
        this.precioNeto= total+totalImpuesto;
        facturaClienteProductoVendido.setIdFacturaCliente(this);
        facturaClienteProductoVendidos.add(facturaClienteProductoVendido);


    }
    public void addPrecioProducto(float prod){
        try {
            System.out.println("\n\nhola"+total);
            this.total+=prod;
        }catch (NullPointerException e){
            this.total = 0;
            this.total+=prod;
        }



    }

    public void discountPrecioProducto(float prod){
        this.total-=prod;
    }
    public void addPrecioNeto(float totalImpuesto){
        precioNeto+=totalImpuesto;
    }
    public void discountPrecioNeto(float totalImpuesto){
        precioNeto-=totalImpuesto;
    }

    public void addImpuestoCliente(ImpuestoCliente impuestoCliente){
        impuestoCliente.setIdFacturaCliente(this);
        this.impuestoClientes.add(impuestoCliente);
    }

    public void addValueImpuesto(FacturaClienteProductoVendido facturaClienteProductoVendido, long cant){
        List<ImpuestoCliente> listafacturaClienteProductoVendido = facturaClienteProductoVendido.getImpuestoProducto();
        boolean vaEliminar = false;
        int eli = -1;
        float totalImpuesto = 0;
        for (ImpuestoCliente impuestoCliente : impuestoClientes) {
            for (int i = 0; i < listafacturaClienteProductoVendido.size(); i++) {
                if (impuestoCliente.getNombre().equalsIgnoreCase(listafacturaClienteProductoVendido.get(i).getNombre()) && impuestoCliente.getOperacion().equalsIgnoreCase(listafacturaClienteProductoVendido.get(i).getOperacion())){
                    impuestoCliente.addImpuestoValue(listafacturaClienteProductoVendido.get(i).getValorSumandoExtra()* cant);
                    ImpuestoClienteServicios.getInstancia().editar(impuestoCliente);

                    eli = i;
                    vaEliminar = true;
                }else if(facturaClienteProductoVendido.getImpuestoProducto().get(i).getValorSumandoExtra() == 0){
                    eli = i;
                    vaEliminar = true;

                }
            }
            if (vaEliminar == true){
                listafacturaClienteProductoVendido.remove(eli);
                vaEliminar = false;
            }
            totalImpuesto+=impuestoCliente.getInteres();


        }
        this.precioNeto= total+totalImpuesto;
    }
    public void removeFacturaClienteProductoVendido(long idFCPV){
        for (int i = 0; i < facturaClienteProductoVendidos.size(); i++) {
            if (facturaClienteProductoVendidos.get(i).getId()==idFCPV){
                facturaClienteProductoVendidos.remove(i);
                break;
            }
        }
    }
    public void discountValueImpuesto(FacturaClienteProductoVendido facturaClienteProductoVendido, long cant){
        List<ImpuestoCliente> listafacturaClienteProductoVendido = facturaClienteProductoVendido.getImpuestoProducto();
        List<Long> posiList = new ArrayList<Long>();
        boolean vaEliminar = false;
        int eli = -1;
        float totalImpuesto = 0;

        for (ImpuestoCliente impuestoCliente : impuestoClientes) {
            for (int i = 0; i < listafacturaClienteProductoVendido.size(); i++) {
                if (impuestoCliente.getNombre().equalsIgnoreCase(listafacturaClienteProductoVendido.get(i).getNombre()) && impuestoCliente.getOperacion().equalsIgnoreCase(listafacturaClienteProductoVendido.get(i).getOperacion())){
                    impuestoCliente.discountImpuestoValue(listafacturaClienteProductoVendido.get(i).getValorSumandoExtra()* cant);
                    if (impuestoCliente.getValorSumandoExtra()==0){
                        posiList.add(impuestoCliente.getId());
                    }else{
                        ImpuestoClienteServicios.getInstancia().editar(impuestoCliente);
                    }


                    eli = i;
                    vaEliminar = true;
                }else if(facturaClienteProductoVendido.getImpuestoProducto().get(i).getValorSumandoExtra() == 0){
                    eli = i;
                    vaEliminar = true;

                }
            }
            if (vaEliminar == true){
                listafacturaClienteProductoVendido.remove(eli);
                vaEliminar = false;
            }
            totalImpuesto+=impuestoCliente.getInteres();
        }
        this.precioNeto= total+totalImpuesto;
        for (long integer : posiList) {

//            ImpuestoClienteServicios.getInstancia().eliminar(integer);
            deleteImpuestoCliente(integer);

        }
    }
    private void deleteImpuestoCliente(long integer){
        for (int i = 0; i < impuestoClientes.size(); i++) {
            if (impuestoClientes.get(i).getId()==integer){
                impuestoClientes.remove(i);
                break;
            }
        }
    }

    public Timestamp getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Timestamp fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public boolean isFacturaGuardada() {
        return facturaGuardada;
    }

    public void setFacturaGuardada(boolean facturaGuardada) {
        this.facturaGuardada = facturaGuardada;
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

    public List<FacturaClienteProductoVendido> getFacturaClienteProductoVendidos() {
        return facturaClienteProductoVendidos;
    }

    public void setFacturaClienteProductoVendidos(List<FacturaClienteProductoVendido> facturaClienteProductoVendidos) {
        this.facturaClienteProductoVendidos = facturaClienteProductoVendidos;
    }

    public List<ImpuestoCliente> getImpuestoClientes() {
        return impuestoClientes;
    }

    public void setImpuestoClientes(List<ImpuestoCliente> impuestoClientes) {
        this.impuestoClientes = impuestoClientes;
    }

    @Override
    public String getId() {
        return idFactura;
    }
}
