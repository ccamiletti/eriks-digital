package nl.eriksdigital.model;

import lombok.Builder;

import java.io.Serializable;
import java.time.Instant;

public class Order implements Serializable {

    private static final long serialVersionUID = -8814257560414121329L;

    private Long id;
    private String status;
    private Double totalPrice;
    private Instant date;

    public Order() {}

    @Builder
    public Order(Long id, String status, Double totalPrice, Instant date) {
        this.id = id;
        this.status = status;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

}
