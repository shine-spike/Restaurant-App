package model;

import java.io.Serializable;

public class Employee implements Serializable {
  private int employeeNumber;
  private EmployeeType employeeType;

  private String firstName;
  private String lastName;
  private int passwordHashCode;

  public Employee(int employeeNumber, String firstName, String lastName, String password, EmployeeType employeeType) {
    this.employeeNumber = employeeNumber;
    this.employeeType = employeeType;

    this.firstName = firstName;
    this.lastName = lastName;
    this.passwordHashCode = password.hashCode();
  }

  public int getEmployeeNumber() {
    return employeeNumber;
  }

  public String getFullName() {
    return firstName + " " + lastName;
  }

  public String[] getName() {
    return new String[] {firstName, lastName};
  }

  public void setName(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public void setPassword(String password) {
    passwordHashCode = password.hashCode();
  }

  public boolean checkPassword(String password) {
    return passwordHashCode == password.hashCode();
  }

  public EmployeeType getEmployeeType() {
    return employeeType;
  }

  public void setEmployeeType(EmployeeType employeeType) {
    this.employeeType = employeeType;
  }

  public boolean[] getPermissions() {
    return new boolean[] {
      hasAdminPermissions(),
      hasManagerPermissions(),
      hasServerPermissions(),
      hasCookPermissions(),
      hasReceiverPermissions()
    };
  }

  private boolean hasAdminPermissions() {
    return employeeType == EmployeeType.ADMIN;
  }

  private boolean hasManagerPermissions() {
    return employeeType == EmployeeType.ADMIN || employeeType == EmployeeType.MANAGER;
  }

  private boolean hasServerPermissions() {
    return employeeType == EmployeeType.ADMIN || employeeType == EmployeeType.SERVER;
  }

  private boolean hasCookPermissions() {
    return employeeType == EmployeeType.ADMIN || employeeType == EmployeeType.COOK;
  }

  private boolean hasReceiverPermissions() {
    return true;
  }

  @Override
  public String toString() {
    return String.format("[%02d] %s %s", employeeNumber, firstName, lastName);
  }
}
