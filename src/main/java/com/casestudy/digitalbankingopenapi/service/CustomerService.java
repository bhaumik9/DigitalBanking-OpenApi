package com.casestudy.digitalbankingopenapi.service;

import com.casestudy.digitalbankingopenapi.controller.ExternalController;
import com.casestudy.digitalbankingopenapi.entity.Customer;
import com.casestudy.digitalbankingopenapi.enums.CustomerStatus;
import com.casestudy.digitalbankingopenapi.mapper.CustomerMapperImpl;
import com.casestudy.digitalbankingopenapi.repository.CustomerRepo;
import com.casestudy.digitalbankingopenapi.util.Util;
import com.casestudy.digitalbankingopenapi.validation.RequestValidation;
import openapi.model.CreateCustomerRequestDto;
import openapi.model.CreateCustomerResponseDto;
import openapi.model.PatchCustomerRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    private RequestValidation requestValidation;

    @Autowired
    CustomerMapperImpl customerMapper;

    @Autowired
    ExternalController externalController;

    Util util = new Util();

    public String generateOtp() {
        int randomValue = util.getSecureRandom();
        return String.valueOf(randomValue);
    }

    public CreateCustomerResponseDto add(CreateCustomerRequestDto customerDto) {
        requestValidation.validateCustomer(customerDto);
        int age = externalController.getExternalApiData(customerDto.getUserName());
        Customer customer = customerMapper.dtoToEntity(customerDto, LocalDateTime.now());
        customer.setAge(age);
        customer.setStatus(CustomerStatus.ACTIVE.toString());
        return insertIntoDatabase(customer);
    }

    private CreateCustomerResponseDto insertIntoDatabase(Customer customer) {
        Customer savedCustomer = customerRepo.save(customer);
        return customerMapper.entityToResponseDto(savedCustomer);
    }

    public ResponseEntity<Void> update(PatchCustomerRequestDto patchCustomerRequestDto, String username) {
        Customer customer = requestValidation.validateUserNameInDatabase(username, "update customer");
        if (!(Objects.isNull(patchCustomerRequestDto.getFirstName()) || patchCustomerRequestDto.getFirstName().isEmpty())) {
            customer.setFirstName(patchCustomerRequestDto.getFirstName());
        }
        if (!(Objects.isNull(patchCustomerRequestDto.getLastName()) || patchCustomerRequestDto.getLastName().isEmpty())) {
            customer.setLastName(patchCustomerRequestDto.getLastName());
        }
        if ((!Objects.isNull(patchCustomerRequestDto.getPhoneNumber()) && !patchCustomerRequestDto.getPhoneNumber().isEmpty()) && requestValidation.validatePhoneNumber(patchCustomerRequestDto.getPhoneNumber(),"update customer")) {
            customer.setPhoneNumber(patchCustomerRequestDto.getPhoneNumber());
        }
        if ((!Objects.isNull(patchCustomerRequestDto.getEmail()) && !patchCustomerRequestDto.getEmail().isEmpty()) && requestValidation.validateEmail(patchCustomerRequestDto.getEmail(),"update customer")) {
            customer.setEmail(patchCustomerRequestDto.getEmail());
        }
        if ((!Objects.isNull(patchCustomerRequestDto.getPreferredLanguage()) && patchCustomerRequestDto.getPreferredLanguage().toString().isEmpty()) && requestValidation.validatePreferredLanguage(patchCustomerRequestDto.getPreferredLanguage().toString(),"update customer")) {
            customer.setPreferredLanguage(patchCustomerRequestDto.getPreferredLanguage().toString());
        }
        if ((!Objects.isNull(patchCustomerRequestDto.getStatus()) && !patchCustomerRequestDto.getStatus().toString().isEmpty()) && requestValidation.validateStatus(patchCustomerRequestDto.getStatus().toString())) {
            customer.setStatus(patchCustomerRequestDto.getStatus().toString());
        }
        customerRepo.save(customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
