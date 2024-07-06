package virkato.otus.lesson26.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import virkato.otus.lesson26.dao.EntityUtil;
import virkato.otus.lesson26.entities.Client;
import virkato.otus.lesson26.entities.Product;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HibernateConfig {
    public static SessionFactory getSessionFactory() {
        Configuration config = new Configuration()
                .configure("/hibernate/hibernate.cfg.xml")
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Product.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties())
                .build();

        return config.buildSessionFactory(serviceRegistry);
    }

    public static void initTables(SessionFactory session) {
        Client client1 = EntityUtil.insert(session, new Client("Вася"));
        Client client2 = EntityUtil.insert(session, new Client("Игнат"));
        Client client3 = EntityUtil.insert(session, new Client("Виктор"));

        EntityUtil.insert(session, new Product("Мука", 3L, Set.of(client1, client2)));
        EntityUtil.insert(session, new Product("Масло", 5L, Set.of(client2, client3)));
        EntityUtil.insert(session, new Product("Соль", 2L, Set.of(client1, client3)));
    }
}
