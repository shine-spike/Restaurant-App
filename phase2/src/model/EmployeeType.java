package model;

import org.jetbrains.annotations.NotNull;

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

  @NotNull
  public static EmployeeType getEmployeeType(String keyword) {
    for (EmployeeType type : EmployeeType.values()) {
      if (type.keyword.equals(keyword)) {
        return type;
      }
    }

    return DEFAULT;
  }
}
