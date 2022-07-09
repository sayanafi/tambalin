package com.naplastech.tambalin.api;

public class CekNomor {
    String nomor_hp;
    Integer status;

    public CekNomor(String nomor_hp, Integer status) {
        this.nomor_hp = nomor_hp;
        this.status = status;
    }

    public String getNomor_hp() {
        return nomor_hp;
    }

    public void setNomor_hp(String nomor_hp) {
        this.nomor_hp = nomor_hp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
