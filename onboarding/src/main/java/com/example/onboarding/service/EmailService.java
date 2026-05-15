package com.example.onboarding.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.Mail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class EmailService {

    @Value("${SENDGRID_API_KEY}")
    private String apiKey;

    // ✅ SEND EMAIL VIA SENDGRID
    public void sendMail(String toEmail, String userName, String utmLink) {

        Email from = new Email("your_verified_sender_email@gmail.com"); 
        Email to = new Email(toEmail);
        String subject = "Welcome to Company 🚀";

        String htmlContent = """
                <h2>Welcome, %s 👋</h2>
                <p>Your onboarding is <b>successful</b>.</p>

                <p><b>Your UTM Link:</b></p>
                <p style="word-break: break-all; color: blue;">%s</p>

                <br/>

                <a href="%s"
                   style="display:inline-block;
                          padding:10px 15px;
                          background-color:#4CAF50;
                          color:white;
                          text-decoration:none;
                          border-radius:5px;">
                    Get Started 🚀
                </a>
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

            System.out.println("✅ SendGrid Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("❌ SendGrid email failed: " + e.getMessage());
        }
    }

    // ✅ UTM GENERATOR
    public String generateUTM(String name) {

        String safeName = name.replaceAll(" ", "_");

        return "https://gpi.industryacademiacommunity.com/iac"
                + "?utm_source=email"
                + "&utm_medium=onboarding"
                + "&utm_campaign=" + safeName + "_" + UUID.randomUUID();
    }
}
