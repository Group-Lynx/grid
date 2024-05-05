package com.grid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import javax.swing.*;
import java.util.Date;

@Entity
public class Notice {
    @Id
    @Column
    private String id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String detail;
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Boolean is_draft;

    public Notice(String id, String title, String detail) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.date = new Date();
        this.is_draft = true;
    }

    public Notice() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getIs_draft() {
        return is_draft;
    }

    public void setIs_draft(Boolean is_draft) {
        this.is_draft = is_draft;
    }
}
