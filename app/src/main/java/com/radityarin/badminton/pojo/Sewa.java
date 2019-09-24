package com.radityarin.badminton.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Sewa implements Parcelable {

    private String idlapangan, namalapangan, nomorlapangan, tglsewa, jamsewa, idpenyewa, namapenyewa, statussewa, idsewa;

    public Sewa() {

    }

    public Sewa(String idlapangan, String namalapangan, String nomorlapangan, String tglsewa, String jamsewa, String idpenyewa, String namapenyewa, String statussewa, String idsewa) {
        this.idlapangan = idlapangan;
        this.namalapangan = namalapangan;
        this.nomorlapangan = nomorlapangan;
        this.tglsewa = tglsewa;
        this.jamsewa = jamsewa;
        this.idpenyewa = idpenyewa;
        this.namapenyewa = namapenyewa;
        this.statussewa = statussewa;
        this.idsewa = idsewa;
    }

    public String getIdlapangan() {
        return idlapangan;
    }

    public String getNamalapangan() {
        return namalapangan;
    }

    public String getTglsewa() {
        return tglsewa;
    }

    public String getJamsewa() {
        return jamsewa;
    }

    public String getIdpenyewa() {
        return idpenyewa;
    }

    public String getNamapenyewa() {
        return namapenyewa;
    }

    public String getStatussewa() {
        return statussewa;
    }

    public String getIdsewa() {
        return idsewa;
    }

    public String getNomorlapangan() {
        return nomorlapangan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idlapangan);
        dest.writeString(this.namalapangan);
        dest.writeString(this.nomorlapangan);
        dest.writeString(this.tglsewa);
        dest.writeString(this.jamsewa);
        dest.writeString(this.idpenyewa);
        dest.writeString(this.namapenyewa);
        dest.writeString(this.statussewa);
        dest.writeString(this.idsewa);
    }

    protected Sewa(Parcel in) {
        this.idlapangan = in.readString();
        this.namalapangan = in.readString();
        this.nomorlapangan = in.readString();
        this.tglsewa = in.readString();
        this.jamsewa = in.readString();
        this.idpenyewa = in.readString();
        this.namapenyewa = in.readString();
        this.statussewa = in.readString();
        this.idsewa = in.readString();
    }

    public static final Creator<Sewa> CREATOR = new Creator<Sewa>() {
        @Override
        public Sewa createFromParcel(Parcel source) {
            return new Sewa(source);
        }

        @Override
        public Sewa[] newArray(int size) {
            return new Sewa[size];
        }
    };
}