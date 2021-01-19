package csuite.mvc.servicios;






//import CarroCompra.Main;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class GestionadDB<T> {
    private static EntityManagerFactory emf;
    private Class<T> claseEntidad;
    private static SessionFactory sf;


    public GestionadDB(Class<T> claseEntidad) {
//        if(emf == null) {
//            if(Main.getModoConexion().equalsIgnoreCase("Heroku")){
//                emf = getConfiguracionBaseDatosHeroku();
//            }else{
//                emf = Persistence.createEntityManagerFactory("CarritoCompraSessiones");
//            }
//        }
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("postgres");
            sf = new Configuration()
                    .configure("META-INF/hibernate.cfg.xml").buildSessionFactory();
        }
        this.claseEntidad = claseEntidad;

    }



    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public static Session getHibernateSession() {


        // factory = new Configuration().configure().buildSessionFactory();
        final Session session = sf.openSession();
//        final Session session = getEntityManager().unwrap(Session.class);
        return session;
    }
    private EntityManagerFactory getConfiguracionBaseDatosHeroku(){
        //Leyendo la información de la variable de ambiente de Heroku
        String databaseUrl = System.getenv("DATABASE_URL");
        StringTokenizer st = new StringTokenizer(databaseUrl, ":@/");
        //Separando las información del conexión.
        String dbVendor = st.nextToken();
        String userName = st.nextToken();
        String password = st.nextToken();
        String host = st.nextToken();
        String port = st.nextToken();
        String databaseName = st.nextToken();
        //creando la jbdc String
        String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s", host, port, databaseName);
        //pasando las propiedades.
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", jdbcUrl );
        properties.put("javax.persistence.jdbc.user", userName );
        properties.put("javax.persistence.jdbc.password", password );
        //
        return Persistence.createEntityManagerFactory("Heroku", properties);
    }

    /**
     * Metodo para obtener el valor del campo anotado como @ID.
     *
     * @param entidad
     * @return
     */
    private Object getValorCampo(T entidad) {
        if (entidad == null) {
            return null;
        }
        //aplicando la clase de reflexión.
        for (Field f : entidad.getClass().getDeclaredFields()) {  //tomando todos los campos privados.
            if (f.isAnnotationPresent(Id.class)) { //preguntando por la anotación ID.
                try {
                    f.setAccessible(true);
                    Object valorCampo = f.get(entidad);

                    System.out.println("Nombre del campo: " + f.getName());
                    System.out.println("Tipo del campo: " + f.getType().getName());
                    System.out.println("Valor del campo: " + valorCampo);

                    return valorCampo;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * @param entidad
     */

    public Object crear(T entidad) throws IllegalArgumentException, EntityExistsException, PersistenceException {
        EntityManager em = getEntityManager();
        Object aux = null;

        try {

            em.getTransaction().begin();
            em.persist(entidad);
            em.flush();
            try {
                aux = entidad;
            }catch (NullPointerException E){
                aux = null;
            }

            em.getTransaction().commit();



            return aux;
        } finally {
            em.close();
        }
    }



    /**
     * @param entidad
     */
    @Transactional
    public Object editar(T entidad) throws PersistenceException {
        Object aux = null;
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        boolean dd = false;
        try {

            em.merge(entidad);
            em.flush();
            try {
                aux = entidad;
            }catch (NullPointerException E){
                aux = null;
            }

            em.getTransaction().commit();
            dd = true;
        } finally {
            em.close();
        }
        return aux;
    }
    public boolean update(T entidad) throws PersistenceException {
//        final Session session = getHibernateSession();
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        boolean dd = false;
        try {
            em.merge(entidad);


            em.getTransaction().commit();
            dd = true;
        } finally {
            em.close();
        }
        return true;
    }

    /**
     * @param entidad
     */
    public Object createAndReturnObjectWithUniqueId(T entidad) throws PersistenceException {

        EntityManager em = getEntityManager();
        Object aux = null;
        em.getTransaction().begin();
        boolean dd = false;
        try {

            em.merge(entidad);
            em.flush();
            try {
                aux = entidad;
            }catch (NullPointerException E){
                aux = null;
            }
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return aux;
    }


    /**
     * @param entidadId
     */
    public boolean eliminar(Object entidadId) throws PersistenceException {
        boolean ok = false;
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            T entidad = em.find(claseEntidad, entidadId);
            em.remove(entidad);
            em.getTransaction().commit();
            ok = true;
        } finally {
            em.close();
        }
        return ok;
    }
    public boolean delete(Object entidadId) throws PersistenceException {
        boolean ok = false;
        final Session session = getHibernateSession();
//        EntityManager em = getEntityManager();
        session.getTransaction().begin();
        try {
            T entidad = session.find(claseEntidad, entidadId);
            session.remove(entidad);
            session.getTransaction().commit();
            ok = true;
        } finally {
            session.close();
        }
        return ok;
    }

    /**
     * @param id
     * @return
     */
    public T buscar(Object id) throws PersistenceException {
        final Session session = getHibernateSession();
//        EntityManager em = getEntityManager();
        session.getTransaction().begin();

        try {
            return session.find(claseEntidad, id);
        } finally {
            session.close();
        }
    }

    public T find(Object id) throws PersistenceException {
//        final Session session = getHibernateSession();
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        try {
            return em.find(claseEntidad, id);
        } finally {
            em.close();
        }
    }

    /**
     * @return
     */
    public List<T> ListadoCompleto() throws PersistenceException {
//        final Session session = getHibernateSession();
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(claseEntidad);
            criteriaQuery.select(criteriaQuery.from(claseEntidad));
            return em.createQuery(criteriaQuery).getResultList();
        } finally {
            em.close();
        }
    }
}
