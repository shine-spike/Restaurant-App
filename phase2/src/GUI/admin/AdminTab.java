package GUI.admin;

import GUI.elements.CustomTab;

/**
 * Administrator tab. Allows control over lower-end restaurant functionality such as employees and
 * menus.
 */
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
