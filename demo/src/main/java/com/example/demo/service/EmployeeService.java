package com.example.demo.service;

import com.example.demo.repository.EmployeeRepository;
import com.example.demo.vo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        List<com.example.demo.entity.Employee> list = employeeRepository.findAll();
        return convertToDTO(list);
    }

    public List<Employee> convertToDTO(List<com.example.demo.entity.Employee> list) {
        return list.stream().map(employee -> {
            Employee emp = new Employee();
            emp.setId(employee.getId());
            emp.setName(employee.getName());
            emp.setAge(employee.getAge());
            emp.setSalary(employee.getSalary());
            emp.setGender(employee.getGender());
            return emp;
        }).toList();
    }
}
