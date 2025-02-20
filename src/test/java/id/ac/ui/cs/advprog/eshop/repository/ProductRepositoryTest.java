package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    // positive
    @Test
    void testDeleteExistingProduct() {
        // create and add a product
        Product product = new Product();
        product.setProductId("test-id-1");
        product.setProductName("Delete");
        product.setProductQuantity(10);
        productRepository.create(product);

        // delete the product
        productRepository.delete("test-id-1");

        // verify that the product is removed.
        assertNull(productRepository.findById("test-id-1"));

        // verify that findAll returns an empty iterator
        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    // negative
    @Test
    void testDeleteNonExistingProduct() {
        // create and add a product to the repository
        Product product = new Product();
        product.setProductId("test-id-2");
        product.setProductName("Existing Product");
        product.setProductQuantity(20);
        productRepository.create(product);

        // attempt to delete a product with an ID that doesn't exist
        productRepository.delete("non-existent-id");

        // verify that the existing product is still in the repository.
        Product retrievedProduct = productRepository.findById("test-id-2");
        assertNotNull(retrievedProduct);
        assertEquals("Existing Product", retrievedProduct.getProductName());
        assertEquals(20, retrievedProduct.getProductQuantity());
    }

    // positive
    @Test
    void testUpdateExistingProduct() {
        // create and add a product
        Product product = new Product();
        product.setProductId("test-id-3");
        product.setProductName("Original");
        product.setProductQuantity(15);
        productRepository.create(product);

        // Verify initial state using findById.
        Product initialProduct = productRepository.findById("test-id-3");
        assertNotNull(initialProduct);
        assertEquals("Original", initialProduct.getProductName());
        assertEquals(15, initialProduct.getProductQuantity());

        // edit the product name and quantity
        Product updatedProduct = new Product();
        updatedProduct.setProductId("test-id-3");
        updatedProduct.setProductName("Updated");
        updatedProduct.setProductQuantity(30);
        Product result = productRepository.update(updatedProduct);

        // verify that the update was successful
        assertNotNull(result);
        assertEquals("Updated", result.getProductName());
        assertEquals(30, result.getProductQuantity());

        // Also check that the changes are reflected in the repository.
        Product retrievedProduct = productRepository.findById("test-id-3");
        assertNotNull(retrievedProduct);
        assertEquals("Updated", retrievedProduct.getProductName());
        assertEquals(30, retrievedProduct.getProductQuantity());

        // Additional check: findById returns null for non-existent product.
        assertNull(productRepository.findById("non-existent-id"));
    }

    // negative
    @Test
    void testUpdateNonExistingProduct() {
        // First, create a product so that the loop in update is actually iterated:
        Product existingProduct = new Product();
        existingProduct.setProductId("existing-id");
        existingProduct.setProductName("Existing Product");
        existingProduct.setProductQuantity(20);
        productRepository.create(existingProduct);

        // Prepare an updated product with an ID that is not in the repository.
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-id");
        updatedProduct.setProductName("Non-existent Product");
        updatedProduct.setProductQuantity(40);

        // Attempt to update and expect a null result.
        Product result = productRepository.update(updatedProduct);
        assertNull(result);

        // Verify that findById returns null for the non-existent product.
        assertNull(productRepository.findById("non-existent-id"));
    }
}
