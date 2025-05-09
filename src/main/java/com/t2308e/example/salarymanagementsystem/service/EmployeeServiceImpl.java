package com.t2308e.example.salarymanagementsystem.service;

import com.t2308e.example.salarymanagementsystem.exception.EmployeeAlreadyExistsException;
import com.t2308e.example.salarymanagementsystem.model.Employee;
import com.t2308e.example.salarymanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        // Check if employee with same name already exists
        if (employeeRepository.existsByName(employee.getName())) {
            throw new EmployeeAlreadyExistsException("A User with name " + employee.getName() + " already exists");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Optional<Employee> getEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Optional<Employee> existingEmployee = employeeRepository.findByName(employee.getName());
        if (existingEmployee.isPresent() && !existingEmployee.get().getId().equals(employee.getId())) {
            throw new EmployeeAlreadyExistsException("A User with name " + employee.getName() + " already exists");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> searchEmployees(String name, Integer minAge, Integer maxAge,
                                          Double minSalary, Double maxSalary) {
        return employeeRepository.searchEmployees(name, minAge, maxAge, minSalary, maxSalary);
    }
}