package model;

import java.util.ArrayList;

/**
 * Controls all aspects of employees in the restaurant.
 */
public class EmployeeController {
  // List of employees indexed by employee number
  private final ArrayList<String> employees;

  /**
   * Creates an EmployeeController with no employees
   */
  EmployeeController() {
    employees = new ArrayList<>();
  }

  /**
   * Adds an Employee to this EmployeeController
   *
   * @param name the first and last name of the Employee to be added
   */
  public void addEmployee(String name) {
    employees.add(name);
  }

  /**
   * Gets the name of an Employee from the Employee's id
   *
   * @param id the id of the desired Employee
   * @return the name of Employee id
   */
  public String getEmployeeName(int id) {
    return employees.get(id);
  }
}
