package GUI.server;

import GUI.elements.CustomTab;
import controller.MenuController;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class ServerTab extends CustomTab {
  private Restaurant restaurant = Restaurant.getInstance();
  
  private MenuController menuController = restaurant.getMenuController();

  private TextField tableNumField;
  private int tableID;
  private ObservableList<String> ordersList;
  private ObservableList<String> menuList;
  private Label selectedTableNum;
  private Button tableButton;
  private Button billButton;
  private Button addOrderButton;
  private Button modifyOrderButton;
  private Button submitOrderButton;
  private Button returnFromOrderButton;
  private Button returnFromBillButton;
  private GridPane mainGrid;
  private GridPane addOrderGrid;
  private GridPane makeBillGrid;
  private Tab serverTab;
  private TextField menuItemField;
  private Spinner<Integer> customerIDSpinner;

  /** Constructs a ServerTab for the employee with the id employeeNumber */
  public ServerTab(int employeeNumber) {
    super("Server", employeeNumber);
  }

  /** Initializes this ServerTab's JavaFX tab */
  public void populateTab() {
    mainGrid = new GridPane();

    initializeMainGrid();
    initializeAddOrderGrid();
    initializeMakeBillGrid();

    getTab().setContent(mainGrid);
  }

  /** Initialized this ServerTab's Main Grid */
  private void initializeMainGrid() {
    mainGrid = new GridPane();
    mainGrid.setAlignment(Pos.CENTER);
    mainGrid.setHgap(10);
    mainGrid.setVgap(10);
    mainGrid.setPadding(new Insets(25, 25, 25, 25));

    // Table Number
    Label tableNum = new Label("Table Number:");
    tableNumField = new TextField();
    tableNumField.setOnAction(new ServerTabHandler());

    // Select Table Button
    tableButton = new Button("Select Table");
    tableButton.setOnAction(new ServerTabHandler());

    // Selected Table Number
    selectedTableNum = new Label("Table not selected");

    // Order List
    Label orderList = new Label("Order List:");
    ordersList = FXCollections.observableArrayList();
    ListView<String> ordersListView = new ListView<>(ordersList);

    // Order Status List
    Label orderStatusList = new Label("Order Status:");
    ObservableList<String> ordersStatusList = FXCollections.observableArrayList();
    ListView<String> ordersStatusListView = new ListView<>(ordersStatusList);

    // Add Order Button
    addOrderButton = new Button("Add Order");
    addOrderButton.setOnAction(new ServerTabHandler());

    // Generate Bill Button
    billButton = new Button("Create Bill");
    billButton.setOnAction(new ServerTabHandler());

    // Grid Additions
    mainGrid.add(tableNum, 0, 0);
    mainGrid.add(tableNumField, 0, 1);
    mainGrid.add(tableButton, 0, 2);
    mainGrid.add(selectedTableNum, 1, 1, 2, 1);
    mainGrid.add(orderList, 1, 2);
    mainGrid.add(ordersListView, 1, 3);
    mainGrid.add(orderStatusList, 2, 2);
    mainGrid.add(ordersStatusListView, 2, 3);
    mainGrid.add(addOrderButton, 1, 4);
    mainGrid.add(billButton, 2, 4);
  }

  private void initializeAddOrderGrid() {
    TabPane addOrderPanes = new TabPane();
    for (String menu : Restaurant.getInstance().getMenuController().getMenuStrings()) {
      addOrderPanes.getTabs().add(new Tab(menu));
    }
    addOrderPanes.getTabs().add(new Tab());

    addOrderGrid = new GridPane();
    addOrderGrid.setAlignment(Pos.CENTER);
    addOrderGrid.setHgap(10);
    addOrderGrid.setVgap(10);
    addOrderGrid.setPadding(new Insets(25, 25, 25, 25));

    // Menu List
    Label menuListLabel = new Label("Menu List:");
    menuList = FXCollections.observableArrayList();
    ListView<String> menuListView = new ListView<>(menuList);
    Label menuSearchLabel = new Label("Search Item:");
    menuItemField = new TextField();
    menuItemField.setOnAction(new ServerTabHandler());

    // Customer ID
    Label customerIDLabel = new Label("Customer Number");
    customerIDSpinner = new Spinner<>(0, 9, 0);
    customerIDSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
    customerIDSpinner.setEditable(true);
    customerIDSpinner.setMaxWidth(70);

    // Modify Order Button
    modifyOrderButton = new Button("Modify Order");
    modifyOrderButton.setOnAction(new ServerTabHandler());

    // Submit Order Button
    submitOrderButton = new Button("Submit Order");
    submitOrderButton.setOnAction(new ServerTabHandler());

    // Return to Main Button
    returnFromOrderButton = new Button("Back");
    returnFromOrderButton.setOnAction(new ServerTabHandler());

    // Grid Additions
    addOrderGrid.add(menuListLabel, 0, 0);
    addOrderGrid.add(menuListView, 0, 2, 2, 7);
    addOrderGrid.add(menuSearchLabel, 0, 1);
    addOrderGrid.add(menuItemField, 0, 2, 2, 1);
    addOrderGrid.add(customerIDLabel, 2, 3);
    addOrderGrid.add(customerIDSpinner, 2, 4);
    addOrderGrid.add(returnFromOrderButton, 2, 5);
    addOrderGrid.add(modifyOrderButton, 2, 6);
    addOrderGrid.add(submitOrderButton, 2, 7);
  }

  private void initializeMakeBillGrid() {
    makeBillGrid = new GridPane();
    makeBillGrid.setAlignment(Pos.CENTER);
    makeBillGrid.setHgap(10);
    makeBillGrid.setVgap(10);
    makeBillGrid.setPadding(new Insets(25, 25, 25, 25));

    // Return to Main Button
    returnFromBillButton = new Button("Back");
    returnFromBillButton.setOnAction(new ServerTabHandler());

    // Grid Additions
    makeBillGrid.add(returnFromBillButton, 0, 0);
  }

  /** Updates all the nodes of this tab with the appropriate new information */
  public void updateTab() {}

  private class ServerTabHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
      if (e.getSource() instanceof Button) {
        if (e.getSource().equals(tableButton)) {
          if (tableNumField.getText().matches("\\d+")) {
            tableID = parseInt(tableNumField.getText());
            selectedTableNum.setText("Table " + tableID + ":");
            ordersList.setAll(
                orderListFormat(restaurant.getTableController().getOrderStrings(tableID)));
          }
          tableNumField.setText("");
        } else if (e.getSource().equals(billButton)) {
          getTab().setContent(makeBillGrid);
        } else if (e.getSource().equals(addOrderButton)) {
          getTab().setContent(addOrderGrid);
        } else if (e.getSource().equals(returnFromBillButton)) {
          getTab().setContent(mainGrid);
        } else if (e.getSource().equals(returnFromOrderButton)) {
          getTab().setContent(mainGrid);
        }
      }
    }

    // Populates the orders list
    // table.getCurrentBill().getOrders()
  }

  private ArrayList<String> orderListFormat(ArrayList<String[]> orders) {
    ArrayList<String> orderStrings = new ArrayList<>();
    for (String[] order : orders) {
      orderStrings.add(orderListFormat(order));
    }
    return orderStrings;
  }

  private String orderListFormat(String[] order) {
    return order[0] + order[1] + order[2] + order[3];
  }
}
