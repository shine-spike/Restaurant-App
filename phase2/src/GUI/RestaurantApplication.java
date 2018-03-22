package GUI;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Employee;
import util.Logger;


public class RestaurantApplication extends Application{
    private Stage stage;

    @Override
    public void start(Stage stage){
        Driver driver = new Driver();
        driver.readConfiguration();
        Logger.startLogger();

        this.stage = stage;
        stage.setTitle("Restaurant");
        stage.setScene(new SigninScene(this).getScene());
        stage.show();
    }

    public void startRestaurantScene(Employee employee){
        stage.setScene(new RestaurantScene(this, employee).getScene());
    }
}
