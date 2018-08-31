package com.fun;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.fun" })
@PropertySource(value = { "classpath:application.properties" })
public class HibernateConfig {

  @Autowired
  private Environment environment;

  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan(new String[] { "com.fun" });
    sessionFactory.setHibernateProperties(hibernateProperties());
    return sessionFactory;
  }


  @Bean
  public DataSource dataSource() {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
    dataSource.setJdbcUrl(environment.getRequiredProperty("jdbc.url"));
    dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
    dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
    dataSource.setMaximumPoolSize(1);
    dataSource.setAutoCommit(false);
    dataSource.setConnectionTimeout(300_000);
    LazyConnectionDataSourceProxy dataSourceProxy = new LazyConnectionDataSourceProxy(dataSource);
    return dataSourceProxy;
  }

  private Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
    properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
    properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
   // properties.put("hibernate.connection.provider_disables_autocommit", true);
    return properties;
  }

  @Bean
  @Autowired
  public HibernateTransactionManager transactionManager(SessionFactory s) {
    HibernateTransactionManager txManager = new HibernateTransactionManager();
    txManager.setSessionFactory(s);
    txManager.setPrepareConnection(false);
    txManager.setAutodetectDataSource(false);
    return txManager;
  }

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);
    final EmployeeService employeeService = context.getBean(EmployeeService.class);

    final Employee emp = new Employee();
    emp.setJoiningDate(new Date());
    emp.setName("pankaj");
    emp.setSalary(new BigDecimal(2));
    emp.setSsn("ssn");

    final Employee emp1 = new Employee();
    emp1.setJoiningDate(new Date());
    emp1.setName("pankaj");
    emp1.setSalary(new BigDecimal(2));
    emp1.setSsn("ssn2");

    employeeService.saveEmployee(emp, emp1);

   // employeeService.saveEmployee(emp, emp1);
//    for(int i=0;i<10;++i) {
//      new Thread(new Runnable() {
//        @Override
//        public void run() {
//          //employeeService.saveEmployee(emp, emp1);
//           employeeService.empty();
//          System.out.println("empty finished");
//        }
//      }).start();
//    }
//    employeeService.empty();


    //System.out.println(employeeService.findAllEmployees());
  }
}

