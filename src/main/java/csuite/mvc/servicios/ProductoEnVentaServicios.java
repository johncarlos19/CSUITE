package csuite.mvc.servicios;

import csuite.mvc.entidades.ProductoComprado;
import csuite.mvc.entidades.ProductoEnVenta;
import csuite.mvc.jsonObject.ProductoJSON;

import java.util.ArrayList;
import java.util.List;

public class ProductoEnVentaServicios extends GestionadDB<ProductoEnVenta> {
    private static ProductoEnVentaServicios instancia;



    public static ProductoEnVentaServicios getInstancia(){
        if(instancia==null){
            instancia = new ProductoEnVentaServicios();
        }
        return instancia;
    }


    public ProductoEnVentaServicios() {
        super(ProductoEnVenta.class);
    }


    public List<ProductoJSON> getListaProductoJson(){
        List<ProductoJSON> productoJSONS = new ArrayList<ProductoJSON>();
        for ( ProductoEnVenta aux : ListadoCompleto()
             ) {
            System.out.println("\n\n\nIdddddd"+aux.getIdProducto().getId());
            productoJSONS.add(aux.getProductoJSON());

        }
        return productoJSONS;

    }
}
