package com.grid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

import java.util.Date;
import java.util.Objects;

@Entity
@IdClass(EmailValidatorkey.class)
public class EmailValidator {
    @Id
    @Column
    private String id;
    @Id
    @Column
    private String text;
    @Id
    @Column
    private Date date;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailValidator that = (EmailValidator) o;
        return Objects.equals(id, that.id) && Objects.equals(text, that.text) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, date);
    }

    public EmailValidator(String id, String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    public EmailValidator() {
    }
}
