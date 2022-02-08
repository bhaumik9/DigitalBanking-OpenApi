package com.casestudy.digitalbankingopenapi.repository;

import com.casestudy.digitalbankingopenapi.entity.CustomerOtp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOtpRepo extends CrudRepository<CustomerOtp,String> {

    public CustomerOtp findByCustomerId(String s);
}
