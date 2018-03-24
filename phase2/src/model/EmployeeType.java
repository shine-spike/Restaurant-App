package model;

public enum EmployeeType {
  ADMIN("ADMIN"),
  DEFAULT("DEFAULT"),
  SERVER("SERVER"),
  COOK("COOK"),
  MANAGER("MANAGER");

  private final String keyword;

  EmployeeType(String keyword) {
    this.keyword = keyword;
  }

  public static EmployeeType getEmployeeType(String keyword) {
    for (EmployeeType type : EmployeeType.values()) {
      if (keyword.equals(type.keyword)) {
        return type;
      }
    }

    return DEFAULT;
  }
}
