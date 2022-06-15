package com.naplastech.tambalin;

public class usermodels {
    private String password;
    private String nama;
    private String notelp;
    private String alamat;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public usermodels(){}

    public usermodels(String password, String nama, String notelp, String alamat, String role) {
        this.password = password;
        this.nama = nama;
        this.notelp = notelp;
        this.alamat = alamat;
        this.role = role;
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
