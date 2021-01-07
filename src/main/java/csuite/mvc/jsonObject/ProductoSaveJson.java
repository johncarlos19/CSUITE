package csuite.mvc.jsonObject;

public class ProductoSaveJson {
    private String nombre;
    private String codigo;
    private String descripcion;
    private String stock;
    private String suplidor;
    private String compra;
    private String venta;
    private String mimetype;
    private String base64;
    private String nombreImg;
    private String categorias;
    private String idFactura;
    private long idProducto;

    public ProductoSaveJson() {
    }

    public ProductoSaveJson(String nombre, String codigo, String descripcion, String stock, String suplidor, String compra, String venta, String mimetype, String base64) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.stock = stock;
        this.suplidor = suplidor;
        this.compra = compra;
        this.venta = venta;
        this.mimetype = mimetype;
        this.base64 = base64;
    }

    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    public String getNombreImg() {
        return nombreImg;
    }

    public void setNombreImg(String nombreImg) {
        this.nombreImg = nombreImg;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getSuplidor() {
        return suplidor;
    }

    public void setSuplidor(String suplidor) {
        this.suplidor = suplidor;
    }

    public String getCompra() {
        return compra;
    }

    public void setCompra(String compra) {
        this.compra = compra;
    }

    public String getVenta() {
        return venta;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
