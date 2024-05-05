package com.grid.request;

public class MailStateRequest {
    Boolean is_read;

    public MailStateRequest(Boolean is_read) {
        this.is_read = is_read;
    }

    public MailStateRequest() {
    }

    public Boolean getIs_read() {
        return is_read;
    }

    public void setIs_read(Boolean is_read) {
        this.is_read = is_read;
    }
}
