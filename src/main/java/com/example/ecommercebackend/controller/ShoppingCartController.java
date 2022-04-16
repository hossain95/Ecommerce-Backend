package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.model.ShoppingCart;
import com.example.ecommercebackend.response.GetRequestResponse;
import com.example.ecommercebackend.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@Tag(name = "Shopping Cart")
@RequestMapping("/shopping-cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;


    @GetMapping("/list")
    public GetRequestResponse<ShoppingCart> shoppingCartList(){
        return shoppingCartService.shoppingCartList();
    }

    @GetMapping("/customer/{customerId}")
    public ShoppingCart shoppingCartByCustomer(@RequestBody @PathVariable("customerId") Long customerId){
        return shoppingCartService.shoppingCartByCustomer(customerId);
    }
}
