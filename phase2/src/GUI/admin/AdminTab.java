package GUI.admin;

import GUI.elements.CustomTab;

public class AdminTab extends CustomTab {
  public AdminTab(int employeeNumber) {
    super("Admin", employeeNumber);
  }

  public void populateTab() {
    new HomeAdminPage().populateTab(getTab());
  }

  /** Updates all the nodes of this tab with the appropriate new information */
  public void updateTab() {}
}