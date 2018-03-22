package GUI;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Employee;


public class RestaurantApplication extends Application{
    private Stage stage;
    private int employeeNumber;

    @Override
    public void start(Stage stage){
        GUI.Driver d = new GUI.Driver();
        d.readConfiguration();

        this.stage = stage;
        stage.setTitle("Restaurant");
        stage.setScene(new SigninScene(this).getScene());
        stage.show();
    }

    public void setEmployeeNumber(int employeeNumber){
        this.employeeNumber = employeeNumber;
    }

    public void startRestaurantScene(Employee employee){
        stage.setScene(new RestaurantScene(this, employee).getScene());
    }
}
