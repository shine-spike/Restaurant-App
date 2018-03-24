package GUI;

import javafx.application.Application;
import javafx.stage.Stage;

import model.Employee;
import util.Logger;

public class RestaurantApplication extends Application {
  private Stage stage;
  private int employeeNumber;

  @Override
  public void start(Stage stage) {
    System.setProperty("prism.lcdtext", "false");

    Driver driver = new Driver();
    driver.readConfiguration();
    Logger.startLogger();

    this.stage = stage;
    stage.setMaximized(true);
    stage.setTitle("Restaurant");
    stage.setScene(new LoginScene(this).getScene());
    stage.show();
  }

  public void setEmployeeNumber(int employeeNumber) {
    this.employeeNumber = employeeNumber;
  }

  public void startRestaurantScene(Employee employee) {
    stage.setScene(new RestaurantScene(this, employee).getScene());
  }
}
