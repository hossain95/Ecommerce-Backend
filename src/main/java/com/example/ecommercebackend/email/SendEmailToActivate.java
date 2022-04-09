package com.example.ecommercebackend.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SendEmailToActivate {
    @Autowired
    private JavaMailSender mailSend;

    public void sendEmail(String name, String email, String verificationCode) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("activation code");
        message.setSentDate(new Date());

        message.setText(
              "Hi, "+  name +"\nPlease visit the link to active the account.\n" +
              "http://localhost:8888/user/account/activate/"+email+"/"+verificationCode
        );
        mailSend.send(message);
    }
}
