package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository implements ProductRepositoryInterface{
    private List<Product> productData = new ArrayList<>();

    @Override
    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    @Override
    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    // deletes product by productid. uses removeIf to remove any product with the matching id.
    @Override
    public void delete(String id) {
        productData.removeIf(product -> id.equals(product.getProductId()));
    }

    @Override
    public Product findById(String id) {
        for (Product product : productData) {
            if (id.equals(product.getProductId())) {
                return product;
            }
        }
        return null;
    }

    @Override
    public Product update(Product updatedProduct) {
        for (Product product : productData) {
            if (updatedProduct.getProductId().equals(product.getProductId())) {
                product.setProductName(updatedProduct.getProductName());
                product.setProductQuantity(updatedProduct.getProductQuantity());
                return product;
            }
        }
        return null;
    }
}
