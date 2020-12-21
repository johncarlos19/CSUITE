package csuite.mvc.servicios;

import csuite.mvc.entidades.Impuesto;
import csuite.mvc.entidades.Usuario;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class ImpuestoServicios extends GestionadDB<Impuesto>{

    private static ImpuestoServicios instancia;



    public static ImpuestoServicios getInstancia(){
        if(instancia==null){
            instancia = new ImpuestoServicios();
        }
        return instancia;
    }
    public ImpuestoServicios() {
        super(Impuesto.class);
    }

    public ArrayList<Impuesto> listaImpuesto(String user) {


        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();


//        Query query = em.createQuery("select c.idCliente from Vendedor v, Cliente c join fetch v.clientes where v.idVendedor.usuario = '"+user+"' group by c.idCliente");
        try {
            Query query = session.createQuery("select i from Vendedor v inner join v.impuestos i where v.idVendedor.usuario = '"+user+"' " );

            //query.setParameter("nombre", apellido+"%");
            List<Impuesto> lista = query.getResultList();
            System.out.println("Cantidad"+lista.size());


            return (ArrayList<Impuesto>) lista;
        } finally {
            session.close();
        }




    }

    public ArrayList<Impuesto> listaImpuestoAplicableATodos(String user) {


        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();


//        Query query = em.createQuery("select c.idCliente from Vendedor v, Cliente c join fetch v.clientes where v.idVendedor.usuario = '"+user+"' group by c.idCliente");
        try {
            Query query = session.createQuery("select i from Vendedor v inner join v.impuestos i where v.idVendedor.usuario = :user and i.aplicarATodos = :valor" );
            query.setParameter("valor",true);
            query.setParameter("user",user);
            //query.setParameter("nombre", apellido+"%");
            List<Impuesto> lista = query.getResultList();
            System.out.println("Cantidad"+lista.size());


            return (ArrayList<Impuesto>) lista;
        } finally {
            session.close();
        }




    }
    public Impuesto getImpuesto(long id) {


        final Session session = getHibernateSession();

        Impuesto impuesto = null;
//        EntityManager em = getEntityManager();


//        Query query = em.createQuery("select c.idCliente from Vendedor v, Cliente c join fetch v.clientes where v.idVendedor.usuario = '"+id+"' group by c.idCliente");
        try {
            Query query = session.createQuery("select i from Impuesto i  where i.id = :id " );
            query.setParameter("id",id);

            //query.setParameter("nombre", apellido+"%");
            impuesto = (Impuesto) query.getSingleResult();
            System.out.println("Cantidad"+impuesto.getImpuestoProductoEnVentas().size());


            return impuesto;
        } finally {
            session.close();
        }




    }
}
