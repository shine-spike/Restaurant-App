package GUI.receiver;

import GUI.elements.*;
import controller.Inventory;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import util.Localizer;

import java.util.ArrayList;

public class HomeReceiverPage extends CustomPage {
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

    // Number of Ingredients
    Label amountLabel = new Label("Amount");
    Spinner<Integer> amountSpinner = new Spinner<>(1, 1000, 1);
    amountSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
    amountSpinner.setEditable(true);
    amountSpinner.setMaxWidth(Double.MAX_VALUE);

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

    // Add Ingredients Button
    CustomButton button = new CustomButton("Enter");
    button.maximize();
    button.setOnAction(
        e -> {
          warningString.setText("");
          int amount = amountSpinner.getValue();
          int ingredientIndex = ingredientsListView.getSelectionModel().getSelectedIndex();

          if (ingredientIndex != -1) {
            String ingredient = ingredientsList.get(ingredientIndex);
            inventory.restockIngredient(ingredient, amount);

            warningString.setInfo();
            warningString.setText(
                amount + " " + Localizer.localize(ingredient) + " added to inventory");
            amountSpinner.getValueFactory().setValue(1);
            nameField.setText("");
          } else {
            warningString.setWarning();
            warningString.setText("No ingredient selected");
          }
        });

    grid.add(nameLabel, 1, 0);
    grid.add(nameField, 1, 1);
    grid.add(amountLabel, 2, 0);
    grid.add(amountSpinner, 2, 1);
    grid.add(ingredientsListView, 1, 2, 2, 1);
    grid.add(button, 1, 4, 2, 1);
    grid.add(warningString, 1, 9, 2, 1);

    tab.setCurrentPage(this, grid);
  }

  @Override
  public void update() {
    ingredientsListView.setItems(
        FXCollections.observableArrayList(Localizer.localize(ingredientsList)));
  }
}
