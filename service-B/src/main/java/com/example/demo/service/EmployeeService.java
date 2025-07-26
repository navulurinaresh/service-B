package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeVO> getEmployees() {
        if(Math.random()>0.5){
            throw new RuntimeException();
        }
        Iterable<Employee> list = employeeRepository.findAll(Sort.by("id"));
        List<Employee> res=new ArrayList<>();
        list.forEach(emp->res.add(emp));
        return convertToDTO(res);
    }
    public List<EmployeeVO> getEmployees(Pageable pageable) {
        Page<Employee> list = employeeRepository.findAll(pageable);
        List<Employee> res=new ArrayList<>();
        System.out.println(list.getTotalElements() +" :::" + list.getTotalPages());
        list.forEach(emp->res.add(emp));
        return convertToDTO(res);
    }



    public List<EmployeeVO> createEmployee(List<EmployeeVO> list) {
        List<Employee> entityList = new ArrayList<Employee>();
        list.forEach(employeeVO -> {
            Employee emp = new com.example.demo.entity.Employee();
            emp.setName(employeeVO.getName());
            emp.setAge(employeeVO.getAge());
            emp.setSalary(employeeVO.getSalary());
            emp.setGender(employeeVO.getGender());
            entityList.add(emp);
        });
        List<Employee> empy = employeeRepository.saveAll(entityList);
        List<EmployeeVO> emps = empy.stream().map(employee -> getEmployeeVO(employee)).toList();
        return emps;
    }

    public List<EmployeeVO> convertToDTO(List<com.example.demo.entity.Employee> list) {
        return list.stream().map(employee -> {
            EmployeeVO emp = getEmployeeVO(employee);
            return emp;
        }).toList();
    }

    private static EmployeeVO getEmployeeVO(Employee employee) {
        EmployeeVO emp = new EmployeeVO();
        emp.setId(employee.getId());
        emp.setName(employee.getName());
        emp.setAge(employee.getAge());
        emp.setSalary(employee.getSalary());
        emp.setGender(employee.getGender());
        return emp;
    }
}
