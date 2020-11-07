package csuite.mvc.servicios;

import csuite.mvc.entidades.Empleado;
import csuite.mvc.entidades.Usuario;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoServicios extends GestionadDB<Empleado>{


    private static EmpleadoServicios instancia;



    public static EmpleadoServicios getInstancia(){
        if(instancia==null){
            instancia = new EmpleadoServicios();
        }
        return instancia;
    }
    public EmpleadoServicios() {
        super(Empleado.class);
    }

    public String getJefe(String empleadp){


        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {




//        Query query = em.createQuery("select c.idCliente from Vendedor v, Cliente c join fetch v.clientes where v.idVendedor.usuario = '"+user+"' group by c.idCliente");
        Query query = session.createQuery("select v.idVendedor.id from Vendedor v inner join v.empleados e where e.idEmpleado.id = '"+empleadp+"' " );

        //query.setParameter("nombre", apellido+"%");
        String user = (String) query.getSingleResult();
        System.out.println("Cantidad"+user);


        return user;
    } finally {
        session.close();
    }
    }

    public ArrayList<Usuario> listaEmpleado(String user) {


        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {


//        Query query = em.createQuery("select c.idCliente from Vendedor v, Cliente c join fetch v.clientes where v.idVendedor.usuario = '"+user+"' group by c.idCliente");
        Query query = session.createQuery("select e.idEmpleado from Vendedor v inner join v.empleados e where v.idVendedor.usuario = '"+user+"' " );

        //query.setParameter("nombre", apellido+"%");
        List<Usuario> lista = query.getResultList();
        System.out.println("Cantidad"+lista.size());


        return (ArrayList<Usuario>) lista;
    } finally {
        session.close();
    }


    }
}
