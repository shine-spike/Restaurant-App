package GUI.elements;

import javafx.scene.Node;
import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Wrapper for our tabs in the main portion of the restaurant interface. Allows easier management of
 * each category of events that can occur with the software.
 */
public class CustomTab extends Tab {
  private Stack<CustomPage> previousPages = new Stack<>();
  private CustomPage currentPage;
  private int employeeNumber;

  /**
   * Constructs a wrapper for a tab in the main restaurant interface.
   *
   * @param tabName the name of this tab.
   * @param employeeNumber the number of the employee this tab is for.
   */
  public CustomTab(String tabName, int employeeNumber, CustomPage startingPage) {
    super(tabName);
    this.employeeNumber = employeeNumber;
    startingPage.populateTab(this);

    initializeTab();
  }

  /** Initializes the tab with default settings and populates it. */
  private void initializeTab() {
    setClosable(false);
    setOnSelectionChanged(e -> currentPage.update());
  }

  public void setCurrentPage(CustomPage page, Node content) {
    previousPages.push(currentPage);
    currentPage = page;
    setContent(content);
    currentPage.update();
  }

  public void goBack() {
    previousPages.pop().populateTab(this);
    previousPages.pop();
  }

  public boolean canGoBack() {
    return !previousPages.empty();
  }

  public int getEmployeeNumber() {
    return employeeNumber;
  }
}
