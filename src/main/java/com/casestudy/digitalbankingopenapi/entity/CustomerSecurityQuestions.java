package com.casestudy.digitalbankingopenapi.entity;

import com.casestudy.digitalbankingopenapi.dto.CustomerSecurityQuestionsDto;
import com.casestudy.digitalbankingopenapi.entity.embeddables.CustomerSecurityQuestionsId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class CustomerSecurityQuestions {

    @EmbeddedId
    CustomerSecurityQuestionsId customerSecurityQuestionsId = new CustomerSecurityQuestionsId();

    private String securityQuestionAnswer;
    private LocalDateTime createdOn;

    @JsonIgnore
    @ManyToOne
    @MapsId("customerId")
    private Customer customer;

    @JsonIgnore
    @ManyToOne
    @MapsId("securityQuestionId")
    private SecurityQuestion securityQuestion;

    public CustomerSecurityQuestionsDto toDto(){
        return new CustomerSecurityQuestionsDto(customerSecurityQuestionsId.getSecurityQuestionId(),securityQuestion.getSecurityQuestionText(),securityQuestionAnswer);
    }
}
