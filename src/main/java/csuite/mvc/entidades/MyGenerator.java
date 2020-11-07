package csuite.mvc.entidades;


import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.mapping.Property;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;
import java.util.stream.Stream;

public class MyGenerator
        implements IdentifierGenerator, Configurable {

    private String prefix;
    private long longitud;



    @Override
    public Serializable generate(
            SharedSessionContractImplementor session, Object obj)
            throws HibernateException {


        if(obj instanceof Identifiable) {
            Identifiable identifiable = (Identifiable) obj;
            Serializable id = identifiable.getId();
            if(id != null) {
                return id;
            }
        }
        String pre = "'%"+prefix+"-%'";
        System.out.println("esto imp:"+pre);

        String query = String.format("select max(%s.id) from %s where %s.id like %s",
                session.getEntityPersister(obj.getClass().getName(), obj).getIdentifierPropertyName(),
                obj.getClass().getSimpleName(),session.getEntityPersister(obj.getClass().getName(), obj).getIdentifierPropertyName(),pre);


        Stream ids = session.createQuery(query).stream();
        Long max = null;
        try {
            max =
                    ids.map(o -> o.toString().replace(prefix + "-", ""))
                            .mapToLong(num -> Long.parseLong((String) num))
                            .max()
                            .orElse(0L);

        }catch (NullPointerException E){
            max = new Long(0);

        }
        StringBuilder value = new StringBuilder();
        long number = 0;
        long code_long;
        number = max;
        code_long = prefix.length();

        String number_String = Long.toString(number+1);

        long total_longitud_0 = longitud - code_long - number_String.length() -1;
        for (int i = 0; i< code_long; i++){
            value.append(prefix.charAt(i));
        }
        value.append("-");
        for (int i = 0; i< total_longitud_0; i++){
            value.append('0');
        }
        for (int i = 0; i< number_String.length(); i++){
            value.append(number_String.charAt(i));
        }


        return value.toString();
    }

    @Override
    public void configure(Type type, Properties properties,
                          ServiceRegistry serviceRegistry) throws MappingException {
        System.out.println("Toda las propiedades: "+properties);
        prefix = properties.getProperty("prefix");
        longitud = Long.parseLong(properties.getProperty("longitud"));


    }

}