package csuite.mvc.entidades;

import csuite.mvc.jsonObject.ClienteJson;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario implements Identifiable<String> {
    @Id
    @GeneratedValue(generator = "cli-generator",strategy = GenerationType.AUTO)
    @GenericGenerator(name = "cli-generator", parameters = {@org.hibernate.annotations.Parameter(name = "prefix", value = "CLI"),@org.hibernate.annotations.Parameter(name = "longitud", value = "10")}
            , strategy = "csuite.mvc.entidades.MyGenerator")

    private String usuario;
    private String nombre;
    private String apellido;
    private String perfil;
    private String nombreCompania;
    private String pais;
    private String municipio;
    private String direccion;
    private String telefono;
    @CreationTimestamp
    private Timestamp fecha_registro;
    private Timestamp fechaNacimiento;
    private String email;
    private String documento;
    private String tipoDocumento;
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL , orphanRemoval = true,mappedBy = "idVendedor")
    private Vendedor vendedor;
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL , orphanRemoval = true,mappedBy = "idEmpleado")
    private Empleado empleado;
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL , orphanRemoval = true,mappedBy = "idCliente")
    private Cliente cliente;
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL ,  orphanRemoval = true)
    @JoinColumn(name="idUsuario")
    @OrderBy(value = "id desc ")
    private List<Politica> politicaList = new ArrayList<Politica>();


    public Usuario(){

    }
    public ClienteJson getUsuarioJson(){
        ClienteJson clienteJson = new ClienteJson(usuario,
        nombre,
        apellido,
        perfil,
        pais,
        municipio,
        direccion,
        telefono,
        fecha_registro,
        email,
        documento,
        tipoDocumento);
        return clienteJson;
    }

    public Timestamp getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Timestamp fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombreCompania() {
        return nombreCompania;
    }

    public void setNombreCompania(String nombreCompania) throws UnsupportedEncodingException {
        this.nombreCompania = new String(nombreCompania.getBytes("UTF-8"), "ISO-8859-1");;
    }

    public List<Politica> getPoliticaList() {
        return politicaList;
    }

    public void setPoliticaList(List<Politica> politicaList) {
        this.politicaList = politicaList;
    }

    public Usuario(String usuario, String nombre) {
        this.usuario = usuario;
        this.nombre = nombre;
    }

    public boolean addPolitica(Politica aux){
        politicaList.add(aux);
        return true;
    }

    public Vendedor addVendedor(Vendedor aux){
        aux.setIdVendedor(this);
        this.vendedor = aux;
        return this.vendedor;
    }
    public Cliente addCliente(Cliente aux){
        aux.setIdCliente(this);
        this.cliente = aux;
        return this.cliente;
    }

    public Empleado addEmpleado(Empleado aux){
        aux.setIdEmpleado(this);
        this.empleado = aux;
        return this.empleado;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) throws UnsupportedEncodingException {
        this.apellido = new String(apellido.getBytes("UTF-8"), "ISO-8859-1");;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) throws UnsupportedEncodingException {
        this.pais = new String(pais.getBytes("UTF-8"), "ISO-8859-1");;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) throws UnsupportedEncodingException {
        this.municipio = new String(municipio.getBytes("UTF-8"), "ISO-8859-1");;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) throws UnsupportedEncodingException  {
        this.direccion = new String(direccion.getBytes("UTF-8"), "ISO-8859-1");;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Timestamp getFecha_registro() {
        return fecha_registro;
    }
    public String getFechaRegistroString() {
        return fecha_registro.toString();
    }

    public void setFecha_registro(Timestamp fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws UnsupportedEncodingException  {
        this.email = email;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) throws UnsupportedEncodingException {
        this.documento = documento;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws UnsupportedEncodingException  {
        this.nombre = new String(nombre.getBytes("UTF-8"), "ISO-8859-1");;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    @Override
    public String getId() {
        return usuario;
    }
}
