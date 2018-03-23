package GUI;

import controller.Inventory;
import event.inventory.ReceiveInventoryEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import controller.Restaurant;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import util.Localizer;

import java.util.ArrayList;

public class ReceiverTab extends RestaurantTab{
    private static final Inventory inventory = Restaurant.getInstance().getInventory();
    private Spinner<Integer> ingredientSpinner;
    private TextField ingredientNameField;
    private ListView<String> ingredientsListView;
    private ObservableList<String> displayIngredientsList;
    private ArrayList<String> ingredientsList;
    private Text warningString;

    /**
     * Constructs a ReceiverTab for the employee with the id employeeNumber
     */
    public ReceiverTab(int employeeNumber){
        super(employeeNumber);
    }

    /**
     * Initializes this ReceiverTab's JavaFX tab
     */
    public void initializeTab(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Ingredient Name
        Label ingredientName = new Label("Ingredient Name");
        ingredientNameField = new TextField();
        ingredientNameField.textProperty().addListener(new IngredientNameChangeListener());

        // Number of Ingredients
        Label ingredientNum = new Label("No.");
        ingredientSpinner = new Spinner<>(1, 99, 1);
        ingredientSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        ingredientSpinner.setEditable(true);
        ingredientSpinner.setMaxWidth(70);

        // Lists of Ingredients
        ingredientsList = inventory.search(ingredientNameField.getText());

        displayIngredientsList = FXCollections.observableArrayList();
        displayIngredientsList.setAll(Localizer.localize(ingredientsList));

        ingredientsListView = new ListView<>(displayIngredientsList);
        ingredientsListView.setMaxHeight(100);

        // Warning String
        warningString = new Text();

        // Add Ingredients Button
        Button button = new Button("Enter");
        button.setOnAction(new ReceiverTabHandler());

        grid.add(ingredientName, 0,0);
        grid.add(ingredientNameField, 0, 1);
        grid.add(ingredientNum, 1, 0);
        grid.add(ingredientSpinner, 1, 1);
        grid.add(ingredientsListView, 0, 2, 2, 1);
        grid.add(warningString, 0, 3, 3, 1);
        grid.add(button, 0, 4);

        Tab receiverTab = new Tab("Receiver", grid);
        receiverTab.setClosable(false);

        setTab(receiverTab);
    }

    /**
     * Updates all the nodes of this tab with the appropriate new information
     */
    public void update(){

    }

    private class ReceiverTabHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            warningString.setText("");
            int num = ingredientSpinner.getValue();
            int selectedIndex = ingredientsListView.getSelectionModel().getSelectedIndex();

            if(selectedIndex != -1){
                ReceiveInventoryEvent receiveEvent = new ReceiveInventoryEvent(
                        getEmployeeNumber(), ingredientsList.get(selectedIndex), num
                );

                switch(receiveEvent.process()){
                        case COMPLETED:
                            warningString.setFill(Color.BLACK);
                            warningString.setText(
                                    num + " " + displayIngredientsList.get(selectedIndex) + "(s) added to inventory"
                            );
                            ingredientSpinner.getValueFactory().setValue(1);
                            ingredientNameField.setText("");
                            break;
                        default:
                            warningString.setFill(Color.FIREBRICK);warningString.setText("Unknown Error");
                }
            } else {
                warningString.setFill(Color.FIREBRICK);
                warningString.setText("No ingredient selected");
            }
        }
    }

    private class IngredientNameChangeListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            ingredientsList = inventory.search(newValue);
            displayIngredientsList.setAll(Localizer.localize(ingredientsList));
        }
    }
}
