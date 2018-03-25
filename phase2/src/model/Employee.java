package model;

public class Employee {
  private static int currentEmployeeNumber = 0;

  private int employeeNumber;
  private EmployeeType employeeType;

  private String firstName;
  private String lastName;
  private int passwordHashCode;

  public Employee(String firstName, String lastName, String password, EmployeeType employeeType) {
    this.employeeNumber = currentEmployeeNumber;
    currentEmployeeNumber++;
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
    return new String[]{firstName, lastName};
  }

  public void setName(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public void setPassword(String password) {
    passwordHashCode = password.hashCode();
  }

  public void setEmployeeType(EmployeeType employeeType) {
    this.employeeType = employeeType;
  }

  public boolean checkPassword( String password) {
    return passwordHashCode == password.hashCode();
  }

  public EmployeeType getEmployeeType() {
    return employeeType;
  }

  public boolean hasAdminPermissions() {
    return employeeType == EmployeeType.ADMIN;
  }

  public boolean hasManagerPermissions() {
    return employeeType == EmployeeType.ADMIN || employeeType == EmployeeType.MANAGER;
  }

  public boolean hasServerPermissions() {
    return employeeType == EmployeeType.ADMIN || employeeType == EmployeeType.SERVER;
  }

  public boolean hasCookPermissions() {
    return employeeType == EmployeeType.ADMIN || employeeType == EmployeeType.COOK;
  }

  public boolean hasReceiverPermissions() {
    return true;
  }

  @Override
  public String toString() {
    return String.format("[%02d] %s %s", employeeNumber, firstName, lastName);
  }
}
