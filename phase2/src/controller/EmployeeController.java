package controller;

import model.Employee;
import model.EmployeeType;
import util.SerializableContents;

import java.util.ArrayList;
import java.util.Arrays;

/** Controls all aspects of employees in the restaurant. */
public class EmployeeController implements SerializableContents<Employee> {
  private final ArrayList<Employee> employees = new ArrayList<>();

  /** Creates an employee controller with only the administrator account. */
  EmployeeController() {
    employees.add(new Employee(employees.size(),"Admin", "account", "adminaccount", EmployeeType.ADMIN));
  }

  /**
   * Register an employee to the controller.
   *
   * @param firstName the employee's first name.
   * @param lastName the employee's last name.
   * @param password the employee's password.
   * @param employeeType the employee's type string.
   */
  public void registerEmployee(
      String firstName, String lastName, String password, String employeeType) {
    employees.add(
        new Employee(employees.size(), firstName, lastName, password, EmployeeType.getEmployeeType(employeeType)));
  }

  /**
   * Gets the number of the employee with the given login information.
   *
   * @param name the name of the employee to login.
   * @param password the password of the employee to login.
   * @return the number of the employee with the given login information, or {@code -1} if the
   *     username-password combination does not exist in the controller.
   */
  public int login(String name, String password) {
    Employee employee = getEmployee(name);
    return employee != null && employee.checkPassword(password) ? employee.getEmployeeNumber() : -1;
  }

  /**
   * Updates an employees name or does nothing if the employee with the given number does not exist.
   *
   * @param employeeNumber the number of the employee.
   * @param firstName the employee's first name.
   * @param lastName the employee's last name.
   */
  public void updateEmployeeName(int employeeNumber, String firstName, String lastName) {
    Employee employee = getEmployee(employeeNumber);
    if (employee != null) {
      employee.setName(firstName, lastName);
    }
  }

  /**
   * Updates an employees password or does nothing if the employee with the given number does not
   * exist.
   *
   * @param employeeNumber the number of the employee.
   * @param password the employee's new password.
   */
  public void updateEmployeePassword(int employeeNumber, String password) {
    Employee employee = getEmployee(employeeNumber);
    if (employee != null) {
      employee.setPassword(password);
    }
  }

  /**
   * Updates an employees type or does nothing if the employee with the given number does not exist.
   *
   * @param employeeNumber the number of the employee.
   * @param type the new type string of the employee.
   */
  public void updateEmployeeType(int employeeNumber, String type) {
    Employee employee = getEmployee(employeeNumber);
    if (employee != null) {
      employee.setEmployeeType(EmployeeType.getEmployeeType(type));
    }
  }

  /**
   * Gets the entire catalogue of employees.
   *
   * @return the list of employees in string format.
   */
  public ArrayList<String> getEmployeeStrings() {
    ArrayList<String> employeeStrings = new ArrayList<>();
    for (Employee employee : employees) {
      employeeStrings.add(employee.toString());
    }
    return employeeStrings;
  }

  /**
   * Gets the information of the employee with the given employee number.
   *
   * @param employeeNumber the number of the employee.
   * @return an array containing information about the employee.
   */
  public String[] getEmployeeInformation(int employeeNumber) {
    String[] info = new String[4];
    Employee employee = getEmployee(employeeNumber);

    if (employee != null) {
      info[0] = employee.getName()[0];
      info[1] = employee.getName()[1];
      info[2] = employee.getEmployeeType().toString();
    }

    return info;
  }

  /**
   * Gets the permissions of the employee with the given number.
   *
   * @param employeeNumber the number of the employee.
   * @return an array of booleans representing the permissions.
   */
  public boolean[] getEmployeePermissions(int employeeNumber) {
    Employee employee = getEmployee(employeeNumber);

    if (employee != null) {
      return employee.getPermissions();
    }

    return new boolean[] {};
  }

  /**
   * Gets the different types of employees.
   *
   * @return an array containing the different types of employees.
   */
  public String[] getEmployeeTypeStrings() {
    return EmployeeType.getEmployeeTypeStrings();
  }

  /**
   * Gets the employee with the given name.
   *
   * @param name the name of the employee.
   * @return the employee with the given name or {@code null} if no such employee exists.
   */
  private Employee getEmployee(String name) {
    for (Employee employee : employees) {
      if (employee.getFullName().equals(name)) {
        return employee;
      }
    }
    return null;
  }

  /**
   * Gets the employee with the given number.
   *
   * @param employeeNumber the number of the employee.
   * @return the employee with the given number or {@code null} if no such employee exists.
   */
  private Employee getEmployee(int employeeNumber) {
    for (Employee employee : employees) {
      if (employee.getEmployeeNumber() == employeeNumber) {
        return employee;
      }
    }
    return null;
  }

  @Override
  public Employee[] getContents() {
    Employee[] employeeArray = new Employee[employees.size()];
    employees.toArray(employeeArray);
    return employeeArray;
  }

  @Override
  public void setContents(Employee[] contents) {
    employees.clear();
    employees.addAll(Arrays.asList(contents));
  }

  @Override
  public String getName() {
    return "employees";
  }
}
