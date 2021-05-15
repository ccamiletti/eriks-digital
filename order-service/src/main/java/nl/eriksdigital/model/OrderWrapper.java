package nl.eriksdigital.model;

import java.io.Serializable;

public class OrderWrapper implements Serializable {

    private static final long serialVersionUID = -4164334611691832075L;

    private Order orderNew;
    private Order orderOld;

    public OrderWrapper(Order orderNew, Order orderOld) {
        this.orderNew = orderNew;
        this.orderOld = orderOld;
    }

    public Order getOrderNew() {
        return orderNew;
    }

    public void setOrderNew(Order orderNew) {
        this.orderNew = orderNew;
    }

    public Order getOrderOld() {
        return orderOld;
    }

    public void setOrderOld(Order orderOld) {
        this.orderOld = orderOld;
    }


}
