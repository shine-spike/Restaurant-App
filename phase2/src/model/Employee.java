package model;

import java.io.Serializable;

/** An employee in the system. */
public class Employee implements Serializable {
  private final int employeeNumber;
  private EmployeeType employeeType;
  private String firstName;
  private String lastName;
  private int passwordHashCode;

  /**
   * Constructs an employee with the given parameters.
   *
   * @param employeeNumber the number of the employee.
   * @param firstName the first name of the employee.
   * @param lastName the last name of the employee.
   * @param password the password of the employee.
   * @param employeeType the type of the employee.
   */
  public Employee(
      int employeeNumber,
      String firstName,
      String lastName,
      String password,
      EmployeeType employeeType) {
    this.employeeNumber = employeeNumber;
    this.employeeType = employeeType;

    this.firstName = firstName;
    this.lastName = lastName;
    this.passwordHashCode = password.hashCode();
  }

  /**
   * Gets the employee's employee number.
   *
   * @return this employee's number.
   */
  public int getEmployeeNumber() {
    return employeeNumber;
  }

  /**
   * Gets the employee's full name.
   *
   * @return this employee's first name.
   */
  public String getFullName() {
    return firstName + " " + lastName;
  }

  /**
   * Gets the employee's name.
   *
   * @return an array of first and last name.
   */
  public String[] getName() {
    return new String[] {firstName, lastName};
  }

  /**
   * Sets this employee's name.
   *
   * @param firstName the employees new first name.
   * @param lastName the employees new last name.
   */
  public void setName(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  /**
   * Sets this employees password.
   *
   * @param password the employee's new password.
   */
  public void setPassword(String password) {
    passwordHashCode = password.hashCode();
  }

  /**
   * Checks the given password matches the employee's password.
   *
   * @param password the password to check.
   * @return whether or not the password is correct.
   */
  public boolean checkPassword(String password) {
    return passwordHashCode == password.hashCode();
  }

  /**
   * Gets the type of the employee.
   *
   * @return this employee's type.
   */
  public EmployeeType getEmployeeType() {
    return employeeType;
  }

  /**
   * Sets the type of this employee.
   *
   * @param employeeType this employee's new type.
   */
  public void setEmployeeType(EmployeeType employeeType) {
    this.employeeType = employeeType;
  }

  /**
   * Gets a permissions array for this employee in the order of admin, manager, server, cook, and
   * receiver.
   *
   * @return an array of booleans representing what this employees permissions are.
   */
  public boolean[] getPermissions() {
    return new boolean[] {
      employeeType == EmployeeType.ADMIN,
      employeeType == EmployeeType.ADMIN || employeeType == EmployeeType.MANAGER,
      employeeType == EmployeeType.ADMIN || employeeType == EmployeeType.SERVER,
      employeeType == EmployeeType.ADMIN || employeeType == EmployeeType.COOK,
      true
    };
  }
}
