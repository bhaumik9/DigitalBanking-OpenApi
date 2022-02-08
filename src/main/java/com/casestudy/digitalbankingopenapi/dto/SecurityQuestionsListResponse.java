package com.casestudy.digitalbankingopenapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class SecurityQuestionsListResponse {
    List<SecurityQuestionsDto> securityQuestions = new ArrayList<>();
    public void addQuestion(SecurityQuestionsDto securityQuestionsDto){
        securityQuestions.add(securityQuestionsDto);
    }
}
