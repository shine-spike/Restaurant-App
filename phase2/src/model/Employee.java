package model;

public class Employee {
    private static int currentEmployeeNumber = 0;

    private int employeeNumber;
    private String firstName;
    private String lastName;
    private String password;

    private boolean isCook;
    private boolean isServer;
    private boolean isReceiver;
    private boolean isManager;


    public Employee(String firstName, String lastName, String password, EmployeeType type){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.employeeNumber = currentEmployeeNumber;
        currentEmployeeNumber++;

        isReceiver = true;
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

    public boolean isServer(){
        return isServer;
    }

    public boolean isReceiver(){
        return isReceiver;
    }
}
