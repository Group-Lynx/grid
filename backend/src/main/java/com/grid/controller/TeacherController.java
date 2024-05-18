package com.grid.controller;

import com.grid.model.Teacher;
import com.grid.repository.ClassesRepository;
import com.grid.repository.TeacherRepository;
import com.grid.request.TeacherLoginRequest;
import com.grid.response.ClaInfoResponse;
import com.grid.response.TeacInfoResponse;
import com.grid.utils.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    TeacherRepository teacherRepository;
    ClassesRepository classesRepository;
    @Autowired
    public TeacherController(TeacherRepository teacherRepository,ClassesRepository classesRepository){
        this.classesRepository=classesRepository;
        this.teacherRepository=teacherRepository;
    }
    @PostMapping("/login")
    public ResponseEntity<?> teacherLogin(@RequestBody TeacherLoginRequest teacherLoginRequest){
        String teachername=teacherRepository.findNameById(teacherLoginRequest.getTeacherId());
        if(teachername==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.TEACHER_NOT_FOUND);
        }else{
            teachername=teacherRepository.findNameByIdAndPwd(teacherLoginRequest.getTeacherId(),teacherLoginRequest.getPassword());
            if(teachername==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.TEACHER_NOT_FOUND);
            }else{
                return ResponseEntity.created(null).build();
            }
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> teacherSignup(@RequestBody TeacherLoginRequest teacherLoginRequest){
        String teachername=teacherRepository.findNameById(teacherLoginRequest.getTeacherId());
        if(teachername!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ErrorResponse.TEACHER_ALREADY_EXISTS);
        }else{
            teacherRepository.save(new Teacher(teacherLoginRequest.getTeacherId(),teacherLoginRequest.getTeacherId(),teacherLoginRequest.getPassword()));
            return ResponseEntity.created(null).build();
        }
    }
    @GetMapping("/{teacherId}")
    public ResponseEntity<?>  getTeacherInfo(@PathVariable String teacherId){
        String teachername=teacherRepository.findNameById(teacherId);
        if(teachername==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.TEACHER_NOT_FOUND);
        }else{
            return ResponseEntity.ok(new TeacInfoResponse(teacherId,teachername));
        }
    }
    @GetMapping("/{teacherId}/class")
    public ResponseEntity<?> getTeacherClasses(@PathVariable String teacherId){
        String teachername=teacherRepository.findNameById(teacherId);
        if(teachername==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.TEACHER_NOT_FOUND);
        }
        List<ClaInfoResponse> result = classesRepository.findClaInfoByTeacherId(teacherId);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/{teacherId}/delete")
    public ResponseEntity<?> deleteTeacher(@PathVariable String teacherId){
        String teachername=teacherRepository.findNameById(teacherId);
        if(teachername==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.TEACHER_NOT_FOUND);
        }else{
            teacherRepository.deleteById(teacherId);
            return ResponseEntity.noContent().build();
        }
    }
}
