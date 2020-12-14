package csuite.mvc.servicios;

import csuite.mvc.entidades.Categoria;
import csuite.mvc.entidades.Mercado;
import csuite.mvc.entidades.Usuario;
import csuite.mvc.entidades.Vendedor;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UsuarioServicios extends GestionadDB<Usuario> {


    private static UsuarioServicios instancia;


    public static UsuarioServicios getInstancia(){
        if(instancia==null){
            instancia = new UsuarioServicios();
        }
        return instancia;
    }




    public UsuarioServicios() {
        super(Usuario.class);
    }

    public ArrayList<Usuario> listaUsuario() {
        /*
        ArrayList<Usuario> list = new ArrayList<>();
        Connection con = null; //objeto conexion.
        try {
            //
            String query = "select * from USUARIO ";
            con = DataBaseServices.getInstancia().getConexion(); //referencia a la conexion.
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                Usuario user = new Usuario(rs.getString("USER"),rs.getString("NOMBRE"),rs.getString("PASSWORD"));

                list.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioServicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioServicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        return (ArrayList<Usuario>) ListadoCompleto();
    }

    public Usuario crearUsuario(Usuario user) {
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
        String id = user.getUsuario();
        Usuario usuario = (Usuario) createAndReturnObjectWithUniqueId(user);
        usuario = find(id);
        usuario.setPoliticaList(Mercado.getInstance().getListaPolitica(user.getPerfil(), 1,usuario));
        usuario = (Usuario) editar(usuario);
        //usuario.setUsuario(id);

        return  usuario;//editar(usuario);
    }

    public Usuario crearUsuariEmpleado(Usuario user, int accion) {
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
        String id = user.getUsuario();
        Usuario usuario = (Usuario) createAndReturnObjectWithUniqueId(user);
        usuario.setPoliticaList(Mercado.getInstance().getListaPolitica(user.getPerfil(), accion,usuario));
        usuario = (Usuario) editar(usuario);
        //usuario.setUsuario(id);

        return  usuario;//editar(usuario);
    }

    public boolean updateProducto(Usuario user) {
        boolean ok = false;
        /*
        Connection con = null;
        try {

            String query = "update USUARIO SET PASSWORD = ? where USER = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, user.getPassword());
            //Indica el where...
            prepareStatement.setString(2, user.getUsuario());
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioServicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioServicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        boolean hh = true;
        if (editar(user)==null){
            hh = false;
        }
        return hh;


    }
    public boolean existe(String us){

        final Session session = getHibernateSession();


//        EntityManager em = getEntityManager();
        try {

            Query query = session.createQuery("select count(*) from Usuario u where u.usuario = '"+us+"' " );

            //query.setParameter("nombre", apellido+"%");
           long lista = (long)query.getSingleResult() ;
            System.out.println("\n\n\ncantidad totak"+lista);
            if (lista == 0){
                return false;
            }else{
                return true;
            }


        } finally {
            session.close();

        }

    }

    public Usuario getUsuario(String us) {
        /*
        Usuario user = null;
        Connection con = null;
        try {
            //utilizando los comodines (?)...
            String query = "select * from USUARIO where USER = ?";
            con = DataBaseServices.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, us);
            //Ejecuto...
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                user =  new Usuario(rs.getString("USER"),rs.getString("NOMBRE"),rs.getString("PASSWORD"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioServicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioServicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/

        return buscar(us);
    }
}
