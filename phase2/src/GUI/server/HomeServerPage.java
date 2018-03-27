package GUI.server;

import GUI.elements.*;
import controller.OrderController;
import controller.Restaurant;
import controller.TableController;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import util.Localizer;

import java.util.ArrayList;

public class HomeServerPage extends CustomPage {
  private OrderController orderController = Restaurant.getInstance().getOrderController();
  private TableController tableController = Restaurant.getInstance().getTableController();

  private ArrayList<String[]> orderList = new ArrayList<>();
  private ListView<String> orderListView = new ListView<>();
  private ListView<String> readyOrderListView = new ListView<>();

  public HomeServerPage() {
    update();
  }

  @Override
  public void populateTab(CustomTab tab) {
    CustomGridPane grid = new CustomGridPane(50);
    grid.setHgap(25);
    grid.setPercentageColumns(25, 25, 25, 25);
    grid.setEvenRows(24);

    CustomLabel tableNumberLabel = new CustomLabel("Table Number");
    Spinner<Integer> tableNumberSpinner =
        new Spinner<>(0, tableController.getNumberTables() - 1, -1);
    tableNumberSpinner
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue != -1) {
                orderList = tableController.getTableOrderInformation(newValue);
                update();
              }
            });
    grid.add(tableNumberLabel, 0, 0);
    grid.add(tableNumberSpinner, 1, 0);

    CustomLabel customerIndexLabel = new CustomLabel("Customer Index");
    Spinner<Integer> customerIndexSpinner = new Spinner<>(-1, 25, -1);
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

    CustomLabel billListLabel = new CustomLabel("Bill");
    billListLabel.setFontSize(20);
    billListLabel.setBold();
    billListLabel.center();
    grid.add(billListLabel, 2, 2, 1, 1);

    TextArea billTextArea = new TextArea();
    grid.add(billTextArea, 2, 3, 1, 10);

    CustomLabel billMessageLabel = new CustomLabel();
    billMessageLabel.center();
    billMessageLabel.setBold();
    grid.add(billMessageLabel, 3, 21, 2, 1);

    CustomButton printTableBillButton = new CustomButton("Print Table Bill");
    printTableBillButton.maximize();
    printTableBillButton.setOnAction(e -> {});

    grid.add(printTableBillButton, 2, 15);

    CustomButton printCustomerBillButton = new CustomButton("Print Customer Bill");
    printCustomerBillButton.maximize();
    printCustomerBillButton.setOnAction(e -> {});

    grid.add(printCustomerBillButton, 2, 17);

    CustomButton paidBillButton = new CustomButton("Bill Paid");
    paidBillButton.maximize();
    paidBillButton.setOnAction(e -> {});

    grid.add(paidBillButton, 2, 19);

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
    ArrayList<Integer> readyOrderNumberList = orderController.getReadyOrderNumbers();
    Integer[] readyOrderNumberArray = new Integer[readyOrderNumberList.size()];
    readyOrderNumberList.toArray(readyOrderNumberArray);
    ArrayList<String[]> readyOrderList = orderController.getOrderInformationFromNumbers(readyOrderNumberArray);

    orderListView.setItems(FXCollections.observableArrayList(formatTableOrder(orderList)));
    readyOrderListView.setItems(
        FXCollections.observableArrayList(formatReadyOrder(readyOrderList)));

    orderListView.refresh();
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
