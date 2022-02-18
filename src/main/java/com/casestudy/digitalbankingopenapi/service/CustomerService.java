package com.casestudy.digitalbankingopenapi.service;

import com.casestudy.digitalbankingopenapi.controller.ExternalController;
import com.casestudy.digitalbankingopenapi.dto.AgeResponseDto;
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

    public ResponseEntity<Void> update(PatchCustomerRequestDto customerDto, String username) {
        Customer customer = requestValidation.validateUserNameInDatabase(username, "update");
        if (!(Objects.isNull(customerDto.getFirstName()) || customerDto.getFirstName().isEmpty())) {
            customer.setFirstName(customerDto.getFirstName());
        }
        if (!(Objects.isNull(customerDto.getLastName()) || customerDto.getLastName().isEmpty())) {
            customer.setLastName(customerDto.getLastName());
        }
        if ((!Objects.isNull(customerDto.getPhoneNumber()) && !customerDto.getPhoneNumber().isEmpty()) && requestValidation.validatePhoneNumber(customerDto.getPhoneNumber())) {
            customer.setPhoneNumber(customerDto.getPhoneNumber());
        }
        if ((!Objects.isNull(customerDto.getEmail()) && !customerDto.getEmail().isEmpty()) && requestValidation.validateEmail(customerDto.getEmail())) {
            customer.setEmail(customerDto.getEmail());
        }
        if ((!Objects.isNull(customerDto.getPreferredLanguage()) && customerDto.getPreferredLanguage().toString().isEmpty()) && requestValidation.validatePreferredLanguage(customerDto.getPreferredLanguage().toString())) {
            customer.setPreferredLanguage(customerDto.getPreferredLanguage().toString());
        }
        if ((!Objects.isNull(customerDto.getStatus()) && !customerDto.getStatus().toString().isEmpty()) && requestValidation.validateStatus(customerDto.getStatus().toString())) {
            customer.setStatus(customerDto.getStatus().toString());
        }
        customerRepo.save(customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
