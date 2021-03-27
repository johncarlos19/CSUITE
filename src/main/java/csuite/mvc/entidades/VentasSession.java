package csuite.mvc.entidades;

import io.javalin.http.sse.SseClient;
import org.eclipse.jetty.websocket.api.Session;

public class VentasSession {
    private Session sseClient;
    private String idJefe;
    private String user;
    private String idSession;
    private boolean vaEnviar = false;
    private long producoAEnviar = -1;

    public VentasSession(Session sseClient, String idJefe, String user) {
        this.sseClient = sseClient;
        this.idJefe = idJefe;
        this.user = user;
    }

    public boolean isVaEnviar() {
        return vaEnviar;
    }
    public void activateVaEnviar(long produc) {
        this.producoAEnviar = produc;
        this.vaEnviar = true;
    }
    public void desactivateVaEnviar() {
        this.producoAEnviar = -1;
        this.vaEnviar = false;
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public void setVaEnviar(boolean vaEnviar) {
        this.vaEnviar = vaEnviar;
    }

    public long getProducoAEnviar() {
        return producoAEnviar;
    }

    public void setProducoAEnviar(long producoAEnviar) {
        this.producoAEnviar = producoAEnviar;
    }

    public Session getSseClient() {
        return sseClient;
    }

    public void setSseClient(Session sseClient) {
        this.sseClient = sseClient;
    }

    public String getIdJefe() {
        return idJefe;
    }

    public void setIdJefe(String idJefe) {
        this.idJefe = idJefe;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
