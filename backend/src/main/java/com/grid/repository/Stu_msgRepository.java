package com.grid.repository;

import com.grid.model.Stu_msg;
import com.grid.model.Stu_msgkey;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Stu_msgRepository extends JpaRepository<Stu_msg, Stu_msgkey> {
    @Query("SELECT sm.msg_id FROM Stu_msg sm WHERE sm.stu_id = :uid")
    List<String> findMailById(String uid);
    @Transactional
    @Modifying
    @Query("UPDATE Stu_msg sm SET sm.is_read = :state WHERE sm.stu_id = :uid AND sm.msg_id = :mailId")
    void updateState(String uid, String mailId, Boolean state);
}
