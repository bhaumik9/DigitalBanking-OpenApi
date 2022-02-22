package com.casestudy.digitalbankingopenapi.entity;

import com.casestudy.digitalbankingopenapi.dto.CustomerDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, length = 36)
    private String id;

    @Column(name = "user_name", length = 30)
    private String userName;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "preferred_language", length = 2)
    private String preferredLanguage;

    @Column(name = "external_id", length = 50)
    private String externalId;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @Column(name = "update_on")
    private LocalDateTime updatedOn;

    private int age;

    @OneToOne(mappedBy = "customer")
    CustomerOtp customerOtp;

    @OneToMany(mappedBy = "customer")
    private List<CustomerSecurityQuestions> customerSecurityQuestionsList;

    @OneToOne(mappedBy = "customer")
    private CustomerSecurityImages customerSecurityImage;

    public Customer(String userName, String firstName, String lastName, String email, String phoneNumber, String preferredLanguage) {
        this.userName = userName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.preferredLanguage = preferredLanguage;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", preferredLanguage='" + preferredLanguage + '\'' +
                ", externalId='" + externalId + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdOn=" + createdOn +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
