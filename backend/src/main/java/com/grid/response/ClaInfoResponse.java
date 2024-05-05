package com.grid.response;

public class ClaInfoResponse {
    private String classId;
    private String className;

    public ClaInfoResponse(String classId, String className) {
        this.classId = classId;
        this.className = className;
    }

    public ClaInfoResponse() {
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
