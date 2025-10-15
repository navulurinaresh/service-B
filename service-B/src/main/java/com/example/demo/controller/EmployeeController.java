package com.example.demo.controller;

import com.example.demo.service.EmployeeService;
import com.example.demo.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/greet/{firstName}/{lastName}")
    public String getMessage(@PathVariable String firstName, @PathVariable String lastName){
        return "Hello "+firstName+" "+lastName;
    }

    @GetMapping("/employees")
    public List<EmployeeVO> getEmployees(){
        return service.getEmployees();
    }

    @PostMapping("/employee/create")
    public List<EmployeeVO> getEmployees(@RequestBody List<EmployeeVO> employees){
        return service.createEmployee(employees);
    }

    @GetMapping("/employeesp")
    public List<EmployeeVO> getEmployeesPagable(@RequestParam int pageNumber,@RequestParam int pageSize){
        Pageable pageableWithSort = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
        return service.getEmployees(pageableWithSort);
    }
};
