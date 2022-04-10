package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.dto.ShippingAddressDto;
import com.example.ecommercebackend.model.ShippingAddress;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.service.ShippingAddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Shipping Address")
public class ShippingAddressController {
    @Autowired
    private ShippingAddressService shippingAddressService;


    @PostMapping("/shipping-address")
    public ResponseEntity<CommonResponse> shippingAddressCreate(@RequestBody ShippingAddressDto shippingAddressDto){
        return shippingAddressService.shippingAddressCreate(shippingAddressDto);
    }
}
