package com.naplastech.tambalin.api;

public class EditBengkel {
    Integer status; // Respon API

    public EditBengkel(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
