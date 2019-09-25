package com.radityarin.badminton.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Penyedia implements Parcelable {
    private String idlapangan, emaillapangan, namalapangan, jumlahlapangan, alamatlapangan, kordinatlapangan, fotolapangan, notelepon, jambuka, jamtutup, harga,namalapangansmallcase, fasilitas;
    private double rating;
    private boolean active;

    public Penyedia() {
    }

    public Penyedia(String idlapangan, String emaillapangan, String namalapangan, String jumlahlapangan, String alamatlapangan, String kordinatlapangan, String fotolapangan, String notelepon, String jambuka, String jamtutup, String harga, String namalapangansmallcase, String fasilitas, double rating, boolean active) {
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
        this.fasilitas = fasilitas;
        this.rating = rating;
        this.active = active;
    }

    protected Penyedia(Parcel in) {
        idlapangan = in.readString();
        emaillapangan = in.readString();
        namalapangan = in.readString();
        jumlahlapangan = in.readString();
        alamatlapangan = in.readString();
        kordinatlapangan = in.readString();
        fotolapangan = in.readString();
        notelepon = in.readString();
        jambuka = in.readString();
        jamtutup = in.readString();
        harga = in.readString();
        namalapangansmallcase = in.readString();
        fasilitas = in.readString();
        rating = in.readDouble();
        active = in.readByte() != 0;
    }

    public static final Creator<Penyedia> CREATOR = new Creator<Penyedia>() {
        @Override
        public Penyedia createFromParcel(Parcel in) {
            return new Penyedia(in);
        }

        @Override
        public Penyedia[] newArray(int size) {
            return new Penyedia[size];
        }
    };

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

    public String getFasilitas() {
        return fasilitas;
    }

    public double getRating() {
        return rating;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idlapangan);
        dest.writeString(emaillapangan);
        dest.writeString(namalapangan);
        dest.writeString(jumlahlapangan);
        dest.writeString(alamatlapangan);
        dest.writeString(kordinatlapangan);
        dest.writeString(fotolapangan);
        dest.writeString(notelepon);
        dest.writeString(jambuka);
        dest.writeString(jamtutup);
        dest.writeString(harga);
        dest.writeString(namalapangansmallcase);
        dest.writeString(fasilitas);
        dest.writeDouble(rating);
        dest.writeByte((byte) (active ? 1 : 0));
    }
}