package GUI.manager;

import GUI.elements.CustomTab;

public class ManagerTab extends CustomTab {

  public ManagerTab(int employeeNumber) {
    super("Manager", employeeNumber);
  }

  @Override
  public void populateTab() {
    new HomeManagerPage().populateTab(getTab(), null);
  }

  @Override
  public void updateTab() {}
}
