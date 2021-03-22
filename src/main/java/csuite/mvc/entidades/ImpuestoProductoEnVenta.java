package csuite.mvc.entidades;

import javax.persistence.*;
import javax.xml.namespace.QName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ImpuestoProductoEnVenta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    @EmbeddedId
//    private ImpuestoProductoEnVentaId id;

    @ManyToOne()
//    @MapsId("idImpuesto")
    @JoinColumn(name = "idImpuesto")
    private Impuesto idImpuesto;


    @ManyToOne
//    @MapsId("idProductoEnVenta")
    @JoinColumn(name = "idProductoEnVenta")
    private ProductoEnVenta idProductoEnVenta;


    public ImpuestoProductoEnVenta() {
    }/*PostTag postTag = new PostTag(this, tag);
        tags.add(postTag);
        tag.getPosts().add(postTag);*/

    public ImpuestoProductoEnVenta(Impuesto idImpuesto, ProductoEnVenta idproductoEnVenta) {
        this.idImpuesto = idImpuesto;
        this.idProductoEnVenta = idproductoEnVenta;
    }

//    public BigDecimal getTotal(){
//        double tot = producto.getPrecio().doubleValue() * cantidad;
//        BigDecimal total = BigDecimal.valueOf(tot).setScale(2);
//        return total;
//    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


//    public ImpuestoProductoEnVentaId getId() {
//        return id;
//    }
//
//    public void setId(ImpuestoProductoEnVentaId id) {
//        this.id = id;
//    }

    public ProductoEnVenta getIdProductoEnVenta() {
        return idProductoEnVenta;
    }

    public void setIdProductoEnVenta(ProductoEnVenta idProductoEnVenta) {
        this.idProductoEnVenta = idProductoEnVenta;
    }

    public Impuesto getIdImpuesto() {
        return idImpuesto;
    }

    public void setIdImpuesto(Impuesto idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    public ProductoEnVenta getIdproductoEnVenta() {
        return idProductoEnVenta;
    }

    public void setIdproductoEnVenta(ProductoEnVenta idproductoEnVenta) {
        this.idProductoEnVenta = idproductoEnVenta;
    }
}

