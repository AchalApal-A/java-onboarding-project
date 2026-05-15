package com.example.onboarding.service;

import com.example.onboarding.entity.User;
import com.example.onboarding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class OnboardingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public User onboardUser(
            String name, String email, String phone, String college,
            String address, String city, String postalCode, String state,
            String dob, String education, String mentorName,
            MultipartFile aadhaarFile,
            MultipartFile bonafideFile,
            MultipartFile marksheetFile
    ) {

        System.out.println(">>> ONBOARDING STARTED");

        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setCollege(college);
        user.setAddress(address);
        user.setCity(city);
        user.setPostalCode(postalCode);
        user.setState(state);
        user.setDob(dob);
        user.setEducation(education);
        user.setMentorName(mentorName);

        try {
            System.out.println(">>> FILE UPLOAD STARTED");

            String uploadDir = "uploads/";
            Files.createDirectories(Paths.get(uploadDir));

            Files.write(Paths.get(uploadDir + aadhaarFile.getOriginalFilename()), aadhaarFile.getBytes());
            Files.write(Paths.get(uploadDir + bonafideFile.getOriginalFilename()), bonafideFile.getBytes());
            Files.write(Paths.get(uploadDir + marksheetFile.getOriginalFilename()), marksheetFile.getBytes());

            System.out.println(">>> FILE UPLOAD DONE");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("File upload failed");
        }

        String utm = emailService.generateUTM(name);
        user.setUtmLink(utm);

        User savedUser = userRepository.save(user);

        System.out.println(">>> USER SAVED IN DB");

        emailService.sendMail(email, name, utm);

        System.out.println(">>> EMAIL CALL TRIGGERED (ASYNC)");

        return savedUser;
    }
}
