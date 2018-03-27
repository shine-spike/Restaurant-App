package GUI.elements;

/** Simple abstract class that guarantees basic functionality of all the pages in the gui. */
public abstract class CustomPage {
  /**
   * Populates the given tab with the contents of this page.
   *
   * @param tab the tab to populate.
   */
  public abstract void populateTab(CustomTab tab);

  /** Updates this page to the most recent contents. */
  public abstract void update();

  /**
   * Gets the back button for this page.
   *
   * @param tab the tab this page is on.
   * @return the back button or {@code null} if there is no previous page.
   */
  protected CustomButton getBackButton(CustomTab tab) {
    if (tab.canGoBack()) {
      CustomButton backButton = new CustomButton("Back");
      backButton.maximize();
      backButton.setOnAction(e -> tab.goBack());
      return backButton;
    }

    return null;
  }
}
