package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.Map;


@Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, Map<String,String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
    }

    public Payment(String id, String method, Map<String,String> paymentData, String status) {
        this(id,method, paymentData);
        this.setStatus(status);
    }

    public void setStatus(String status) {
        if (!"SUCCESS".equals(status) && !"REJECTED".equals(status)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        this.status = status;
    }

}