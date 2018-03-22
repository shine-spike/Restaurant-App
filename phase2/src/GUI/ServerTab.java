package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import controller.Restaurant;
import model.Order;
import model.Table;

import static java.lang.Integer.parseInt;

public class ServerTab extends RestaurantTab{

    private Restaurant restaurant = Restaurant.getInstance();
    private TextField tableNumField;
    private int tableID;
    private Button tableButton;
    private Boolean tableSelected = false;
    private ObservableList<Order> ordersList;
    private Label selectedTableNum;
    private Button billButton;
    private Button orderButton;

    /**
     * Initializes this ServerTab's JavaFX tab
     */
    public void initializeTab(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Table Number
        Label tableNum = new Label("Table Number:");
        tableNumField = new TextField();
        tableNumField.setOnAction(new ServerTabHandler());

        // Select Table Button
        tableButton = new Button("Select Table");
        tableButton.setOnAction(new ServerTabHandler());

        // Selected Table Number
        selectedTableNum = new Label("Table not selected");

        //Order List
        Label orderList = new Label("Order List:");
        ordersList = FXCollections.observableArrayList();
        ListView<Order> ordersListView = new ListView<>(ordersList);

        //Order Status List
        Label orderStatusList = new Label("Order Status:");
        ObservableList<Order> ordersStatusList = FXCollections.observableArrayList();
        ListView<Order> ordersStatusListView = new ListView<>(ordersStatusList);

        //Add Order Button
        orderButton = new Button("Add Order");
        orderButton.setOnAction(new ServerTabHandler());

        //Generate Bill Button
        billButton = new Button("Create Bill");
        billButton.setOnAction(new ServerTabHandler());

        // Grid Additions
        grid.add(tableNum, 0,0);
        grid.add(tableNumField, 0, 1);
        grid.add(tableButton, 0, 2);
        grid.add(selectedTableNum, 2, 1);
        grid.add(orderList, 1,2);
        grid.add(ordersListView, 1, 3);
        grid.add(orderStatusList, 3,2);
        grid.add(ordersStatusListView, 3, 3);
        grid.add(orderButton, 1,4);
        grid.add(billButton, 3, 4);


        Tab serverTab = new Tab("Server", grid);
        serverTab.setClosable(false);

        setTab(serverTab);
    }

    /**
     * Updates all the nodes of this tab with the appropriate new information
     */
    public void update(){

    }

    private class ServerTabHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if (e.getSource() instanceof Button){

                if(e.getSource().equals(tableButton)){
                    if(tableNumField.getText().matches("\\d+")){
                        tableID = parseInt(tableNumField.getText());
                        selectedTableNum.setText("Table " + tableID + ":");
                        ordersList.setAll(restaurant.getTableController().getTable(tableID).getCurrentBill().getOrders());
                    }
                    tableNumField.setText("");
                }

                else if(e.getSource().equals(billButton)){
                    //new Scene();
                }

                else if(e.getSource().equals(orderButton)){
                    //new Scene();
                }
            }
        }

        // Populates the orders list
        // table.getCurrentBill().getOrders()
    }


}
