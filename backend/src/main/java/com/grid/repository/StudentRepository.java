package com.grid.repository;

import com.grid.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,String> {
    @Query("SELECT s.name FROM Student s WHERE s.id = :studentId")
    String findNameById(String studentId);
    @Transactional
    @Modifying
    @Query("DELETE FROM Student s WHERE s.id =:studentId ")
    void deleteStuById(String studentId);
    @Query("SELECT s.name FROM Student s WHERE s.id = :studentId AND s.password =:password")
    String findNameByIdAndPassword(String studentId,String password);
    @Query("SELECT sc.cla_id FROM Stu_cla sc where sc.stu_id=:studentId")
    List<String> findClassesById(String studentId);
    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.password =:password WHERE s.id=:id")
    void updateInfoById(String id, String password);
}
