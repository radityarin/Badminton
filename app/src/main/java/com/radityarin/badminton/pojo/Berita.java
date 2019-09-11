package com.radityarin.badminton.pojo;

public class Berita {
    private String isiberita,judul,urlfoto;

    public Berita(){

    }

    public Berita(String isiberita, String judul, String urlfoto) {
        this.isiberita = isiberita;
        this.judul = judul;
        this.urlfoto = urlfoto;
    }

    public String getIsiberita() {
        return isiberita;
    }

    public String getJudul() {
        return judul;
    }

    public String getUrlfoto() {
        return urlfoto;
    }
}
