package com.example.ecommerce.model;

public class PaymentDetails {

    private String paymentMethod;
    private String status;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentStatus;
    private String razorpayPaymentId;

    public PaymentDetails() {
    }

    public PaymentDetails(String paymentMethod, String status, String razorpayPaymentLinkId,
                          String razorpayPaymentStatus, String razorpayPaymentId) {
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.razorpayPaymentLinkId = razorpayPaymentLinkId;
        this.razorpayPaymentStatus = razorpayPaymentStatus;
        this.razorpayPaymentId = razorpayPaymentId;
    }
// --- Getters and Setters ---/


    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRazorpayPaymentLinkId() {
        return razorpayPaymentLinkId;
    }

    public void setRazorpayPaymentLinkId(String razorpayPaymentLinkId) {
        this.razorpayPaymentLinkId = razorpayPaymentLinkId;
    }

    public String getRazorpayPaymentStatus() {
        return razorpayPaymentStatus;
    }

    public void setRazorpayPaymentStatus(String razorpayPaymentStatus) {
        this.razorpayPaymentStatus = razorpayPaymentStatus;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }
}
