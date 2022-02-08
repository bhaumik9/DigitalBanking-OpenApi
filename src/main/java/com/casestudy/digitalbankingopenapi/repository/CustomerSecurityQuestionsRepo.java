package com.casestudy.digitalbankingopenapi.repository;

import com.casestudy.digitalbankingopenapi.entity.CustomerSecurityQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerSecurityQuestionsRepo extends JpaRepository<CustomerSecurityQuestions,String> {
}
