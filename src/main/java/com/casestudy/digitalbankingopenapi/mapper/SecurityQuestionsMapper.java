package com.casestudy.digitalbankingopenapi.mapper;

import com.casestudy.digitalbankingopenapi.entity.CustomerSecurityQuestions;
import openapi.model.SecurityQuestionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SecurityQuestionsMapper {

    @Mapping(source = "customerSecurityQuestions.customerSecurityQuestionsId.securityQuestionId",target = "securityQuestionId")
    @Mapping(source = "customerSecurityQuestions.securityQuestion.securityQuestionText",target = "securityQuestionText")
    @Mapping(source = "customerSecurityQuestions.securityQuestionAnswer",target = "securityQuestionAnswer")
    SecurityQuestionDto maptoSQDto(CustomerSecurityQuestions customerSecurityQuestions);
}
