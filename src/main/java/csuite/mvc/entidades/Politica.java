package csuite.mvc.entidades;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Politica {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;
    private String key;
    private boolean permitido = true;


    public Politica() {

    }

    public Politica(String key, boolean permitido) {

        this.key = key;
        this.permitido = permitido;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        if (permitido==true){
            return "display: block";
        }else {
            return "display: none";
        }
    }


    public boolean isPermitido() {
        return permitido;
    }

    public void setPermitido(boolean permitido) {
        this.permitido = permitido;
    }
}
