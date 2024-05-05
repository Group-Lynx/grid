package com.grid.model;

import java.io.Serializable;
import java.util.Objects;

public class Stu_clakey implements Serializable {
    private String stu_id;
    private String cla_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stu_clakey stuClakey = (Stu_clakey) o;
        return Objects.equals(stu_id, stuClakey.stu_id) && Objects.equals(cla_id, stuClakey.cla_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stu_id, cla_id);
    }

    public Stu_clakey(String cla_id,String stu_id){
        this.stu_id=stu_id;
        this.cla_id=cla_id;
    }

    public Stu_clakey() {
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getCla_id() {
        return cla_id;
    }

    public void setCla_id(String cla_id) {
        this.cla_id = cla_id;
    }
}
