package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    @Test
    void testCreatePaymentSuccessfully() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData, PaymentStatus.SUCCESS.getValue());

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
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
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData, PaymentStatus.REJECTED.getValue());

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("ESHOP1234ABCD567".toLowerCase(), payment.getPaymentData().get("voucherCode"));
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }


    @Test
    void testSetStatusForSuccessStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123ABC567");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData, PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        payment.setStatus("SUCCESS");
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusForInvalidStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123ABC567");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData, PaymentStatus.REJECTED.getValue());
        assertThrows(IllegalArgumentException.class,
                () -> payment.setStatus("MEOW"));
    }
}