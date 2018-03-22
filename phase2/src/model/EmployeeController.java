package model;

import java.util.ArrayList;

/**
 * Controls all aspects of employees in the restaurant.
 */
public class EmployeeController {
  // List of employees indexed by employee number
  private final ArrayList<Employee> employees;

  /**
   * Creates an EmployeeController with no employees
   */
  EmployeeController() {
    employees = new ArrayList<>();
  }

  /**
   * Adds an Employee to this EmployeeController
   *
   * @param firstName this employee's first name
   * @param lastName this employee's last name
   * @param password this employee's password
   * @param type this employee's type
   */
  public void addEmployee(String firstName, String lastName, String password, String type) {
    EmployeeType employeeType = EmployeeType.getEmployeeType(type);
    if(employeeType != null) {
      employees.add(new Employee(firstName, lastName, password, employeeType));
    }
  }

  /**
   * Gets the name of an Employee from the Employee's id
   *
   * @param id the id of the desired Employee
   * @return the Employee object with the given id
   */
  public Employee getEmployee(int id) {
    return employees.get(id);
  }

  /**
   * Gets the name of an Employee from the Employee's id
   *
   * @param name the name of the desired Employee
   * @return the Employee object with the given name
   */
  public Employee getEmployee(String name) {
    for(Employee employee: employees){
      if(employee.getName().equals(name)){
        return employee;
      }
    }

    return null;
  }
}
