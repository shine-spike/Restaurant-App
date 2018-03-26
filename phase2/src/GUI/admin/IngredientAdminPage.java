package GUI.admin;

import GUI.elements.CustomGridPane;
import GUI.elements.CustomLabel;
import GUI.elements.CustomPage;
import controller.Inventory;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import util.Localizer;

import java.util.ArrayList;

public class IngredientAdminPage extends CustomPage {
  private Inventory inventory = Restaurant.getInstance().getInventory();

  private ArrayList<String> ingredientList = new ArrayList<>();
  private ListView<String> ingredientListView = new ListView<>();

  IngredientAdminPage() {
    update();
  }

  @Override
  public void populateTab(Tab tab, Node previous) {
    CustomGridPane grid = new CustomGridPane(25);
    grid.setHgap(25);
    grid.setVgap(25);
    grid.setPercentageColumns(10, 40, 10, 10, 10, 10, 10);

    CustomLabel ingredientListLabel = new CustomLabel("Ingredients");
    ingredientListLabel.setFontSize(20);
    ingredientListLabel.setBold();
    ingredientListLabel.center();
    grid.add(ingredientListLabel, 0, 0, 2, 1);

    CustomLabel searchBarLabel = new CustomLabel("Search Ingredients");
    TextField searchBarField = new TextField();
    searchBarField
        .textProperty()
        .addListener(
            e -> {
              ingredientList = inventory.search(searchBarField.getText());
              ingredientListView.setItems(
                  FXCollections.observableArrayList(Localizer.localize(ingredientList)));
            });
    grid.add(searchBarLabel, 0, 1);
    grid.add(searchBarField, 1, 1);

    ingredientListView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener((obs, oldSelection, newSelection) -> {});

    grid.add(ingredientListView, 0, 2, 2, 1);

    //    if (previous != null) {
    //      grid.add(getBackButton(tab, previous), 0, 4, 6, 3);
    //    }

    tab.setContent(grid);
  }

  @Override
  public void update() {
    ingredientListView.setItems(
        FXCollections.observableArrayList(Localizer.localize(inventory.getIngredientStrings())));
  }
}
