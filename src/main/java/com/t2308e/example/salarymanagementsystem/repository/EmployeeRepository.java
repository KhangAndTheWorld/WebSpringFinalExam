package com.t2308e.example.salarymanagementsystem.repository;

import com.t2308e.example.salarymanagementsystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT e FROM Employee e WHERE " +
            "(:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:minAge IS NULL OR e.age >= :minAge) AND " +
            "(:maxAge IS NULL OR e.age <= :maxAge) AND " +
            "(:minSalary IS NULL OR e.salary >= :minSalary) AND " +
            "(:maxSalary IS NULL OR e.salary <= :maxSalary)")
    List<Employee> searchEmployees(
            @Param("name") String name,
            @Param("minAge") Integer minAge,
            @Param("maxAge") Integer maxAge,
            @Param("minSalary") Double minSalary,
            @Param("maxSalary") Double maxSalary);
}
