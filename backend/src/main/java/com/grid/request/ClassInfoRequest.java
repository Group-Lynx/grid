package com.grid.request;

public class ClassInfoRequest {
    String className;
    String teacherId;
    public ClassInfoRequest(String className,String teacherId){
        this.className=className;
        this.teacherId=teacherId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
