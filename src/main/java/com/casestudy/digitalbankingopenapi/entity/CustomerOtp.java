package com.casestudy.digitalbankingopenapi.entity;

import com.casestudy.digitalbankingopenapi.entity.embeddables.CustomerOtpId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CustomerOtp {

    @Transient
    String templateId;

    @Transient
    String userName;

    @EmbeddedId
    CustomerOtpId customerOtpId = new CustomerOtpId();

    @OneToOne
    @MapsId("customerId")
    Customer customer;

    @Column(name = "otp_message")
    String otpMessage;

    @Column(name = "otp")
    String otp;

    @Column(name = "retries", length = 1)
    int retries;

    @Column(name = "expires_on")
    LocalDateTime expiresOn;

    @Column(name = "created_on")
    LocalDateTime createdOn;

    public CustomerOtp(String otpMessage, String otp, int retries, LocalDateTime expiresOn, LocalDateTime createdOn, String username) {
        this.otpMessage = otpMessage;
        this.otp = otp;
        this.retries = retries;
        this.expiresOn = expiresOn;
        this.createdOn = createdOn;
        this.userName = username;
    }
}
