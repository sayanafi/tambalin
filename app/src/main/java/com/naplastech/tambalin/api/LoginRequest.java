package com.naplastech.tambalin.api;

public class LoginRequest {
    public Integer status, role, id_user;

    public LoginRequest(Integer status, Integer role, Integer id_user) {
        this.status = status;
        this.role = role;
        this.id_user = id_user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }
}
