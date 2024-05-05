package com.grid.request;

public class EventInfoRequest {
    private String name;
    private String location;
    private Integer start;
    private Integer end;

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

    public EventInfoRequest(String name, String location, Integer start, Integer end) {
        this.name = name;
        this.location = location;
        this.start = start;
        this.end = end;
    }

    public EventInfoRequest() {
    }
}
