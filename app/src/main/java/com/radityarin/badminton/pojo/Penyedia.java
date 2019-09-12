package com.radityarin.badminton.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Penyedia implements Parcelable {
    private String idlapangan, emaillapangan, namalapangan, jumlahlapangan, alamatlapangan, kordinatlapangan, fotolapangan, notelepon, jambuka, jamtutup, harga,namalapangansmallcase;
    private Boolean active;

    public Penyedia() {
    }

    public Penyedia(String idlapangan, String emaillapangan, String namalapangan, String jumlahlapangan, String alamatlapangan, String kordinatlapangan, String fotolapangan, String notelepon, String jambuka, String jamtutup, String harga, String namalapangansmallcase, Boolean active) {
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
        this.namalapangansmallcase = namalapangansmallcase;
        this.active = active;
    }

    public String getIdlapangan() {
        return idlapangan;
    }

    public String getEmaillapangan() {
        return emaillapangan;
    }

    public String getNamalapangan() {
        return namalapangan;
    }

    public String getJumlahlapangan() {
        return jumlahlapangan;
    }

    public String getAlamatlapangan() {
        return alamatlapangan;
    }

    public String getKordinatlapangan() {
        return kordinatlapangan;
    }

    public String getFotolapangan() {
        return fotolapangan;
    }

    public String getNotelepon() {
        return notelepon;
    }

    public String getJambuka() {
        return jambuka;
    }

    public String getJamtutup() {
        return jamtutup;
    }

    public String getHarga() {
        return harga;
    }

    public String getNamalapangansmallcase() {
        return namalapangansmallcase;
    }

    public Boolean getActive() {
        return active;
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
        dest.writeString(this.namalapangansmallcase);
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
        this.namalapangansmallcase = in.readString();
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