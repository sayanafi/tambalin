package com.naplastech.tambalin;

public class usermodels {
    private String password;
    private String nama;
    private String notelp;
    private String alamat;

    public usermodels(){}

    public usermodels(String password, String nama, String notelp, String alamat) {
        this.password = password;
        this.nama = nama;
        this.notelp = notelp;
        this.alamat = alamat;
    }


    public String getPassword() {
        return password;
    }

    public String getNama() {
        return nama;
    }

    public String getNotelp() {
        return notelp;
    }

    public String getAlamat() {
        return alamat;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
