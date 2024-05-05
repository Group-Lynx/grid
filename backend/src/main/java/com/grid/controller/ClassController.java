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
import com.grid.utils.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
