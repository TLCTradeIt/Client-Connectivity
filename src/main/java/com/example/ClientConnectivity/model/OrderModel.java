package com.example.ClientConnectivity.model;

import com.example.ClientConnectivity.enums.Side;
import com.example.ClientConnectivity.enums.Status;

import java.util.Date;

public class OrderModel {
    private int orderId;
    private Product product;
    private int quantity;
    private Side side;
    private Status status;
    private Client client;
    private Date timestamp;

    public OrderModel(int orderId, Product product, int quantity, Side side, Status status, Client client, Date timestamp) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.side = side;
        this.status = status;
        this.client = client;
        this.timestamp = timestamp;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "orderId=" + orderId +
                ", product=" + product +
                ", quantity=" + quantity +
                ", side=" + side +
                ", status=" + status +
                ", client=" + client +
                ", timestamp=" + timestamp +
                '}';
    }
}
