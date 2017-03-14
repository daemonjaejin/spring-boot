package com.example.service;

import com.example.domain.Customer;
import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jin on 17. 3. 13.
 */

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> findAll(){
        return customerRepository.findAllOrderByName();
    }

    public Page<Customer> findAll(Pageable pageable){
        return customerRepository.findAllOrderByName(pageable);
    }

    public Customer findOne(Integer id){
        return customerRepository.findOne(id);
    }

    public Customer create(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer update(Customer customer){
        return customerRepository.save(customer);
    }

    public void delete(Integer id){
        customerRepository.delete(id);
    }

//    public Customer save(Customer customer){
//        return customerRepository.save(customer);
//    }
//
//    public List<Customer> findAll(){
//        return customerRepository.findAll();
//    }

}
