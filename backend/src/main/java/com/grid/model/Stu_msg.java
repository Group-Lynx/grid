package com.grid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(Stu_msgkey.class)
public class Stu_msg {
    @Id
    @Column()
    private String stu_id;
    @Id
    @Column()
    private String msg_id;
    @Column(nullable = false)
    private Boolean is_read;

    public Stu_msg() {
    }

    public Stu_msg(String stu_id, String msg_id, Boolean is_read) {
        this.stu_id = stu_id;
        this.msg_id = msg_id;
        this.is_read = is_read;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public Boolean getRead() {
        return is_read;
    }

    public void setRead(Boolean is_read) {
        this.is_read = is_read;
    }
}
