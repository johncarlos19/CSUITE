package csuite.mvc.jsonObject;

import csuite.mvc.entidades.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoJSON {

    private long id;
    private String nombre;
    private String descripcion;
    private String codigo_local;
    private long cantProductoVendido;
    private boolean disponible = true;
    private String categorias;

    private long stock;
    private float precioVenta;
    private float precioCompra;
    private long cantMaxPorVenta;
    private float descuentoPorciento;
    private float impuesto;
    private float precioLista;
    private List<ImpuestoCliente>  impuestoClientes= new ArrayList<ImpuestoCliente>();

    private String nombreFoto;
    private String mimeType;
    private String fotoBase64;

    public ProductoJSON(long id, String nombre, String descripcion, String codigo_local, long cantProductoVendido, boolean disponible, String categorias, long stock, float precioVenta, float precioCompra, long cantMaxPorVenta, float descuentoPorciento, float impuesto, float precioLista, String nombreFoto, String mimeType, String fotoBase64) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigo_local = codigo_local;
        this.cantProductoVendido = cantProductoVendido;
        this.disponible = disponible;
        this.categorias = categorias;
        this.stock = stock;
        this.precioVenta = precioVenta;
        this.precioCompra = precioCompra;
        this.cantMaxPorVenta = cantMaxPorVenta;
        this.descuentoPorciento = descuentoPorciento;
        this.nombreFoto = nombreFoto;
        this.mimeType = mimeType;
        this.fotoBase64 = fotoBase64;
        this.impuesto = impuesto;
        this.precioLista = precioLista+descuentoPorciento;
    }

    public void addImpuestoCliente(ImpuestoCliente impuestoCliente) {
         impuestoClientes.add(impuestoCliente);
    }
    public List<ImpuestoCliente> getImpuestoClientes() {
        return impuestoClientes;
    }

    public void setImpuestoClientes(List<ImpuestoCliente> impuestoClientes) {
        this.impuestoClientes = impuestoClientes;
    }

    public float getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(float impuesto) {
        this.impuesto = impuesto;
    }

    public float getPrecioLista() {
        return precioLista;
    }

    public void setPrecioLista(float precioLista) {
        this.precioLista = precioLista;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo_local() {
        return codigo_local;
    }

    public void setCodigo_local(String codigo_local) {
        this.codigo_local = codigo_local;
    }

    public long getCantProductoVendido() {
        return cantProductoVendido;
    }

    public void setCantProductoVendido(long cantProductoVendido) {
        this.cantProductoVendido = cantProductoVendido;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public long getCantMaxPorVenta() {
        return cantMaxPorVenta;
    }

    public void setCantMaxPorVenta(long cantMaxPorVenta) {
        this.cantMaxPorVenta = cantMaxPorVenta;
    }

    public float getDescuentoPorciento() {
        return descuentoPorciento;
    }

    public void setDescuentoPorciento(float descuentoPorciento) {
        this.descuentoPorciento = descuentoPorciento;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }
}
