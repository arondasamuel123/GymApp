package com.example.root.volleygymapp;

public class Instructor {
;
    private String name;
    private String email;
    private String gender;
    private String phonenumber;
    private String image;

    public Instructor(String name, String email, String gender, String phonenumber, String image) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.phonenumber = phonenumber;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getImage() {
        return image;
    }
}
