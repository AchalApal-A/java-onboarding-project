package com.example.onboarding.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    private String phone;
    private String college;
    private String address;
    private String mentorName;

    private String city;
    private String postalCode;
    private String state;
    private String dob;
    private String education;

    private String utmLink;

    // getters & setters

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCollege() { return college; }
    public void setCollege(String college) { this.college = college; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public String getUtmLink() { return utmLink; }
    public void setUtmLink(String utmLink) { this.utmLink = utmLink; }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }
}
