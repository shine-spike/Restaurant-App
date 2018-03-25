package GUI;

import controller.OrderController;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import model.Ingredient;
import model.Order;
import model.OrderStatus;
import util.Localizer;

import java.util.ArrayList;
import java.util.HashMap;

public class CookTab extends RestaurantTab{
    private static OrderController orderController = Restaurant.getInstance().getOrderController();
    private ArrayList<Order> orders;
    private ObservableList<String> displayOrders;
    private ListView<String> orderListView;
    private Label activeOrderInfo;

    /**
     * Constructs a CookTab for the employee with the id employeeNumber
     */
    public CookTab(int employeeNumber){
        super("Cook", employeeNumber);
    }


    /**
     * Initializes this CookTab's JavaFX tab
     */
    public void populateTab(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Active Orders
        Label activeOrderLabel = new Label("Active Orders");

        // TODO Remove, this is just for testing
        HashMap<String, Integer> ingred = new HashMap<>();
        ingred.put("burger_patty", 1);
        // PlaceOrderEvent placeOrderEvent = new PlaceOrderEvent(1, 1, 1,"lunch", "burger", ingred);
        // placeOrderEvent.process();

        orders = new ArrayList<>();
        orders.addAll(orderController.ordersFromStatus(OrderStatus.PLACED));
        orders.addAll(orderController.ordersFromStatus(OrderStatus.SEEN));
        orders.addAll(orderController.ordersFromStatus(OrderStatus.REDO));

        displayOrders = FXCollections.observableArrayList(activeOrdersFormat(orders));

        orderListView = new ListView<>(displayOrders);

        // Select Button
        Button button = new Button("Select");
        button.setOnAction(new CookTabHandler());

        // Active Order
        activeOrderInfo = new Label("");

        grid.add(activeOrderLabel, 0, 0);
        grid.add(orderListView, 0, 1, 1, 2);
        grid.add(button, 0, 3);
        grid.add(activeOrderInfo, 1, 1);

        // Tab cookTab = new Tab("Cook", grid);
        // cookTab.setClosable(false);

        getTab().setContent(grid);
    }

    /**
     * Formats all the Orders in an ArrayList for the active ordersList
     * @param orders The ArrayList of Orders to be formatted
     * @return an ArrayList of formatted Strings for the active orders list
     */
    public ArrayList<String> activeOrdersFormat(ArrayList<Order> orders) {
        ArrayList<String> out = new ArrayList<String>();

        for(Order i: orders){
            out.add(activeOrdersFormat(i));
        }

        return out;
    }

    /**
     * Returns formatted Order String for active orders list
     * @param order The Order to be formatted
     * @return a formatted string for this active order
     */
    public String activeOrdersFormat(Order order){
        return order.getOrderNumber() + " " + order.getMenuItem().getName();
    }

    /**
     * Returns formatted Order String for selected orders list
     * @param order The Order to be formatted
     * @return a formatted string for this active order
     */
    public String selectedOrdersFormat(Order order){
        StringBuilder out = new StringBuilder(order.getMenuItem().getName());

        HashMap<Ingredient, Integer> ingredients = order.getIngredients();

        for(Ingredient i: ingredients.keySet()){
            out.append("\n - " + Localizer.localize(i.getName()) + " " + ingredients.get(i));
        }

        return out.toString();
    }

    /**
     * Updates all the nodes of this tab with the appropriate new information
     */
    public void updateTab(){

    }

    private class CookTabHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            int selectedOrderIndex = orderListView.getSelectionModel().getSelectedIndex();

            if(selectedOrderIndex != -1){
                activeOrderInfo.setText(selectedOrdersFormat(orders.get(selectedOrderIndex)));
            }
        }
    }
}
