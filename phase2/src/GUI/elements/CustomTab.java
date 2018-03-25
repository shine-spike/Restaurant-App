package GUI.elements;

import javafx.scene.control.Tab;

/**
 * Wrapper for our tabs in the main portion of the restaurant interface. Allows easier management of
 * each category of events that can occur with the software.
 */
public abstract class CustomTab {
  private Tab tab;
  private int employeeNumber;

  /**
   * Constructs a wrapper for a tab in the main restaurant interface.
   *
   * @param tabName the name of this tab.
   * @param employeeNumber the number of the employee this tab is for.
   */
  public CustomTab(String tabName, int employeeNumber) {
    this.employeeNumber = employeeNumber;
    this.tab = new Tab(tabName);
    initializeTab();
  }

  /** Initializes the tab with default settings and populates it. */
  private void initializeTab() {
    tab.setClosable(false);
    populateTab();
  }

  /**
   * Gets the tab this wrapper is managing. Useful for completely changing the root of the tab.
   *
   * @return the tab object this wrapper is managing.
   */
  public Tab getTab() {
    return tab;
  }

  /**
   * Gets the number of the employee currently opening this tab.
   *
   * @return the number of current employee on this tab.
   */
  public int getEmployeeNumber() {
    return employeeNumber;
  }

  /** Populates the tab with the nodes it should display. */
  public abstract void populateTab();

  /** Updates all the nodes of this tab with the appropriate new information. */
  public abstract void updateTab();
}
