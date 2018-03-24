package GUI;

import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import model.Employee;

public class RestaurantScene {
  private RestaurantApplication application;
  private Employee employee;

  public RestaurantScene(RestaurantApplication application, Employee employee) {
    this.application = application;
    this.employee = employee;
  }

  public Scene getScene() {
    TabPane restaurantTabPane = new TabPane();
    int employeeNumber = employee.getEmployeeNumber();

    if (employee.hasManagerPermissions()) {
      restaurantTabPane.getTabs().add(new ManagerTab(employeeNumber).getTab());
    }
    if (employee.hasServerPermissions()) {
      restaurantTabPane.getTabs().add(new ServerTab(employeeNumber).getTab());
    }
    if (employee.hasCookPermissions()) {
      restaurantTabPane.getTabs().add(new CookTab(employeeNumber).getTab());
    }
    if (employee.hasReceiverPermissions()) {
      restaurantTabPane.getTabs().add(new ReceiverTab(employeeNumber).getTab());
    }

    Scene scene = new Scene(restaurantTabPane);
    scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
    return scene;
  }
}
