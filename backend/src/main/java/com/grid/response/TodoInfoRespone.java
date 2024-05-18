package com.grid.response;

import java.util.Date;

public class TodoInfoRespone {
    private String todoId;
    private String title;
    private Date due;
    private String detail;
    private Boolean done;
    private Boolean display;

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public TodoInfoRespone() {
    }

    public TodoInfoRespone(String todoId, String title, Date due, String detail, Boolean done,Boolean dispaly) {
        this.todoId = todoId;
        this.title = title;
        this.due = due;
        this.detail = detail;
        this.done = done;
        this.display = dispaly;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
