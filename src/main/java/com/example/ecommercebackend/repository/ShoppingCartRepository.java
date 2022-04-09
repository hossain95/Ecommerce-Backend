package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.ShoppingCart;
import com.example.ecommercebackend.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    public ShoppingCart findShoppingCartByUserModel(UserModel userModel);

}
