package com.fun;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;


@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{
 
    @Autowired
    private EmployeeDao dao;

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    EmployeeService employeeService;

    public Employee findById(int id) {
        return dao.findById(id);
    }

    @Transactional(propagation= Propagation.REQUIRED )
    public void saveEmployee(Employee employee, Employee employee1) {
        System.out.println("start of save");

        //employee.setSsn("ss3");
        employeeService.saveEmployee(employee);
        //saveEmployee(employee);

        employee.setSsn("22222");
        ///dao.saveEmployee(employee1);
//        throw  new RuntimeException("abc");
      //  employeeService.empty();
        //System.out.println("hi from saveEmployee");
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    @Override
    public void saveEmployee(Employee employee) {
        dao.saveEmployee(employee);
    }


    @Transactional(propagation= Propagation.REQUIRED)
    public void empty() {

        System.out.println("hi from empty");
        //throw new RuntimeException("hoi");
        //  dao.saveEmployee(employee1);
        try{
            Thread.sleep(10000);
        } catch (Exception e){

        }
    }
 
    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends. 
     */
    @Transactional(propagation= Propagation.REQUIRED)
    public void updateEmployee(Employee employee) {
        Employee entity = dao.findById(employee.getId());
        if(entity!=null){
            entity.setName(employee.getName());
            entity.setJoiningDate(employee.getJoiningDate());
            entity.setSalary(employee.getSalary());
            entity.setSsn(employee.getSsn());
        }
    }
 
    public void deleteEmployeeBySsn(String ssn) {
        dao.deleteEmployeeBySsn(ssn);
    }

    @Transactional
    public List<Employee> findAllEmployees() {
        return dao.findAllEmployees();
    }
 
    public Employee findEmployeeBySsn(String ssn) {

        return dao.findEmployeeBySsn(ssn);
    }
 
    public boolean isEmployeeSsnUnique(Integer id, String ssn) {
        Employee employee = findEmployeeBySsn(ssn);
        return ( employee == null || ((id != null) && (employee.getId() == id)));
    }
     
}