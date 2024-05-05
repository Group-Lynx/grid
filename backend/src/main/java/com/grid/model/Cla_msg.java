package com.grid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(Cla_msgkey.class)
public class Cla_msg {
    @Id
    @Column(nullable = false)
    private String cla_id;
    @Id
    @Column(nullable = false)
    private String msg_id;


    public Cla_msg(String cla_id, String msg_id) {
        this.cla_id = cla_id;
        this.msg_id = msg_id;
    }

    public Cla_msg() {
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getCla_id() {
        return cla_id;
    }

    public void setCla_id(String cla_id) {
        this.cla_id = cla_id;
    }

}
