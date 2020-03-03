package fdmc.services;

import fdmc.domain.entities.Product;
import fdmc.repositories.ProductRepository;
import fdmc.repositories.implementation.ProductRepositoryImpl;

import java.util.Map;

public class ProductService {
    private ProductRepository productRepository;

    public ProductService() {
        this.productRepository = new ProductRepositoryImpl();
    }

    public Product createProduct(Map<String, String[]> productData) {
        int lol = 5;

        return null;
    }
}
