package com.ezyindustries.goes_englishcourse;

public class User {
    private String username, nickname, email, phone,Lesson, Tes, Latihan,Gender, deskripsion, website;

    public User (){}

    public User(String username, String nickname, String email, String phone, String lesson, String tes, String latihan, String gender, String deskripsion, String website) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        Lesson = lesson;
        Tes = tes;
        Latihan = latihan;
        Gender = gender;
        this.deskripsion = deskripsion;
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLesson() {
        return Lesson;
    }

    public void setLesson(String lesson) {
        Lesson = lesson;
    }

    public String getTes() {
        return Tes;
    }

    public void setTes(String tes) {
        Tes = tes;
    }

    public String getLatihan() {
        return Latihan;
    }

    public void setLatihan(String latihan) {
        Latihan = latihan;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDeskripsion() {
        return deskripsion;
    }

    public void setDeskripsion(String deskripsion) {
        this.deskripsion = deskripsion;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
