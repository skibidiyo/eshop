package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Map<String, String> paymentMapping = new HashMap<>();

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Order existingOrder = orderRepository.findById(order.getId());
        if (existingOrder == null) {
            throw new NoSuchElementException("Order not found");
        }
        String paymentStatus;
        if ("voucherCode".equals(method)) {
            paymentStatus = payWithVoucher(paymentData);
        } else if ("bankTransfer".equals(method)) {
            paymentStatus = payWithBankTransfer(paymentData);
        } else {
            throw new IllegalArgumentException("Invalid payment method");
        }
        Payment payment = new Payment(UUID.randomUUID().toString(), method, paymentData, paymentStatus);
        String orderStatus = PaymentStatus.SUCCESS.getValue().equals(paymentStatus)
                ? OrderStatus.SUCCESS.getValue() : OrderStatus.FAILED.getValue();
        existingOrder.setStatus(orderStatus);
        orderRepository.save(existingOrder);
        paymentMapping.put(payment.getId(), existingOrder.getId());
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        Payment storedPayment = paymentRepository.getPayment(payment.getId());
        if (storedPayment == null) {
            throw new NoSuchElementException("Payment not found");
        }
        payment.setStatus(status);
        paymentRepository.save(payment);
        Order associatedOrder = orderRepository.findById(paymentMapping.get(payment.getId()));
        String orderStatus = PaymentStatus.SUCCESS.getValue().equals(status)
                ? OrderStatus.SUCCESS.getValue() : OrderStatus.FAILED.getValue();
        associatedOrder.setStatus(orderStatus);
        orderRepository.save(associatedOrder);
        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.getPayment(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }

    public Map<String, String> getPaymentMapping() {
        return paymentMapping;
    }

    private String payWithVoucher(Map<String, String> paymentData) {
        if (!paymentData.containsKey("voucherCode")) {
            throw new IllegalArgumentException("Missing voucherCode");
        }
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null || voucherCode.length() != 16 ||
                !voucherCode.startsWith("ESHOP") || getDigitCount(voucherCode) != 8) {
            return PaymentStatus.REJECTED.getValue();
        }
        return PaymentStatus.SUCCESS.getValue();
    }

    private static int getDigitCount(String voucherCode) {
        return (int) voucherCode.chars().filter(Character::isDigit).count();
    }

    private String payWithBankTransfer(Map<String, String> paymentData) {
        if (!paymentData.containsKey("bankName") || !paymentData.containsKey("referenceCode")) {
            throw new IllegalArgumentException("Missing bank transfer information");
        }
        String bank = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        if (bank == null || bank.isEmpty() || referenceCode == null || referenceCode.isEmpty()){
            return PaymentStatus.REJECTED.getValue();
        }
        return PaymentStatus.SUCCESS.getValue();
    }
}
