package controller;

import model.Employee;
import model.EmployeeType;

import java.util.ArrayList;


/**
 * Controls all aspects of employees in the restaurant.
 */
public class EmployeeController {
  // List of employees indexed by employee number
  private final ArrayList<Employee> employees;


  /**
   * Creates an empty employee controller.
   */
  EmployeeController() {
    employees = new ArrayList<>();
  }

  /**
   * Register an employee to the controller.
   *
   * @param firstName the employee's first name.
   * @param lastName the employee's last name.
   * @param password the employee's password.
   * @param employeeType the employee's {@link EmployeeType}.
   */
  public void registerEmployee(String firstName, String lastName, String password, EmployeeType employeeType) {
    employees.add(new Employee(firstName, lastName, password, employeeType));
  }

  /**
   * Gets the employee object
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

  /**
   * Gets the employee with the given login information.
   *
   * @param name the name of the employee to login.
   * @param password the password of the employee to login.
   * @return the employee with the given login information, or {@code null} if the username-password
   * combination does not exist in the controller.
   */
  public Employee login(String name, String password) {
    Employee employee = getEmployee(name);
    return employee != null && employee.checkPassword(password) ? employee : null;
  }
}
