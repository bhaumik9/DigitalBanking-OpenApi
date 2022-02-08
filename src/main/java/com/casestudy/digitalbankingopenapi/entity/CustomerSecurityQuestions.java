package com.casestudy.digitalbankingopenapi.entity;

import com.casestudy.digitalbankingopenapi.entity.embeddables.CustomerSecurityQuestionsId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import openapi.model.SecurityQuestionDto;

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

    public SecurityQuestionDto toDto() {
        SecurityQuestionDto securityQuestionDto = new SecurityQuestionDto();
        securityQuestionDto.setSecurityQuestionId(customerSecurityQuestionsId.getSecurityQuestionId());
        securityQuestionDto.setSecurityQuestionText(securityQuestion.getSecurityQuestionText());
        securityQuestionDto.setSecurityQuestionAnswer(securityQuestionAnswer);
        return securityQuestionDto;
    }
}
