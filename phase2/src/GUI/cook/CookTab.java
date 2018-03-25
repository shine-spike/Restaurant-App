package GUI.cook;

import GUI.elements.CustomTab;
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
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Ingredient;
import model.Order;
import model.OrderStatus;
import util.Localizer;
import util.OrderFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class CookTab extends CustomTab{
    private static OrderController orderController = Restaurant.getInstance().getOrderController();
    private ArrayList<Order> orders;
    private ObservableList<String> displayOrders;
    private ListView<String> orderListView;
    private TextArea activeOrderInfo;
    private Text warningText;
    private Button readyButton, seenButton, cancelButton;
    private int selectedOrderIndex = -1;

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
        orderController.placeOrder(OrderFactory.createOrder(1, 1, 1,"lunch", "burger", new HashMap<String, Integer>()));
        System.out.println(orderController.getOrderFromNumber(0).getStatus());

        orders = new ArrayList<>();
        orders.addAll(orderController.ordersFromStatus(OrderStatus.PLACED));
        orders.addAll(orderController.ordersFromStatus(OrderStatus.SEEN));
        orders.addAll(orderController.ordersFromStatus(OrderStatus.REDO));

        displayOrders = FXCollections.observableArrayList(activeOrdersFormat(orders));

        orderListView = new ListView<>(displayOrders);
        orderListView.setOnMouseClicked(new CookTabClickHandler());

        // Active Order grid
        GridPane activeOrderGrid = new GridPane();
        activeOrderGrid.setAlignment(Pos.CENTER);
        activeOrderGrid.setHgap(10);
        activeOrderGrid.setVgap(10);
        activeOrderGrid.setPadding(new Insets(25, 25, 25, 25));

        // Active Order grid buttons
        seenButton = new Button("Seen");
        seenButton.setOnAction(new CookTabButtonHandler());

        readyButton = new Button("Ready");
        readyButton.setOnAction(new CookTabButtonHandler());

        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new CookTabButtonHandler());

        // Active Order
        activeOrderInfo = new TextArea();
        activeOrderInfo.setEditable(false);
        activeOrderInfo.setMaxWidth(200);

        // Warning Text
        warningText = new Text();
        warningText.setFill(Color.FIREBRICK);

        activeOrderGrid.add(activeOrderInfo, 0, 0, 3, 1);
        activeOrderGrid.add(seenButton, 0, 1);
        activeOrderGrid.add(readyButton, 1, 1);
        activeOrderGrid.add(cancelButton, 2, 1);
        activeOrderGrid.add(warningText, 0, 3, 3, 1);

        grid.add(activeOrderLabel, 0, 0);
        grid.add(orderListView, 0, 1, 1, 2);
        grid.add(activeOrderGrid, 1, 1, 1, 2);

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
        StringBuilder out = new StringBuilder();

        if(order.getStatus().equals(OrderStatus.PLACED)) {
            out.append("NEW ");
        } else if(order.getStatus().equals(OrderStatus.REDO)){
            out.append("REDO ");
        }

        out.append(order.getOrderNumber());
        out.append(" ");
        out.append(order.getMenuItem().getName());

        return out.toString();
    }

    /**
     * Returns formatted Order String for selected orders list
     * @param order The Order to be formatted
     * @return a formatted string for this active order*/
    public String selectedOrdersFormat(Order order){
        StringBuilder out = new StringBuilder(order.getOrderNumber() + " " + order.getMenuItem().getName());

        HashMap<Ingredient, Integer> ingredients = order.getIngredients();

        for(Ingredient i: ingredients.keySet()){
            out.append("\n - ");
            out.append(Localizer.localize(i.getName()));
            out.append(" ");
            out.append(ingredients.get(i));
        }

        return out.toString();
    }

    /**
     * Returns whether all orders have been seen
     * @return True iff all orders have been seen
     */
    private boolean allOrdersSeen(){
        for(Order i: orders){
            if(!i.getStatus().equals(OrderStatus.SEEN))
                return false;
        }
        return true;
    }

    /**
     * Updates all the nodes of this tab with the appropriate new information
     */
    public void updateTab(){
        orders = new ArrayList<>();
        orders.addAll(orderController.ordersFromStatus(OrderStatus.PLACED));
        orders.addAll(orderController.ordersFromStatus(OrderStatus.SEEN));
        orders.addAll(orderController.ordersFromStatus(OrderStatus.REDO));

        displayOrders.setAll(activeOrdersFormat(orders));

    }

    private class CookTabButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            warningText.setText("");

            if(selectedOrderIndex != -1) {
                Order selectedOrder = orders.get(selectedOrderIndex);

                if (e.getSource() == seenButton) {
                    orderController.seeOrder(selectedOrder);
                } else if (allOrdersSeen()) {
                    if (e.getSource() == readyButton) {
                        orderController.readyOrder(selectedOrder);
                    } else if (e.getSource() == cancelButton) {
                        orderController.cancelOrder(selectedOrder);
                    }
                }
                else {
                    warningText.setText("Acknowledge all orders first");
                }
            } else {
                warningText.setText("No Order Selected");
            }

            updateTab();
        }
    }

    private  class CookTabClickHandler implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent e) {
            selectedOrderIndex = orderListView.getSelectionModel().getSelectedIndex();

            if(selectedOrderIndex != -1) {
                activeOrderInfo.setText(selectedOrdersFormat(orders.get(selectedOrderIndex)));
            }
        }
    }
}
