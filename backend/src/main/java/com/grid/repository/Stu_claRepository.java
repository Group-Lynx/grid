package com.grid.repository;

import com.grid.model.Stu_cla;
import com.grid.model.Stu_clakey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Stu_claRepository extends JpaRepository<Stu_cla, Stu_clakey> {
    @Query("SELECT sc.stu_id FROM Stu_cla sc WHERE sc.cla_id=:classId and sc.stu_id=:studentId")
    String findByIdAndClassId(String classId, String studentId);
    @Query("SELECT sc.stu_id FROM Stu_cla sc WHERE sc.cla_id=:classId")
    List<String> findStuIdByClaId(String classId);
    @Query("SELECT sc.stu_id FROM Stu_cla sc WHERE sc.stu_id=:studentId and sc.cla_id=:classId")
    String findStuIdByClaIdAndStuId(String classId, String studentId);
}
