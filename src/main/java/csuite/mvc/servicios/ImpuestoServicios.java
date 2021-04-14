package csuite.mvc.servicios;

import csuite.mvc.entidades.*;
import csuite.mvc.jsonObject.ImpuestoJson;
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


    public long getCantImpuesto(String user) {

        long cant = -1;
        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();


//        Query query = em.createQuery("select c.idCliente from Vendedor v, Cliente c join fetch v.clientes where v.idVendedor.usuario = '"+user+"' group by c.idCliente");
        try {
            Query query = session.createQuery("select count(i) from Vendedor v inner join v.impuestos i where v.idVendedor.usuario = '"+user+"' " );

            //query.setParameter("nombre", apellido+"%");
            cant = (long) query.getSingleResult();
            System.out.println("Cantidad"+cant);


            return cant;
        } finally {
            session.close();
        }




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
        System.out.println("\n\n\n\nUsuario"+user);


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
    public ArrayList<Impuesto> impuestoProductoNotAdded( long id, String user) {


        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {

            Query query = session.createQuery("select i from ImpuestoProductoEnVenta imp, Vendedor v inner join v.impuestos i where imp.idProductoEnVenta.idProducto.id = :id and imp.idImpuesto != i.id and v.idVendedor.usuario = :user order by i.id desc ");
            query.setParameter("id",id).setParameter("user",user);
//            if (page != 0) {
//                query.setFirstResult(0 + 10 * (page - 1));
//                query.setMaxResults(10);
//            }

            //query.setParameter("nombre", apellido+"%");
            List<Impuesto> lista = query.getResultList();
            return (ArrayList<Impuesto>) lista;
        }finally {
            session.close();
        }



    }
    public ArrayList<ImpuestoJson> impuestoFacturaNotAdded(String user, FacturaCliente factura) {


        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {

            Query query = session.createQuery("select i from Vendedor v inner join v.impuestos i where v.idVendedor.usuario = :user and i.aplicarATodos = false order by i.id desc ");
            query.setParameter("user",user);
//            if (page != 0) {
//                query.setFirstResult(0 + 10 * (page - 1));
//                query.setMaxResults(10);
//            }

            //query.setParameter("nombre", apellido+"%");
            List<Impuesto> lista = query.getResultList();
            List<ImpuestoJson> listaAdd = new ArrayList<ImpuestoJson>();
            boolean deboAdd = true;

            for (int i = 0; i < lista.size(); i++) {
                for (ImpuestoCliente impuestoCliente : factura.getImpuestoClientes()) {
                    if (lista.get(i).getNombre().equalsIgnoreCase(impuestoCliente.getNombre()) && lista.get(i).getOperacion().equalsIgnoreCase(impuestoCliente.getOperacion())){
                        deboAdd = false;
                    }
                }
                if (deboAdd==true){
                    listaAdd.add(lista.get(i).damImpuestoJson((double) factura.getTotal(),-1,true));
                    deboAdd = true;
                }else {
                    deboAdd = true;
                }
            }

            return (ArrayList<ImpuestoJson>) listaAdd;
        }finally {
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
