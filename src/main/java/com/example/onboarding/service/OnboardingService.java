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

    try {
        System.out.println(">>> BEFORE EMAIL CALL");

        emailService.sendMail(email, name, utm);

        System.out.println(">>> AFTER EMAIL CALL");

    } catch (Exception e) {
        System.out.println(">>> EMAIL FAILED");
        e.printStackTrace();
    }

    return savedUser;
}
