package com.example.ecommercebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    private double totalProductPrice = 0.0;
    private double totalDeliveryCharge = 0.0;
    private double total = 0.0;

    @OneToMany(mappedBy = "shoppingCart")
    private List<CartItem> cartItems;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public double getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(double totalProductPrice) {
        this.totalProductPrice = totalProductPrice;
    }

    public double getTotalDeliveryCharge() {
        return totalDeliveryCharge;
    }

    public void setTotalDeliveryCharge(double totalDeliveryCharge) {
        this.totalDeliveryCharge = totalDeliveryCharge;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
