//package com.casestudy.digitalbankingopenapi.validation;
//
//import com.mob.casestudy.digitalbanking.dto.CustomerDto;
//import com.mob.casestudy.digitalbanking.dto.CustomerOtpDto;
//import com.mob.casestudy.digitalbanking.exception.*;
//import com.mob.casestudy.digitalbanking.repository.CustomerRepo;
//import com.mob.casestudy.digitalbanking.service.CustomerService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static com.mob.casestudy.digitalbanking.constants.ErrorCode.LOGIN;
//
//@ExtendWith(MockitoExtension.class)
//class RequestValidationTest {
//
//    @InjectMocks
//    RequestValidation requestValidation;
//
//    @Mock
//    CustomerService customerService;
//
//    @Mock
//    CustomerRepo customerRepo;
//
//    @Test
//    void validateRequest_validation_returnTrue() {
//        CustomerOtpDto customerOtpDto = new CustomerOtpDto();
//        customerOtpDto.setTemplateId(LOGIN);
//        customerOtpDto.setUserName("bhaumik");
////        Mockito.when(customerService.isValidTemplateId(customerOtpDto.getTemplateId())).thenReturn(Boolean.TRUE);
////        Boolean actualValue = requestValidation.validateInitiateOtpRequest(customerOtpDto);
////        Assertions.assertEquals(Boolean.TRUE, actualValue);
//    }
//
//    @Test
//    void validateRequest_validation_returnFalse() {
//        CustomerOtpDto customerOtpDto = new CustomerOtpDto();
//        customerOtpDto.setTemplateId(LOGIN);
//        customerOtpDto.setUserName("bhaumik");
////        Mockito.when(customerService.isValidTemplateId(customerOtpDto.getTemplateId())).thenReturn(Boolean.FALSE);
////        Boolean actualValue = requestValidation.validateInitiateOtpRequest(customerOtpDto);
////        Assertions.assertEquals(Boolean.FALSE, actualValue);
//    }
//
//    @Test
//    void validateCustomer_validateUsername_ThrowsCustomerFieldMissingException() {
//        CustomerDto customerDto = new CustomerDto();
//        Assertions.assertThrows(CustomerFieldMissing.class, () -> requestValidation.validateCustomer(customerDto));
//    }
//
//    @Test
//    void validateCustomer_validateEmail_ThrowsCustomerFieldMissingException() {
//        CustomerDto customerDto = new CustomerDto();
//        customerDto.setUserName("bhk");
//        Assertions.assertThrows(CustomerFieldMissing.class, () -> requestValidation.validateCustomer(customerDto));
//    }
//
//    @Test
//    void validateCustomer_validateLastName_ThrowsCustomerFieldMissingException() {
//        CustomerDto customerDto = new CustomerDto();
//        customerDto.setUserName("bhk");
//        customerDto.setEmail("bhaumik@mail.com");
//        Assertions.assertThrows(CustomerFieldMissing.class, () -> requestValidation.validateCustomer(customerDto));
//    }
//
//    @Test
//    void validateCustomer_validatePhoneNumber_ThrowsCustomerFieldMissingException() {
//        CustomerDto customerDto = new CustomerDto();
//        customerDto.setUserName("bhk");
//        customerDto.setEmail("bhaumik@mail.com");
//        customerDto.setLastName("khatri");
//        Assertions.assertThrows(CustomerFieldMissing.class, () -> requestValidation.validateCustomer(customerDto));
//    }
//
//    @Test
//    void validateCustomer_validatePreferredLanguage_ThrowsCustomerFieldMissingException() {
//        CustomerDto customerDto = new CustomerDto();
//        customerDto.setUserName("bhk");
//        customerDto.setEmail("bhaumik@mail.com");
//        customerDto.setLastName("khatri");
//        customerDto.setPhoneNumber("9988776655");
//        Assertions.assertThrows(CustomerFieldMissing.class, () -> requestValidation.validateCustomer(customerDto));
//    }
//
//    @Test
//    void validateCustomer_validateFirstName_ThrowsCustomerFieldMissingException() {
//        CustomerDto customerDto = new CustomerDto();
//        customerDto.setUserName("bhk");
//        customerDto.setEmail("bhaumik@mail.com");
//        customerDto.setLastName("khatri");
//        customerDto.setPhoneNumber("9988776655");
//        customerDto.setPreferredLanguage("EN");
//        Assertions.assertThrows(CustomerFieldMissing.class, () -> requestValidation.validateCustomer(customerDto));
//    }
//
//    @Test
//    void validateCustomer_invalidPhoneNumber_ThrowsInvalidPhoneNumberException() {
//        CustomerDto customerDto = new CustomerDto();
//        customerDto.setUserName("bhk");
//        customerDto.setEmail("bhaumik@mail.com");
//        customerDto.setLastName("khatri");
//        customerDto.setPhoneNumber("99885");
//        customerDto.setPreferredLanguage("EN");
//        customerDto.setFirstName("Bhaumik");
//        Assertions.assertThrows(InvalidPhoneNumberException.class, () -> requestValidation.validateCustomer(customerDto));
//    }
//
//    @Test
//    void validateCustomer_invalidEmail_ThrowsInvalidEmailException() {
//        CustomerDto customerDto = new CustomerDto();
//        customerDto.setUserName("bhk");
//        customerDto.setEmail("bhaumik@mail");
//        customerDto.setLastName("khatri");
//        customerDto.setPhoneNumber("9998887776");
//        customerDto.setPreferredLanguage("EN");
//        customerDto.setFirstName("Bhaumik");
//        Assertions.assertThrows(InvalidEmailException.class, () -> requestValidation.validateCustomer(customerDto));
//    }
//
//    @Test
//    void validateCustomer_invalidPreferredLanguage_ThrowsInvalidLanguageException() {
//        CustomerDto customerDto = new CustomerDto();
//        customerDto.setUserName("bhk");
//        customerDto.setEmail("bhaumik@gmail.com");
//        customerDto.setLastName("khatri");
//        customerDto.setPhoneNumber("9998887776");
//        customerDto.setPreferredLanguage("aq");
//        customerDto.setFirstName("Bhaumik");
//        Assertions.assertThrows(InvalidLanguageException.class, () -> requestValidation.validateCustomer(customerDto));
//    }
//
//    @Test
//    void validateCustomer_invalidUsername_ThrowsInvalidUsernameException() {
//        CustomerDto customerDto = new CustomerDto();
//        customerDto.setUserName("bhk");
//        customerDto.setEmail("bhaumik@gmail.com");
//        customerDto.setLastName("khatri");
//        customerDto.setPhoneNumber("9998887776");
//        customerDto.setPreferredLanguage("EN");
//        customerDto.setFirstName("Bhaumik");
//        Assertions.assertThrows(InvalidUsernameException.class, () -> requestValidation.validateCustomer(customerDto));
//    }
//
//    @Test
//    void validateCustomer_uniqueUsername_ThrowsDuplicateUsernameException() {
//        CustomerDto customerDto = new CustomerDto();
//        customerDto.setUserName("Bhaumik009");
//        customerDto.setEmail("bhaumik@gmail.com");
//        customerDto.setLastName("khatri");
//        customerDto.setPhoneNumber("9998887776");
//        customerDto.setPreferredLanguage("EN");
//        customerDto.setFirstName("Bhaumik");
//        Mockito.when(customerRepo.existsByUserName(customerDto.getUserName())).thenReturn(Boolean.TRUE);
//        Assertions.assertThrows(DuplicateUsernameException.class, () -> requestValidation.validateCustomer(customerDto));
//    }
//}