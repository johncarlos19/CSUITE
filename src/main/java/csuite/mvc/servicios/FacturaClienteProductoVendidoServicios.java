package csuite.mvc.servicios;

import csuite.mvc.entidades.FacturaClienteProductoVendido;
import csuite.mvc.entidades.Usuario;
import org.hibernate.Session;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

public class FacturaClienteProductoVendidoServicios extends GestionadDB<FacturaClienteProductoVendido>{

    private static FacturaClienteProductoVendidoServicios instancia;



    public static FacturaClienteProductoVendidoServicios getInstancia(){
        if(instancia==null){
            instancia = new FacturaClienteProductoVendidoServicios();
        }
        return instancia;
    }

    public FacturaClienteProductoVendidoServicios() {
        super(FacturaClienteProductoVendido.class);
    }



    public FacturaClienteProductoVendido verifyProducto(String idFactura, long idProducto){
        long cant = 0;


        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {


//        Query query = em.createQuery("select c.idCliente from Vendedor v, Cliente c join fetch v.clientes where v.idVendedor.usuario = '"+user+"' group by c.idCliente");
            Query query = session.createQuery("select fcp from FacturaClienteProductoVendido fcp inner join fcp.impuestoProducto fcpi where fcp.idFacturaCliente.idFactura = :idFactura and fcp.idProducto.id = :idProducto" );
            query.setParameter("idFactura",idFactura);
            query.setParameter("idProducto",idProducto);

            //query.setParameter("nombre", apellido+"%");
            try {
                FacturaClienteProductoVendido facturaClienteProductoVendido = (FacturaClienteProductoVendido) query.getSingleResult();
                System.out.println("\n"+facturaClienteProductoVendido.getImpuestoProducto().size());
                session.close();
                return  facturaClienteProductoVendido;
            }catch (javax.persistence.NoResultException D){
                return null;
            }



            //query.setParameter("nombre", apellido+"%");

        } finally {
            session.close();
        }
    }
}
