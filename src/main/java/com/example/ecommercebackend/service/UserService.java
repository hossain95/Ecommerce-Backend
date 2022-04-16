//package com.example.ecommercebackend.service;
//
//import com.example.ecommercebackend.email.SendEmailToActivate;
//import com.example.ecommercebackend.model.PurchaseHistory;
//import com.example.ecommercebackend.repository.PurchaseHistoryRepository;
//import com.example.ecommercebackend.response.CommonResponse;
//import com.example.ecommercebackend.response.GetRequestResponse;
//import com.example.ecommercebackend.response.status.ResponseStatus;
//import com.example.ecommercebackend.security.PasswordEncode;
//import com.example.ecommercebackend.security.PasswordValidation;
//import com.example.ecommercebackend.security.VerificationCode;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Objects;
//
//@Service
//public class UserService {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private SendEmailToActivate sendEmailToActivate;
//
//    @Autowired
//    private PurchaseHistoryRepository purchaseHistoryRepository;
//
//
//    public ResponseEntity<CommonResponse> userRegistration(UserModel newUserModel){
//        UserModel userModel = userRepository.findByEmail(newUserModel.getEmail());
//        if (Objects.nonNull(userModel)){
//            return new ResponseEntity<>(new CommonResponse("email already registered", ResponseStatus.failed), HttpStatus.NOT_ACCEPTABLE);
//        }
//
//        if (!new PasswordValidation().isPasswordValid(newUserModel.getPassword())){
//            String message = new PasswordValidation().passwordMustBeContain();
//            message = "password does not valid\n"+message;
//            return new ResponseEntity<>(new CommonResponse(message, ResponseStatus.failed), HttpStatus.NOT_ACCEPTABLE);
//        }
//
//        newUserModel.setPassword(new PasswordEncode().Encoded(newUserModel.getPassword()));
//        String verificationCode = new VerificationCode().GenerateVerificationCode();
//
//        newUserModel.setVerificationCode(verificationCode);
//
//        sendEmailToActivate.sendEmail(newUserModel.getFirstName()+ " "+ newUserModel.getLastName(), newUserModel.getEmail(), newUserModel.getVerificationCode());
//
//        userRepository.save(newUserModel);
//        return new ResponseEntity<>(new CommonResponse("registration successful", ResponseStatus.succeed), HttpStatus.CREATED);
//    }
//
//    public ResponseEntity<GetRequestResponse> userList(){
//        return new ResponseEntity<>(new GetRequestResponse(ResponseStatus.succeed, userRepository.findAll()), HttpStatus.OK);
//    }
//
//    public ResponseEntity<GetRequestResponse> purchaseHistoryById(Long userId){
//        UserModel user = userRepository.findById(userId).get();
//
//        List<PurchaseHistory> purchaseHistories = purchaseHistoryRepository.findAllByUser(user);
//        return new ResponseEntity<>(new GetRequestResponse<>(ResponseStatus.succeed, purchaseHistories), HttpStatus.OK);
//    }
//
//    public List<PurchaseHistory> purchaseHistoryDownload(Long userId){
//        UserModel user = userRepository.findById(userId).get();
//
//        List<PurchaseHistory> purchaseHistories = purchaseHistoryRepository.findAllByUser(user);
//        return purchaseHistories;
//    }
//}
