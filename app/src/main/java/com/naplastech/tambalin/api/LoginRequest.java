package com.naplastech.tambalin.api;

public class LoginRequest {
    public Integer status, role;

    public LoginRequest(Integer status, Integer role) {
        this.status = status;
        this.role = role;
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
}
