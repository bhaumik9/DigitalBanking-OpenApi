package com.casestudy.digitalbankingopenapi.mapper;

import com.casestudy.digitalbankingopenapi.entity.CustomerOtp;
import openapi.model.InitiateOtpRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface OtpMapper {

    @Mapping(target = "userName",source = "initiateOtpRequestDto.userName")
    @Mapping(target = "createdOn",expression = "java(LocalDateTime.now())")
    @Mapping(target = "expiresOn",expression = "java(customerOtp.getCreatedOn().plusMinutes(5))")
    CustomerOtp mapToEntity(InitiateOtpRequestDto initiateOtpRequestDto, LocalDateTime localDateTime);
}
