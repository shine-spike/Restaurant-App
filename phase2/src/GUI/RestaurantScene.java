package GUI;

import GUI.admin.AdminTab;
import GUI.cook.CookTab;
import GUI.elements.CustomGridPane;
import GUI.manager.ManagerTab;
import GUI.receiver.ReceiverTab;
import GUI.server.ServerTab;
import controller.Restaurant;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;

public class RestaurantScene {
  private RestaurantApplication application;
  private int employeeNumber;

  RestaurantScene(RestaurantApplication application, int employeeNumber) {
    this.application = application;
    this.employeeNumber = employeeNumber;
  }

  public Parent getRoot() {
    CustomGridPane grid = new CustomGridPane(0);
    grid.setPercentageColumns(100);

    Button logoutButton = new Button("Logout");
    logoutButton.setOnAction(new LogoutButtonHandler());

    ToolBar toolbar = new ToolBar(logoutButton);
    toolbar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    grid.add(toolbar, 0, 0);

    TabPane restaurantTabPane = new TabPane();
    restaurantTabPane.setMinHeight(application.getStage().getHeight());

    boolean[] permissions =
        Restaurant.getInstance().getEmployeeController().getEmployeePermissions(employeeNumber);
    if (permissions[0]) {
      restaurantTabPane.getTabs().add(new AdminTab(employeeNumber).getTab());
    }
    if (permissions[1]) {
      restaurantTabPane.getTabs().add(new ManagerTab(employeeNumber).getTab());
    }
    if (permissions[2]) {
      restaurantTabPane.getTabs().add(new ServerTab(employeeNumber).getTab());
    }
    if (permissions[3]) {
      restaurantTabPane.getTabs().add(new CookTab(employeeNumber).getTab());
    }
    if (permissions[4]) {
      restaurantTabPane.getTabs().add(new ReceiverTab(employeeNumber).getTab());
    }
    grid.add(restaurantTabPane, 0, 1);

    return grid;
  }

  private class LogoutButtonHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
      application.startLoginScene();
    }
  }
}
