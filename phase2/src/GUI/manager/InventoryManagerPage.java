package GUI.manager;

import GUI.elements.*;
import controller.Inventory;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import util.Localizer;

import java.util.ArrayList;

public class InventoryManagerPage extends CustomPage {
  private final Inventory inventory = Restaurant.getInstance().getInventory();

  private ArrayList<String> ingredientsList;
  private ListView<String> ingredientsListView;

  @Override
  public void populateTab(CustomTab tab) {
    CustomGridPane grid = new CustomGridPane(25);
    grid.setPercentageColumns(15, 60, 10, 15);
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);

    // Ingredient Name
    CustomLabel nameLabel = new CustomLabel("Ingredient Name");
    TextField nameField = new TextField();
    nameField
            .textProperty()
            .addListener(
                    ((observable, oldValue, newValue) -> {
                      ingredientsList = inventory.search(newValue);
                      update();
                    }));

    // Lists of Ingredients
    ingredientsList = new ArrayList<>();
    ingredientsListView = new ListView<>();
    ingredientsList = inventory.search(nameField.getText());
    ingredientsListView.setMaxHeight(Double.MAX_VALUE);
    update();

    // Warning String
    CustomLabel warningString = new CustomLabel();
    warningString.setBold();
    warningString.center();

    // Back Button
    if (tab.canGoBack()) {
      grid.add(getBackButton(tab), 0, 22, 5, 2);
    }


    grid.add(nameLabel, 1, 0);
    grid.add(nameField, 1, 1);
    grid.add(ingredientsListView, 1, 2, 2, 1);
    grid.add(warningString, 1, 9, 2, 1);

    tab.setCurrentPage(this, grid);
  }

  @Override
  public void update() {
    ArrayList<String> formattedIngredientsList = formatList(Localizer.localize(ingredientsList), ingredientsList);
    ingredientsListView.setItems(
            FXCollections.observableArrayList(formattedIngredientsList));
  }


  /**
   * Formats all the Ingredients in an ArrayList for the active ingredientsList
   *
   * @param localIngredientsList localised ingredient names.
   * @param ingredientsList original ingredient names, to search through the inventory with.
   * @return an ArrayList of formatted Strings for the active orders list
   */
  private ArrayList<String> formatList(ArrayList<String> localIngredientsList,
                                 ArrayList<String> ingredientsList){
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
