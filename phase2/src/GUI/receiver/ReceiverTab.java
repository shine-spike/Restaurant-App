package GUI.receiver;

import GUI.elements.CustomButton;
import GUI.elements.CustomGridPane;
import GUI.elements.CustomLabel;
import GUI.elements.CustomTab;
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

public class ReceiverTab extends CustomTab {
  private static final Inventory inventory = Restaurant.getInstance().getInventory();

  private ArrayList<String> ingredientsList;
  private ListView<String> ingredientsListView;

  /** Constructs a ReceiverTab for the employee with the id employeeNumber */
  public ReceiverTab(int employeeNumber) {
    super("Receiver", employeeNumber);
  }

  /** Initializes this ReceiverTab's JavaFX tab */
  public void populateTab() {
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
              updateTab();
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
    updateTab();

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

    getTab().setContent(grid);
  }

  /** Updates all the nodes of this tab with the appropriate new information */
  public void updateTab() {
    ingredientsListView.setItems(
        FXCollections.observableArrayList(Localizer.localize(ingredientsList)));
  }
}
