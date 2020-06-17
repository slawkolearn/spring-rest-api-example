package com.luv2code.springdemo.rest;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

    // autowire the CustomerService
    @Autowired
    private CustomerService customerService;

    // add  mapping for GET /customers
    @GetMapping("/customers")
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    // add mapping for GET /customers/{customerId}

    @GetMapping("/customers/{customerId}")
    public Customer getCustomer(@PathVariable int customerId){

        Customer customer = customerService.getCustomer(customerId);

        // if customer is not valid throw an error
        if(customer == null){
            throw new CustomerNotFoundException("Customer id not found " + customerId);
        }


        return customer;
    }

    // add mapping for POST /customers - add new customer

    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody Customer theCustomer){

        // also just in case the pass an id in JSON.. set id to 0
        // this is force a save of new item .. instead of update

        theCustomer.setId(0);

        customerService.saveCustomer(theCustomer);

        return theCustomer;
    }

    // add mapping for PUT /customers - update existing customer
    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer theCustomer){

        customerService.saveCustomer(theCustomer);

        return theCustomer;
    }

    @DeleteMapping("/customers/{customerId}")
    public String deleteCustomer(@PathVariable int customerId){

        // check if the user exists
        Customer customer = customerService.getCustomer(customerId);

        // if not throw exception
        if(customer == null){
            throw new CustomerNotFoundException("Customer id not found " + customerId);
        }

        customerService.deleteCustomer(customerId);

        return "Deleted customer id : " + customerId;
    }

}
