package csuite.mvc.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Empleado  implements Serializable {
    @Id
    @JoinColumn(name = "idEmpleado")
    @OneToOne()
    private Usuario idEmpleado;
    private String password;
    private boolean validacion;
    private String email;


    public Empleado() {

    }

    public Usuario getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Usuario idEmpleado) {
        this.idEmpleado = idEmpleado;
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


}
