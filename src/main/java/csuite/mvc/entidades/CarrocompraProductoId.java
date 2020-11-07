package csuite.mvc.entidades;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CarrocompraProductoId implements Serializable {

    @Column(name = "CarroCompraId")
    private Long carroCompraId;

    @Column(name = "ProductoId")
    private long productoId;

    public CarrocompraProductoId() {}

    public CarrocompraProductoId(
            Long carroCompraId,
            long productoId) {
        this.carroCompraId = carroCompraId;
        this.productoId = productoId;
    }

    //Getters omitted for brevity

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        CarrocompraProductoId that = (CarrocompraProductoId) o;
        return Objects.equals(carroCompraId, that.carroCompraId) &&
                Objects.equals(productoId, that.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carroCompraId, productoId);
    }

    public Long getcarroCompraId() {
        return carroCompraId;
    }

    public void setcarroCompraId(Long carroCompraId) {
        this.carroCompraId = carroCompraId;
    }

    public long getproductoId() {
        return productoId;
    }

    public void setproductoId(long productoId) {
        this.productoId = productoId;
    }
}
