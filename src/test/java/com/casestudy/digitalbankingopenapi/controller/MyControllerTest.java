package com.casestudy.digitalbankingopenapi.controller;

import com.casestudy.digitalbankingopenapi.dto.CustomerOtpDto;
import com.casestudy.digitalbankingopenapi.entity.CustomerOtp;
import com.casestudy.digitalbankingopenapi.service.CustomerOtpService;
import com.casestudy.digitalbankingopenapi.service.CustomerService;
import com.casestudy.digitalbankingopenapi.service.SecurityQuestionsService;
import openapi.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.casestudy.digitalbankingopenapi.constants.ErrorCode.LOGIN;

@ExtendWith(MockitoExtension.class)
class MyControllerTest {

    @InjectMocks
    MyController myController;
    @Mock
    CustomerService customerService;
    @Mock
    CustomerOtpService customerOtpService;
    @Mock
    SecurityQuestionsService securityQuestionsService;

    @Test
    void initiateOtp() {
        InitiateOtpRequestDto customerOtpDto = new InitiateOtpRequestDto();
        customerOtpDto.setTemplateId(LOGIN);
        customerOtpDto.setUserName("bhaumik");
        ResponseEntity<Void> expected = new ResponseEntity<Void>(HttpStatus.OK);
        Mockito.when(customerOtpService.initiateOtpRequest(customerOtpDto)).thenReturn(expected);
        ResponseEntity<Void> actual = myController.initiateOtp(customerOtpDto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void patchCustomerByUserName() {
        String username = "bhaumik";
        PatchCustomerRequestDto patchCustomerRequestDto = new PatchCustomerRequestDto();
        ResponseEntity<Void> expected = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(customerService.update(patchCustomerRequestDto, username)).thenReturn(expected);
        ResponseEntity<Void> actual = myController.patchCustomerByUserName(username, patchCustomerRequestDto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void postCustomers_addingCustomers_ReturnsHttpStatusCreated() {
        CreateCustomerRequestDto createCustomerRequestDto = new CreateCustomerRequestDto();
        CreateCustomerResponseDto createCustomerResponseDto = new CreateCustomerResponseDto();
        Mockito.when(customerService.add(createCustomerRequestDto)).thenReturn(createCustomerResponseDto);
        ResponseEntity<CreateCustomerResponseDto> actual = myController.postCustomers(createCustomerRequestDto);
        ResponseEntity<CreateCustomerResponseDto> expected = new ResponseEntity<>(createCustomerResponseDto, HttpStatus.CREATED);
        org.assertj.core.api.Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void postCustomers_addingCustomers_ReturnsHttpStatusInternalServerError() {
        CreateCustomerRequestDto createCustomerRequestDto = new CreateCustomerRequestDto();
        CreateCustomerResponseDto createCustomerResponseDto = null;
        Mockito.when(customerService.add(createCustomerRequestDto)).thenReturn(createCustomerResponseDto);
        ResponseEntity<CreateCustomerResponseDto> actual = myController.postCustomers(createCustomerRequestDto);
        ResponseEntity<CreateCustomerResponseDto> expected = new ResponseEntity<>(createCustomerResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        org.assertj.core.api.Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void getSecurityQuestions() {
        ResponseEntity<GetSecurityQuestionsResponseDto> expected = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(securityQuestionsService.getAllSecurityQuestions()).thenReturn(expected);
        ResponseEntity<GetSecurityQuestionsResponseDto> actual = myController.getSecurityQuestions();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getSecurityQuestionsByUserName() {
        ResponseEntity<GetCustomerSecurityQuestionResponseDto> expected = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(securityQuestionsService.getSecurityQuestionsByUsername("bhaumik")).thenReturn(expected);
        ResponseEntity<GetCustomerSecurityQuestionResponseDto> actual = myController.getSecurityQuestionsByUserName("bhaumik");
        Assertions.assertEquals(expected, actual);

    }
}