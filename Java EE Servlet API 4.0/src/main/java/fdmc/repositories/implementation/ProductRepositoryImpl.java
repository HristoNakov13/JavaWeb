package fdmc.repositories.implementation;

import fdmc.domain.entities.Product;
import fdmc.repositories.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    private EntityManager entityManager;

    public ProductRepositoryImpl() {
        this.entityManager = Persistence.createEntityManagerFactory("chushka").createEntityManager();
    }

    @Override
    public List<Product> findAll() {
        String queryString = "SELECT p FROM Product p";

        return this.entityManager
                .createQuery(queryString, Product.class)
                .getResultList();
    }

    @Override
    public Product save(Product product) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(product);
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();

        return product;
    }
}
