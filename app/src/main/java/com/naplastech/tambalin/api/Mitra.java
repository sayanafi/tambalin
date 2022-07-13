package com.naplastech.tambalin.api;

public class Mitra {
    String nama;
    Integer jum_pesanan, status;
    Double penilaian;

    public Mitra(String nama, Integer jum_pesanan, Integer status, Double penilaian) {
        this.nama = nama;
        this.jum_pesanan = jum_pesanan;
        this.status = status;
        this.penilaian = penilaian;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getJum_pesanan() {
        return jum_pesanan;
    }

    public void setJum_pesanan(Integer jum_pesanan) {
        this.jum_pesanan = jum_pesanan;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getPenilaian() {
        return penilaian;
    }

    public void setPenilaian(Double penilaian) {
        this.penilaian = penilaian;
    }
}
