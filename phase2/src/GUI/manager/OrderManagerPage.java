package GUI.manager;

import GUI.elements.CustomGridPane;
import GUI.elements.CustomPage;
import GUI.elements.CustomTab;
import javafx.geometry.Pos;

public class OrderManagerPage extends CustomPage {
  @Override
  public void populateTab(CustomTab tab) {
    CustomGridPane grid = new CustomGridPane(50);
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);

    tab.setCurrentPage(this, grid);
  }

  @Override
  public void update() {}
}
