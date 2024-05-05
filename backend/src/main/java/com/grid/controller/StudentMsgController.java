package com.grid.controller;

import com.grid.repository.NoticeRepository;
import com.grid.repository.Stu_msgRepository;
import com.grid.repository.StudentRepository;
import com.grid.request.MailStateRequest;
import com.grid.response.MsgInfoResponse;
import com.grid.response.MsgResponse;
import com.grid.utils.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/student")
public class StudentMsgController {
    Stu_msgRepository stuMsgRepository;
    StudentRepository studentRepository;
    NoticeRepository noticeRepository;
    @Autowired
    public StudentMsgController(Stu_msgRepository stuMsgRepository,StudentRepository studentRepository,NoticeRepository noticeRepository){
        this.stuMsgRepository=stuMsgRepository;
        this.studentRepository=studentRepository;
        this.noticeRepository=noticeRepository;
    }

    @GetMapping("/{uid}/mail")
    public ResponseEntity<?> getAllMail(@PathVariable String uid){
        String studentname = studentRepository.findNameById(uid);
        if(studentname==null){
            return ResponseEntity.ok(ErrorResponse.STUDENT_NOT_FOUND);
        }
        List<String> mail_id = stuMsgRepository.findMailById(uid);
        List<MsgResponse> result = new ArrayList<>();
        for(String msg_id : mail_id){
            String title=noticeRepository.findTitleById(msg_id);
            result.add(new MsgResponse(msg_id,title));
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{uid}/mail/{mailId}")
    public ResponseEntity<?> getAllMailInfo(@PathVariable String uid,@PathVariable String mailId){
        String studentname = studentRepository.findNameById(uid);
        if(studentname==null) {
            return ResponseEntity.ok(ErrorResponse.STUDENT_NOT_FOUND);
        }
        MsgInfoResponse msgInfoResponse = noticeRepository.findInfoById(mailId);
        return ResponseEntity.ok(Objects.requireNonNullElse(msgInfoResponse, ErrorResponse.MAIL_NOT_FOUND));
    }
    @PutMapping("/{uid}/mail/{mailId}")
    public ResponseEntity<?> updateMailState(@PathVariable String uid, @PathVariable String mailId, @RequestBody MailStateRequest mailStateRequest){
        String studentname = studentRepository.findNameById(uid);
        if(studentname==null) {
            return ResponseEntity.ok(ErrorResponse.STUDENT_NOT_FOUND);
        }
        stuMsgRepository.updateState(uid,mailId,mailStateRequest.getIs_read());
        return ResponseEntity.ok().build();
    }
}
