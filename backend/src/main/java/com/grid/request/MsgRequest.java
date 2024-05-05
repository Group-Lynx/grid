package com.grid.request;

public class MsgRequest {
    private String title;
    private String detail;

    public MsgRequest(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public MsgRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
