package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.dto.CartItemDto;
import com.example.ecommercebackend.model.CartItem;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.GetRequestResponse;
import com.example.ecommercebackend.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart-item")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/list")
    public GetRequestResponse<CartItem> cartItems(){
        return cartItemService.cartItems();
    }

    @PostMapping("/create")
    public ResponseEntity<CommonResponse> cartItemCreate(@RequestBody CartItemDto cartItemDto){
        return cartItemService.cartItemCreate(cartItemDto);
    }
}
