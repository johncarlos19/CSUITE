package csuite.mvc.servicios;


import csuite.mvc.entidades.Foto;
import  csuite.mvc.entidades.Producto;
import csuite.mvc.entidades.ProductoEnVenta;
import csuite.mvc.jsonObject.ProductoJSON;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoServicios extends GestionadDB<Producto> {


    private static ProductoServicios instancia;



    public static ProductoServicios getInstancia(){
        if(instancia==null){
            instancia = new ProductoServicios();
        }
        return instancia;
    }

    public ProductoServicios() {
        super(Producto.class);
    }


    public ArrayList<Producto> listaProductoCompleto () {

        return (ArrayList<Producto>) ListadoCompleto();
    }

    public List<ProductoJSON> getListaProductoJson(String user){
        List<ProductoJSON> productoJSONS = new ArrayList<ProductoJSON>();
        for ( Producto aux : listaProducto(0,user)
        ) {
            System.out.println("\n\n\nIdddddd"+aux.getId());
            productoJSONS.add(aux.getProductoJSON(1));

        }
        return productoJSONS;

    }
    public ArrayList<Producto> listaProducto(int page, String user) {

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
        List<Producto> lista = null;
        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {

        Query query = session.createQuery("select p from Vendedor v inner join v.productoList p inner join p.foto f where v.idVendedor.usuario = '"+user+"' order by p.id desc " );

        if (page != 0){
            query.setFirstResult(0+10*(page-1));
            query.setMaxResults(5);
        }
        //query.setParameter("nombre", apellido+"%");
            lista = query.getResultList();
            System.out.println("\n\n\n\n\nCantidadLista +"+ ((ArrayList<Producto>) lista).size());

    } finally {
            session.close();
    }return (ArrayList<Producto>) lista;


    }

    public long cantidadProductos(String user){
        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {

            Query query = session.createQuery("select count(*) from Vendedor v inner join v.productoList p where v.idVendedor.usuario = '"+user+"'   " );

            //query.setParameter("nombre", apellido+"%");
            long lista = (long)query.getSingleResult() ;
            return lista;
        } finally {
            session.close();
        }

    }
    public ArrayList<Producto> listaProductoRecienAgregado(int page) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select cp from Producto cp ");
        query.setFirstResult(0+10*(page-1));
        query.setMaxResults(10);
        //query.setParameter("nombre", apellido+"%");
        List<Producto> lista = query.getResultList();
        return (ArrayList<Producto>) lista;
    }
    /*
    public Set<Producto> listaProductoCarroCompra(long ID_carrocompra){

        Set<Producto> list = new HashSet<>();
        Connection con = null; //objeto conexion.
        try {
            //
            String query = "select pr.ID, pr.NOMBRE ,pr.PRECIO, cpr.CANTIDAD from PRODUCTO pr, CARROCOMPRAPRODUCTO cpr, CARROCOMPRA c where ? = cpr.IDCARROCOMPRA and cpr.IDPRODUCTO = pr.ID group by pr.ID, pr.NOMBRE, pr.PRECIO, cpr.CANTIDAD;";
            con = DataBaseServices.getInstancia().getConexion(); //referencia a la conexion.
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            prepareStatement.setLong(1, ID_carrocompra);
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                Producto produ = new Producto(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3));
                produ.setCantidad(rs.getInt(4));
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
        }
        return list;
    }*/


    public Producto crearProducto(Producto produ) {
        return (Producto) crear(produ);
    }

    public boolean updateProducto(Producto produ) {

        /*
        boolean ok = false;

        Connection con = null;
        try {

            String query = "update PRODUCTO SET NOMBRE = ?, PRECIO = ? where ID = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, produ.getNombre());
            prepareStatement.setBigDecimal(2, produ.getPrecio());
            //Indica el where...
            prepareStatement.setInt(3, produ.getId());
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ProductoServicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductoServicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/

        boolean hh = true;
        if (editar(produ)==null){
            hh = false;
        }
        return hh;
    }

    public boolean borrarProducto(int ID) {/*
        boolean ok = false;

        Connection con = null;
        try {
            String query = "delete FROM CARROCOMPRAPRODUCTO where IDPRODUCTO = ?;";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);

            //Indica el where...
            prepareStatement.setInt(1, ID);
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0;

            query = "delete from PRODUCTO where ID = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            prepareStatement = con.prepareStatement(query);

            //Indica el where...
            prepareStatement.setInt(1, ID);
            //
            fila = prepareStatement.executeUpdate();
            ok = fila > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ProductoServicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductoServicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/

        return eliminar(ID);
    }

    public Producto getProductoSinFoto(long id) {
        Producto lista = null;
        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {

            Query query = session.createQuery("select p from Producto p where p.id = :id order by p.id desc " );
            query.setParameter("id",id);

            //query.setParameter("nombre", apellido+"%");
            lista = (Producto) query.getSingleResult();

        } finally {
            session.close();
        }return (Producto) lista;
    }
    public Producto getProductoConFoto(long id) {
        Producto lista = null;
        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {

            Query query = session.createQuery("select p from Producto p  inner join p.foto f where p.id = :id order by p.id desc " );
            query.setParameter("id",id);

            //query.setParameter("nombre", apellido+"%");
            lista = (Producto) query.getSingleResult();

        } finally {
            session.close();
        }return (Producto) lista;
    }
//    public boolean deleteFoto(Producto producto, Foto foto){
//        EntityManager em = getEntityManager();
//        em.getTransaction().begin();
//
//        Producto b = em.find(Producto.class, producto.getId());
//        b.getFotos().remove(foto);
//
//        em.getTransaction().commit();
//        em.close();
//        return true;
//    }

    public int getIdentityMax() {
        int max = -1;
        Connection con = null;
        try {
            //utilizando los comodines (?)...
            String query = "select max(ID) from PRODUCTO";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            //Ejecuto...
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                max = rs.getInt(1);
            }
            return max;

        } catch (SQLException ex) {
            Logger.getLogger(CarroCompraServicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CarroCompraServicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }
}

