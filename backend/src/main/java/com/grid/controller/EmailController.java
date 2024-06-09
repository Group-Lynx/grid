package com.grid.controller;

import com.grid.model.EmailValidator;
import com.grid.repository.EmailValidatorRepository;
import com.grid.repository.StudentRepository;
import com.grid.repository.TeacherRepository;
import com.grid.request.EmailVerifyRequest;
import com.grid.request.TeacherRecoverRequest;
import com.grid.utils.ErrorResponse;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import java.security.SecureRandom;
import java.util.Date;

@RestController
@RequestMapping("/email")
public class EmailController {
    JavaMailSender mailSender;
    EmailValidatorRepository emailValidatorRepository;
    StudentRepository studentRepository;
    TeacherRepository teacherRepository;
    public EmailController(JavaMailSender mailSender, EmailValidatorRepository emailValidatorRepository,StudentRepository studentRepository,TeacherRepository teacherRepository){
        this.mailSender=mailSender;
        this.emailValidatorRepository=emailValidatorRepository;
        this.studentRepository=studentRepository;
        this.teacherRepository=teacherRepository;
    }

    @PostMapping("/student/{studentId}")
    public ResponseEntity<?> sendStudent(@PathVariable String studentId){
        try {
            String student_name = studentRepository.findNameById(studentId);
            if (student_name == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.STUDENT_NOT_FOUND);
            } else {
                SecureRandom random = new SecureRandom();
                String code = String.valueOf( random.nextInt(900000) + 100000);
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message,true);
                helper.setFrom("19716319824@163.com","Grid开发团队");
                String email = studentId+"@mail.sdu.edu.cn";
                helper.setTo(email);
                helper.setSubject("密码找回");
                helper.setText("<h1>邮箱验证</h1>\n" +
                        "<p>同学您好，</p>\n" +
                        "<p>您请求了密码修改。请在五分钟以内使用以下验证码完成您的密码重置：</p>\n" +
                        "<p style=\"font-size: 20px; font-weight: bold; color: #2E86C1;\">"+code+"</p>\n" +
                        "<p>如果您没有请求密码修改，请忽略此邮件。</p>\n" +
                        "<p>谢谢，<br>Grid开发团队</p>",true);
                mailSender.send(message);
                emailValidatorRepository.save(new EmailValidator(studentId,code,new Date()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/teacher/{teacherId}")
    public ResponseEntity<?> sendTeacher(@PathVariable String teacherId, @RequestBody TeacherRecoverRequest teacherRecoverRequest){
        try {
            String teachername=teacherRepository.findNameById(teacherId);
            if(teachername==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.TEACHER_NOT_FOUND);
            }else {
                SecureRandom random = new SecureRandom();
                String code = String.valueOf( random.nextInt(900000) + 100000);
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message,true);
                helper.setFrom("19716319824@163.com","Grid开发团队");
                String email = teacherRecoverRequest.getEmail();
                helper.setTo(email);
                helper.setSubject("密码找回");
                helper.setText("<h1>邮箱验证</h1>\n" +
                        "<p>教师您好，</p>\n" +
                        "<p>您请求了密码修改。请在五分钟以内使用以下验证码完成您的密码重置：</p>\n" +
                        "<p style=\"font-size: 20px; font-weight: bold; color: #2E86C1;\">"+code+"</p>\n" +
                        "<p>如果您没有请求密码修改，请忽略此邮件。</p>\n" +
                        "<p>谢谢，<br>Grid开发团队</p>",true);
                mailSender.send(message);
                emailValidatorRepository.save(new EmailValidator(teacherId,code,new Date()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody EmailVerifyRequest emailVerifyRequest){
        EmailValidator emailValidator =  emailValidatorRepository.findTextById(emailVerifyRequest.getId());
        if(emailValidator==null || !emailValidator.getText().equals(emailVerifyRequest.getCode())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.CODE_NOT_FOUND);
        }else{
            if((new Date().getTime()-emailValidator.getDate().getTime())/(1000 * 60)>5){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponse.CODE_NOT_FOUND);
            }else {
                return ResponseEntity.ok().build();
            }
        }
    }
}
