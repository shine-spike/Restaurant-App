package model;

/** Enum for the different types of employees we can have. */
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

  /**
   * Gets the employee type relative to a keyword.
   *
   * @param keyword the keyword of the employee type.
   * @return the respective employee type.
   */
  public static EmployeeType getEmployeeType(String keyword) {
    for (EmployeeType type : EmployeeType.values()) {
      if (type.keyword.equals(keyword)) {
        return type;
      }
    }

    return DEFAULT;
  }

  /**
   * Gets the different employee type keywords.
   *
   * @return an array containing all different type keywords.
   */
  public static String[] getEmployeeTypeStrings() {
    String[] typeStrings = new String[EmployeeType.values().length];
    for (int i = 0; i < EmployeeType.values().length; i++) {
      typeStrings[i] = EmployeeType.values()[i].keyword;
    }
    return typeStrings;
  }
}
