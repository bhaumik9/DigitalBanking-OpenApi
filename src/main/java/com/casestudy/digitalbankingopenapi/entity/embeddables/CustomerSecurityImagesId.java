package com.casestudy.digitalbankingopenapi.entity.embeddables;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSecurityImagesId implements Serializable {
    private String customerId;
    private String securityImageId;
}
