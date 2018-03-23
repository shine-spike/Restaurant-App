package model;


public enum EmployeeType {
    DEFAULT("DEFAULT"),
    SERVER("SERVER"),
    COOK("COOK"),
    MANAGER("MANAGER");

    private final String keyword;

    EmployeeType(String keyword) {
        this.keyword = keyword;
    }

    public static EmployeeType getEmployeeType(String keyword) {
        for (EmployeeType i : EmployeeType.values()){
            if (keyword.equals(i.keyword)){
                return i;
            }
        }

        return DEFAULT;
    }
}
