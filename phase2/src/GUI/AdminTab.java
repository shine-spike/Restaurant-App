package GUI;

import GUI.admin.HomeAdminPage;

public class AdminTab extends RestaurantTab {
  AdminTab(int employeeNumber) {
    super("Admin", employeeNumber);
  }

  public void populateTab() {
    new HomeAdminPage().populateTab(getTab());
  }

  /** Updates all the nodes of this tab with the appropriate new information */
  public void updateTab() {}
}
