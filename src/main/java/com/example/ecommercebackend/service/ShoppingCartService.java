package com.example.ecommercebackend.service;

import com.example.ecommercebackend.model.Buyer;
import com.example.ecommercebackend.model.ShoppingCart;
import com.example.ecommercebackend.repository.BuyerRepository;
import com.example.ecommercebackend.repository.ShoppingCartRepository;
import com.example.ecommercebackend.response.GetRequestResponse;
import com.example.ecommercebackend.response.status.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCardRepository;
    @Autowired
    private BuyerRepository buyerRepository;

    public GetRequestResponse<ShoppingCart> shoppingCartList(){
        return new GetRequestResponse<>(ResponseStatus.succeed, shoppingCardRepository.findAll());
    }

    public ShoppingCart createShoppingCart(Buyer buyer){
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setBuyer(buyer);
        return shoppingCardRepository.save(shoppingCart);
    }

    public ShoppingCart shoppingCartByCustomer(Long userId){
        Buyer buyer = buyerRepository.findById(userId).get();

        ShoppingCart shoppingCart = shoppingCardRepository.findShoppingCartByBuyer(buyer);
        return shoppingCart;
    }

    public void deleteShoppingCart(Long cartId){
        shoppingCardRepository.deleteById(cartId);
    }
}
