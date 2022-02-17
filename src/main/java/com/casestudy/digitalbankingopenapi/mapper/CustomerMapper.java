package com.casestudy.digitalbankingopenapi.mapper;

import com.casestudy.digitalbankingopenapi.entity.Customer;
import openapi.model.CreateCustomerRequestDto;
import openapi.model.CreateCustomerResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "createdOn",expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedOn",expression = "java(LocalDateTime.now())")
    @Mapping(target = "createdBy",expression = "java(System.getProperty(\"user.name\"))")
    @Mapping(target = "updatedBy",expression = "java(System.getProperty(\"user.name\"))")
    @Mapping(target = "externalId",expression = "java(customer.getUserName() + \"_ext\")")
    Customer dtoToEntity(CreateCustomerRequestDto createCustomerRequestDto,LocalDateTime localDateTime);
    CreateCustomerResponseDto entityToResponseDto(Customer customer);
}
