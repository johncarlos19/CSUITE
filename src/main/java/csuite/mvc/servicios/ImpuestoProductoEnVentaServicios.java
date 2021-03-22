package csuite.mvc.servicios;

import csuite.mvc.entidades.Impuesto;
import csuite.mvc.entidades.ImpuestoProductoEnVenta;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

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

    public ImpuestoProductoEnVenta getImpuestoProductoEnVenta(long id, long idPro) {


        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {

            Query query = session.createQuery("select i from ImpuestoProductoEnVenta i where i.idImpuesto.id = :id and i.idProductoEnVenta.idProducto.id = :idPro order by i.id desc ");
            query.setParameter("id",id);
            query.setParameter("idPro",idPro);
//            if (page != 0) {
//                query.setFirstResult(0 + 10 * (page - 1));
//                query.setMaxResults(10);
//            }

            //query.setParameter("nombre", apellido+"%");
            ImpuestoProductoEnVenta impuestoProductoEnVenta= (ImpuestoProductoEnVenta) query.getSingleResult();
            return impuestoProductoEnVenta;
        }finally {
            session.close();
        }



    }
    public boolean deleteImpuestoProductoEnVentaId(long id) {


        final Session session = getHibernateSession();
        Transaction txn = session.beginTransaction();
//        EntityManager em = getEntityManager();
        try {

            Query query = session.createQuery("delete from ImpuestoProductoEnVenta i where i.id = :id ").setParameter("id",id);

            query.executeUpdate();
            txn.commit();


            return true;
        }finally {
            session.close();
        }



    }

    public boolean deleteImpuestoProductoEnVenta(long id, long idPro) {


        final Session session = getHibernateSession();
        Transaction txn = session.beginTransaction();
//        EntityManager em = getEntityManager();
        try {

            Query query = session.createQuery("delete from ImpuestoProductoEnVenta i where i.idImpuesto.id = :id and i.idProductoEnVenta.idProducto.id = :idPro ");
            query.setParameter("id",id);
            query.setParameter("idPro",idPro);
            txn.commit();


//            query.executeUpdate();
            return true;
        }finally {
            session.close();
        }



    }
}
