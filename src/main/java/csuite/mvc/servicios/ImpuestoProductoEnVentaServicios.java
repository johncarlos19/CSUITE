package csuite.mvc.servicios;

import csuite.mvc.entidades.Impuesto;
import csuite.mvc.entidades.ImpuestoProductoEnVenta;

public class ImpuestoProductoEnVentaServicios extends GestionadDB<ImpuestoProductoEnVenta> {
    private static ImpuestoProductoEnVentaServicios instancia;



    public static ImpuestoProductoEnVentaServicios getInstancia(){
        if(instancia==null){
            instancia = new ImpuestoProductoEnVentaServicios();
        }
        return instancia;
    }
    public ImpuestoProductoEnVentaServicios() {
        super(ImpuestoProductoEnVenta.class);
    }
}
