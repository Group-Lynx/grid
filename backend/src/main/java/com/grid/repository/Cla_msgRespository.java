package com.grid.repository;

import com.grid.model.Cla_msg;
import com.grid.model.Cla_msgkey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Cla_msgRespository extends JpaRepository<Cla_msg, Cla_msgkey> {
    @Query("SELECT cm.msg_id from Cla_msg cm WHERE cm.cla_id=:classId")
    List<String> findMsgById(String classId);
}
