package com.example.foodapps.Model;

public class User {

    private String Name;
    private String Password;
    private String Phone;
    private String Email;
    private String IsStaff;

    public User() {
    }

    public User(String name, String password, String email) {
        Name = name;
        Password = password;
        IsStaff = "false";
        Email = email;
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
