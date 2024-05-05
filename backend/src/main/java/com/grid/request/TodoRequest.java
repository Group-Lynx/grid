package com.grid.request;

import java.util.Date;

public class TodoRequest {
    private String title;
    private Date due;
    private String detail;

    public TodoRequest(String title, Date due, String detail) {
        this.title = title;
        this.due = due;
        this.detail = detail;
    }

    public TodoRequest() {
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
}
