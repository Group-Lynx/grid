package com.grid.response;

public class MsgInfoResponse {
    private String title;
    private String detail;
    private Boolean isDraft;

    public MsgInfoResponse(String title, String detail, Boolean isDraft) {
        this.title = title;
        this.detail = detail;
        this.isDraft = isDraft;
    }

    public MsgInfoResponse() {
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

    public Boolean getDraft() {
        return isDraft;
    }

    public void setDraft(Boolean draft) {
        isDraft = draft;
    }
}
