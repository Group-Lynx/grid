package com.grid.repository;

import com.grid.model.Classes;
import com.grid.response.ClaInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassesRepository extends JpaRepository<Classes,String> {
    @Query("SELECT c.name FROM Classes c WHERE  c.id=:classId")
    String findClassNameById(String classId);
    @Query("SELECT new com.grid.response.ClaInfoResponse(c.id,c.name) FROM Classes c WHERE c.tch_id=:teacherId")
    List<ClaInfoResponse> findClaInfoByTeacherId(String teacherId);
}
