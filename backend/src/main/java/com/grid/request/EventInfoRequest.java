package com.grid.request;

import java.util.Date;

public class EventInfoRequest {
    private String name;
    private String location;
    private Integer start;
    private Integer end;
    private Date date;

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

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EventInfoRequest(String name, String location, Integer start, Integer end, Date date) {
        this.name = name;
        this.location = location;
        this.start = start;
        this.end = end;
        this.date = date;
    }

    public EventInfoRequest() {
    }
}
