package com.example.ClientConnectivity.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioId;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    @CreationTimestamp
    private Date dateCreated;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Product> registeredProducts;

    // getters and setters
    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
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

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioId=" + portfolioId +
                ", value=" + value +
                ", dateCreated=" + dateCreated +
                ", client=" + client +
                ", registeredProducts=" + registeredProducts +
                '}';
    }
}
