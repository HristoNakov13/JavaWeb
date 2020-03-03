package fdmc.repositories;

import fdmc.domain.entities.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();

    Product save(Product product);
}
