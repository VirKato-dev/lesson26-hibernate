package virkato.otus.lesson26;

import org.hibernate.SessionFactory;
import virkato.otus.lesson26.config.HibernateConfig;
import virkato.otus.lesson26.dao.EntityUtil;
import virkato.otus.lesson26.entities.Client;
import virkato.otus.lesson26.entities.Product;

import java.util.StringJoiner;

public class ShopHistory {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        sessionFactory = HibernateConfig.getSessionFactory();

        HibernateConfig.initTables(sessionFactory);

        System.out.println(clientProducts(1L));
        System.out.println(productClients(3L));

        EntityUtil.deleteClient(sessionFactory, 2L);
        System.out.println(EntityUtil.findAll(sessionFactory, Client.class));

        EntityUtil.deleteProduct(sessionFactory, 2L);
        System.out.println(EntityUtil.findAll(sessionFactory, Product.class));
    }

    public static String clientProducts(Long id) {
        StringJoiner sj = new StringJoiner("\n");

        Client client = EntityUtil.findOneById(sessionFactory, Client.class, id);

        for (Product product : client.getProducts()) {
            sj.add(product.toString());
        }
        return "\n\nКлиент: " + client + ":\n" + sj;
    }

    public static String productClients(Long id) {
        StringJoiner sj = new StringJoiner("\n");

        Product product = EntityUtil.findOneById(sessionFactory, Product.class, id);

        for (Client client : product.getClients()) {
            sj.add(client.toString());
        }
        return "\n\nПродукт: " + product + ":\n" + sj;
    }
}
