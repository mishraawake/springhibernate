package com.fun;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public interface EmployeeService {
 
    Employee findById(int id);
     
    void saveEmployee(Employee employee, Employee employee1);
     
    void updateEmployee(Employee employee);
     
    void deleteEmployeeBySsn(String ssn);
 
    List<Employee> findAllEmployees(); 
     
    Employee findEmployeeBySsn(String ssn);

    @Transactional(propagation= Propagation.REQUIRED)
    void saveEmployee(Employee employee);

    void empty();
    boolean isEmployeeSsnUnique(Integer id, String ssn);
     
}