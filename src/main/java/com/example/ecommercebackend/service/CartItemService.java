package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.CartItemDto;
import com.example.ecommercebackend.dto.dtoConverter.DtoConverter;
import com.example.ecommercebackend.model.Buyer;
import com.example.ecommercebackend.model.CartItem;
import com.example.ecommercebackend.model.Product;
import com.example.ecommercebackend.model.ShoppingCart;
import com.example.ecommercebackend.repository.BuyerRepository;
import com.example.ecommercebackend.repository.CartItemRepository;
import com.example.ecommercebackend.repository.ProductRepository;
import com.example.ecommercebackend.repository.ShoppingCartRepository;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.GetRequestResponse;
import com.example.ecommercebackend.response.status.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;


    public GetRequestResponse<CartItem> cartItems(){
        return new GetRequestResponse<>(ResponseStatus.succeed, cartItemRepository.findAll());
    }

    public CommonResponse cartItemCreate(CartItemDto cartItemDto){

        Optional<Product> product = productRepository.findById(cartItemDto.getProductId());

        if (product.isEmpty()){
            return new CommonResponse("Product does not found!", HttpStatus.BAD_REQUEST);
        }

        Optional<Buyer> buyer = buyerRepository.findById(cartItemDto.getCustomerId());
        if (buyer.isEmpty()){
            return new CommonResponse("Customer does not found!", HttpStatus.BAD_REQUEST);
        }
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByBuyer(buyer.get());
        if (Objects.isNull(shoppingCart)){

            shoppingCart = shoppingCartService.createShoppingCart(buyer.get());
        }


        CartItem cartItem = new DtoConverter().cartItemDtoToCartItem(cartItemDto, product.get());
        cartItem.setSubTotalDeliveryCharge(cartItem.getQuantity()*product.get().getDeliveryCharge());

        shoppingCart.setTotalProductPrice(shoppingCart.getTotalProductPrice()+cartItem.getSubTotal());
        shoppingCart.setTotalDeliveryCharge(shoppingCart.getTotalDeliveryCharge()+cartItem.getSubTotalDeliveryCharge());
        shoppingCart.setTotal(shoppingCart.getTotalProductPrice()+shoppingCart.getTotalDeliveryCharge());

        cartItem.setShoppingCart(shoppingCart);

        cartItemRepository.save(cartItem);

        return new CommonResponse("cart item added", HttpStatus.CREATED);
    }

    public CommonResponse cartItemDeleteById(Long cartId){
        cartItemRepository.deleteById(cartId);

        return new CommonResponse("cart item deleted", HttpStatus.OK);
    }
}
