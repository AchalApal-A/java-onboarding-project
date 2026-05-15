package com.example.onboarding.controller;

import com.example.onboarding.dto.ApiResponse;
import com.example.onboarding.entity.User;
import com.example.onboarding.service.OnboardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class OnboardingController {

    @Autowired
    private OnboardingService onboardingService;

    @PostMapping(value = "/onboard", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse> onboardUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String college,
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam String postalCode,
            @RequestParam String state,
            @RequestParam String dob,
            @RequestParam String education,
            @RequestParam(required = false) String mentorName,
            @RequestParam("aadhaarFile") MultipartFile aadhaarFile,
            @RequestParam("bonafideFile") MultipartFile bonafideFile,
            @RequestParam("marksheetFile") MultipartFile marksheetFile
    ) {

        System.out.println(">>> CONTROLLER HIT /onboard");

        User savedUser = onboardingService.onboardUser(
                name, email, phone, college, address, city,
                postalCode, state, dob, education, mentorName,
                aadhaarFile, bonafideFile, marksheetFile
        );

        return ResponseEntity.ok(
                new ApiResponse("SUCCESS", "User onboarded successfully", savedUser)
        );
    }
}
