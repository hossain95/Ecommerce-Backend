package com.example.ecommercebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity = 1;
    private Double subTotal = 0.0;
    private Double subTotalDeliveryCharge = 0.0;

    @ManyToOne
    private Product product;

    @JsonIgnore
    @ManyToOne
    private ShoppingCart shoppingCart;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getSubTotalDeliveryCharge() {
        return subTotalDeliveryCharge;
    }

    public void setSubTotalDeliveryCharge(Double subTotalDeliveryCharge) {
        this.subTotalDeliveryCharge = subTotalDeliveryCharge;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
