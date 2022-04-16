package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.BuyerInfo;
import com.example.ecommercebackend.dto.SellerInfo;
import com.example.ecommercebackend.email.SendEmailToActivate;
import com.example.ecommercebackend.model.Buyer;
import com.example.ecommercebackend.model.PurchaseHistory;
import com.example.ecommercebackend.model.Seller;
import com.example.ecommercebackend.repository.BuyerRepository;
import com.example.ecommercebackend.repository.PurchaseHistoryRepository;
import com.example.ecommercebackend.repository.SellerRepository;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.GetRequestResponse;
import com.example.ecommercebackend.response.status.ResponseStatus;
import com.example.ecommercebackend.security.PasswordEncode;
import com.example.ecommercebackend.security.PasswordValidation;
import com.example.ecommercebackend.security.Token;
import com.example.ecommercebackend.security.VerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private SendEmailToActivate sendEmailToActivate;

    @Autowired
    private PurchaseHistoryRepository purchaseHistoryRepository;


    public CommonResponse sellerRegistration(Seller seller){
        Seller userModel = sellerRepository.findSellerByEmail(seller.getEmail());
        Buyer buyer = buyerRepository.findBuyerByEmail(seller.getEmail());
        if (Objects.nonNull(userModel) || Objects.nonNull(buyer)){
            return new CommonResponse("email already registered", HttpStatus.BAD_REQUEST);
        }

        if (!new PasswordValidation().isPasswordValid(seller.getPassword())){
            String message = new PasswordValidation().passwordMustBeContain();
            message = "password does not valid\n"+message;
            return new CommonResponse(message, HttpStatus.BAD_REQUEST);
        }

        seller.setPassword(new PasswordEncode().Encoded(seller.getPassword()));
        String verificationCode = new VerificationCode().GenerateVerificationCode();

//        String token = new Token().getToken();

        seller.setVerificationCode(verificationCode);
//        seller.setToken(token);


        sendEmailToActivate.sendEmail(seller.getName(), seller.getEmail(), seller.getVerificationCode());

        sellerRepository.save(seller);
        return new CommonResponse("registration successful", HttpStatus.CREATED);
    }

    public void saveToken(Seller seller, String token){
        seller.setToken(token);
        sellerRepository.save(seller);
    }

    public ResponseEntity<GetRequestResponse> sellList(){
        return new ResponseEntity<>(new GetRequestResponse(ResponseStatus.succeed, sellerRepository.findAll()), HttpStatus.OK);
    }

    public SellerInfo getSeller(String token){
        Seller seller = sellerRepository.findByToken(token);

        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setName(seller.getName());
        sellerInfo.setEmail(seller.getEmail());
        sellerInfo.setContact(seller.getContact());
        sellerInfo.setId(seller.getId());

        return sellerInfo;
    }
}
