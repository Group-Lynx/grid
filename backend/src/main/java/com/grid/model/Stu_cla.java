package com.grid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(Stu_clakey.class)
public class Stu_cla {
    @Id
    @Column(nullable = false)
    private String stu_id;
    @Id
    @Column(nullable = false)
    private String cla_id;

    public Stu_cla(String cla_id,String stu_id){
        this.cla_id=cla_id;
        this.stu_id=stu_id;
    }

    public Stu_cla() {
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
