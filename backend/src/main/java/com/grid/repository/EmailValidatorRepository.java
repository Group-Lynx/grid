package com.grid.repository;

import com.grid.model.EmailValidator;
import com.grid.model.EmailValidatorkey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmailValidatorRepository extends JpaRepository<EmailValidator, EmailValidatorkey> {
    @Query("SELECT new com.grid.model.EmailValidator(e.id,e.text,e.date) FROM EmailValidator e where e.id=:id order by e.date desc limit 1")
    EmailValidator findTextById(String id);
}
