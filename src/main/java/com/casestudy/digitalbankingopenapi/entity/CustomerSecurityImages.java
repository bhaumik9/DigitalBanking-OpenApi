package com.casestudy.digitalbankingopenapi.entity;

import com.casestudy.digitalbankingopenapi.entity.embeddables.CustomerSecurityImagesId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class CustomerSecurityImages {

    @EmbeddedId
    CustomerSecurityImagesId customerSecurityImagesId = new CustomerSecurityImagesId();

    @OneToOne
    @MapsId("customerId")
    Customer customer;

    @ManyToOne
    @MapsId("securityImageId")
    SecurityImages securityImages;

    @Column(name = "security_image_caption")
    String securityImageCaption;

    @Column(name = "created_on")
    LocalDateTime createdOn;
}
