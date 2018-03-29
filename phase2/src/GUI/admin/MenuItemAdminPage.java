package GUI.admin;

import GUI.elements.*;
import controller.Inventory;
import controller.MenuController;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import util.Localizer;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuItemAdminPage extends CustomPage {
  private final Inventory inventory = Restaurant.getInstance().getInventory();
  private final MenuController menuController = Restaurant.getInstance().getMenuController();
  private final String menu;
  private final String menuItem;

  private ArrayList<String> ingredientList = new ArrayList<>();
  private ArrayList<String> menuItemIngredientList = new ArrayList<>();
  private HashMap<String, Integer> menuItemIngredientAmounts = new HashMap<>();
  private final ListView<String> ingredientListView = new ListView<>();
  private final ListView<String> menuItemIngredientListView = new ListView<>();

  MenuItemAdminPage(String menu, String menuItem, int price) {
    this.menu = menu;
    this.menuItem = menuItem;

    if (menuController.getMenuItemStrings(menu).contains(menuItem)) {
      menuController.setMenuItemPrice(menu, menuItem, price);
    } else {
      menuController.addMenuItem(
              menu, menuItem, price, menuItemIngredientAmounts);
    }

    update();
  }

  @Override
  public void populateTab(CustomTab tab) {
    CustomGridPane grid = new CustomGridPane(50);
    grid.setHgap(25);
    grid.setPercentageColumns(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
    grid.setEvenRows(24);

    CustomLabel ingredientListLabel = new CustomLabel("Ingredient List");
    ingredientListLabel.setFontSize(20);
    ingredientListLabel.setBold();
    ingredientListLabel.center();
    grid.add(ingredientListLabel, 0, 0, 5, 1);

    CustomLabel addIngredientLabel = new CustomLabel("Add Ingredient");
    addIngredientLabel.setFontSize(20);
    addIngredientLabel.setBold();
    addIngredientLabel.center();
    grid.add(addIngredientLabel, 0, 13, 5, 1);

    CustomLabel newIngredientNameLabel = new CustomLabel("Ingredient Name");
    CustomLabel newIngredientNameField = new CustomLabel();
    grid.add(newIngredientNameLabel, 0, 14, 2, 1);
    grid.add(newIngredientNameField, 3, 14, 2, 1);

    CustomLabel newIngredientAmountLabel = new CustomLabel("Amount");
    TextField newIngredientAmountField = new TextField();
    grid.add(newIngredientAmountLabel, 0, 15, 2, 1);
    grid.add(newIngredientAmountField, 3, 15, 2, 1);

    CustomLabel newIngredientLabel = new CustomLabel();
    newIngredientLabel.center();
    newIngredientLabel.setBold();
    grid.add(newIngredientLabel, 0, 16, 5, 1);

    CustomButton newIngredientButton = new CustomButton("Add Ingredient");
    newIngredientButton.maximize();
    newIngredientButton.setOnAction(
        e -> {
          int selectedIndex = ingredientListView.getSelectionModel().getSelectedIndex();
          if (selectedIndex != -1 && newIngredientAmountField.getText().matches("\\d+")) {
            int newIngredientAmount = Integer.parseInt(newIngredientAmountField.getText());
            String ingredient = ingredientList.get(selectedIndex);

            menuController.modifyMenuItemIngredients(
                menu, menuItem, ingredient, newIngredientAmount);

            newIngredientLabel.setInfo();
            newIngredientLabel.setText("Ingredient has been added.");
            update();
          } else {
            newIngredientLabel.setWarning();
            newIngredientLabel.setText(
                "Please select an item and input a number in the amount field.");
          }
        });
    grid.add(newIngredientButton, 0, 18, 5, 1);

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
    grid.add(searchBarLabel, 0, 1, 2, 1);
    grid.add(searchBarField, 3, 1, 2, 1);

    ingredientListView
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              int selectionIndex = newSelection.intValue();
              if (selectionIndex != -1) {
                String ingredient = ingredientList.get(selectionIndex);

                newIngredientAmountField.setText("0");
                newIngredientNameField.setText(ingredient);
              } else {
                newIngredientAmountField.setText("");
                newIngredientNameField.setText("");
              }
            });
    grid.add(ingredientListView, 0, 2, 5, 10);

    CustomLabel modifyIngredientLabel = new CustomLabel("Modify Ingredient");
    modifyIngredientLabel.setFontSize(20);
    modifyIngredientLabel.setBold();
    modifyIngredientLabel.center();
    grid.add(modifyIngredientLabel, 5, 13, 5, 1);

    CustomLabel ingredientNameLabel = new CustomLabel("Ingredient Name");
    CustomLabel ingredientNameField = new CustomLabel();
    grid.add(ingredientNameLabel, 5, 14, 2, 1);
    grid.add(ingredientNameField, 8, 14, 2, 1);

    CustomLabel ingredientAmountLabel = new CustomLabel("Amount");
    TextField ingredientAmountField = new TextField();
    grid.add(ingredientAmountLabel, 5, 15, 2, 1);
    grid.add(ingredientAmountField, 8, 15, 2, 1);

    CustomLabel ingredientLabel = new CustomLabel();
    ingredientLabel.center();
    ingredientLabel.setBold();
    grid.add(ingredientLabel, 5, 16, 5, 1);

    CustomButton modifyIngredientButton = new CustomButton("Modify Ingredient");
    modifyIngredientButton.maximize();
    modifyIngredientButton.setOnAction(
        e -> {
          int selectedIndex = menuItemIngredientListView.getSelectionModel().getSelectedIndex();
          if (selectedIndex != -1 && ingredientAmountField.getText().matches("\\d+")) {
            int ingredientAmount = Integer.parseInt(ingredientAmountField.getText());
            String ingredient = menuItemIngredientList.get(selectedIndex);

            menuController.modifyMenuItemIngredients(menu, menuItem, ingredient, ingredientAmount);

            ingredientLabel.setInfo();
            ingredientLabel.setText("Ingredient has been modified.");
            update();
          } else {
            ingredientLabel.setWarning();
            ingredientLabel.setText(
                "Please select an item and input a number in the amount field.");
          }
        });
    grid.add(modifyIngredientButton, 5, 18, 5, 1);

    CustomButton removeIngredientButton = new CustomButton("Remove Ingredient");
    removeIngredientButton.maximize();
    removeIngredientButton.setOnAction(
        e -> {
          int selectedIndex = menuItemIngredientListView.getSelectionModel().getSelectedIndex();
          if (selectedIndex != -1) {
            String ingredient = menuItemIngredientList.get(selectedIndex);

            menuController.modifyMenuItemIngredients(menu, menuItem, ingredient, -1);

            ingredientLabel.setInfo();
            ingredientLabel.setText("Ingredient has been removed.");
            update();
          }
        });
    grid.add(removeIngredientButton, 5, 20, 5, 1);

    CustomLabel itemIngredientListLabel =
        new CustomLabel(
            Localizer.localize(menu) + " " + Localizer.localize(menuItem) + " Ingredient List");
    itemIngredientListLabel.setFontSize(20);
    itemIngredientListLabel.setBold();
    itemIngredientListLabel.center();
    grid.add(itemIngredientListLabel, 5, 0, 5, 1);

    menuItemIngredientListView
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              int selectionIndex = newSelection.intValue();
              if (selectionIndex != -1) {
                String ingredient = menuItemIngredientList.get(selectionIndex);
                int amount = menuItemIngredientAmounts.get(ingredient);

                ingredientNameField.setText(Localizer.localize(ingredient));
                ingredientAmountField.setText(Integer.toString(amount));
              } else {
                ingredientNameField.setText("");
                ingredientAmountField.setText("");
              }
            });
    grid.add(menuItemIngredientListView, 5, 2, 5, 10);

    if (tab.canGoBack()) {
      grid.add(getBackButton(tab), 0, 22, 10, 2);
    }

    tab.setCurrentPage(this, grid);
  }

  @Override
  public void update() {
    ingredientList = inventory.getIngredientStrings();
    ingredientListView.setItems(
        FXCollections.observableArrayList(Localizer.localize(ingredientList)));

    menuItemIngredientAmounts = menuController.getIngredientStrings(menu, menuItem);
    menuItemIngredientList = new ArrayList<>(menuItemIngredientAmounts.keySet());
    menuItemIngredientListView.setItems(
        FXCollections.observableArrayList(formatMenuItemIngredients(menuItemIngredientList)));

    ingredientListView.refresh();
    menuItemIngredientListView.refresh();
  }

  private String formatMenuItemIngredients(String menuItem, int amount) {
    return Integer.toString(amount) + " " + Localizer.localize(menuItem);
  }

  private ArrayList<String> formatMenuItemIngredients(ArrayList<String> menuItemIngredients) {
    ArrayList<String> menuItemIngredientStrings = new ArrayList<>();
    for (String menuItem : menuItemIngredients) {
      menuItemIngredientStrings.add(
          formatMenuItemIngredients(menuItem, menuItemIngredientAmounts.get(menuItem)));
    }
    return menuItemIngredientStrings;
  }
}
