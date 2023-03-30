package com.bsib.demo.fileUploader.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public void sendEmail(String email) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(email);
            mailMessage.setSubject("File Upload Success");
            mailMessage.setText("Congrats, You have uploaded the file successfully...!");
            javaMailSender.send(mailMessage);
            log.info("Mail send success : " + mailMessage);
        } catch (Exception e){
            log.error("send email failed", e);
            throw new RuntimeException("Unable to send email", e);
        }

    }
}
