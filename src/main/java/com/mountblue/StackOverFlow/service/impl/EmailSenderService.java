package com.mountblue.StackOverFlow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.text.SimpleDateFormat;

public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String toEmail,
                         String subject,
                         String body){
        SimpleMailMessage message=new SimpleMailMessage();

        message.setFrom("dixitujjwal77200@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        System.out.println("send Succcessfully");
    }
}
