package csuite.mvc.entidades;

import org.hibernate.annotations.IndexColumn;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Vendedor implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JoinColumn(name = "idVendedor")
    @OneToOne()
    private Usuario idVendedor;
    private long id_subscripcion;
    private Timestamp fecha_exp;
    private Timestamp fecha_renovacion;
    private String emailPaypal;
    private String lactitud;
    private String longitud;
    private String password;
    private boolean validacion;
    private String email;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL ,  orphanRemoval = true)
    @JoinColumn(name="idVendedor")
    @OrderBy(value = "id desc ")
    private List<Producto> productoList = new ArrayList<Producto>();


    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL ,  orphanRemoval = true)
    @JoinColumn(name="idVendedor")
    private List<Empleado> empleados = new ArrayList<Empleado>();
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL ,  orphanRemoval = true)
    @JoinColumn(name="idVendedor")
    private List<Cliente>  clientes= new ArrayList<Cliente>();
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL ,  orphanRemoval = true)
    @JoinColumn(name="idVendedor")
    private List<FacturaCliente>  facturaClientes= new ArrayList<FacturaCliente>();
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL ,  orphanRemoval = true)
    @JoinColumn(name="idVendedor")
    private List<Impuesto>  impuestos= new ArrayList<Impuesto>();
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL ,  orphanRemoval = true)
    @JoinColumn(name="idVendedor")
    private List<Categoria>  categorias= new ArrayList<Categoria>();








    public Vendedor() {
    }

    public Vendedor(Usuario idVendedor, long id_subscripcion, Timestamp fecha_exp, Timestamp fecha_renovacion, String emailPaypal) {
        this.idVendedor = idVendedor;
        this.id_subscripcion = id_subscripcion;
        this.fecha_exp = fecha_exp;
        this.fecha_renovacion = fecha_renovacion;
        this.emailPaypal = emailPaypal;
    }

    public void addCliente(@NotNull Cliente aux){
        this.clientes.add(aux);
    }
    public void addImpuesto(@NotNull Impuesto aux){
        this.impuestos.add(aux);
    }
    public void addEmpleado(@NotNull Empleado aux){
        this.empleados.add(aux);
    }
    public void addProducto(@NotNull Producto aux){
        this.productoList.add(aux);
    }
    public void addCategoria(@NotNull Categoria aux){
        this.categorias.add(aux);
    }
    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<FacturaCliente> getFacturaClientes() {
        return facturaClientes;
    }

    public void setFacturaClientes(List<FacturaCliente> facturaClientes) {
        this.facturaClientes = facturaClientes;
    }

    public String getLactitud() {
        return lactitud;
    }

    public void setLactitud(String lactitud) {
        this.lactitud = lactitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValidacion() {
        return validacion;
    }

    public void setValidacion(boolean validacion) {
        this.validacion = validacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public List<Producto> getProductoList() {
        return productoList;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Producto getProductoEspecif(long id_p) {
        for (Producto aux: productoList
             ) {
            if (aux.getId() == id_p){
                return aux;
            }

        }
        return null;
    }


    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }
    public Usuario getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Usuario idVendedor) {
        this.idVendedor = idVendedor;
    }

    public long getId_subscripcion() {
        return id_subscripcion;
    }

    public void setId_subscripcion(long id_subscripcion) {
        this.id_subscripcion = id_subscripcion;
    }

    public Timestamp getFecha_exp() {
        return fecha_exp;
    }

    public void setFecha_exp(Timestamp fecha_exp) {
        this.fecha_exp = fecha_exp;
    }

    public Timestamp getFecha_renovacion() {
        return fecha_renovacion;
    }

    public void setFecha_renovacion(Timestamp fecha_renovacion) {
        this.fecha_renovacion = fecha_renovacion;
    }

    public String getEmailPaypal() {
        return emailPaypal;
    }

    public void setEmailPaypal(String emailPaypal) {
        this.emailPaypal = emailPaypal;
    }

    public List<Impuesto> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<Impuesto> impuestos) {
        this.impuestos = impuestos;
    }
}
