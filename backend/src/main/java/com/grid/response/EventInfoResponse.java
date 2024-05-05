package com.grid.response;

public class EventInfoResponse {
    private String eventId;
    private String name;
    private String location;
    private Integer start;
    private Integer end;

    public EventInfoResponse(String eventId, String name, String location, Integer start, Integer end) {
        this.eventId = eventId;
        this.name = name;
        this.location = location;
        this.start = start;
        this.end = end;
    }

    public EventInfoResponse() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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
}
