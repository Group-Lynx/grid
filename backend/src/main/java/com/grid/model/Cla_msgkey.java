package com.grid.model;

import java.io.Serializable;
import java.util.Objects;

public class Cla_msgkey implements Serializable {
    private String cla_id;
    private String msg_id;

    public Cla_msgkey(String cla_id, String msg_id) {
        this.cla_id = cla_id;
        this.msg_id = msg_id;
    }

    public Cla_msgkey() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cla_msgkey claMsgkey = (Cla_msgkey) o;
        return Objects.equals(cla_id, claMsgkey.cla_id) && Objects.equals(msg_id, claMsgkey.msg_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cla_id, msg_id);
    }

    public String getCla_id() {
        return cla_id;
    }

    public void setCla_id(String cla_id) {
        this.cla_id = cla_id;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }
}
