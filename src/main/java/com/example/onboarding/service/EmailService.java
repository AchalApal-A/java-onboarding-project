package com.example.onboarding.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendMail(String toEmail, String userName, String utmLink) {

        try {
            System.out.println(">>> EMAIL METHOD ENTERED");

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Welcome to Company 🚀");
            helper.setFrom("byeh8096@gmail.com");

            String htmlContent = """
                    <h2>Welcome, %s 👋</h2>
                    <p>Your onboarding is <b>successful</b>.</p>

                    <p><b>Your UTM Link:</b></p>
                    <p style="word-break: break-all; color: blue;">
                        %s
                    </p>

                    <br>

                    <p>Click below to get started:</p>

                    <a href="%s"
                       style="display:inline-block; padding:10px 15px; background-color:#4CAF50; color:white; text-decoration:none; border-radius:5px;">
                        Get Started 🚀
                    </a>
                    """.formatted(userName, utmLink, utmLink);

            helper.setText(htmlContent, true);

            System.out.println(">>> BEFORE SMTP SEND");

            mailSender.send(message);

            System.out.println(">>> EMAIL SENT SUCCESSFULLY");

        } catch (Exception e) {
            System.out.println(">>> EMAIL FAILED");
            e.printStackTrace();
        }
    }

    public String generateUTM(String name) {
        String safeName = name.replaceAll(" ", "_");

        return "https://gpi.industryacademiacommunity.com/iac"
                + "?utm_source=email"
                + "&utm_medium=onboarding"
                + "&utm_campaign=" + safeName + "_" + UUID.randomUUID();
    }
}
