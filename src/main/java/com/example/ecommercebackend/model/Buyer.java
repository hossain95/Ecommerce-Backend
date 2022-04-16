package com.example.ecommercebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", length = 65, nullable = false)
    private String name;

    @NotNull
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;


    @Column(name = "contact", length = 11, nullable = true)
    private String contact;


    @Column(name = "verification_code", length = 6, nullable = true)
    private String verificationCode;

    @Column(name = "enabled")
    private Boolean enabled = false;

    @Column(name = "user_status")
    private String userStatus = "buyer";

    @Column(name = "token", nullable = true)
    private String token;

    @OneToOne(mappedBy = "buyer")
    private ShippingAddress shippingAddress;

    @JsonIgnore
    @OneToMany(mappedBy = "buyer")
    private List<PurchaseHistory> purchaseHistory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<PurchaseHistory> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(List<PurchaseHistory> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }
}
