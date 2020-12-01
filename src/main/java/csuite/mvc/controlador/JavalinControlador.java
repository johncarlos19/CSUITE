package csuite.mvc.controlador;

import io.javalin.Javalin;


public abstract class JavalinControlador {

    protected static Javalin app;

    public JavalinControlador(Javalin app) {
        this.app = app;
    }

    abstract public void aplicarRutas();

}
