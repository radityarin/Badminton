package com.radityarin.badminton.pojo;

public class Profil {

    private String userId;
    private String namaUser;
    private String emailUser;
    private String nomorHP;
    private String fotoUser;

    public Profil() {
    }

    public Profil(String userId, String namaUser, String emailUser, String nomorHP, String fotoUser) {
        this.userId = userId;
        this.namaUser = namaUser;
        this.emailUser = emailUser;
        this.nomorHP = nomorHP;
        this.fotoUser = fotoUser;
    }

    public String getUserId() {
        return userId;
    }

    public String getNamaUser() {
        return namaUser;
    }


    public String getEmailUser() {
        return emailUser;
    }


    public String getNomorHP() {
        return nomorHP;
    }

    public String getFotoUser() {
        return fotoUser;
    }

    public void setFotoUser(String fotoUser) {
        this.fotoUser = fotoUser;
    }
}
