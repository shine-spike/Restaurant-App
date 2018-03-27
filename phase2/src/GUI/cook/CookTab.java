package GUI.cook;

import GUI.elements.CustomButton;
import GUI.elements.CustomGridPane;
import GUI.elements.CustomLabel;
import GUI.elements.CustomTab;
import controller.OrderController;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import util.Localizer;
import util.OrderFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class CookTab extends CustomTab {
  private static OrderController orderController = Restaurant.getInstance().getOrderController();

  private ArrayList<Integer> orderNumbers;
  private ListView<String> orderListView;
  private TextArea activeOrderInfo;
  private CustomLabel warningText;
  private CustomButton readyButton, seenButton, cancelButton;

  /** Constructs a CookTab for the employee with the id employeeNumber */
  public CookTab(int employeeNumber) {
    super("Cook", employeeNumber);
    updateTab();
  }

  /** Initializes this CookTab's JavaFX tab */
  public void populateTab() {
    CustomGridPane grid = new CustomGridPane(25);
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPercentageColumns(50, 10, 10, 10, 10, 10);
    grid.setPercentageRows(10, 70, 5, 5);

    CustomLabel ingredientListLabel = new CustomLabel("Active Orders");
    ingredientListLabel.setFontSize(20);
    ingredientListLabel.setBold();
    ingredientListLabel.center();
    grid.add(ingredientListLabel, 0, 0);

    orderNumbers = new ArrayList<>();
    orderListView = new ListView<>();

    orderListView.setStyle("-fx-font-size: 20px");
    orderListView
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              int selectedOrder = newSelection.intValue();
              if (selectedOrder != -1) {
                String[] order =
                    orderController.getOrderInformationFromNumber(orderNumbers.get(selectedOrder));
                activeOrderInfo.setText(selectedOrdersFormat(order));
              } else {
                activeOrderInfo.setText("");
              }
            });
    grid.add(orderListView, 0, 1, 1, 3);

    // TODO Remove, this is just for testing
    orderController.registerOrder(
        OrderFactory.createOrder(1, 1, 1, "lunch", "burger", new HashMap<>()));
    orderController.placeOrder(0);
    updateTab();

    CustomLabel activeOrderLabel = new CustomLabel("Current Order");
    activeOrderLabel.setFontSize(20);
    activeOrderLabel.setBold();
    activeOrderLabel.center();
    grid.add(activeOrderLabel, 1, 0, 5, 1);

    // Active Order
    activeOrderInfo = new TextArea();
    activeOrderInfo.setFont(new Font(20));
    activeOrderInfo.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    activeOrderInfo.setEditable(false);
    grid.add(activeOrderInfo, 1, 1, 5, 1);

    // Active Order grid buttons
    seenButton = new CustomButton("Seen");
    seenButton.maximize();
    seenButton.setOnAction(new CookTabButtonHandler());
    grid.add(seenButton, 1, 2);

    readyButton = new CustomButton("Ready");
    readyButton.maximize();
    readyButton.setOnAction(new CookTabButtonHandler());
    grid.add(readyButton, 2, 2, 3, 1);

    cancelButton = new CustomButton("Cancel");
    cancelButton.maximize();
    cancelButton.setOnAction(new CookTabButtonHandler());
    grid.add(cancelButton, 5, 2);

    // Warning Text
    warningText = new CustomLabel();
    warningText.setBold();
    warningText.setWarning();
    warningText.center();
    grid.add(warningText, 1, 3, 5, 1);

    getTab().setContent(grid);
  }

  /** Updates all the nodes of this tab with the appropriate new information */
  public void updateTab() {
    orderNumbers = orderController.getCookOrderNumbers();
    Integer[] ordersArray = new Integer[orderNumbers.size()];
    orderNumbers.toArray(ordersArray);

    orderListView.setItems(
        FXCollections.observableArrayList(
            activeOrdersFormat(orderController.getOrderInformationFromNumbers(ordersArray))));
  }

  /**
   * Formats all the Orders in an ArrayList for the active ordersList
   *
   * @param orders The ArrayList of Orders to be formatted
   * @return an ArrayList of formatted Strings for the active orders list
   */
  private ArrayList<String> activeOrdersFormat(String[][] orders) {
    ArrayList<String> out = new ArrayList<>();
    for (String[] order : orders) {
      out.add(activeOrdersFormat(order));
    }
    return out;
  }

  /**
   * Returns formatted Order String for active orders list
   *
   * @param order The Order to be formatted
   * @return a formatted string for this active order
   */
  private String activeOrdersFormat(String[] order) {
    return "["
        + order[0]
        + " : "
        + order[1]
        + "]"
        + " "
        + Localizer.localize(order[2])
        + " "
        + Localizer.localize(order[3]);
  }

  /**
   * Returns formatted Order String for selected orders list
   *
   * @param order The Order to be formatted
   * @return a formatted string for this active order
   */
  private String selectedOrdersFormat(String[] order) {
    StringBuilder out =
        new StringBuilder(
            "["
                + order[0]
                + "]"
                + " "
                + Localizer.localize(order[2])
                + " "
                + Localizer.localize(order[3]));

    HashMap<String, Integer> ingredients =
        orderController.getOrderIngredientStrings(Integer.parseInt(order[0]));
    for (String ingredient : ingredients.keySet()) {
      if (ingredients.get(ingredient) == 0) {
        continue;
      }
      out.append("\n - ");
      out.append(ingredients.get(ingredient));
      out.append(" ");
      out.append(Localizer.localize(ingredient));
    }

    return out.toString();
  }

  private class CookTabButtonHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
      warningText.setText("");

      int orderIndex = orderListView.getSelectionModel().getSelectedIndex();
      if (orderIndex != -1) {
        int selectedOrderNumber = orderNumbers.get(orderIndex);

        if (e.getSource() == seenButton) {
          orderController.seeOrder(selectedOrderNumber);
        } else if (true) { // TODO: implement checking for seen
          if (e.getSource() == readyButton) {
            orderController.readyOrder(selectedOrderNumber);
          } else if (e.getSource() == cancelButton) {
            orderController.cancelOrder(selectedOrderNumber);
          }
        } else {
          warningText.setText("Acknowledge all orders first");
        }
      } else {
        warningText.setText("No Order Selected");
      }

      updateTab();
    }
  }
}
