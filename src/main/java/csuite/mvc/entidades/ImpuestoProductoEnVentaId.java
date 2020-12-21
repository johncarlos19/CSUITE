package csuite.mvc.entidades;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ImpuestoProductoEnVentaId implements Serializable {

    @Column(name = "idImpuesto")
    private long idImpuesto;

    @Column(name = "idProductoEnVenta")
    private long idProductoEnVenta;

    public ImpuestoProductoEnVentaId() {}

    public ImpuestoProductoEnVentaId(
            long idImpuesto,
            long idProductoEnVenta) {
        this.idImpuesto = idImpuesto;
        this.idProductoEnVenta = idProductoEnVenta;
    }

    //Getters omitted for brevity

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ImpuestoProductoEnVentaId that = (ImpuestoProductoEnVentaId) o;
        return Objects.equals(idImpuesto, that.idImpuesto) &&
                Objects.equals(idProductoEnVenta, that.idProductoEnVenta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idImpuesto, idProductoEnVenta);
    }

    public long getidImpuesto() {
        return idImpuesto;
    }

    public void setidImpuesto(long idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    public long getidProductoEnVenta() {
        return idProductoEnVenta;
    }

    public void setidProductoEnVenta(long idProductoEnVenta) {
        this.idProductoEnVenta = idProductoEnVenta;
    }
}
