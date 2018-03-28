package GUI.manager;

import GUI.elements.CustomButton;
import GUI.elements.CustomGridPane;
import GUI.elements.CustomPage;
import GUI.elements.CustomTab;
import javafx.geometry.Pos;

public class InventoryManagerPage extends CustomPage {
  @Override
  public void populateTab(CustomTab tab) {
    CustomGridPane grid = new CustomGridPane(50);
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);

    CustomButton sendButton = new CustomButton("Send");
    grid.add(sendButton, 0, 0);

    CustomButton backButton = getBackButton(tab);
    grid.add(backButton, 1, 1);

    tab.setCurrentPage(this, grid);
  }

  @Override
  public void update() {}
}
