import java.util.ArrayList;

public class EmployeeController {
    private ArrayList<String> employees;

    EmployeeController(){
        employees = new ArrayList<>();
    }

    public void addEmployee(String name){
        employees.add(name);
    }

    public String getEmployeeName(int id){
        return employees.get(id);
    }
}
