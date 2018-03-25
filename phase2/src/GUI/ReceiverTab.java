package GUI;

import controller.Inventory;
import controller.Restaurant;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import util.Localizer;

import java.util.ArrayList;

public class ReceiverTab extends RestaurantTab {
  private static final Inventory inventory = Restaurant.getInstance().getInventory();
  private Spinner<Integer> amountSpinner;
  private TextField nameField;
  private ListView<String> ingredientsListView;
  private ObservableList<String> displayIngredientsList;
  private ArrayList<String> ingredientsList;
  private Label warningString;

  /** Constructs a ReceiverTab for the employee with the id employeeNumber */
  ReceiverTab(int employeeNumber) {
    super("Receiver", employeeNumber);
  }

  /** Initializes this ReceiverTab's JavaFX tab */
  public void populateTab() {
    GridPane grid = new GridPane();
    ColumnConstraints columnOne = new ColumnConstraints();
    columnOne.setPercentWidth(75);
    ColumnConstraints columnTwo = new ColumnConstraints();
    columnTwo.setPercentWidth(25);
    grid.getColumnConstraints().addAll(columnOne, columnTwo);

    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25));

    // Ingredient Name
    Label nameLabel = new Label("Ingredient Name");
    nameField = new TextField();
    nameField.textProperty().addListener(new IngredientNameChangeListener());

    // Number of Ingredients
    Label amountLabel = new Label("Amount");
    amountSpinner = new Spinner<>(1, 1000, 1);
    amountSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
    amountSpinner.setEditable(true);
    amountSpinner.setMaxWidth(Double.MAX_VALUE);

    // Lists of Ingredients
    ingredientsList = inventory.search(nameField.getText());
    displayIngredientsList = FXCollections.observableArrayList(Localizer.localize(ingredientsList));
    ingredientsListView = new ListView<>(displayIngredientsList);
    ingredientsListView.setMaxHeight(Double.MAX_VALUE);

    // Warning String
    warningString = new Label();
    warningString.setMaxWidth(Double.MAX_VALUE);
    warningString.setTextFill(Color.FIREBRICK);
    warningString.setAlignment(Pos.CENTER);

    // Add Ingredients Button
    Button button = new Button("Enter");
    button.setMaxWidth(Double.MAX_VALUE);
    button.setOnAction(e -> receiveButtonPressed());

    grid.add(nameLabel, 0, 0);
    grid.add(nameField, 0, 1);
    grid.add(amountLabel, 1, 0);
    grid.add(amountSpinner, 1, 1);
    grid.add(ingredientsListView, 0, 2, 2, 1);
    grid.add(button, 0, 4, 2, 1);
    grid.add(warningString, 0, 5, 2, 1);

    getTab().setContent(grid);
  }

  /** Updates all the nodes of this tab with the appropriate new information */
  public void updateTab() {}

  private void receiveButtonPressed() {
    warningString.setText("");
    int num = amountSpinner.getValue();
    int selectedIndex = ingredientsListView.getSelectionModel().getSelectedIndex();

    if (selectedIndex != -1) {
      boolean hasReceived = inventory.restockIngredient(ingredientsList.get(selectedIndex), num);

      if (hasReceived) {
        warningString.setStyle("-fx-font-weight: normal");
        warningString.setTextFill(Color.BLACK);
        warningString.setText(
                num + " " + displayIngredientsList.get(selectedIndex) + "(s) added to inventory");
        amountSpinner.getValueFactory().setValue(1);
        nameField.setText("");
      } else {
        warningString.setStyle("-fx-font-weight: bold");
        warningString.setTextFill(Color.FIREBRICK);
        warningString.setText("Unknown Error");
      }
    } else {
      warningString.setStyle("-fx-font-weight: bold");
      warningString.setTextFill(Color.FIREBRICK);
      warningString.setText("No ingredient selected");
    }
  }

  private class IngredientNameChangeListener implements ChangeListener<String> {
    @Override
    public void changed(
        ObservableValue<? extends String> observable, String oldValue, @NotNull String newValue) {
      ingredientsList = inventory.search(newValue);
      displayIngredientsList.setAll(Localizer.localize(ingredientsList));
    }
  }
}
