package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.BuyerInfo;
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
public class BuyerService {
    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private SellerRepository sellerRepository;


    @Autowired
    private SendEmailToActivate sendEmailToActivate;

    @Autowired
    private PurchaseHistoryRepository purchaseHistoryRepository;


    public CommonResponse buyerRegistration(Buyer buyer){
        Buyer userModel = buyerRepository.findBuyerByEmail(buyer.getEmail());
        Seller seller = sellerRepository.findSellerByEmail(buyer.getEmail());

        if (Objects.nonNull(userModel) || Objects.nonNull(seller)){
            return new CommonResponse("email already registered", HttpStatus.BAD_REQUEST);
        }

        if (!new PasswordValidation().isPasswordValid(buyer.getPassword())){
            String message = new PasswordValidation().passwordMustBeContain();
            message = "password does not valid\n"+message;
            return new CommonResponse(message, HttpStatus.BAD_REQUEST);
        }

        buyer.setPassword(new PasswordEncode().Encoded(buyer.getPassword()));
        String verificationCode = new VerificationCode().GenerateVerificationCode();

//        String token = new Token().getToken();

        buyer.setVerificationCode(verificationCode);
//        buyer.setToken(token);

        sendEmailToActivate.sendEmail(buyer.getName(), buyer.getEmail(), buyer.getVerificationCode());

        buyerRepository.save(buyer);
        return new CommonResponse("registration successful", HttpStatus.CREATED);
    }

    public void saveToken(Buyer buyer, String token){
        buyer.setToken(token);
        buyerRepository.save(buyer);
    }

    public GetRequestResponse buyerList(){
        return new GetRequestResponse(ResponseStatus.succeed, buyerRepository.findAll());
    }

    public GetRequestResponse purchaseHistoryById(Long userId){
        Buyer user = buyerRepository.findById(userId).get();

        List<PurchaseHistory> purchaseHistories = purchaseHistoryRepository.findAllByBuyer(user);
        return new GetRequestResponse<>(ResponseStatus.succeed, purchaseHistories);
    }

    public List<PurchaseHistory> purchaseHistoryDownload(Long userId){
        Buyer user = buyerRepository.findById(userId).get();

        List<PurchaseHistory> purchaseHistories = purchaseHistoryRepository.findAllByBuyer(user);
        return purchaseHistories;
    }

    public BuyerInfo getBuyer(String token){
        Buyer buyer = buyerRepository.findByToken(token);

        BuyerInfo buyerInfo = new BuyerInfo();
        buyerInfo.setContact(buyer.getContact());
        buyerInfo.setEmail(buyer.getEmail());
        buyerInfo.setContact(buyer.getContact());
        buyerInfo.setId(buyer.getId());

        return buyerInfo;
    }
}
