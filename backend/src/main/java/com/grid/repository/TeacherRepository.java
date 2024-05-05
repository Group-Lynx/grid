package com.grid.repository;

import com.grid.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeacherRepository extends JpaRepository<Teacher,String> {
    @Query("SELECT t.name FROM Teacher t WHERE t.id=:teacherId")
    String findNameById(String teacherId);
    @Query("SELECT t.name FROM Teacher t WHERE t.id=:teacherId AND t.password =:password")
    String findNameByIdAndPwd(String teacherId, String password);
}
