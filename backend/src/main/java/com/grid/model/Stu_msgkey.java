package com.grid.model;

import java.io.Serializable;
import java.util.Objects;

public class Stu_msgkey implements Serializable {
    private String stu_id;
    private String msg_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stu_msgkey stuMsgkey = (Stu_msgkey) o;
        return Objects.equals(stu_id, stuMsgkey.stu_id) && Objects.equals(msg_id, stuMsgkey.msg_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stu_id, msg_id);
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
}
