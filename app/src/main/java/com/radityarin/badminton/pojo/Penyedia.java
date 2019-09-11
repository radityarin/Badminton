package com.radityarin.badminton.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Penyedia implements Parcelable {
    private String idlapangan, emaillapangan, namalapangan, jumlahlapangan, alamatlapangan, kordinatlapangan, fotolapangan, notelepon, jambuka, jamtutup, harga;
    private Boolean active;

    public Penyedia() {
    }

    public Penyedia(String idlapangan, String emaillapangan, String namalapangan, String jumlahlapangan, String alamatlapangan, String kordinatlapangan, String fotolapangan, String notelepon, String jambuka, String jamtutup, String harga, Boolean active) {
        this.idlapangan = idlapangan;
        this.emaillapangan = emaillapangan;
        this.namalapangan = namalapangan;
        this.jumlahlapangan = jumlahlapangan;
        this.alamatlapangan = alamatlapangan;
        this.kordinatlapangan = kordinatlapangan;
        this.fotolapangan = fotolapangan;
        this.notelepon = notelepon;
        this.jambuka = jambuka;
        this.jamtutup = jamtutup;
        this.harga = harga;
        this.active = active;
    }

    public String getIdlapangan() {
        return idlapangan;
    }

    public void setIdlapangan(String idlapangan) {
        this.idlapangan = idlapangan;
    }

    public String getEmaillapangan() {
        return emaillapangan;
    }

    public void setEmaillapangan(String emaillapangan) {
        this.emaillapangan = emaillapangan;
    }

    public String getNamalapangan() {
        return namalapangan;
    }

    public void setNamalapangan(String namalapangan) {
        this.namalapangan = namalapangan;
    }

    public String getJumlahlapangan() {
        return jumlahlapangan;
    }

    public void setJumlahlapangan(String jumlahlapangan) {
        this.jumlahlapangan = jumlahlapangan;
    }

    public String getAlamatlapangan() {
        return alamatlapangan;
    }

    public void setAlamatlapangan(String alamatlapangan) {
        this.alamatlapangan = alamatlapangan;
    }

    public String getKordinatlapangan() {
        return kordinatlapangan;
    }

    public void setKordinatlapangan(String kordinatlapangan) {
        this.kordinatlapangan = kordinatlapangan;
    }

    public String getFotolapangan() {
        return fotolapangan;
    }

    public void setFotolapangan(String fotolapangan) {
        this.fotolapangan = fotolapangan;
    }

    public String getNotelepon() {
        return notelepon;
    }

    public void setNotelepon(String notelepon) {
        this.notelepon = notelepon;
    }

    public String getJambuka() {
        return jambuka;
    }

    public void setJambuka(String jambuka) {
        this.jambuka = jambuka;
    }

    public String getJamtutup() {
        return jamtutup;
    }

    public void setJamtutup(String jamtutup) {
        this.jamtutup = jamtutup;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idlapangan);
        dest.writeString(this.emaillapangan);
        dest.writeString(this.namalapangan);
        dest.writeString(this.jumlahlapangan);
        dest.writeString(this.alamatlapangan);
        dest.writeString(this.kordinatlapangan);
        dest.writeString(this.fotolapangan);
        dest.writeString(this.notelepon);
        dest.writeString(this.jambuka);
        dest.writeString(this.jamtutup);
        dest.writeString(this.harga);
        dest.writeValue(this.active);
    }

    protected Penyedia(Parcel in) {
        this.idlapangan = in.readString();
        this.emaillapangan = in.readString();
        this.namalapangan = in.readString();
        this.jumlahlapangan = in.readString();
        this.alamatlapangan = in.readString();
        this.kordinatlapangan = in.readString();
        this.fotolapangan = in.readString();
        this.notelepon = in.readString();
        this.jambuka = in.readString();
        this.jamtutup = in.readString();
        this.harga = in.readString();
        this.active = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<Penyedia> CREATOR = new Creator<Penyedia>() {
        @Override
        public Penyedia createFromParcel(Parcel source) {
            return new Penyedia(source);
        }

        @Override
        public Penyedia[] newArray(int size) {
            return new Penyedia[size];
        }
    };
}