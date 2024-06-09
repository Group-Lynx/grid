package com.grid.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class EmailValidatorkey implements Serializable {
    private String id;
    private String text;
    private Date date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailValidatorkey that = (EmailValidatorkey) o;
        return Objects.equals(id, that.id) && Objects.equals(text, that.text) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, date);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EmailValidatorkey() {
    }

    public EmailValidatorkey(String id, String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }
}
