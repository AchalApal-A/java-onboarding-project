package com.example.onboarding.service;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${SENDGRID_API_KEY}")
    private String apiKey;

    public void sendMail(String toEmail, String userName, String utmLink) {

        Email from = new Email("byeh8096@gmail.com");
        Email to = new Email(toEmail);
        String subject = "Welcome to Company 🚀";

        String htmlContent = """
                <h2>Welcome, %s 👋</h2>
                <p>Your onboarding is successful.</p>

                <p><b>Your UTM Link:</b></p>
                <p>%s</p>

                <a href="%s">Get Started 🚀</a>
                """.formatted(userName, utmLink, utmLink);

        Content content = new Content("text/html", htmlContent);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println("SendGrid Status: " + response.getStatusCode());

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("SendGrid email failed");
        }
    }
}
