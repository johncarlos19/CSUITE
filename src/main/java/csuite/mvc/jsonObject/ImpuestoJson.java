package csuite.mvc.jsonObject;

public class ImpuestoJson {
    private long id;
    private long idUnion;
    private String nombre;
    private String operacion;
    private double valorSumandoExtra;
    private double aux;

    public ImpuestoJson(long id, String nombre, String operacion, double valorSumandoExtra) {
        this.id = id;
        this.nombre = nombre;
        this.operacion = operacion;
        this.valorSumandoExtra = valorSumandoExtra;
    }
    public double getPrecioNeto(){
        double neto = 0;
        if (operacion.equalsIgnoreCase("Porciento")){
            neto = this.aux*((double)valorSumandoExtra/100);
        }else if (operacion.equalsIgnoreCase("Suma de Cantidad")){
            neto = valorSumandoExtra;
        }else if (operacion.equalsIgnoreCase("Descuento Absoluto")){
            neto = -1*valorSumandoExtra;
        }else if (operacion.equalsIgnoreCase("Descuento Porcentual")){
            neto = -1*this.aux*((double)valorSumandoExtra/100);
        }else{
            neto = 0;
        }
        return neto;
    }

    public double getAux() {
        return aux;
    }

    public void setAux(double aux) {
        this.aux = aux;
    }

    public long getIdUnion() {
        return idUnion;
    }

    public void setIdUnion(long idUnion) {
        this.idUnion = idUnion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public double getValorSumandoExtra() {
        return valorSumandoExtra;
    }

    public void setValorSumandoExtra(double valorSumandoExtra) {
        this.valorSumandoExtra = valorSumandoExtra;
    }
}
