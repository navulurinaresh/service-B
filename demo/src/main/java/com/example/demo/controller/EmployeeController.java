package com.example.demo.controller;

import com.example.demo.service.EmployeeService;
import com.example.demo.vo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/greet/{name}")
    public String getMessage(@PathVariable String name){
        return "Hello "+name;
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees(){
        return service.getEmployees();
    }
}
