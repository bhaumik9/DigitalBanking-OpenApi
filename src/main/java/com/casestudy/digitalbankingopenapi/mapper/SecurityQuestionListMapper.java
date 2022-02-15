package com.casestudy.digitalbankingopenapi.mapper;

import com.casestudy.digitalbankingopenapi.entity.CustomerSecurityQuestions;
import openapi.model.GetCustomerSecurityQuestionResponseDto;
import openapi.model.SecurityQuestionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SecurityQuestionListMapper {

    @Autowired
    SecurityQuestionsMapper securityQuestionsMapper;

    public GetCustomerSecurityQuestionResponseDto toCustomerSecurityQuestionResponseDto(List<CustomerSecurityQuestions> list) {
        GetCustomerSecurityQuestionResponseDto obj=new GetCustomerSecurityQuestionResponseDto();
        list.forEach(l-> {
            SecurityQuestionDto securityQuestionDto = securityQuestionsMapper.maptoSQDto(l);
            obj.addSecurityQuestionsItem(securityQuestionDto);
        });
        return obj;
    }
}
