package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        when(productRepository.create(product)).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertNotNull(createdProduct.getProductId());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAllProducts() {
        List<Product> products = new ArrayList<>();
        Iterator<Product> productIterator = products.iterator();
        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> allProducts = productService.findAll();

        assertEquals(products, allProducts);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testDeleteProduct() {
        String productId = "123";
        doNothing().when(productRepository).delete(productId);

        productService.delete(productId);

        verify(productRepository, times(1)).delete(productId);
    }

    @Test
    void testGetProductById() {
        String productId = "123";
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(product);

        Product foundProduct = productService.getById(productId);

        assertEquals(product, foundProduct);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        when(productRepository.update(product)).thenReturn(product);

        Product updatedProduct = productService.update(product);

        assertEquals(product, updatedProduct);
        verify(productRepository, times(1)).update(product);
    }
}