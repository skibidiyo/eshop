package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testCreateProductPage() throws Exception {
        // Perform GET /product/create and expect a new Product to be added in the model
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void testCreateProductPost() throws Exception {
        // Perform POST /product/create with sample parameters
        mockMvc.perform(post("/product/create")
                        .param("id", "1")
                        .param("name", "Test Product"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        // Verify that the service's create method is called with any Product instance.
        verify(productService).create(ArgumentMatchers.any(Product.class));
    }

    @Test
    public void testProductListPage() throws Exception {
        // Prepare a list of products to be returned by the mocked service.
        List<Product> productList = Arrays.asList(new Product(), new Product());
        when(productService.findAll()).thenReturn(productList);

        // Perform GET /product/list and verify model and view.
        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ProductList"))
                .andExpect(model().attribute("products", productList));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        String productId = "1";

        // Perform GET /product/delete/{productId} and verify redirection.
        mockMvc.perform(get("/product/delete/" + productId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        // Verify that the service's delete method is called with the correct productId.
        verify(productService).delete(productId);
    }

    @Test
    public void testEditProductPage() throws Exception {
        String productId = "1";
        Product product = new Product();
        // Configure the mocked service to return a Product for the given id.
        when(productService.getById(productId)).thenReturn(product);

        // Perform GET /product/edit/{productId} and verify view and model.
        mockMvc.perform(get("/product/edit/" + productId))
                .andExpect(status().isOk())
                .andExpect(view().name("EditProduct"))
                .andExpect(model().attribute("product", product));
    }

    @Test
    public void testEditProductPost() throws Exception {
        // Perform POST /product/edit with sample product parameters.
        mockMvc.perform(post("/product/edit")
                        .param("id", "1")
                        .param("name", "Updated Product"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        // Verify that the service's update method is called with any Product instance.
        verify(productService).update(ArgumentMatchers.any(Product.class));
    }
}
