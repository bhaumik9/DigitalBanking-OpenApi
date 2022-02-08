package com.casestudy.digitalbankingopenapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomerSecurityQuestionsResponse {
    List<CustomerSecurityQuestionsDto> securityQuestions = new ArrayList<>();
    public void addData(CustomerSecurityQuestionsDto customerSecurityQuestion){
        securityQuestions.add(customerSecurityQuestion);
    }
}
