package model;

public class Employee {
    private static int currentEmployeeNumber = 0;

    private int employeeNumber;
    private String firstName;
    private String lastName;
    private String password;

    private boolean isCook = false;
    private boolean isServer = false;
    private boolean isReceiver = true;
    private boolean isManager = false;

    public Employee(String firstName, String lastName, String password, EmployeeType type){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.employeeNumber = currentEmployeeNumber;
        currentEmployeeNumber++;

        switch (type){
            case COOK:
                isCook = true;
                break;
            case SERVER:
                isServer = true;
                break;
            case MANAGER:
                isCook = true;
                isServer = true;
                isManager = true;
                break;
        }
    }

    public int getEmployeeNumber(){
        return employeeNumber;
    }

    public String getName(){
        return firstName + " " + lastName;
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }

    public boolean isManager(){
        return isManager;
    }

    public boolean isCook(){
        return isCook;
    }

    public boolean isReceiver(){
        return isReceiver;
    }

    public boolean isServer(){
        return isServer;
    }
}
