package com.example.ecommercebackend.dto.dtoConverter;

import com.example.ecommercebackend.dto.CartItemDto;
import com.example.ecommercebackend.dto.CategoryDto;
import com.example.ecommercebackend.dto.ProductDto;
import com.example.ecommercebackend.dto.ShippingAddressDto;
import com.example.ecommercebackend.model.*;

public class DtoConverter {

    public Category categoryDtoToCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());

        return category;
    }
    public CartItem cartItemDtoToCartItem(CartItemDto cartItemDto, Product product){
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setProduct(product);
//        cartItem.setShoppingCart(shoppingCart);
        cartItem.setSubTotal(product.getPrice()*cartItemDto.getQuantity());
        cartItem.setSubTotalDeliveryCharge(product.getDeliveryCharge()*cartItemDto.getQuantity());
        return cartItem;
    }

    public Product productDtoToProduct(ProductDto productDto, Category category){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        product.setImage(productDto.getImage());
        product.setDeliveryCharge(productDto.getDeliveryCharge());
        product.setCategory(category);
        product.setQuantity(productDto.getQuantity());

        return product;
    }

    public ShippingAddress shippingAddressDtoToShippingAddress(ShippingAddressDto shippingAddressDto, Buyer buyer){
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setName(shippingAddressDto.getName());
        shippingAddress.setPhone(shippingAddressDto.getPhone());
        shippingAddress.setEmail(shippingAddressDto.getEmail());
        shippingAddress.setDivision(shippingAddressDto.getDivision());
        shippingAddress.setDistrict(shippingAddressDto.getDistrict());
        shippingAddress.setThana(shippingAddressDto.getThana());
        shippingAddress.setPostOffice(shippingAddressDto.getPostOffice());
        shippingAddress.setBuyer(buyer);

        return shippingAddress;
    }
}
