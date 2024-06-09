package com.grid.request;

public class TeacherRecoverRequest {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TeacherRecoverRequest(String email) {
        this.email = email;
    }

    public TeacherRecoverRequest() {
    }
}
