package com.grid.controller;

import com.grid.model.Todo;
import com.grid.repository.StudentRepository;
import com.grid.repository.TodoRepository;
import com.grid.request.TodoInfoRequest;
import com.grid.request.TodoRequest;
import com.grid.response.TodoInfoRespone;
import com.grid.utils.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
public class TodoController {
    private TodoRepository todoRepository;
    private StudentRepository studentRepository;
    @Autowired
    public TodoController(TodoRepository todoRepository,StudentRepository studentRepository){
        this.todoRepository=todoRepository;
        this.studentRepository=studentRepository;
    }
    @GetMapping("/{studentId}/todo")
    public ResponseEntity<?> getStudentTodo(@PathVariable String studentId){
        String studentname = studentRepository.findNameById(studentId);
        if(studentname==null){
            return ResponseEntity.ok(ErrorResponse.STUDENT_NOT_FOUND);
        }
        List<TodoInfoRespone> result =  todoRepository.findTodoByStuId(studentId);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/{studentId}/todo")
    public ResponseEntity<?> createTodo(@PathVariable String studentId, @RequestBody TodoRequest todoRequest){
        String studentname = studentRepository.findNameById(studentId);
        if(studentname==null){
            return ResponseEntity.ok(ErrorResponse.STUDENT_NOT_FOUND);
        }
        String todoId= UUID.randomUUID().toString();
        todoRepository.save(new Todo(todoId,studentId,todoRequest.getTitle(),todoRequest.getDetail(),todoRequest.getDue(),false,false));
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/{studentId}/todo/{todoId}")
    public ResponseEntity<?> updateTodoInfo(@PathVariable String studentId, @PathVariable String todoId, @RequestBody TodoInfoRequest todoInfoRequest){
        String studentname = studentRepository.findNameById(studentId);
        if(studentname==null){
            return ResponseEntity.ok(ErrorResponse.STUDENT_NOT_FOUND);
        }
        todoRepository.updateTodoInfoById(studentId,todoId,todoInfoRequest.getTitle(),todoInfoRequest.getDetail(),todoInfoRequest.getDue(),todoInfoRequest.getDone(),todoInfoRequest.getDisplay());
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{studentId}/todo/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable String studentId, @PathVariable String todoId){
        String studentname = studentRepository.findNameById(studentId);
        if(studentname==null){
            return ResponseEntity.ok(ErrorResponse.STUDENT_NOT_FOUND);
        }
        todoRepository.deleteById(todoId);
        return ResponseEntity.ok().build();
    }
}
