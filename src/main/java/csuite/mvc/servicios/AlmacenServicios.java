package csuite.mvc.servicios;

import csuite.mvc.entidades.Almacen;
import csuite.mvc.entidades.Producto;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class AlmacenServicios extends GestionadDB<Almacen> {

    private static AlmacenServicios instancia;



    public static AlmacenServicios getInstancia(){
        if(instancia==null){
            instancia = new AlmacenServicios();
        }
        return instancia;
    }
    public AlmacenServicios() {
        super(Almacen.class);
    }

    public ArrayList<Almacen> listAlmacen(int page, long id) {

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
        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {

            Query query = session.createQuery("select p.almacenList from Producto p where p.id = '" + id + "' order by idAlmacen desc ");
            if (page != 0) {
                query.setFirstResult(0 + 10 * (page - 1));
                query.setMaxResults(10);
            }

            //query.setParameter("nombre", apellido+"%");
            List<Almacen> lista = query.getResultList();
            return (ArrayList<Almacen>) lista;
        }finally {
            session.close();
        }



    }
}
