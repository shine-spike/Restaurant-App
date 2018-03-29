package GUI;

import GUI.admin.HomeAdminPage;
import GUI.cook.HomeCookPage;
import GUI.elements.CustomGridPane;
import GUI.elements.CustomTab;
import GUI.manager.HomeManagerPage;
import GUI.receiver.HomeReceiverPage;
import GUI.server.HomeServerPage;
import controller.Restaurant;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;

class RestaurantScene {
  private final RestaurantApplication application;
  private final int employeeNumber;

  RestaurantScene(RestaurantApplication application, int employeeNumber) {
    this.application = application;
    this.employeeNumber = employeeNumber;
  }

  public Parent getRoot() {
    CustomGridPane grid = new CustomGridPane(0);
    grid.setPercentageColumns(100);

    Button logoutButton = new Button("Logout");
    logoutButton.setOnAction(e -> application.startLoginScene());

    ToolBar toolbar = new ToolBar(logoutButton);
    toolbar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    grid.add(toolbar, 0, 0);

    TabPane restaurantTabPane = new TabPane();
    restaurantTabPane.setMinHeight(application.getStage().getHeight());

    boolean[] permissions =
        Restaurant.getInstance().getEmployeeController().getEmployeePermissions(employeeNumber);

    ObservableList<Tab> tabs = restaurantTabPane.getTabs();
    if (permissions[0]) {
      tabs.add(new CustomTab("Admin", employeeNumber, new HomeAdminPage()));
    }
    if (permissions[1]) {
      tabs.add(new CustomTab("Manager", employeeNumber, new HomeManagerPage()));
    }
    if (permissions[2]) {
      tabs.add(new CustomTab("Server", employeeNumber, new HomeServerPage()));
    }
    if (permissions[3]) {
      tabs.add(new CustomTab("Cook", employeeNumber, new HomeCookPage()));
    }
    if (permissions[4]) {
      tabs.add(new CustomTab("Receiver", employeeNumber, new HomeReceiverPage()));
    }
    grid.add(restaurantTabPane, 0, 1);

    return grid;
  }
}
