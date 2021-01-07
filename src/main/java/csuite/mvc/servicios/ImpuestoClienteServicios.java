package csuite.mvc.servicios;

import csuite.mvc.entidades.Categoria;
import csuite.mvc.entidades.ImpuestoCliente;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class ImpuestoClienteServicios  extends GestionadDB<ImpuestoCliente>{

    private static ImpuestoClienteServicios instancia;



    public static ImpuestoClienteServicios getInstancia(){
        if(instancia==null){
            instancia = new ImpuestoClienteServicios();
        }
        return instancia;
    }

    public List<ImpuestoCliente> ListaImpuestoFacturaCliente(String idFactura){
        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {

            Query query = session.createQuery("select f.impuestoClientes from FacturaCliente f where f.idFactura = '"+idFactura+"' " );
//            query.setParameter("idFactura",idFactura);

            //query.setParameter("nombre", apellido+"%");
            List<ImpuestoCliente> lista = query.getResultList();
            System.out.println("\n\ncantidad"+lista.size());
            return (ArrayList<ImpuestoCliente>) lista;
        } finally {
            session.close();
        }

    }
    public ImpuestoClienteServicios() {
        super(ImpuestoCliente.class);
    }
}
