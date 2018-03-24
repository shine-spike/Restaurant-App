package GUI;

import controller.Restaurant;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import model.Employee;

public class RestaurantScene {
  private RestaurantApplication application;
  private Employee employee;

  RestaurantScene(RestaurantApplication application, Employee employee) {
    this.application = application;
    this.employee = employee;
  }

  public Parent getRoot() {
    GridPane grid = new GridPane();
    ColumnConstraints column = new ColumnConstraints();
    column.setPercentWidth(100);
    grid.getColumnConstraints().add(column);

    Button logoutButton = new Button("Logout");
    logoutButton.setOnAction(new LogoutButtonHandler());

    ToolBar toolbar = new ToolBar(logoutButton);
    toolbar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    grid.add(toolbar, 0, 0);

    TabPane restaurantTabPane = new TabPane();
    int employeeNumber = employee.getEmployeeNumber();
    if (employee.hasAdminPermissions()) {
      restaurantTabPane.getTabs().add(new AdminTab(employeeNumber).getTab());
    }
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
    grid.add(restaurantTabPane, 0 ,1);

    return grid;
  }

  private class LogoutButtonHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
      application.startLoginScene();
    }
  }
}
