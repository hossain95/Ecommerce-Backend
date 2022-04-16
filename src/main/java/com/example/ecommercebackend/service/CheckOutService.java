package com.example.ecommercebackend.service;

import com.example.ecommercebackend.model.*;
import com.example.ecommercebackend.repository.PurchaseHistoryRepository;
import com.example.ecommercebackend.repository.ShoppingCartRepository;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.status.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckOutService {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private PurchaseHistoryRepository purchaseHistoryRepository;

    @Autowired
    private CartItemService cartItemService;

    public CommonResponse orderCheckOut(Long userId){

        ShoppingCart shoppingCart = shoppingCartService.shoppingCartByCustomer(userId);

        List<CartItem> cartItems = shoppingCart.getCartItems();

        for (final CartItem cartItem: cartItems){
            Product product = new Product();
            product = cartItem.getProduct();

            PurchaseHistory purchaseHistory = new PurchaseHistory();

            purchaseHistory.setName(product.getName());
            purchaseHistory.setImage(product.getImage());
            purchaseHistory.setPrice(product.getPrice());
            purchaseHistory.setQuantity(cartItem.getQuantity());
            purchaseHistory.setDeliveryCharge(product.getDeliveryCharge());
            purchaseHistory.setBuyer(shoppingCart.getBuyer());

            purchaseHistoryRepository.save(purchaseHistory);
            cartItemService.cartItemDeleteById(cartItem.getId());
        }

        shoppingCartService.deleteShoppingCart(shoppingCart.getId());

        return new CommonResponse("check out successful", HttpStatus.OK);
    }
}
