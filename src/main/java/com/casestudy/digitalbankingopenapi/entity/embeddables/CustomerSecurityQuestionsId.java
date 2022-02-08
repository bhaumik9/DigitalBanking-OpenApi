package com.casestudy.digitalbankingopenapi.entity.embeddables;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class CustomerSecurityQuestionsId implements Serializable {

    private String customerId;
    private String securityQuestionId;
}
