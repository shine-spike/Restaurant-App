package GUI.admin;

import GUI.elements.CustomGridPane;
import GUI.elements.CustomLabel;
import GUI.elements.CustomPage;
import controller.MenuController;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import util.Localizer;

import java.util.ArrayList;

public class MenuAdminPage extends CustomPage {
  private MenuController menuController = Restaurant.getInstance().getMenuController();

  private ArrayList<String> menuList = new ArrayList<>();
  private ListView<String> menuListView = new ListView<>();
  private ArrayList<String> menuItemList = new ArrayList<>();
  private ListView<String> menuItemListView = new ListView<>();

  MenuAdminPage() {
    update();
  }

  @Override
  public void populateTab(Tab tab, Node previous) {
    CustomGridPane grid = new CustomGridPane(25);
    grid.setHgap(25);
    grid.setVgap(25);
    grid.setPercentageColumns(50, 10, 10, 10, 10, 10);

    CustomLabel menuListLabel = new CustomLabel("Menu List");
    menuListLabel.setFontSize(20);
    menuListLabel.setBold();
    menuListLabel.center();
    grid.add(menuListLabel, 0, 0);

    menuListView
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              if (newSelection.intValue() != -1) {
                String menu = menuList.get(newSelection.intValue());

                menuItemList = menuController.getMenuItemStrings(menu);
                menuItemListView.setItems(
                    FXCollections.observableArrayList(Localizer.localize(menuItemList)));
              }
            });
    grid.add(menuListView, 0, 1);

    CustomLabel menuItemListLabel = new CustomLabel("Menu Item List");
    menuItemListLabel.setFontSize(20);
    menuItemListLabel.setBold();
    menuItemListLabel.center();
    grid.add(menuItemListLabel, 0, 2);

    menuItemListView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener((obs, oldSelection, newSelection) -> {});

    grid.add(menuItemListView, 0, 3);

    if (previous != null) {
      grid.add(getBackButton(tab, previous), 0, 4, 6, 3);
    }

    tab.setContent(grid);
  }

  @Override
  public void update() {
    menuList = menuController.getMenuStrings();
    menuListView.setItems(FXCollections.observableList(Localizer.localize(menuList)));
  }
}
