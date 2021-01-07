package csuite.mvc.entidades;

import io.javalin.http.sse.SseClient;

public class VentasSession {
    private SseClient sseClient;
    private String idJefe;
    private String user;

    public VentasSession(SseClient sseClient, String idJefe, String user) {
        this.sseClient = sseClient;
        this.idJefe = idJefe;
        this.user = user;
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
