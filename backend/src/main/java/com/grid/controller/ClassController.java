package com.grid.controller;

import com.grid.model.Classes;
import com.grid.model.Stu_cla;
import com.grid.model.Stu_clakey;
import com.grid.repository.ClassesRepository;
import com.grid.repository.Stu_claRepository;
import com.grid.repository.StudentRepository;
import com.grid.repository.TeacherRepository;
import com.grid.request.ClassInfoRequest;
import com.grid.request.JoinClassRequest;
import com.grid.response.ClaInfoResponse;
import com.grid.response.StuInfoResponse;
import com.grid.utils.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/class")
public class ClassController {

    ClassesRepository classesRepository;
    Stu_claRepository stuClaRepository;
    TeacherRepository teacherRepository;
    StudentRepository studentRepository;

    @Autowired ClassController(ClassesRepository classesRepository,
                               Stu_claRepository stuClaRepository,
                               TeacherRepository teacherRepository,
                               StudentRepository studentRepository){
        this.classesRepository=classesRepository;
        this.stuClaRepository=stuClaRepository;
        this.teacherRepository=teacherRepository;
        this.studentRepository=studentRepository;
    }
    @GetMapping("/{classId}")
    public ResponseEntity<?> getClassInfo(@PathVariable String classId){
        String className = classesRepository.findClassNameById(classId);
        if(className==null){
            return ResponseEntity.ok(ErrorResponse.CLASS_NOT_FOUND);
        }
        return ResponseEntity.ok(new ClaInfoResponse(classId,className));
    }
    @GetMapping("/{classId}/students")
    public ResponseEntity<?> getStudentInfo(@PathVariable String classId){
        String className = classesRepository.findClassNameById(classId);
        if(className==null){
            return ResponseEntity.ok(ErrorResponse.CLASS_NOT_FOUND);
        }
        List<String> studentId = stuClaRepository.findStuIdByClaId(classId);
        List<StuInfoResponse> result = new ArrayList<>();
        for(String studentid:studentId){
            String studentName = studentRepository.findNameById(studentid);
            result.add(new StuInfoResponse(studentid,studentName));
        }
        return ResponseEntity.ok(result);
    }
    @PostMapping("")
    public ResponseEntity<?> createClass(@RequestBody ClassInfoRequest classInfoRequest){
        String classId= UUID.randomUUID().toString();
        String teacherid=classInfoRequest.getTeacherId();
        String teachername=teacherRepository.findNameById(teacherid);
        if(teachername==null){
            return ResponseEntity.ok(ErrorResponse.TEACHER_NOT_FOUND);
        }
        classesRepository.save(new Classes(classId,classInfoRequest.getTeacherId(),classInfoRequest.getClassName()));
        return ResponseEntity.ok(classId);
    }
    @PostMapping("/join")
    public ResponseEntity<?> joinStuToClass(@RequestBody JoinClassRequest joinClassRequest){
        String studentname=studentRepository.findNameById(joinClassRequest.getStudentId());
        if(studentname==null){
            return ResponseEntity.ok(ErrorResponse.STUDENT_NOT_FOUND);
        }
        String classname=classesRepository.findClassNameById(joinClassRequest.getClassId());
        if(classname==null){
            return ResponseEntity.ok(ErrorResponse.CLASS_NOT_FOUND);
        }
        String studentid = stuClaRepository.findStuIdByClaIdAndStuId(joinClassRequest.getClassId(),joinClassRequest.getStudentId());
        if(studentid!=null){
            return ResponseEntity.ok(ErrorResponse.STUDENT_NOT_FOUND);
        }
        stuClaRepository.save(new Stu_cla(joinClassRequest.getClassId(),joinClassRequest.getStudentId()));
        return ResponseEntity.ok().build();
    }
    @PostMapping("/leave")
    public ResponseEntity<?> leaveClass(@RequestBody JoinClassRequest joinClassRequest){
        String studentname=studentRepository.findNameById(joinClassRequest.getStudentId());
        if(studentname==null){
            return ResponseEntity.ok(ErrorResponse.STUDENT_NOT_FOUND);
        }
        String classname=classesRepository.findClassNameById(joinClassRequest.getClassId());
        if(classname==null){
            return ResponseEntity.ok(ErrorResponse.CLASS_NOT_FOUND);
        }
        String studentId= stuClaRepository.findByIdAndClassId(joinClassRequest.getClassId(),joinClassRequest.getStudentId());
        if(studentId==null){
            return ResponseEntity.ok(ErrorResponse.STUDENT_NOT_FOUND);
        }else{
            stuClaRepository.deleteById(new Stu_clakey(joinClassRequest.getClassId(),studentId));
            return ResponseEntity.ok().build();
        }
    }
}
