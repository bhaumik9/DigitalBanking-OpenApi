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
public class    CustomerSecurityImagesId  implements Serializable {

    private String customerId;
    private String securityImageId;

    public CustomerSecurityImagesId(){
        this.securityImageId= UUID.randomUUID().toString();
    }

}
