package com.grid.controller;

import com.grid.model.Cla_msg;
import com.grid.model.Notice;
import com.grid.model.Stu_msg;
import com.grid.repository.*;
import com.grid.request.MsgRequest;
import com.grid.response.MsgInfoResponse;
import com.grid.response.MsgResponse;
import com.grid.utils.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/class")
public class ClassMsgController {
    ClassesRepository classesRepository;
    Cla_msgRespository claMsgRespository;
    NoticeRepository noticeRepository;
    Stu_msgRepository stuMsgRepository;
    Stu_claRepository stuClaRepository;
    @Autowired ClassMsgController(Cla_msgRespository claMsgRespository,
                                  ClassesRepository classesRepository,
                                  NoticeRepository noticeRepository,
                                  Stu_msgRepository stuMsgRepository,
                                  Stu_claRepository stuClaRepository){
        this.claMsgRespository=claMsgRespository;
        this.classesRepository=classesRepository;
        this.noticeRepository=noticeRepository;
        this.stuClaRepository=stuClaRepository;
        this.stuMsgRepository=stuMsgRepository;
    }
    @GetMapping("/{classId}/mail")
    public ResponseEntity<?> getMsg(@PathVariable String classId){
        String classname = classesRepository.findClassNameById(classId);
        if(classname==null){
            return ResponseEntity.ok(ErrorResponse.CLASS_NOT_FOUND);
        }else{
            List<String> msgId = claMsgRespository.findMsgById(classId);
            List<MsgResponse> result = new ArrayList<>();
            for(String msgid : msgId){
                String title = noticeRepository.findTitleById(msgid);
                result.add(new MsgResponse(msgid,title));
            }
            return ResponseEntity.ok(result);
        }
    }
    @PostMapping("/{classId}/mail")
    public ResponseEntity<?> createMsg(@PathVariable String classId, @RequestBody MsgRequest msgRequest){
        String msgid = UUID.randomUUID().toString();
        String classname=classesRepository.findClassNameById(classId);
        if(classname==null){
            return ResponseEntity.ok(ErrorResponse.CLASS_NOT_FOUND);
        }else{
            noticeRepository.save(new Notice(msgid,msgRequest.getTitle(),msgRequest.getDetail()));
            claMsgRespository.save(new Cla_msg(classId,msgid));
            return ResponseEntity.ok().build();
        }
    }
    @GetMapping("/{classId}/mail/{mailId}")
    public ResponseEntity<?> getMsgInfo(@PathVariable String classId,@PathVariable String mailId){
        String classname = classesRepository.findClassNameById(classId);
        String msgtitle = noticeRepository.findTitleById(mailId);
        if(msgtitle==null){
            return ResponseEntity.ok(ErrorResponse.MAIL_NOT_FOUND);
        }
        if(classname==null){
            return ResponseEntity.ok(ErrorResponse.CLASS_NOT_FOUND);
        }else{
            MsgInfoResponse msgInfoResponse = noticeRepository.findInfoById(mailId);
            return ResponseEntity.ok(msgInfoResponse);
        }
    }
    @PutMapping("/{classId}/mail/{mailId}")
    public ResponseEntity<?> updateMsgInfo(@PathVariable String classId, @PathVariable String mailId,@RequestBody MsgRequest msgRequest){
        String classname = classesRepository.findClassNameById(classId);
        String msgtitle = noticeRepository.findTitleById(mailId);
        if(msgtitle==null){
            return ResponseEntity.ok(ErrorResponse.MAIL_NOT_FOUND);
        }
        if(classname==null){
            return ResponseEntity.ok(ErrorResponse.CLASS_NOT_FOUND);
        }else{
            noticeRepository.updateMsg(mailId,msgRequest.getTitle(),msgRequest.getTitle());
            return ResponseEntity.ok().build();
        }
    }
    @PostMapping("/{classId}/mail/{mailId}")
    public ResponseEntity<?> postMail(@PathVariable String classId,@PathVariable String mailId){
        String classname = classesRepository.findClassNameById(classId);
        String msgtitle = noticeRepository.findTitleById(mailId);
        if(msgtitle==null){
            return ResponseEntity.ok(ErrorResponse.MAIL_NOT_FOUND);
        }
        if(classname==null){
            return ResponseEntity.ok(ErrorResponse.CLASS_NOT_FOUND);
        }else{
            List<String> studentId = stuClaRepository.findStuIdByClaId(classId);
            for(String studentid:studentId){
                stuMsgRepository.save(new Stu_msg(studentid,mailId,false));
            }
            return ResponseEntity.ok().build();
        }
    }
}
