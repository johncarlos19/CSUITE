package csuite.mvc.servicios;

import csuite.mvc.entidades.Cliente;
import csuite.mvc.entidades.Producto;
import csuite.mvc.entidades.Usuario;
import csuite.mvc.entidades.Vendedor;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class ClienteServicios extends GestionadDB<Cliente>{


    private static ClienteServicios instancia;



    public static ClienteServicios getInstancia(){
        if(instancia==null){
            instancia = new ClienteServicios();
        }
        return instancia;
    }

    public ClienteServicios( ) {
        super(Cliente.class);
    }
    public Cliente getCliente(String aux){
        long cant = 0;


        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {


//        Query query = em.createQuery("select c.idCliente from Vendedor v, Cliente c join fetch v.clientes where v.idVendedor.usuario = '"+user+"' group by c.idCliente");
            Query query = session.createQuery("select u from Usuario u inner join u.cliente c where u.usuario = :aux " );

            query.setParameter("aux", aux);
            Usuario lista = (Usuario) query.getSingleResult();
            System.out.println("\n\nNombre1 "+lista.getCliente().getIdCliente().getUsuario());
            System.out.println("\n\nNombre1 "+lista.getCliente().getFacturaClientes().size());
//            System.out.println("\n\nNombre "+lista.getNombre());
            return lista.getCliente();


            //query.setParameter("nombre", apellido+"%");




        } finally {
            session.close();
        }

    }


    public Usuario getClienteUsuario(String user, String id) {
        long cant = 0;


        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {


//        Query query = em.createQuery("select c.idCliente from Vendedor v, Cliente c join fetch v.clientes where v.idVendedor.usuario = '"+user+"' group by c.idCliente");
            Query query = session.createQuery("select c.idCliente from Vendedor v inner join v.clientes c where v.idVendedor.usuario = '"+user+"' " );

            //query.setParameter("nombre", apellido+"%");
            List<Usuario> lista =  query.getResultList();
            System.out.println("Cantidad"+lista.size());

            try {
            for (Usuario usuario :  lista) {
                System.out.println("\n\nimprimio"+usuario.getUsuario());
                    if (usuario.getUsuario().equalsIgnoreCase(id) || usuario.getDocumento().equalsIgnoreCase(id)){

                        System.out.println("\n\nimprimio123");
                        session.close();
                        return usuario;

                    }


            }}catch (NullPointerException e){
            return null;
        }

            //query.setParameter("nombre", apellido+"%");



            return  null;
        } finally {
            session.close();
        }


    }

    public ArrayList<Usuario> listaCliente(String user) {


        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {


//        Query query = em.createQuery("select c.idCliente from Vendedor v, Cliente c join fetch v.clientes where v.idVendedor.usuario = '"+user+"' group by c.idCliente");
        Query query = session.createQuery("select c.idCliente from Vendedor v inner join v.clientes c where v.idVendedor.usuario = '"+user+"' " );

        //query.setParameter("nombre", apellido+"%");
        List<Usuario> lista = query.getResultList();
        System.out.println("Cantidad"+lista.size());
        for (Usuario aux:lista
             ) {
            System.out.println("ss:"+aux.getCliente().getIdClienteLocal());
        }

        return (ArrayList<Usuario>) lista;
    } finally {
        session.close();
    }


    }

    public long cantidadCliente(String user){
        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {

            Query query = session.createQuery("select count(*) from Vendedor v inner join v.clientes c where v.idVendedor.usuario = '"+user+"'   " );

            //query.setParameter("nombre", apellido+"%");
            long lista = (long)query.getSingleResult() ;
            return lista;
        } finally {
            session.close();
        }

    }
}
