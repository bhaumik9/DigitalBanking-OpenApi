package com.casestudy.digitalbankingopenapi.entity.embeddables;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class CustomerOtpId implements Serializable {

    private String otpId;
    private String customerId;

    public CustomerOtpId(){
        this.otpId = UUID.randomUUID().toString();
    }

}
