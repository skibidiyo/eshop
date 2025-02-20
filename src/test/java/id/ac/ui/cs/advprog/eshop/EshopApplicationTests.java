package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
        // This test is intentionally left empty.
        // Its purpose is to verify that the Spring Boot application context loads successfully.
    }

    @Test
    void mainMethodShouldStartApplication() {
        EshopApplication.main(new String[]{});
    }
}
