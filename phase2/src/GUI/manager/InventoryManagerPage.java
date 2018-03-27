package GUI.manager;

import GUI.elements.CustomButton;
import GUI.elements.CustomGridPane;
import GUI.elements.CustomPage;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Tab;

public class InventoryManagerPage extends CustomPage {
  @Override
  public void populateTab(Tab tab, Node previous) {
    CustomGridPane grid = new CustomGridPane(25);
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);

    CustomButton sendButton = new CustomButton("Send");
    grid.add(sendButton, 0, 0);

    CustomButton backButton = getBackButton(tab, previous);
    grid.add(backButton, 1, 1);

    tab.setContent(grid);
  }

  @Override
  public void update() {}
}
