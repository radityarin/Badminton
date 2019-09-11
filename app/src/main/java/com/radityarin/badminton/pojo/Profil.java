package com.radityarin.badminton.pojo;

public class Profil {

    private String userId, namaUser, emailUser, nomorHP;

    public Profil() {
    }

    public Profil(String userId, String namaUser, String emailUser, String nomorHP) {
        this.userId = userId;
        this.namaUser = namaUser;
        this.emailUser = emailUser;
        this.nomorHP = nomorHP;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getNomorHP() {
        return nomorHP;
    }

    public void setNomorHP(String nomorHP) {
        this.nomorHP = nomorHP;
    }

}
