package controller;

import model.Employee;
import model.EmployeeType;

import java.util.ArrayList;

/** Controls all aspects of employees in the restaurant. */
public class EmployeeController {
  private final ArrayList<Employee> employees = new ArrayList<>();

  /** Creates an employee controller with only the administrator account. */
  EmployeeController() {
    employees.add(new Employee("Admin", "account", "adminaccount", EmployeeType.ADMIN));
  }

  /**
   * Register an employee to the controller.
   *
   * @param firstName the employee's first name.
   * @param lastName the employee's last name.
   * @param password the employee's password.
   * @param employeeType the employee's type string identifier.
   */
  public void registerEmployee(
      String firstName, String lastName,  String password, String employeeType) {
    employees.add(
        new Employee(firstName, lastName, password, EmployeeType.getEmployeeType(employeeType)));
  }

  /**
   * Gets the employee with the given name.
   *
   * @param name the name of the employee.
   * @return the employee with the given name or {@code null} if no such employee exists.
   */
  
  public Employee getEmployee(String name) {
    for (Employee employee : employees) {
      if (employee.getFullName().equals(name)) {
        return employee;
      }
    }
    return null;
  }

  /**
   * Gets the entire catalogue of employees.
   *
   * @return the list of employees in this controller.
   */
  
  public ArrayList<Employee> getEmployees() {
    return employees;
  }

  /**
   * Gets the employee with the given login information.
   *
   * @param name the name of the employee to login.
   * @param password the password of the employee to login.
   * @return the employee with the given login information, or {@code null} if the username-password
   *     combination does not exist in the controller.
   */
  
  public Employee login(String name,  String password) {
    Employee employee = getEmployee(name);
    return employee != null && employee.checkPassword(password) ? employee : null;
  }
}
