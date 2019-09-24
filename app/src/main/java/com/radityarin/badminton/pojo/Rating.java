package com.radityarin.badminton.pojo;

public class Rating {

    private String idlapangan, namalapangan, idpenyewa, namapenyewa, idsewa, rating, komentar;

    public Rating() {
    }

    public Rating(String idlapangan, String namalapangan, String idpenyewa, String namapenyewa, String idsewa, String rating, String komentar) {
        this.idlapangan = idlapangan;
        this.namalapangan = namalapangan;
        this.idpenyewa = idpenyewa;
        this.namapenyewa = namapenyewa;
        this.idsewa = idsewa;
        this.rating = rating;
        this.komentar = komentar;
    }

    public String getIdlapangan() {
        return idlapangan;
    }

    public String getNamalapangan() {
        return namalapangan;
    }

    public String getIdpenyewa() {
        return idpenyewa;
    }

    public String getNamapenyewa() {
        return namapenyewa;
    }

    public String getIdsewa() {
        return idsewa;
    }

    public String getRating() {
        return rating;
    }

    public String getKomentar() {
        return komentar;
    }
}
