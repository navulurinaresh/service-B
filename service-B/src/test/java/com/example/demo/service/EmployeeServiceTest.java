package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.vo.EmployeeVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee testEmployee;
    private EmployeeVO testEmployeeVO;

    @BeforeEach
    void setUp() {
        testEmployee = new Employee();
        testEmployee.setId(1);
        testEmployee.setName("John Doe");
        testEmployee.setAge(30);
        testEmployee.setSalary(50000);
        testEmployee.setGender("Male");

        testEmployeeVO = new EmployeeVO();
        testEmployeeVO.setId(1);
        testEmployeeVO.setName("John Doe Updated");
        testEmployeeVO.setAge(31);
        testEmployeeVO.setSalary(55000);
        testEmployeeVO.setGender("Male");
    }

    @Test
    void testUpdateEmployee_Success() {
        // Given
        when(employeeRepository.findById(1)).thenReturn(Optional.of(testEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(testEmployee);

        // When
        EmployeeVO result = employeeService.updateEmployee(1, testEmployeeVO);

        // Then
        assertNotNull(result);
        assertEquals(testEmployeeVO.getName(), result.getName());
        assertEquals(testEmployeeVO.getAge(), result.getAge());
        assertEquals(testEmployeeVO.getSalary(), result.getSalary());
        assertEquals(testEmployeeVO.getGender(), result.getGender());
        
        verify(employeeRepository, times(1)).findById(1);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_EmployeeNotFound() {
        // Given
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeeService.updateEmployee(999, testEmployeeVO);
        });

        assertEquals("Employee not found with id: 999", exception.getMessage());
        verify(employeeRepository, times(1)).findById(999);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_PartialUpdate() {
        // Given
        EmployeeVO partialUpdate = new EmployeeVO();
        partialUpdate.setName("Jane Smith");
        partialUpdate.setAge(35);
        partialUpdate.setSalary(60000);
        partialUpdate.setGender("Female");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(testEmployee));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee emp = invocation.getArgument(0);
            emp.setId(1);
            return emp;
        });

        // When
        EmployeeVO result = employeeService.updateEmployee(1, partialUpdate);

        // Then
        assertNotNull(result);
        assertEquals("Jane Smith", result.getName());
        assertEquals(35, result.getAge());
        assertEquals(60000, result.getSalary());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdateEmployee_SameValues() {
        // Given
        EmployeeVO sameValues = new EmployeeVO();
        sameValues.setName("John Doe");
        sameValues.setAge(30);
        sameValues.setSalary(50000);
        sameValues.setGender("Male");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(testEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(testEmployee);

        // When
        EmployeeVO result = employeeService.updateEmployee(1, sameValues);

        // Then
        assertNotNull(result);
        assertEquals(testEmployee.getName(), result.getName());
        assertEquals(testEmployee.getAge(), result.getAge());
        assertEquals(testEmployee.getSalary(), result.getSalary());
        assertEquals(testEmployee.getGender(), result.getGender());
    }

    @Test
    void testUpdateEmployee_UpdateOnlyName() {
        // Given
        EmployeeVO updateNameOnly = new EmployeeVO();
        updateNameOnly.setName("Updated Name");
        updateNameOnly.setAge(testEmployee.getAge());
        updateNameOnly.setSalary(testEmployee.getSalary());
        updateNameOnly.setGender(testEmployee.getGender());

        when(employeeRepository.findById(1)).thenReturn(Optional.of(testEmployee));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        EmployeeVO result = employeeService.updateEmployee(1, updateNameOnly);

        // Then
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
    }

    @Test
    void testUpdateEmployee_UpdateOnlySalary() {
        // Given
        EmployeeVO updateSalaryOnly = new EmployeeVO();
        updateSalaryOnly.setName(testEmployee.getName());
        updateSalaryOnly.setAge(testEmployee.getAge());
        updateSalaryOnly.setSalary(75000);
        updateSalaryOnly.setGender(testEmployee.getGender());

        when(employeeRepository.findById(1)).thenReturn(Optional.of(testEmployee));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        EmployeeVO result = employeeService.updateEmployee(1, updateSalaryOnly);

        // Then
        assertNotNull(result);
        assertEquals(75000, result.getSalary());
    }
}
