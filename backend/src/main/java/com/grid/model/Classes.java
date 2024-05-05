package com.grid.model;

import com.grid.repository.ClassesRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Classes {
    @Id
    @Column
    private String id;
    @Column(nullable = false)
    private String tch_id;

    @Column(nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classes aClass = (Classes) o;
        return Objects.equals(id, aClass.id) && Objects.equals(tch_id, aClass.tch_id) && Objects.equals(name, aClass.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tch_id, name);
    }

    public Classes(String id,String tch_id,String name){
        this.id=id;
        this.tch_id=tch_id;
        this.name=name;
    }
    public Classes(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTch_id() {
        return tch_id;
    }

    public void setTch_id(String tch_id) {
        this.tch_id = tch_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
