package com.grid.response;

public class StuInfoResponse {
    private String studentId;
    private String studentName;
    public
    StuInfoResponse(String studentId,String studentName){
        this.studentName=studentName;
        this.studentId=studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
