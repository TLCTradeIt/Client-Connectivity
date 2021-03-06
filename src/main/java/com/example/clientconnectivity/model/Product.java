package com.example.clientconnectivity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable= false, length= 20)
    private String ticker;

    @Column(length= 20)
    private String exchange;

    @Column(nullable = false)
    private Integer prodQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolioId")
    @JsonIgnore
    private Portfolio portfolio;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public Integer getProdQuantity() {
        return prodQuantity;
    }

    public void setProdQuantity(Integer prodQuantity) {
        this.prodQuantity = prodQuantity;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", ticker='" + ticker + '\'' +
                ", exchange='" + exchange + '\'' +
                ", prodQuantity=" + prodQuantity +
                ", portfolio=" + portfolio +
                '}';
    }
}
