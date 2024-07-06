package virkato.otus.lesson26.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import virkato.otus.lesson26.entities.Client;
import virkato.otus.lesson26.entities.Product;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityUtil {

    public static <T> T insert(SessionFactory sessionFactory, T entity) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            T merged = (T) session.merge(entity);
            session.getTransaction().commit();

            return merged;
        }
    }


    public static <T> T findOneById(SessionFactory sessionFactory, Class<T> cls, long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            return session.get(cls, id);
        }
    }


    public static <T> List<T> findAll(SessionFactory sessionFactory, Class<T> cls) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT a FROM " + cls.getSimpleName() + " a", cls)
                    .getResultList();
        }
    }


    public static void deleteProduct(SessionFactory sessionFactory, Long id) {
        if (sessionFactory == null || id == null) {
            return;
        }

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Product product = session.get(Product.class, id);

            product.getClients().removeIf(c -> id.equals(c.getId()));

            session.remove(product);

            session.getTransaction().commit();
        }
    }


    public static void deleteClient(SessionFactory sessionFactory, Long id) {
        if (sessionFactory == null || id == null) {
            return;
        }

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Client client = session.get(Client.class, id);
            System.out.println("\n" + client + "\n");

            for (Product product : client.getProducts()) {
                product.getClients().removeIf(c -> id.equals(c.getId()));
            }

            session.remove(client);
            session.getTransaction().commit();
        }
    }

}
