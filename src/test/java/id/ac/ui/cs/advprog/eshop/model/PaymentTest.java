package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    @Test
    void testCreatePaymentSuccessfully() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData, "SUCCESS");

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentForInvalidStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12ABC3456");
        assertThrows(IllegalArgumentException.class,
                () ->new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData, "MEOW"));
    }

    @Test
    void testCreatePaymentByVoucherCodeLowered() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABCD567".toLowerCase());
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData, "REJECTED");

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("ESHOP1234ABCD567".toLowerCase(), payment.getPaymentData().get("voucherCode"));
        assertEquals("REJECTED", payment.getStatus());
    }


    @Test
    void testSetStatusForSuccessStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123ABC567");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData, "REJECTED");
        assertEquals("REJECTED", payment.getStatus());
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatusForInvalidStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123ABC567");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData, "REJECTED");
        assertThrows(IllegalArgumentException.class,
                () -> payment.setStatus("MEOW"));
    }
}