package GUI.server;

import GUI.elements.*;
import controller.OrderController;
import controller.Restaurant;
import controller.TableController;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import util.Localizer;

import java.util.ArrayList;

public class HomeServerPage extends CustomPage {
  private final OrderController orderController = Restaurant.getInstance().getOrderController();
  private final TableController tableController = Restaurant.getInstance().getTableController();

  private int tableNumber;
  private int customerIndex;
  private final Spinner<Integer> tableNumberSpinner;
  private final Spinner<Integer> customerIndexSpinner;

  private ArrayList<Integer> orderNumberList = new ArrayList<>();
  private ArrayList<Integer> readyOrderNumberList = new ArrayList<>();
  private final ListView<String> orderListView = new ListView<>();
  private final ListView<String> readyOrderListView = new ListView<>();

  public HomeServerPage() {
    this.tableNumber = 0;
    this.customerIndex = 0;
    tableNumberSpinner = new Spinner<>(0, tableController.getNumberTables() - 1, 0);
    customerIndexSpinner = new Spinner<>(0, 25, 0);
    update();
  }

  @Override
  public void populateTab(CustomTab tab) {
    CustomGridPane grid = new CustomGridPane(50);
    grid.setHgap(25);
    grid.setPercentageColumns(25, 25, 25, 25);
    grid.setEvenRows(24);

    CustomLabel tableNumberLabel = new CustomLabel("Table Number");
    tableNumberSpinner
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue != -1) {
                tableNumber = newValue;
                update();
              }
            });
    grid.add(tableNumberLabel, 0, 0);
    grid.add(tableNumberSpinner, 1, 0);

    CustomLabel customerIndexLabel = new CustomLabel("Customer Index");
    customerIndexSpinner
        .valueProperty()
        .addListener((observable, oldValue, newValue) -> customerIndex = newValue);
    grid.add(customerIndexLabel, 2, 0);
    grid.add(customerIndexSpinner, 3, 0);

    CustomLabel orderListLabel = new CustomLabel("Table Order List");
    orderListLabel.setFontSize(20);
    orderListLabel.setBold();
    orderListLabel.center();
    grid.add(orderListLabel, 0, 2, 2, 1);

    orderListView
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener((observable, oldValue, newValue) -> {});
    grid.add(orderListView, 0, 3, 2, 10);

    CustomLabel orderMessageLabel = new CustomLabel();
    orderMessageLabel.setWarning();
    orderMessageLabel.center();
    orderMessageLabel.setBold();
    grid.add(orderMessageLabel, 0, 23, 2, 1);

    CustomLabel reasonAreaLabel = new CustomLabel("Reason for Reorder/Rejection");
    reasonAreaLabel.center();
    reasonAreaLabel.setBold();
    grid.add(reasonAreaLabel, 1, 19);

    TextArea reasonArea = new TextArea();
    grid.add(reasonArea, 1, 20, 1, 2);

    CustomButton addOrderButton = new CustomButton("Add Order");
    addOrderButton.maximize();
    addOrderButton.setOnAction(
        e -> {
          if (readyOrderNumberList.size() == 0) {
            new OrderServerPage(tableNumber, customerIndex).populateTab(tab);
          } else {
            orderMessageLabel.setText("Deliver all orders before adding a new one.");
          }
          update();
        });
    grid.add(addOrderButton, 0, 15, 2, 1);

    CustomButton acceptOrderButton = new CustomButton("Accept Order");
    acceptOrderButton.maximize();
    acceptOrderButton.setOnAction(
        e -> {
          int selectedIndex = orderListView.getSelectionModel().getSelectedIndex();
          if (selectedIndex != -1) {
            int orderNumber = orderNumberList.get(selectedIndex);
            if (!orderController.acceptOrder(orderNumber)) {
              orderMessageLabel.setText("Order cannot be accepted.");
            }
            update();
          } else {
            orderMessageLabel.setText("Select an order to accept.");
          }
        });
    grid.add(acceptOrderButton, 0, 17);

    CustomButton cancelOrderButton = new CustomButton("Cancel Order");
    cancelOrderButton.maximize();
    cancelOrderButton.setOnAction(
        e -> {
          int selectedIndex = orderListView.getSelectionModel().getSelectedIndex();
          if (selectedIndex != -1) {
            int orderNumber = orderNumberList.get(selectedIndex);
            if (!orderController.cancelOrder(orderNumber)) {
              orderMessageLabel.setText("Order cannot be cancelled.");
            }
            update();
          } else {
            orderMessageLabel.setText("Select an order to cancel.");
          }
        });
    grid.add(cancelOrderButton, 1, 17);

    CustomButton rejectOrderButton = new CustomButton("Reject Order");
    rejectOrderButton.maximize();
    rejectOrderButton.setOnAction(
        e -> {
          int selectedIndex = orderListView.getSelectionModel().getSelectedIndex();
          if (selectedIndex != -1) {
            int orderNumber = orderNumberList.get(selectedIndex);
            if (!orderController.rejectOrder(orderNumber, reasonArea.getText())) {
              orderMessageLabel.setText("Order cannot be rejected.");
            }
            update();
          } else {
            orderMessageLabel.setWarning();
            orderMessageLabel.setText("Select an order to reject.");
          }
        });
    grid.add(rejectOrderButton, 0, 19);

    CustomButton redoOrderButton = new CustomButton("Redo Order");
    redoOrderButton.maximize();
    redoOrderButton.setOnAction(
        e -> {
          int selectedIndex = orderListView.getSelectionModel().getSelectedIndex();
          if (selectedIndex != -1) {
            int orderNumber = orderNumberList.get(selectedIndex);
            if (!orderController.redoOrder(orderNumber, reasonArea.getText())) {
              orderMessageLabel.setText("Order cannot be requested for redo.");
            }
            update();
          } else {
            orderMessageLabel.setWarning();
            orderMessageLabel.setText("Select an order to request for redo.");
          }
        });
    grid.add(redoOrderButton, 0, 21);

    CustomLabel billListLabel = new CustomLabel("Bill");
    billListLabel.setFontSize(20);
    billListLabel.setBold();
    billListLabel.center();
    grid.add(billListLabel, 2, 2, 1, 1);

    TextArea billTextArea = new TextArea();
    billTextArea.setEditable(false);
    grid.add(billTextArea, 2, 3, 1, 10);

    CustomLabel billMessageLabel = new CustomLabel();
    billMessageLabel.center();
    billMessageLabel.setBold();
    grid.add(billMessageLabel, 3, 23, 2, 1);

    CustomButton printTableBillButton = new CustomButton("Print Table Bill");
    printTableBillButton.maximize();
    printTableBillButton.setOnAction(
        e -> billTextArea.setText(tableController.getBillString(tableNumber)));
    grid.add(printTableBillButton, 2, 15);

    CustomButton printCustomerBillButton = new CustomButton("Print Customer Bill");
    printCustomerBillButton.maximize();
    printCustomerBillButton.setOnAction(
        e -> billTextArea.setText(tableController.getBillString(tableNumber, customerIndex)));
    grid.add(printCustomerBillButton, 2, 17);

    CustomButton printAllCustomerBillButton = new CustomButton("Print All Customer Bills");
    printAllCustomerBillButton.maximize();
    printAllCustomerBillButton.setOnAction(
        e -> billTextArea.setText(tableController.getBillString(tableNumber, -1)));
    grid.add(printAllCustomerBillButton, 2, 19);

    CustomButton paidBillButton = new CustomButton("Bill Paid");
    paidBillButton.maximize();
    paidBillButton.setOnAction(
        e -> {
          tableController.clearBill(tableNumber);
          billTextArea.setText("");
          update();
        });
    grid.add(paidBillButton, 2, 21);

    CustomLabel readyOrderLabel = new CustomLabel("Ready Orders");
    readyOrderLabel.setFontSize(20);
    readyOrderLabel.setBold();
    readyOrderLabel.center();
    grid.add(readyOrderLabel, 3, 2, 2, 1);

    grid.add(readyOrderListView, 3, 3, 2, 20);

    tab.setCurrentPage(this, grid);
  }

  @Override
  public void update() {
    orderNumberList = tableController.getTableOrderNumbers(tableNumber);
    orderListView.setItems(
        FXCollections.observableArrayList(
            formatTableOrder(orderController.getOrderInformation(orderNumberList))));

    readyOrderNumberList = orderController.getReadyOrderNumbers();
    readyOrderListView.setItems(
        FXCollections.observableArrayList(
            formatReadyOrder(
                orderController.getOrderInformation(readyOrderNumberList))));

    tableNumberSpinner.getValueFactory().setValue(tableNumber);
    customerIndexSpinner.getValueFactory().setValue(customerIndex);

    orderListView.refresh();
    readyOrderListView.refresh();
  }

  private String formatTableOrder(String[] order) {
    return "["
        + order[1]
        + "]"
        + " "
        + Localizer.localize(order[2])
        + " "
        + Localizer.localize(order[3]);
  }

  private ArrayList<String> formatTableOrder(ArrayList<String[]> orders) {
    ArrayList<String> orderStrings = new ArrayList<>();
    for (String[] order : orders) {
      orderStrings.add(formatTableOrder(order));
    }
    return orderStrings;
  }

  private String formatReadyOrder(String[] order) {
    return "Table "
        + order[4]
        + ": "
        + Localizer.localize(order[2])
        + " "
        + Localizer.localize(order[3]);
  }

  private ArrayList<String> formatReadyOrder(ArrayList<String[]> orders) {
    ArrayList<String> orderStrings = new ArrayList<>();
    for (String[] order : orders) {
      orderStrings.add(formatReadyOrder(order));
    }
    return orderStrings;
  }
}
