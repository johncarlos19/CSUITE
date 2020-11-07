package csuite.mvc.servicios;

import csuite.mvc.entidades.FacturaCliente;

public class FacturaClienteServicios extends GestionadDB<FacturaCliente>{

    private static FacturaClienteServicios instancia;



    public static FacturaClienteServicios getInstancia(){
        if(instancia==null){
            instancia = new FacturaClienteServicios();
        }
        return instancia;
    }

    public FacturaClienteServicios() {
        super(FacturaCliente.class);
    }
}
