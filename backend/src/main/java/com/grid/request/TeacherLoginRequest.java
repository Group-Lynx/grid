package com.grid.request;

public class TeacherLoginRequest {
    private String teacherId;
    private String password;

    public TeacherLoginRequest(String teacherId, String password) {
        this.teacherId = teacherId;
        this.password = password;
    }

    public TeacherLoginRequest() {
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
