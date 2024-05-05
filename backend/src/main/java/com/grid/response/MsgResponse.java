package com.grid.response;

public class MsgResponse {
    private String mailId;
    private String title;

    public MsgResponse(String mailId, String title) {
        this.mailId = mailId;
        this.title = title;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
