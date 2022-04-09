package com.example.ecommercebackend.service;

import com.example.ecommercebackend.email.SuccessfulEmail;
import com.example.ecommercebackend.model.UserModel;
import com.example.ecommercebackend.repository.UserRepository;
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
    private UserRepository userRepository;
//    @Autowired
//    private SellerRepository sellerRepository;
    @Autowired
    private SuccessfulEmail successfulEmail;

    public ResponseEntity<CommonResponse> accountActivate(String email, String code){
        UserModel userModel = userRepository.findByEmail(email);
//        Seller seller = sellerRepository.findSellerByEmail(email);

        if (Objects.isNull(userModel)){
            return new ResponseEntity<>(new CommonResponse("user does not exist", ResponseStatus.failed), HttpStatus.NOT_FOUND);
        }

        if (Objects.nonNull(userModel)){
            if (code.equals(userModel.getVerificationCode())){
                userModel.setEnabled(true);
                userRepository.save(userModel);
                successfulEmail.sendEmail(userModel.getFirstName()+ " "+ userModel.getLastName(), userModel.getEmail());
                return new ResponseEntity<>(new CommonResponse("your account is activated", ResponseStatus.succeed), HttpStatus.ACCEPTED);
            }
        }
//        if (Objects.nonNull(seller)){
//            if (code.equals(seller.getVerificationCode())){
//                seller.setEnabled(true);
//                sellerRepository.save(seller);
//                successfulEmail.sendEmail(seller.getFirstName()+ " " + seller.getLastName(), seller.getEmail());
//                return new ResponseEntity<>(new CommonResponse("your account is activated", ResponseStatus.succeed), HttpStatus.ACCEPTED);
//            }
//        }
        return new ResponseEntity<>(new CommonResponse("your verification code is incorrect", ResponseStatus.failed), HttpStatus.NOT_ACCEPTABLE);
    }
}
