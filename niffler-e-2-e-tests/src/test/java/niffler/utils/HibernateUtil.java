package niffler.utils;

import lombok.experimental.UtilityClass;
import niffler.database.entity.user.User;
import niffler.tests.DataTest;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory(){
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(DataTest.class);
        configuration.addAnnotatedClass(User.class);
        configuration.configure();
        return configuration.buildSessionFactory();
    }
}
