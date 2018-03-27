package GUI.admin;

import GUI.elements.CustomTab;

public class AdminTab extends CustomTab {
  public AdminTab(int employeeNumber) {
    super("Admin", employeeNumber);
  }

  @Override
  public void populateTab() {
    new HomeAdminPage().populateTab(getTab(), null);
  }

  @Override
  public void updateTab() {}
}
