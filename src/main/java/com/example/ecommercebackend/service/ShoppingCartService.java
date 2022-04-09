package com.example.ecommercebackend.service;

import com.example.ecommercebackend.model.ShoppingCart;
import com.example.ecommercebackend.model.UserModel;
import com.example.ecommercebackend.repository.ShoppingCartRepository;
import com.example.ecommercebackend.repository.UserRepository;
import com.example.ecommercebackend.response.GetRequestResponse;
import com.example.ecommercebackend.response.status.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCardRepository;
    @Autowired
    private UserRepository userRepository;

    public GetRequestResponse<ShoppingCart> shoppingCartList(){
        return new GetRequestResponse<>(ResponseStatus.succeed, shoppingCardRepository.findAll());
    }

    public ShoppingCart createShoppingCart(UserModel userModel){
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(userModel);
        return shoppingCardRepository.save(shoppingCart);
    }

    public ShoppingCart shoppingCartByCustomer(Long userId){
        UserModel userModel = userRepository.findById(userId).get();

        ShoppingCart shoppingCart = shoppingCardRepository.findShoppingCartByUserModel(userModel);
        return shoppingCart;
    }

    public void deleteShoppingCart(Long cartId){
        shoppingCardRepository.deleteById(cartId);
    }
}
