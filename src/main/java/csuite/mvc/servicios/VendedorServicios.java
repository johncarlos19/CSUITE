package csuite.mvc.servicios;


import csuite.mvc.entidades.Usuario;
import csuite.mvc.entidades.Vendedor;

public class VendedorServicios extends GestionadDB<Vendedor>{

    private static VendedorServicios instancia;



    public static VendedorServicios getInstancia(){
        if(instancia==null){
            instancia = new VendedorServicios();
        }
        return instancia;
    }
    public VendedorServicios() {
        super(Vendedor.class);
    }

    public Vendedor getVendedor(String aux){
        return find(aux);
    }

}


