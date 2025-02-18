package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product();
        // Only setting productId if necessary. Remove name/description if they don't exist.
    }

    @Test
    public void testCreateGeneratesProductIdIfNotProvided() {
        // Ensure productId is null so that it gets generated
        product.setProductId(null);
        Product createdProduct = productService.create(product);

        // Verify that a productId was generated
        assertNotNull(createdProduct.getProductId());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    public void testCreateDoesNotOverwriteExistingProductId() {
        String existingId = UUID.randomUUID().toString();
        product.setProductId(existingId);
        Product createdProduct = productService.create(product);

        // The productId should remain the same
        assertEquals(existingId, createdProduct.getProductId());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    public void testFindAllReturnsAllProducts() {
        // Create dummy products
        Product product1 = new Product();
        product1.setProductId("1");

        Product product2 = new Product();
        product2.setProductId("2");

        List<Product> products = Arrays.asList(product1, product2);
        Iterator<Product> iterator = products.iterator();

        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteCallsRepositoryDelete() {
        String productId = "test-product-id";
        productService.delete(productId);
        verify(productRepository, times(1)).delete(productId);
    }

    @Test
    public void testGetByIdReturnsProduct() {
        String productId = "test-product-id";
        Product expectedProduct = new Product();
        expectedProduct.setProductId(productId);

        when(productRepository.findById(productId)).thenReturn(expectedProduct);

        Product actualProduct = productService.getById(productId);

        assertEquals(expectedProduct, actualProduct);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    public void testUpdateReturnsUpdatedProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("updated-id");

        when(productRepository.update(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.update(updatedProduct);

        assertEquals(updatedProduct, result);
        verify(productRepository, times(1)).update(updatedProduct);
    }
}
