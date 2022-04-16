package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.dto.ProductDto;
import com.example.ecommercebackend.model.Product;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.GetRequestResponse;
import com.example.ecommercebackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@Tag(name = "Product")
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public GetRequestResponse<Product> productsList(){
        return productService.productList();
    }

    @PostMapping("/create")
    public CommonResponse productCreate(@RequestBody ProductDto productDto){
        return productService.productCreate(productDto);
    }

    @GetMapping("/{productId}/details")
    public Product productDetails(@PathVariable("productId") Long productId){
        return productService.productDetails(productId);
    }
}
