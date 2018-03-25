package GUI.manager;

import GUI.elements.CustomTab;

public class ManagerTab extends CustomTab {

  /** Constructs a ManagerTab for the employee with the id employeeNumber */
  public ManagerTab(int employeeNumber) {
    super("Manager", employeeNumber);
  }

  /** Initializes this ManagerTab's JavaFX tab */
  public void populateTab() {
    new MangerMenuPage().populateTab(getTab());
  }

  /** Updates all the nodes of this tab with the appropriate new information */
  public void updateTab() {}
}
