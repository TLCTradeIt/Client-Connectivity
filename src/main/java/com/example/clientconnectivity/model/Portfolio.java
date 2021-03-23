package com.example.clientconnectivity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long portfolioId;

    @Column(nullable = false, length= 20)
    private String name;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    @CreationTimestamp
    private Date dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientId", nullable = false)
    @JsonIgnore
    private Client client;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Product> registeredProducts;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Order> orders;


    // getters and setters
    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Product> getRegisteredProducts() {
        return registeredProducts;
    }

    public void setRegisteredProducts(List<Product> registeredProducts) {
        this.registeredProducts = registeredProducts;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioId=" + portfolioId +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", dateCreated=" + dateCreated +
                ", client=" + client +
                ", registeredProducts=" + registeredProducts +
                ", orders=" + orders +
                '}';
    }
}
