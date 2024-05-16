package com.grid.repository;

import com.grid.model.Event;
import com.grid.response.EventInfoResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event,String> {
    @Query("SELECT new com.grid.response.EventInfoResponse(e.id,e.name,e.location,e.start_hour,e.end_hour,e.date) FROM Event e WHERE e.stud_id=:studentId")
    List<EventInfoResponse> findEventByStuId(String studentId);
    @Transactional
    @Modifying
    @Query("UPDATE Event e SET e.name=:name, e.location=:location, e.start_hour=:start, e.end_hour=:end , e.date=:date WHERE e.stud_id=:studentId AND e.id=:eventId")
    void updateEventInfobyId(String eventId, String studentId, String name, String location, Integer start, Integer end, Date date);
}
