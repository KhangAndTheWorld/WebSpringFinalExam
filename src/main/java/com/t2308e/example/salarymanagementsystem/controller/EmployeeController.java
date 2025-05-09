package com.t2308e.example.salarymanagementsystem.controller;

import com.t2308e.example.salarymanagementsystem.exception.EmployeeAlreadyExistsException;
import com.t2308e.example.salarymanagementsystem.model.Employee;
import com.t2308e.example.salarymanagementsystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("employee", new Employee());
        return "index";
    }

    @PostMapping("/employees/add")
    public String addEmployee(@Valid @ModelAttribute("employee") Employee employee,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "index";
        }

        try {
            employeeService.saveEmployee(employee);
            redirectAttributes.addFlashAttribute("successMessage", "User created successfully");
        } catch (EmployeeAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error while creating User: " + e.getMessage());
        }

        return "redirect:/";
    }

    @GetMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteEmployee(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error while deleting User: " + e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/employees/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);

        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            model.addAttribute("isEditMode", true);
            return "index";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found");
            return "redirect:/";
        }
    }

    @PostMapping("/employees/update/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @Valid @ModelAttribute("employee") Employee employee,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "index";
        }

        Optional<Employee> existingEmployee = employeeService.getEmployeeById(id);
        if (existingEmployee.isPresent()) {
            employee.setId(id);
            try {
                employeeService.updateEmployee(employee);
                redirectAttributes.addFlashAttribute("successMessage", "User updated successfully");
            } catch (EmployeeAlreadyExistsException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Error while updating User: " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found");
        }

        return "redirect:/";
    }

    @GetMapping("/employees/search")
    public String searchEmployees(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) Integer minAge,
                                  @RequestParam(required = false) Integer maxAge,
                                  @RequestParam(required = false) Double minSalary,
                                  @RequestParam(required = false) Double maxSalary,
                                  Model model) {
        List<Employee> searchResults = employeeService.searchEmployees(name, minAge, maxAge, minSalary, maxSalary);
        model.addAttribute("employees", searchResults);
        model.addAttribute("employee", new Employee());
        model.addAttribute("searchName", name);
        model.addAttribute("searchMinAge", minAge);
        model.addAttribute("searchMaxAge", maxAge);
        model.addAttribute("searchMinSalary", minSalary);
        model.addAttribute("searchMaxSalary", maxSalary);

        return "index";
    }
}
