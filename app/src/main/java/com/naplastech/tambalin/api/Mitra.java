package com.naplastech.tambalin.api;

public class Mitra {
    String nama;
    Integer status;

    public Mitra(String nama, Integer status) {
        this.nama = nama;
        this.status = status;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
