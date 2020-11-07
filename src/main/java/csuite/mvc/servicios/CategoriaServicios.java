package csuite.mvc.servicios;

import csuite.mvc.entidades.Categoria;
import csuite.mvc.entidades.Producto;
import csuite.mvc.entidades.Vendedor;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class CategoriaServicios extends GestionadDB<Categoria>{


    private static CategoriaServicios instancia;



    public static CategoriaServicios getInstancia(){
        if(instancia==null){
            instancia = new CategoriaServicios();
        }
        return instancia;
    }


    public CategoriaServicios() {
        super(Categoria.class);
    }

    public List<Categoria> ListaCategoria(String user){
        final Session session = getHibernateSession();

//        EntityManager em = getEntityManager();
        try {

            Query query = session.createQuery("select v.categorias from Vendedor v where v.idVendedor.usuario = '"+user+"' order by id desc " );

        //query.setParameter("nombre", apellido+"%");
        List<Categoria> lista = query.getResultList();
        return (ArrayList<Categoria>) lista;
    } finally {
        session.close();
    }

    }



}
