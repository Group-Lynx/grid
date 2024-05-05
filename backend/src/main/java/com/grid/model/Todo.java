package com.grid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;
import java.util.Objects;

@Entity
public class Todo {
    @Id
    @Column
    private String id;
    @Column(nullable = false)
    private String stu_id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String detail;
    @Column(nullable = false)
    private Date due;

    @Column(nullable = false)
    private Boolean done;

    @Column(nullable = false)
    private Boolean display;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(id, todo.id) && Objects.equals(stu_id, todo.stu_id) && Objects.equals(title, todo.title) && Objects.equals(detail, todo.detail) && Objects.equals(due, todo.due) && Objects.equals(done, todo.done) && Objects.equals(display, todo.display);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stu_id, title, detail, due, done, display);
    }

    public Todo(String id, String stu_id, String title, String detail, Date due, Boolean done, Boolean display) {
        this.id = id;
        this.stu_id = stu_id;
        this.title = title;
        this.detail = detail;
        this.due = due;
        this.done = done;
        this.display = display;
    }

    public Todo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
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

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }
}
