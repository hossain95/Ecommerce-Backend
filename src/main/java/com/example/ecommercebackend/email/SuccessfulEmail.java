package com.example.ecommercebackend.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SuccessfulEmail {
    @Autowired
    private JavaMailSender mailSend;

    public void sendEmail(String name, String email) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("account activated");
        message.setSentDate(new Date());

        message.setText(
                "Thank you "+  name +"\nYou account activated"
        );
        mailSend.send(message);
    }
}
