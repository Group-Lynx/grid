package com.grid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Event {
    @Id
    @Column
    private String id;
    @Column
    private String name;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Integer start_hour;
    @Column(nullable = false)
    private Integer end_hour;
    @Column(nullable = false)
    private String stud_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(Integer start_hour) {
        this.start_hour = start_hour;
    }

    public Integer getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(Integer end_hour) {
        this.end_hour = end_hour;
    }

    public String getStud_id() {
        return stud_id;
    }

    public void setStud_id(String stud_id) {
        this.stud_id = stud_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(name, event.name) && Objects.equals(location, event.location) && Objects.equals(start_hour, event.start_hour) && Objects.equals(end_hour, event.end_hour) && Objects.equals(stud_id, event.stud_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location, start_hour, end_hour, stud_id);
    }

    public Event(String id, String name, String location, Integer start_hour, Integer end_hour, String stud_id) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.start_hour = start_hour;
        this.end_hour = end_hour;
        this.stud_id = stud_id;
    }

    public Event() {
    }
}
