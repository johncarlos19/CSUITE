package csuite.mvc.entidades;

import io.javalin.http.sse.SseClient;

public class VentasSession {
    private SseClient sseClient;
    private String idJefe;
    private String user;
    private boolean vaEnviar = false;
    private long producoAEnviar = -1;

    public VentasSession(SseClient sseClient, String idJefe, String user) {
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

    public void setVaEnviar(boolean vaEnviar) {
        this.vaEnviar = vaEnviar;
    }

    public long getProducoAEnviar() {
        return producoAEnviar;
    }

    public void setProducoAEnviar(long producoAEnviar) {
        this.producoAEnviar = producoAEnviar;
    }

    public SseClient getSseClient() {
        return sseClient;
    }

    public void setSseClient(SseClient sseClient) {
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
