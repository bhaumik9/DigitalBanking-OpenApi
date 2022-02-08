package com.casestudy.digitalbankingopenapi.dto;

import com.casestudy.digitalbankingopenapi.entity.Customer;
import com.casestudy.digitalbankingopenapi.entity.CustomerSecurityQuestions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {

    private String id;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{7,29}$", message = "Invalid username")
    private String userName;
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "\\d{10}", message = "Invalid phoneNumber")
    private String phoneNumber;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[\\w+.]*@\\w+.[com|in]{2,3}", message = "Invalid email")
    private String email;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "EN|DE|FR", message = "Invalid Preferred Language")
    private String preferredLanguage;
    private String status;

    public List<CustomerSecurityQuestions> toDtoList(List<CustomerSecurityQuestions> customerSecurityQuestionsList) {
        List<CustomerSecurityQuestions> customerSecurityQuestionsListDto = new ArrayList<>();
        customerSecurityQuestionsList.forEach(customerSecurityQuestionsListDto::add);
        return customerSecurityQuestionsListDto;
    }

    public CustomerDto(String userName, String firstName, String lastName, String phoneNumber, String email, String preferredLanguage) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.preferredLanguage = preferredLanguage;
    }

    public Customer toEntity() {
        return new Customer(this.userName, this.firstName, this.lastName, this.email, this.phoneNumber, this.preferredLanguage);
    }
}
