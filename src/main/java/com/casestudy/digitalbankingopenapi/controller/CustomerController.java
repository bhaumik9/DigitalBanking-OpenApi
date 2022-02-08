package com.casestudy.digitalbankingopenapi.controller;

import com.casestudy.digitalbankingopenapi.dto.CustomerDto;
import com.casestudy.digitalbankingopenapi.service.CustomerService;
import com.casestudy.digitalbankingopenapi.validation.RequestValidation;
import openapi.model.CreateCustomerRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/customer-service")
public class CustomerController {

    private CustomerService customerService;
    private RequestValidation requestValidation;

    @Autowired
    public CustomerController(CustomerService customerService, RequestValidation requestValidation) {
        this.customerService = customerService;
        this.requestValidation = requestValidation;
    }

    @PostMapping(value = "/client-api/v1/customers", produces = "application/json")
    ResponseEntity<Object> addCustomer(@Valid @RequestBody CreateCustomerRequestDto customerDto) {
        requestValidation.validateCustomer(customerDto);
        String id = customerService.add(customerDto);
        if (!Objects.isNull(id)) {
            return new ResponseEntity<>("id " + id, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value = "/client-api/v1/customers/{username}", produces = "application/json")
    ResponseEntity<Object> updateCustomer(@PathVariable String username, @RequestBody CustomerDto customerDto) {
        customerService.update(customerDto, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
