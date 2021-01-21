package csuite.mvc.servicios;

import csuite.mvc.entidades.*;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class FacturaClienteServicios extends GestionadDB<FacturaCliente>{

    private static FacturaClienteServicios instancia;



    public static FacturaClienteServicios getInstancia(){
        if(instancia==null){
            instancia = new FacturaClienteServicios();
        }
        return instancia;
    }

    public FacturaClienteServicios() {
        super(FacturaCliente.class);
    }
    public FacturaCliente getFacturaCliente(String idFacturaCliente){
        final Session session = getHibernateSession();
        FacturaCliente facturaCliente = null;

//        EntityManager em = getEntityManager();
        try {
            try {
                Query query = session.createQuery("select fc from FacturaCliente fc inner join fc.impuestoClientes ic inner join fc.facturaClienteProductoVendidos fcv inner join fcv.impuestoProducto fcvi where fc.idFactura = :idFacturaCliente   " );
                query.setParameter("idFacturaCliente",idFacturaCliente);
                //query.setParameter("nombre", apellido+"%");
                facturaCliente = (FacturaCliente) query.getSingleResult() ;

                System.out.println("\n\nxxxxTamano debe salir"+facturaCliente.getImpuestoClientes().size());
                for (int i = 0; i < facturaCliente.getFacturaClienteProductoVendidos().size(); i++) {
                    System.out.println("\n\nxxxxIDPeroductoFactura: "+facturaCliente.getFacturaClienteProductoVendidos().get(i).getId()+" ImpuestoProducto"+facturaCliente.getFacturaClienteProductoVendidos().get(i).getImpuestoProducto().size());
                }
                return facturaCliente;
            }catch (javax.persistence.NoResultException D){
                try {
                    D.printStackTrace();
                    Query query = session.createQuery("select fc from FacturaCliente fc inner join fc.impuestoClientes ic  where fc.idFactura = :idFacturaCliente   " );
                    query.setParameter("idFacturaCliente",idFacturaCliente);
                    //query.setParameter("nombre", apellido+"%");
                    facturaCliente = (FacturaCliente) query.getSingleResult() ;
                    System.out.println("\n\nxxxxTamano debe salir"+facturaCliente.getImpuestoClientes().size());
                    return facturaCliente;
                }catch (javax.persistence.NoResultException f){
                    f.printStackTrace();
                    Query query = session.createQuery("select fc from FacturaCliente fc  where fc.idFactura = :idFacturaCliente   " );
                    query.setParameter("idFacturaCliente",idFacturaCliente);
                    //query.setParameter("nombre", apellido+"%");
                    facturaCliente = (FacturaCliente) query.getSingleResult() ;
                    return facturaCliente;
                }

            }

        } finally {
            session.close();
        }
    }

    public List<FacturaCliente> ListFacturaClienteActivaVendedor(String idFacturaCliente){
        final Session session = getHibernateSession();
        try {
            Query query = session.createQuery("select fc from FacturaCliente fc  where fc.idVendedor = :idFacturaCliente and fc.facturaGuardada = :fact " );
            query.setParameter("idFacturaCliente",idFacturaCliente);
            query.setParameter("fact",false);
            //query.setParameter("nombre", apellido+"%");
            List<FacturaCliente> facturaClienteList = (List<FacturaCliente>) query.getResultList() ;


            return facturaClienteList;

        } finally {
            session.close();
        }

    }
    public List<FacturaCliente> ListFacturaClienteActivaEmpleado(String idFacturaCliente){
        final Session session = getHibernateSession();
        try {
            Query query = session.createQuery("select fc from FacturaCliente fc  where fc.idQuienLoRealizo = :idFacturaCliente and fc.facturaGuardada = :fact " );
            query.setParameter("idFacturaCliente",idFacturaCliente);
            query.setParameter("fact",false);
            //query.setParameter("nombre", apellido+"%");
            List<FacturaCliente> facturaClienteList = (List<FacturaCliente>) query.getResultList() ;


            return facturaClienteList;

        } finally {
            session.close();
        }

    }

    public FacturaCliente crearFacturaCliente(FacturaCliente facturaCliente, String user, String idCliente) {
        boolean subio = false;
/*
        Connection con = null;
        try {

            String query = "insert into USUARIO(NOMBRE, USER, PASSWORD) VALUES (?,?,?);";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, user.getNombre());
            prepareStatement.setString(2, user.getUsuario());
            prepareStatement.setString(3, user.getPassword());
            //
            int fila = prepareStatement.executeUpdate();
            subio = fila > 0 ;

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioServicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioServicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        Cliente cliente = ClienteServicios.getInstancia().getCliente(idCliente);
        facturaCliente.setIdCliente(idCliente);
        facturaCliente.setNombreCliente(cliente.getIdCliente().getNombre() + " "+cliente.getIdCliente().getApellido());
        FacturaCliente facturaClienteAux = (FacturaCliente) crear(facturaCliente);
         facturaClienteAux.getId();
        facturaClienteAux = (FacturaCliente) buscar(facturaClienteAux.getId());
        List<Impuesto> impuestos = ImpuestoServicios.getInstancia().listaImpuestoAplicableATodos((String) Mercado.getInstance().getUserJefe(user).get("user"));
        List<ImpuestoCliente> impuestoClientes = new ArrayList<ImpuestoCliente>();
        for (Impuesto impuesto : impuestos) {
            ImpuestoCliente aux = new ImpuestoCliente();
            aux.setNombre(impuesto.getNombre());
            aux.setOperacion(impuesto.getOperacion());
            aux.setValorSumandoExtra(0);
            aux = (ImpuestoCliente) ImpuestoClienteServicios.getInstancia().crear(aux);
            facturaCliente.addImpuestoCliente(aux);
        }
        facturaCliente = (FacturaCliente) editar(facturaCliente);


        cliente.addFacturaCliente(facturaCliente);
        ClienteServicios.getInstancia().editar(cliente);
        facturaCliente = (FacturaCliente) getFacturaCliente(facturaClienteAux.getId());
        //usuario.setUsuario(id);

        return  facturaCliente;//editar(usuario);
    }
}
