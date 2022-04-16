package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.BuyerInfo;
import com.example.ecommercebackend.email.SendEmailToActivate;
import com.example.ecommercebackend.model.Buyer;
import com.example.ecommercebackend.model.PurchaseHistory;
import com.example.ecommercebackend.model.Seller;
import com.example.ecommercebackend.model.ShippingAddress;
import com.example.ecommercebackend.repository.BuyerRepository;
import com.example.ecommercebackend.repository.PurchaseHistoryRepository;
import com.example.ecommercebackend.repository.SellerRepository;
import com.example.ecommercebackend.repository.ShippingAddressRepository;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.GetRequestResponse;
import com.example.ecommercebackend.response.status.GetResponseSingle;
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
    private ShippingAddressRepository shippingAddressRepository;
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
        buyerInfo.setName(buyer.getName());
        buyerInfo.setContact(buyer.getContact());
        buyerInfo.setEmail(buyer.getEmail());
        buyerInfo.setContact(buyer.getContact());
        buyerInfo.setId(buyer.getId());

        return buyerInfo;
    }

    public CommonResponse buyerInfoUpdate(BuyerInfo buyerInfo){
        Buyer buyer = buyerRepository.findById(buyerInfo.getId()).get();

        buyer.setName(buyerInfo.getName());
        buyer.setContact(buyerInfo.getContact());
        buyer.setEmail(buyerInfo.getEmail());

        buyerRepository.save(buyer);
        return new CommonResponse("updated", HttpStatus.CREATED);
    }

    public GetResponseSingle <ShippingAddress> shippingAddress(Long buyerId){
        Buyer buyer = buyerRepository.findById(buyerId).get();


        ShippingAddress shippingAddress = shippingAddressRepository.findByBuyer(buyer);
        if (Objects.isNull(shippingAddress)){
            return new GetResponseSingle<>(HttpStatus.NOT_FOUND, null);
        }

        return new GetResponseSingle<>(HttpStatus.OK, shippingAddress);
    }

    public CommonResponse shippingAddressUpdate(ShippingAddress shippingAddress, Long buyerId){
        Buyer buyer = buyerRepository.findById(buyerId).get();

        ShippingAddress address = shippingAddressRepository.findByBuyer(buyer);

        if (Objects.isNull(address)){
            return new CommonResponse("shipping address not found", HttpStatus.BAD_REQUEST);
        }
        address.setName(shippingAddress.getName());
        address.setPhone(shippingAddress.getPhone());
        address.setEmail(shippingAddress.getEmail());
        address.setDivision(shippingAddress.getDivision());
        address.setDivision(shippingAddress.getDistrict());
        address.setThana(shippingAddress.getThana());
        address.setPostOffice(shippingAddress.getPostOffice());

        shippingAddressRepository.save(address);

        return new CommonResponse("shipping address updated", HttpStatus.OK);
    }
}
