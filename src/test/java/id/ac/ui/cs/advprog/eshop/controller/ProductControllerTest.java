package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        model = new BindingAwareModelMap();
    }


    @Test
    void testCreateProductPost() {
        Product product = new Product();
        String viewName = productController.createProductPost(product, model);
        verify(productService, times(1)).create(product);
        assertEquals("redirect:/product/list", viewName);
    }


    @Test
    void testDeleteProduct() {
        String productId = "123";
        String viewName = productController.deleteProduct(productId);
        verify(productService, times(1)).delete(productId);
        assertEquals("redirect:/product/list", viewName);
    }


    @Test
    void testEditProductPost() {
        Product product = new Product();
        String viewName = productController.editProductPost(product);
        verify(productService, times(1)).update(product);
        assertEquals("redirect:/product/list", viewName);
    }
}