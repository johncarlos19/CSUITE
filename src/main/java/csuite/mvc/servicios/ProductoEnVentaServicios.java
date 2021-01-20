package csuite.mvc.servicios;

import csuite.mvc.entidades.Producto;
import csuite.mvc.entidades.ProductoComprado;
import csuite.mvc.entidades.ProductoEnVenta;
import csuite.mvc.jsonObject.ProductoJSON;
import org.hibernate.Session;

import javax.persistence.Query;
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

    public ProductoEnVenta getVentaProducto(long id, String user) {


        ProductoEnVenta productoEnVenta = null;
        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {

            Query query = session.createQuery("select pv from Vendedor v inner join v.productoList p inner join p.productoEnVenta pv where v.idVendedor.usuario = :user and p.id = :id order by p.id desc " );
            query.setParameter("id",id);
            query.setParameter("user",user);



            //query.setParameter("nombre", apellido+"%");
            productoEnVenta = (ProductoEnVenta) query.getSingleResult();


        } finally {
            session.close();
        }return productoEnVenta;


    }

    public ArrayList<ProductoEnVenta> listaVentaProducto(int page, String user) {

        /*Session session = sessionFactory.openSession();
Query query = sess.createQuery("From Foo");
query.setFirstResult(0);
query.setMaxResults(10);
List<Foo> fooList = fooList = query.list();*/
        /*
        ArrayList<Producto> list = new ArrayList<>();
        Connection con = null; //objeto conexion.
        try {
            //
            String query = "select * from PRODUCTO ";
            con = DataBaseServices.getInstancia().getConexion(); //referencia a la conexion.
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                Producto produ = new Producto(rs.getInt("ID"), rs.getString("NOMBRE"), rs.getBigDecimal("PRECIO"));

                list.add(produ);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoServicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductoServicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        List<ProductoEnVenta> lista = null;
        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {

            Query query = session.createQuery("select pv from Vendedor v inner join v.productoList p inner join p.productoEnVenta pv where v.idVendedor.usuario = '"+user+"' order by p.id desc " );



            //query.setParameter("nombre", apellido+"%");
            lista = query.getResultList();
            System.out.println("\nCantidad"+lista.size());

        } finally {
            session.close();
        }return (ArrayList<ProductoEnVenta>) lista;


    }


    public ProductoEnVentaServicios() {
        super(ProductoEnVenta.class);
    }


    public List<ProductoJSON> getListaProductoJson(){
        List<ProductoJSON> productoJSONS = new ArrayList<ProductoJSON>();
        for ( ProductoEnVenta aux : ListadoCompleto()
             ) {
            System.out.println("\n\n\nIdddddd"+aux.getIdProducto().getId());
            productoJSONS.add(aux.getProductoJSON(1));

        }
        return productoJSONS;

    }
}
