package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.UserDto;
import com.example.ecommercebackend.model.Buyer;
import com.example.ecommercebackend.model.Seller;
import com.example.ecommercebackend.repository.BuyerRepository;
import com.example.ecommercebackend.repository.SellerRepository;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.LoginResponse;
import com.example.ecommercebackend.response.status.ResponseStatus;
import com.example.ecommercebackend.security.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserLoginService {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private SellerRepository sellerRepository;


    public LoginResponse userLogin(UserDto userDto){
        Buyer buyer = buyerRepository.findBuyerByEmail(userDto.getUserName());
        Seller seller = sellerRepository.findSellerByEmail(userDto.getUserName());

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        String token = new Token().getToken();

        if (Objects.isNull(buyer) && Objects.isNull(seller)){
            return new LoginResponse("", "user does not exist");
        }
        if (Objects.nonNull(buyer)){
            String password = userDto.getPassword();
            if (!bcrypt.matches(password, buyer.getPassword())){
                return new LoginResponse("", "wrong password");
            }
            if (!buyer.getEnabled()){
                return new LoginResponse("", "your account not activated yet");
            }

            buyerService.saveToken(buyer, "1"+token);

            return new LoginResponse("1"+token, "login successful");
        }


        if (Objects.nonNull(seller)){
            String password = userDto.getPassword();
            if (!bcrypt.matches(password, seller.getPassword())){
                return new LoginResponse("", "wrong password");
            }
            if (!seller.getEnabled()){
                return new LoginResponse("", "your account not activated yet");
            }
        }
        sellerService.saveToken(seller, "2"+token);
        return new LoginResponse("2"+token, "login successful");
    }
}
