package com.t2308e.example.salarymanagementsystem.service;

import com.t2308e.example.salarymanagementsystem.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee saveEmployee(Employee employee);

    Optional<Employee> getEmployeeById(Long id);

    Optional<Employee> getEmployeeByName(String name);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Long id);

    List<Employee> searchEmployees(String name, Integer minAge, Integer maxAge,
                                   Double minSalary, Double maxSalary);
}
