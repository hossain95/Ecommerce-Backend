package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.CartItemDto;
import com.example.ecommercebackend.dto.dtoConverter.DtoConverter;
import com.example.ecommercebackend.model.CartItem;
import com.example.ecommercebackend.model.Product;
import com.example.ecommercebackend.model.ShoppingCart;
import com.example.ecommercebackend.model.UserModel;
import com.example.ecommercebackend.repository.CartItemRepository;
import com.example.ecommercebackend.repository.ProductRepository;
import com.example.ecommercebackend.repository.ShoppingCartRepository;
import com.example.ecommercebackend.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;


    public GetRequestResponse<CartItem> cartItems(){
        return new GetRequestResponse<>(ResponseStatus.succeed, cartItemRepository.findAll());
    }

    public ResponseEntity<CommonResponse> cartItemCreate(CartItemDto cartItemDto){

        Optional<Product> product = productRepository.findById(cartItemDto.getProductId());

        if (product.isEmpty()){
            return new ResponseEntity<>(new CommonResponse("Product does not found!", ResponseStatus.failed), HttpStatus.BAD_REQUEST);
        }

        Optional<UserModel> user = userRepository.findById(cartItemDto.getCustomerId());
        if (user.isEmpty()){
            return new ResponseEntity<>(new CommonResponse("Customer does not found!", ResponseStatus.failed), HttpStatus.BAD_REQUEST);
        }
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserModel(user.get());
        if (Objects.isNull(shoppingCart)){

            shoppingCart = shoppingCartService.createShoppingCart(user.get());
        }


        CartItem cartItem = new DtoConverter().cartItemDtoToCartItem(cartItemDto, product.get());
        cartItem.setSubTotalDeliveryCharge(cartItem.getQuantity()*product.get().getDeliveryCharge());

        shoppingCart.setTotalProductPrice(shoppingCart.getTotalProductPrice()+cartItem.getSubTotal());
        shoppingCart.setTotalDeliveryCharge(shoppingCart.getTotalDeliveryCharge()+cartItem.getSubTotalDeliveryCharge());
        shoppingCart.setTotal(shoppingCart.getTotalProductPrice()+shoppingCart.getTotalDeliveryCharge());

        cartItem.setShoppingCart(shoppingCart);

        cartItemRepository.save(cartItem);

        return new ResponseEntity<>(new CommonResponse("cart item added", ResponseStatus.succeed), HttpStatus.CREATED);
    }

    public ResponseEntity<CommonResponse> cartItemDeleteById(Long cartId){
        cartItemRepository.deleteById(cartId);

        return new ResponseEntity<>(new CommonResponse("cart item deleted", ResponseStatus.succeed), HttpStatus.OK);
    }
}
