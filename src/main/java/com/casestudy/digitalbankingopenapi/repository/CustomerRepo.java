package com.casestudy.digitalbankingopenapi.repository;

import com.casestudy.digitalbankingopenapi.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface CustomerRepo extends CrudRepository<Customer,String> {

    Customer findByUserName(String s);
    boolean existsByUserName(String username);
}
