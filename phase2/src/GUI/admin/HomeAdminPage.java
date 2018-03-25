package GUI.admin;

import GUI.elements.CustomButton;
import GUI.elements.CustomGridPane;
import GUI.elements.CustomLabel;
import GUI.elements.CustomPage;
import javafx.scene.control.Tab;

public class HomeAdminPage extends CustomPage {
  @Override
  public void populateTab(Tab tab) {
    CustomGridPane grid = new CustomGridPane(0);
    grid.setPercentageRows(10);
    grid.setPercentageColumns(25, 50, 25);
    grid.setVgap(50);

    CustomLabel headerLabel = new CustomLabel("Administration");
    headerLabel.setFontSize(30);
    headerLabel.center();
    headerLabel.setBold();
    grid.add(headerLabel, 1, 1);

    CustomButton manageEmployeesButton = new CustomButton("Manage Employees");
    manageEmployeesButton.maximize();
    manageEmployeesButton.setFontSize(20);
    manageEmployeesButton.setOnAction(e -> new EmployeeAdminPage().populateTab(tab));
    grid.add(manageEmployeesButton, 1, 3);

    CustomButton manageMenusButton = new CustomButton("Manage Menus");
    manageMenusButton.maximize();
    manageMenusButton.setFontSize(20);
    manageMenusButton.setOnAction(e -> new MenuAdminPage().populateTab(tab));
    grid.add(manageMenusButton, 1, 4);

    tab.setContent(grid);
  }

  @Override
  public void update() {}
}
