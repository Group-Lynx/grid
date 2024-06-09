package com.grid.controller;

import com.grid.model.Student;
import com.grid.repository.ClassesRepository;
import com.grid.repository.StudentRepository;
import com.grid.request.PatchInfoRequest;
import com.grid.request.PatchPswdRequest;
import com.grid.request.StudentLoginRequest;
import com.grid.response.ClaInfoResponse;
import com.grid.response.StuInfoResponse;
import com.grid.utils.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class Stu_AccController {
    StudentRepository studentRepository;
    ClassesRepository classesRepository;
    @Autowired
    public Stu_AccController(StudentRepository studentRepository,ClassesRepository classesRepository){
        this.studentRepository=studentRepository;
        this.classesRepository=classesRepository;
    }
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody StudentLoginRequest studentLoginRequest){
        String student_name=studentRepository.findNameById(studentLoginRequest.getStudentId());
        if(student_name==null){
            // 返回错误信息
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.STUDENT_NOT_FOUND);
        }else{
            student_name=studentRepository.findNameByIdAndPassword(studentLoginRequest.getStudentId(),studentLoginRequest.getPassword());
            if(student_name==null){
                // 返回错误信息
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.STUDENT_NOT_FOUND);
            }else{
                return ResponseEntity.created(null).build();
            }
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> userSignUp(@RequestBody StudentLoginRequest studentLoginRequest){
        String student_name=studentRepository.findNameById(studentLoginRequest.getStudentId());
        if(student_name==null){
            studentRepository.save( new Student(studentLoginRequest.getStudentId(), studentLoginRequest.getStudentId(), studentLoginRequest.getPassword()));
            return ResponseEntity.created(null).build();
        }else{
            // 返回错误
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ErrorResponse.STUDENT_ALREADY_EXISTS);
        }
    }
    @GetMapping("/{studentId}")
    public ResponseEntity<?> getInfoById(@PathVariable String studentId){
        String student_name=studentRepository.findNameById(studentId);
        if(student_name==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.STUDENT_NOT_FOUND);
        }else{
            return ResponseEntity.ok(new StuInfoResponse(studentId,student_name));
        }
    }
    @GetMapping("/{studentId}/class")
    public ResponseEntity<?> getStuClass(@PathVariable String studentId){
        String student_name = studentRepository.findNameById(studentId);
        if(student_name==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.STUDENT_NOT_FOUND);
        }
        List<String> classId = studentRepository.findClassesById(studentId);
        List<ClaInfoResponse> result = new ArrayList<>();
        for(String classid : classId){
            String classname = classesRepository.findClassNameById(classid);
            result.add(new ClaInfoResponse(classid,classname));
        }
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/{studentId}/delete")
    public ResponseEntity<?> deleteStuById(@PathVariable String studentId){
        String student_name = studentRepository.findNameById(studentId);
        if(student_name==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.STUDENT_NOT_FOUND);
        }else {
            studentRepository.deleteStuById(studentId);
            return ResponseEntity.noContent().build();
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateInfoById(@PathVariable String id, @RequestBody PatchInfoRequest req) {
        String student_name = studentRepository.findNameById(id);
        if (student_name == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.STUDENT_NOT_FOUND);
        } else {
            studentRepository.updateInfoById(id, req.getName());
            return ResponseEntity.noContent().build();
        }
    }
    @PutMapping("/{id}/password")
    public ResponseEntity<?> updatePswdById(@PathVariable String id, @RequestBody PatchPswdRequest req) {
        String student_name = studentRepository.findNameById(id);
        if (student_name == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.STUDENT_NOT_FOUND);
        } else {
            studentRepository.updatePswdById(id, req.getPswd());
            return ResponseEntity.noContent().build();
        }
    }
}
