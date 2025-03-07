package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {
    private PaymentRepository repo;
    private Payment payment1;
    private Payment payment2;

    @BeforeEach
    void setUp() {
        repo = new PaymentRepository();
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP123ABCD5678");
        payment1 = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", data, PaymentStatus.SUCCESS.getValue());
        payment2 = new Payment("ed2a3070-1c4c-4cb9-81c2-44ca6cfb1c63", "bankTransfer", data, PaymentStatus.REJECTED.getValue());
    }

    @Test
    void testSaveCreate() {
        Payment result = repo.save(payment2);
        Payment found = repo.getPayment(payment2.getId());
        assertPaymentEquals(payment2, result);
        assertPaymentEquals(payment2, found);
    }

    @Test
    void testSaveUpdate() {
        repo.save(payment1);
        Payment updated = new Payment(payment1.getId(), payment1.getMethod(), payment1.getPaymentData());
        updated.setStatus(PaymentStatus.REJECTED.getValue());
        Payment result = repo.save(updated);
        Payment found = repo.getPayment(payment1.getId());

        assertEquals(payment1.getId(), result.getId());
        assertEquals(payment1.getId(), found.getId());
        assertEquals(payment1.getMethod(), found.getMethod());
        assertEquals(payment1.getPaymentData(), found.getPaymentData());
        assertEquals(PaymentStatus.REJECTED.getValue(), found.getStatus());
    }

    @Test
    void testGetPaymentFound() {
        repo.save(payment1);
        repo.save(payment2);
        Payment found = repo.getPayment(payment2.getId());
        assertPaymentEquals(payment2, found);
    }

    @Test
    void testGetPaymentNotFound() {
        repo.save(payment1);
        repo.save(payment2);
        assertNull(repo.getPayment("zczc"));
    }

    @Test
    void testGetAllPayments() {
        repo.save(payment1);
        repo.save(payment2);
        List<Payment> list = repo.getAllPayments();
        assertEquals(2, list.size());
        // Assuming insertion order is preserved
        assertPaymentEquals(payment1, list.get(0));
        assertPaymentEquals(payment2, list.get(1));
    }

    private void assertPaymentEquals(Payment expected, Payment actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getMethod(), actual.getMethod());
        assertEquals(expected.getPaymentData(), actual.getPaymentData());
        assertEquals(expected.getStatus(), actual.getStatus());
    }
}
