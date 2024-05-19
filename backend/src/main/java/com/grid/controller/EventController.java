package com.grid.controller;

import com.grid.model.Event;
import com.grid.repository.EventRepository;
import com.grid.repository.StudentRepository;
import com.grid.request.EventInfoRequest;
import com.grid.response.EventInfoResponse;
import com.grid.utils.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
public class EventController {
    EventRepository eventRepository;
    StudentRepository studentRepository;
    @Autowired
    public EventController(EventRepository eventRepository,StudentRepository studentRepository){
        this.eventRepository=eventRepository;
        this.studentRepository=studentRepository;
    }
    @GetMapping("/{studentId}/event")
    public ResponseEntity<?> getStudentEventInfo(@PathVariable String studentId){
        String studentname = studentRepository.findNameById(studentId);
        if(studentname==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.STUDENT_NOT_FOUND);
        }
        List<EventInfoResponse> result = eventRepository.findEventByStuId(studentId);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/{studentId}/event")
    public ResponseEntity<?> createEvent(@PathVariable String studentId, @RequestBody EventInfoRequest eventInfoRequest){
        String studentname = studentRepository.findNameById(studentId);
        if(studentname==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.STUDENT_NOT_FOUND);
        }
        String event_id = UUID.randomUUID().toString();
        eventRepository.save(new Event(event_id,eventInfoRequest.getName(),eventInfoRequest.getLocation(),eventInfoRequest.getStart(),eventInfoRequest.getEnd(),studentId,eventInfoRequest.getDate()));
        return ResponseEntity.created(null).build();

    }
    @PatchMapping("/{studentId}/event/{eventId}")
    public ResponseEntity<?> updateEventInfo(@PathVariable String studentId,@PathVariable String eventId,@RequestBody EventInfoRequest eventInfoRequest){
        String studentname = studentRepository.findNameById(studentId);
        if(studentname==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.STUDENT_NOT_FOUND);
        }
        eventRepository.updateEventInfobyId(eventId,studentId,eventInfoRequest.getName(),eventInfoRequest.getLocation(),eventInfoRequest.getStart(),eventInfoRequest.getEnd(),eventInfoRequest.getDate());
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{studentId}/event/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable String studentId,@PathVariable String eventId){
        String studentname = studentRepository.findNameById(studentId);
        if(studentname==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.STUDENT_NOT_FOUND);
        }
        eventRepository.deleteById(eventId);
        return ResponseEntity.noContent().build();
    }
}
