package com.kucp1127.PingMe.Email.EmailController;

import com.kucp1127.PingMe.Email.EmailService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailController {

    @Autowired
    private EmailService emailService;

    public String sendEmail(String toEmail , String subject , String body) {
        emailService.sendSimpleEmail(
                toEmail,
                subject,
                body
        );
        return "Email sent successfully";
    }

}