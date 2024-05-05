package com.grid.repository;

import com.grid.model.Todo;
import com.grid.response.TodoInfoRespone;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,String> {
    @Query("SELECT new com.grid.response.TodoInfoRespone(t.id,t.title,t.due,t.detail,t.done) FROM Todo t WHERE t.stu_id =:studentId")
    List<TodoInfoRespone> findTodoByStuId(String studentId);
    @Transactional
    @Modifying
    @Query("UPDATE Todo t SET t.title = :title, t.detail = :detail, t.due = :due, t.done = :done, t.display = :display WHERE t.stu_id = :studentId AND t.id = :todoId")
    void updateTodoInfoById(String studentId, String todoId, String title, String detail, Date due, Boolean done, Boolean display);
}
