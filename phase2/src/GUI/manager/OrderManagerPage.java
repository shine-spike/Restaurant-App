package GUI.manager;

import GUI.elements.CustomButton;
import GUI.elements.CustomGridPane;
import GUI.elements.CustomPage;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Tab;

public class OrderManagerPage extends CustomPage {
  @Override
  public void populateTab(Tab tab, Node previous) {
    CustomGridPane grid = new CustomGridPane(50);
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);



    tab.setContent(grid);
  }

  @Override
  public void update() {}
}
