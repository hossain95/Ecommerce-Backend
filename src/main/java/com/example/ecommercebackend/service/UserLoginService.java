package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.UserDto;
import com.example.ecommercebackend.model.UserModel;
import com.example.ecommercebackend.repository.UserRepository;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.status.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserLoginService {

    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private SellerRepository sellerRepository;


    public ResponseEntity<CommonResponse> userLogin(UserDto userDto){
        UserModel userModel = userRepository.findByEmail(userDto.getUserName());
//        Seller seller = sellerRepository.findSellerByEmail(userDto.getUserName());

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        if (Objects.isNull(userModel)){
            return new ResponseEntity<>(new CommonResponse("user does not exist", ResponseStatus.failed), HttpStatus.NOT_FOUND);
        }
        if (Objects.nonNull(userModel)){
            String password = userDto.getPassword();
            if (!bcrypt.matches(password, userModel.getPassword())){
                return new ResponseEntity<>(new CommonResponse("wrong password", ResponseStatus.failed), HttpStatus.NOT_ACCEPTABLE);
            }
            if (!userModel.getEnabled()){
                return new ResponseEntity<>(new CommonResponse("your account not activated yet", ResponseStatus.failed), HttpStatus.NOT_ACCEPTABLE);
            }
        }
//        if (Objects.nonNull(seller)){
//            String password = userDto.getPassword();
//            if (!bcrypt.matches(password, seller.getPassword())){
//                return new ResponseEntity<>(new CommonResponse("wrong password", ResponseStatus.failed), HttpStatus.NOT_ACCEPTABLE);
//            }
//            if (!seller.getEnabled()){
//                return new ResponseEntity<>(new CommonResponse("your account not activated yet", ResponseStatus.failed), HttpStatus.NOT_ACCEPTABLE);
//            }
//        }

        return new ResponseEntity<>(new CommonResponse("login successful", ResponseStatus.succeed), HttpStatus.OK);
    }
}
