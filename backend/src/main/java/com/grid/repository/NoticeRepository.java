package com.grid.repository;

import com.grid.model.Notice;
import com.grid.response.MsgInfoResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice,String> {
    @Query("select n.title from Notice n where n.id=:msgid")
    String findTitleById(String msgid);
    @Query("select new com.grid.response.MsgInfoResponse(n.title,n.detail,n.is_draft) from Notice n where n.id=:mailId")
    MsgInfoResponse findInfoById(String mailId);
    @Transactional
    @Modifying
    @Query("UPDATE Notice n SET n.title=:title, n.detail=:detail WHERE n.id=:mailId")
    void updateMsg(String mailId, String title, String detail);

}
