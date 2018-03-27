package GUI.manager;

import GUI.elements.CustomButton;
import GUI.elements.CustomGridPane;
import GUI.elements.CustomLabel;
import GUI.elements.CustomPage;
import javafx.scene.Node;
import javafx.scene.control.Tab;

public class HomeManagerPage extends CustomPage {
  @Override
  public void populateTab(Tab tab, Node previous) {
    CustomGridPane grid = new CustomGridPane(0);
    grid.setPercentageRows(10);
    grid.setPercentageColumns(25, 50, 25);
    grid.setVgap(50);

    CustomLabel headerLabel = new CustomLabel("Management");
    headerLabel.setFontSize(30);
    headerLabel.center();
    headerLabel.setBold();
    grid.add(headerLabel, 1, 1);

    CustomButton manageEmployeesButton = new CustomButton("View Inventory");
    manageEmployeesButton.maximize();
    manageEmployeesButton.setFontSize(20);
    manageEmployeesButton.setOnAction(e -> new InventoryManagerPage().populateTab(tab, grid));
    grid.add(manageEmployeesButton, 1, 3, 1, 2);

    CustomButton manageMenusButton = new CustomButton("Request Inventory");
    manageMenusButton.maximize();
    manageMenusButton.setFontSize(20);
    manageMenusButton.setOnAction(e -> new RequestManagerPage().populateTab(tab, grid));
    grid.add(manageMenusButton, 1, 5, 1, 2);

    tab.setContent(grid);
  }

  @Override
  public void update() {}
}
