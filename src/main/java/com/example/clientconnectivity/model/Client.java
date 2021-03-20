package com.example.clientconnectivity.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long clientId;

    @Column(nullable= false, length= 20)
    private String firstName;

    @Column(nullable= false, length= 20)
    private String lastName;

    @Column(nullable= false, unique=true, length= 45)
    private String email;

    @Column(nullable= false, length= 64)
    private String password;

    @Column(nullable= false, length= 12)
    private int phoneNumber;

    @Column(nullable= false, length= 30)
    private Double accBalance;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Portfolio> portfolios;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> orders;


    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(Double accBalance) {
        this.accBalance = accBalance;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", accBalance=" + accBalance +
                '}';
    }
}
