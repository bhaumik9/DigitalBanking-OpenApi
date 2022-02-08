package com.casestudy.digitalbankingopenapi.repository;

import com.casestudy.digitalbankingopenapi.entity.SecurityQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityQuestionsRepo extends JpaRepository<SecurityQuestion,String> {
}
