package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Ingredient;
import model.Restaurant;
import java.util.ArrayList;

public class ReceiverTab extends RestaurantTab{
    private Restaurant restaurant = Restaurant.getInstance();
    private TextField ingredientNumField;
    private TextField ingredientNameField;
    private ListView<String> ingredientsListView;
    //private ObservableList<String> ingredientsList = FXCollections.observableArrayList(restaurant.inventory.search(""));
    private ObservableList<String> ingredientsList = FXCollections.observableArrayList(new ArrayList<String>());

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
        ingredientNameField.setOnAction(new ReceiverTabHandler());

        // Number of Ingredients
        Label ingredientNum = new Label("No.");
        ingredientNumField = new TextField();
        ingredientNumField.setMaxWidth(40);

        // List of Ingredients
        ingredientsListView = new ListView<>(ingredientsList);
        ingredientsListView.setMaxHeight(100);

        // Add Ingredients Button
        Button button = new Button("Enter");
        button.setOnAction(new ReceiverTabHandler());

        grid.add(ingredientName, 0,0);
        grid.add(ingredientNameField, 0, 1);
        grid.add(ingredientNum, 1, 0);
        grid.add(ingredientNumField, 1, 1);
        grid.add(ingredientsListView, 0, 2, 2, 1);
        grid.add(button, 0, 3);

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
            if(e.getSource() instanceof Button){
                // ReceiveEvent receiveEvent = new ReceiveEvent(textField.getName);
            } else if (e.getSource() instanceof TextField){
                // ingredientsList = new ObservableList<String>(Restaurant.getInstance().inventory.search(ingredientNameField.getText()));
                System.out.println("Hi");
            }
        }
    }
}
