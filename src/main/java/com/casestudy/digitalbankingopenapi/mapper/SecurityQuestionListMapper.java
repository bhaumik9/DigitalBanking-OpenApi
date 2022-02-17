package com.casestudy.digitalbankingopenapi.mapper;

import com.casestudy.digitalbankingopenapi.entity.CustomerSecurityQuestions;
import com.casestudy.digitalbankingopenapi.entity.SecurityQuestion;
import openapi.model.GetCustomerSecurityQuestionResponseDto;
import openapi.model.GetSecurityQuestionsResponseDto;
import openapi.model.SecurityQuestionDto;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SecurityQuestionListMapper {

    @Autowired
    SecurityQuestionsMapper securityQuestionsMapper;

    public GetCustomerSecurityQuestionResponseDto mapToCustomerSecurityQuestionResponseDto(List<CustomerSecurityQuestions> list) {
        GetCustomerSecurityQuestionResponseDto obj = new GetCustomerSecurityQuestionResponseDto();
        list.forEach(l -> {
            SecurityQuestionDto securityQuestionDto = securityQuestionsMapper.maptoCSQDto(l);
            obj.addSecurityQuestionsItem(securityQuestionDto);
        });
        return obj;
    }

    public GetSecurityQuestionsResponseDto mapToSecurityQuestionsResponseDto(List<SecurityQuestion> list) {
        GetSecurityQuestionsResponseDto obj = new GetSecurityQuestionsResponseDto();
        list.forEach(l -> {
            SecurityQuestionDto securityQuestionDto = securityQuestionsMapper.mapSQtoSQDto(l);
            obj.addSecurityQuestionsItem(securityQuestionDto);
        });
        return obj;
    }
}
