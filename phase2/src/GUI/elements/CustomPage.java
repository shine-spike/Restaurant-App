package GUI.elements;

import javafx.scene.Node;
import javafx.scene.control.Tab;

/** Simple abstract class that guarantees basic functionality of all the pages in the gui. */
public abstract class CustomPage {
  /**
   * Populates the given tab with the contents of this page and references the last contents of the
   * tab.
   *
   * @param tab the tab to populate.
   * @param previous the previous contents of the tab.
   */
  public abstract void populateTab(Tab tab, Node previous);

  /** Updates this page to the most recent contents. */
  public abstract void update();

  /**
   * Gets the back button for this page.
   *
   * @param tab the tab this page is on.
   * @param previous the previous content to go back to.
   * @return the back button or {@code null} if there is no previous page.
   */
  protected CustomButton getBackButton(Tab tab, Node previous) {
    if (previous != null) {
      CustomButton backButton = new CustomButton("Back");
      backButton.maximize();
      backButton.setOnAction(e -> tab.setContent(previous));
      return backButton;
    }

    return null;
  }
}
