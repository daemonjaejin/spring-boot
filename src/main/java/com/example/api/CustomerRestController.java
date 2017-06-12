package com.example.api;

import com.example.domain.Customer;
import com.example.service.CustomerService;
import com.example.service.LoginUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Created by jin on 17. 3. 14.
 */

@RestController
@RequestMapping("api/customers")
public class CustomerRestController {

    @Autowired
    CustomerService customerService;

//    @RequestMapping(method = RequestMethod.GET)
//    List<Customer> getCustomers(){
//        List<Customer> customers = customerService.findAll();
//        return customers;
//    }

    @RequestMapping(method = RequestMethod.GET)
    Page<Customer> getCustomers(@PageableDefault Pageable pageable){
        Page<Customer> customers = customerService.findAll(pageable);
        return customers;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    Customer getCustomer(@PathVariable Integer id){
        Customer customer = customerService.findOne(id);
        return customer;
    }

    // 신규 고객 정보 작성
//    @RequestMapping(method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.CREATED)
//    Customer postCustomers(@RequestBody Customer customer){
//        return customerService.create(customer);
//    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Customer> postCustomers(@RequestBody Customer customer,
                                           UriComponentsBuilder uriBuilder,
                                           @AuthenticationPrincipal LoginUserDetails userDetails){
        Customer created = customerService.create(customer, userDetails.getUser());
        URI location = uriBuilder.path("api/customers/{id}")
                .buildAndExpand(created.getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    // 고객 한 명의 정보 업데이트
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    Customer putCustomer(@PathVariable Integer id, @RequestBody Customer customer,
                         @AuthenticationPrincipal LoginUserDetails userDetails){
        customer.setId(id);
        return customerService.update(customer, userDetails.getUser());
    }

    // 고객 한 명의 정보 삭제
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCustomer(@PathVariable Integer id){
        customerService.delete(id);
    }

}
