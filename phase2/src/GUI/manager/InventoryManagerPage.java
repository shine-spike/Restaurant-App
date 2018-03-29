package GUI.manager;

import GUI.elements.CustomGridPane;
import GUI.elements.CustomLabel;
import GUI.elements.CustomPage;
import GUI.elements.CustomTab;
import controller.Inventory;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import util.Localizer;

import java.util.ArrayList;

public class InventoryManagerPage extends CustomPage {
  private final Inventory inventory = Restaurant.getInstance().getInventory();

  private ArrayList<String> ingredientsList = new ArrayList<>();
  private ListView<String> ingredientsListView = new ListView<>();

  @Override
  public void populateTab(CustomTab tab) {
    CustomGridPane grid = new CustomGridPane(50);
    grid.setPercentageColumns(20, 80);
    grid.setHgap(10);
    grid.setEvenRows(24);

    CustomLabel nameLabel = new CustomLabel("Ingredient Name");
    TextField nameField = new TextField();
    nameField
        .textProperty()
        .addListener(
            ((observable, oldValue, newValue) -> {
              ingredientsList = inventory.search(newValue);
              update();
            }));
    grid.add(nameLabel, 0, 0);
    grid.add(nameField, 0, 1);

    CustomLabel listLabel = new CustomLabel("Inventory");
    listLabel.center();
    listLabel.setBold();
    listLabel.setFontSize(20);
    grid.add(listLabel, 0, 3, 2, 1);

    ingredientsList = inventory.search(nameField.getText());
    grid.add(ingredientsListView, 0, 4, 2, 16);

    if (tab.canGoBack()) {
      grid.add(getBackButton(tab), 0, 22, 2, 2);
    }

    tab.setCurrentPage(this, grid);
  }

  @Override
  public void update() {
    ArrayList<String> formattedIngredientsList =
        formatList(Localizer.localize(ingredientsList), ingredientsList);
    ingredientsListView.setItems(FXCollections.observableArrayList(formattedIngredientsList));
  }

  /**
   * Formats all the Ingredients in an ArrayList for the active ingredientsList
   *
   * @param localIngredientsList localised ingredient names.
   * @param ingredientsList original ingredient names, to search through the inventory with.
   * @return an ArrayList of formatted Strings for the active orders list
   */
  private ArrayList<String> formatList(
      ArrayList<String> localIngredientsList, ArrayList<String> ingredientsList) {
    ArrayList<String> newList = new ArrayList<>();
    for (int i = 0; i < ingredientsList.size(); i++) {
      String name = ingredientsList.get(i);
      String localName = localIngredientsList.get(i);
      Integer num = inventory.getIngredient(name).getQuantity();
      newList.add(String.format("%-10d %s", num, localName));
    }
    return newList;
  }
}
