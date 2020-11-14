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
        return find(aux);
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
