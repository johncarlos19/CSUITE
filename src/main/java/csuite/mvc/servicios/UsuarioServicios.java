package csuite.mvc.servicios;

import csuite.mvc.entidades.Usuario;
import csuite.mvc.entidades.Vendedor;

import java.util.ArrayList;

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
        user.setPais("RD");
        Usuario usuario = (Usuario) createAndReturnObjectWithUniqueId(user);
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
