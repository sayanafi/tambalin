package com.naplastech.tambalin.api;

public class PengendaraReq {
    Integer status; // Respon API

    public PengendaraReq(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
