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
      if (type.keyword.equals(keyword)) {
        return type;
      }
    }

    return DEFAULT;
  }

  public static String[] getEmployeeTypeStrings() {
    String[] typeStrings = new String[EmployeeType.values().length];
    for (int i = 0; i < EmployeeType.values().length; i++) {
      typeStrings[i] = EmployeeType.values()[i].keyword;
    }
    return typeStrings;
  }
}
