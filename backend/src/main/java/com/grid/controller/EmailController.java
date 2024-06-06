package com.grid.controller;

import com.grid.model.EmailValidator;
import com.grid.repository.EmailValidatorRepository;
import com.grid.request.EmailVerifyRequest;
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
    public EmailController(JavaMailSender mailSender, EmailValidatorRepository emailValidatorRepository){
        this.mailSender=mailSender;
        this.emailValidatorRepository=emailValidatorRepository;
    }

    @PostMapping("/send/{id}")
    public ResponseEntity<?> send(@PathVariable String id){
        try {
            SecureRandom random = new SecureRandom();
            String code = String.valueOf( random.nextInt(900000) + 100000);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom("19716319824@163.com","Grid开发团队");
            String email = id+"@mail.sdu.edu.cn";
            helper.setTo(email);
            helper.setSubject("密码找回");
            helper.setText("<h1>邮箱验证</h1>\n" +
                    "<p>您好，</p>\n" +
                    "<p>您请求了密码修改。请在五分钟以内使用以下验证码完成您的密码重置：</p>\n" +
                    "<p style=\"font-size: 20px; font-weight: bold; color: #2E86C1;\">"+code+"</p>\n" +
                    "<p>如果您没有请求密码修改，请忽略此邮件。</p>\n" +
                    "<p>谢谢，<br>Grid开发团队</p>",true);
            mailSender.send(message);
            emailValidatorRepository.save(new EmailValidator(id,code,new Date()));
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
