package GUI.admin;

import GUI.elements.CustomButton;
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
    CustomGridPane grid = new CustomGridPane(50);
    grid.setHgap(25);
    grid.setPercentageColumns(10, 40, 20, 10, 20);
    grid.setEvenRows(24);

    CustomLabel employeeInformationLabel = new CustomLabel("Ingredient Information");
    employeeInformationLabel.setFontSize(20);
    employeeInformationLabel.setBold();
    employeeInformationLabel.center();
    grid.add(employeeInformationLabel, 2, 0, 3, 1);

    CustomLabel ingredientNameLabel = new CustomLabel("Ingredient Name");
    TextField ingredientNameField = new TextField();
    grid.add(ingredientNameLabel, 2, 2);
    grid.add(ingredientNameField, 4, 2);

    CustomLabel ingredientUnlocalizedNameLabel = new CustomLabel("Unlocalized Name");
    CustomLabel ingredientUnlocalizedNameField = new CustomLabel();
    grid.add(ingredientUnlocalizedNameLabel, 2, 3);
    grid.add(ingredientUnlocalizedNameField, 4, 3);

    CustomLabel thresholdLabel = new CustomLabel("Reorder Threshold");
    TextField thresholdField = new TextField();
    grid.add(thresholdLabel, 2, 4);
    grid.add(thresholdField, 4, 4);

    CustomLabel modificationLabel = new CustomLabel();
    modificationLabel.center();
    modificationLabel.setBold();
    grid.add(modificationLabel, 2, 8, 3, 1);

    CustomButton modifyButton = new CustomButton("Modify");
    modifyButton.maximize();
    modifyButton.setOnAction(
        e -> {
          modificationLabel.setText("");

          int selectedIndex = ingredientListView.getSelectionModel().getSelectedIndex();
          if (selectedIndex != -1) {
            String ingredient = ingredientList.get(selectedIndex);
            String name = ingredientNameField.getText();
            String threshold = thresholdField.getText();

            if (threshold.matches("\\d+")) {
              Localizer.register(ingredient, name);
              inventory.setIngredientThreshold(ingredient, Integer.parseInt(threshold));
              modificationLabel.setText("Ingredient has been modified.");
            } else {
              modificationLabel.setWarning();
              modificationLabel.setText("One or more of the above fields are empty or invalid.");
            }
            update();
          }
        });
    grid.add(modifyButton, 2, 6, 3, 2);

    CustomLabel newIngredientLabel = new CustomLabel("New Ingredient Registration");
    newIngredientLabel.setFontSize(20);
    newIngredientLabel.setBold();
    newIngredientLabel.center();
    grid.add(newIngredientLabel, 2, 9, 3, 1);

    CustomLabel newIngredientNameLabel = new CustomLabel("Ingredient Name");
    TextField newIngredientNameField = new TextField();
    grid.add(newIngredientNameLabel, 2, 10);
    grid.add(newIngredientNameField, 4, 10);

    CustomLabel newIngredientLocalNameLabel = new CustomLabel("Unlocalized Name");
    TextField newIngredientLocalNameField = new TextField();
    grid.add(newIngredientLocalNameLabel, 2, 11);
    grid.add(newIngredientLocalNameField, 4, 11);

    CustomLabel newThresholdLabel = new CustomLabel("Reorder Threshold");
    TextField newThresholdField = new TextField();
    grid.add(newThresholdLabel, 2, 12);
    grid.add(newThresholdField, 4, 12);

    CustomLabel additionLabel = new CustomLabel("");
    additionLabel.center();
    additionLabel.setBold();
    grid.add(additionLabel, 2, 16, 3, 1);

    CustomButton addButton = new CustomButton("Register");
    addButton.maximize();
    addButton.setOnAction(
        e -> {
          modificationLabel.setText("");

          String name = newIngredientNameField.getText();
          String localName = newIngredientLocalNameField.getText();
          String threshold = newThresholdField.getText();

          if (name.length() > 0 && localName.matches("[a-z][a-z_]*") && threshold.matches("\\d+")) {
            inventory.addIngredient(name, 0, Integer.parseInt(threshold));
            Localizer.register(localName, name);

            additionLabel.setInfo();
            additionLabel.setText("New Ingredient has been registered.");

            newIngredientNameField.setText("");
            newIngredientLocalNameField.setText("");
            newThresholdField.setText("");
          } else {
            additionLabel.setWarning();
            additionLabel.setText("One or more of the above fields are empty or invalid.");
          }

          update();
        });
    grid.add(addButton, 2, 14, 3, 2);

    CustomLabel ingredientListLabel = new CustomLabel("Ingredient List");
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
        .selectedIndexProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              int selectionIndex = newSelection.intValue();
              if (selectionIndex != -1) {
                String ingredient = ingredientList.get(selectionIndex);

                ingredientNameField.setText(Localizer.localize(ingredient));
                ingredientUnlocalizedNameField.setText(ingredient);
                thresholdField.setText(Integer.toString(inventory.getIngredientThreshold(ingredient)));
              } else {
                ingredientNameField.setText("");
                ingredientUnlocalizedNameField.setText("");
                thresholdField.setText("");
              }
            });
    grid.add(ingredientListView, 0, 2, 2, 19);

    if (previous != null) {
      grid.add(getBackButton(tab, previous), 0, 22, 5, 2);
    }

    tab.setContent(grid);
  }

  @Override
  public void update() {
    ingredientList = inventory.getIngredientStrings();
    ingredientListView.setItems(
        FXCollections.observableArrayList(Localizer.localize(ingredientList)));
  }
}
