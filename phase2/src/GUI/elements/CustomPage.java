package GUI.elements;

import javafx.scene.control.Tab;

/**
 * Simple abstract class that guarantees basic functionality of all the pages in the gui.
 */
public abstract class CustomPage {
  /**
   * Populates the given tab with the contents of this page.
   *
   * @param tab the tab to populate.
   */
  public abstract void populateTab(Tab tab);

  /**
   * Updates this page to the most recent contents.
   */
  public abstract void update();
}
