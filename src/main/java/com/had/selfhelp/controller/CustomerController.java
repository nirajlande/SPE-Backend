package com.had.selfhelp.controller;

import com.had.selfhelp.entity.Camplaints;
import com.had.selfhelp.entity.Customer;
import com.had.selfhelp.entity.LoginRequest;
import com.had.selfhelp.service.CamplaintServices;
import com.had.selfhelp.service.CustomerServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerServices customerServices;

     @Autowired
    CamplaintServices camplaintServices;

    @PostMapping("/customer")
    public void save(@RequestBody Customer c ){
        customerServices.save(c);
    }

    @PostMapping("/customer/{cust_id}")
    public List<Camplaints> save(@RequestBody Camplaints c , @PathVariable(name="cust_id")int cust_id)
    {
         Customer cust = customerServices.findById(cust_id);
         c.setCustomer(cust);
         c.setStatus("Pending");
         c.setAknow("Pending");
         camplaintServices.save(c);
         log.info("Registering the complaints");
         return customerServices.costumerComplaint(cust);
    }

    @GetMapping("/complaints/{cust_id}")
    public List<Camplaints>getcomplaints(@PathVariable(name="cust_id")int cust_id)
    {
        log.info("updating the complaints instance");
        Customer cust = customerServices.findById(cust_id);
        if(cust_id==1)
        {
          return   camplaintServices.getAll();
        }
        else
            return customerServices.costumerComplaint(cust);
    }
    @PostMapping("/login")
    public Customer validate(@RequestBody LoginRequest log)
    {
          return customerServices.login(log);
    }

    @PostMapping("/owner/status/{com_id}")
    public void status(@PathVariable(name="com_id")int com_id)
    {
        log.info("cheking complaints status");
        Camplaints c =  camplaintServices.findById(com_id);
        c.setStatus("Done");
         camplaintServices.save(c);

    }

    @PostMapping("/owner/aknow/{com_id}/{aknow}")
    public void status(@PathVariable(name="com_id")int com_id,@PathVariable(name="aknow")String aknow)
    {
        log.info("Aknowledge the complaints");
        Camplaints c =  camplaintServices.findById(com_id);
        c.setAknow(aknow);
        camplaintServices.save(c);

    }


}
