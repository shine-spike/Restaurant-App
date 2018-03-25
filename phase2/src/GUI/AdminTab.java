package GUI;

import GUI.elements.CustomButton;
import GUI.elements.CustomGridPane;

public class AdminTab extends RestaurantTab {
  AdminTab(int employeeNumber) {
    super("Admin", employeeNumber);
  }

  public void populateTab() {
    CustomGridPane grid = new CustomGridPane();
    grid.setPercentageColumns(20, 25, 10, 25, 20);
    grid.setPercentageRows(20, 25, 10, 25, 20);

    CustomButton manageEmployeesButton = new CustomButton("Manage Employees", true, 20);
    manageEmployeesButton.setOnAction(e -> manageEmployees());
    grid.add(manageEmployeesButton, 1, 1);

    CustomButton manageMenusButton = new CustomButton("Manage Menus", true, 20);
    manageMenusButton.setOnAction(e -> manageMenus());
    grid.add(manageMenusButton, 3, 1);

    getTab().setContent(grid);
  }

  private void manageEmployees() {

  }

  private void manageMenus() {

  }

  /** Updates all the nodes of this tab with the appropriate new information */
  public void updateTab() {}
}
