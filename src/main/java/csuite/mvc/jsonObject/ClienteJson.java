package csuite.mvc.jsonObject;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

public class ClienteJson {
    private String usuario;
    private String nombre;
    private String apellido;
    private String perfil;
    private String pais;
    private String municipio;
    private String direccion;
    private String telefono;
    private Timestamp fecha_registro;
    private String email;
    private String documento;
    private String tipoDocumento;

    public ClienteJson(String usuario, String nombre, String apellido, String perfil, String pais, String municipio, String direccion, String telefono, Timestamp fecha_registro, String email, String documento, String tipoDocumento) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.perfil = perfil;
        this.pais = pais;
        this.municipio = municipio;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fecha_registro = fecha_registro;
        this.email = email;
        this.documento = documento;
        this.tipoDocumento = tipoDocumento;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
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

    public Timestamp getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Timestamp fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}
