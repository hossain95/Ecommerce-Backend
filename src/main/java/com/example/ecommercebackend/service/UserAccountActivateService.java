package com.example.ecommercebackend.service;

import com.example.ecommercebackend.email.SuccessfulEmail;
import com.example.ecommercebackend.model.Buyer;
import com.example.ecommercebackend.model.Seller;
import com.example.ecommercebackend.repository.BuyerRepository;
import com.example.ecommercebackend.repository.SellerRepository;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.status.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserAccountActivateService {
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private SuccessfulEmail successfulEmail;

    public CommonResponse accountActivate(String email, String code){
        Buyer buyer = buyerRepository.findBuyerByEmail(email);
        Seller seller = sellerRepository.findSellerByEmail(email);

        if (Objects.isNull(buyer) && Objects.isNull(seller)){
            return new CommonResponse("user does not exist", HttpStatus.NOT_FOUND);
        }

        if (Objects.nonNull(buyer)){
            if (code.equals(buyer.getVerificationCode())){
                buyer.setEnabled(true);
                buyerRepository.save(buyer);
                successfulEmail.sendEmail(buyer.getName(), buyer.getEmail());
                return new CommonResponse("your account is activated", HttpStatus.ACCEPTED);
            }
        }
        if (Objects.nonNull(seller)){
            if (code.equals(seller.getVerificationCode())){
                seller.setEnabled(true);
                sellerRepository.save(seller);
                successfulEmail.sendEmail(seller.getName(), seller.getEmail());
                return new CommonResponse("your account is activated", HttpStatus.ACCEPTED);
            }
        }
        return new CommonResponse("your verification code is incorrect", HttpStatus.NOT_ACCEPTABLE);
    }
}
