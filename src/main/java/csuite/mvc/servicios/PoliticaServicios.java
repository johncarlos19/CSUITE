package csuite.mvc.servicios;

import csuite.mvc.entidades.Politica;
import csuite.mvc.entidades.Producto;
import csuite.mvc.entidades.ProductoComprado;

public class PoliticaServicios extends GestionadDB<Politica>{


    private static PoliticaServicios instancia;



    public static PoliticaServicios getInstancia(){
        if(instancia==null){
            instancia = new PoliticaServicios();
        }
        return instancia;
    }

    public PoliticaServicios() {
        super(Politica.class);
    }
}
