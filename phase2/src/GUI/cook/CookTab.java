package GUI.cook;

import GUI.elements.CustomGridPane;
import GUI.elements.CustomTab;
import controller.OrderController;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import util.Localizer;
import util.OrderFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class CookTab extends CustomTab {
  private static OrderController orderController = Restaurant.getInstance().getOrderController();

  private ArrayList<Integer> orderNumbers;
  private ListView<String> orderListView;
  private TextArea activeOrderInfo;
  private Text warningText;
  private Button readyButton, seenButton, cancelButton;

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

    // Active Orders
    Label activeOrderLabel = new Label("Active Orders");

    orderNumbers = new ArrayList<>();
    orderListView = new ListView<>();

    // TODO Remove, this is just for testing
    orderController.registerOrder(
        OrderFactory.createOrder(1, 1, 1, "lunch", "burger", new HashMap<>()));
    orderController.placeOrder(0);
    updateTab();

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
              }
            });

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

  /** Updates all the nodes of this tab with the appropriate new information */
  public void updateTab() {
    orderNumbers = orderController.getCookOrderNumbers();
    Integer[] ordersArray = new Integer[orderNumbers.size()];
    orderNumbers.toArray(ordersArray);

    orderListView.setItems(
        FXCollections.observableArrayList(
            activeOrdersFormat(orderController.getOrderInformationFromNumbers(ordersArray))));
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
    return order[2] + " " + order[0] + " " + Localizer.localize(order[1]);
  }

  /**
   * Returns formatted Order String for selected orders list
   *
   * @param order The Order to be formatted
   * @return a formatted string for this active order
   */
  private String selectedOrdersFormat(String[] order) {
    StringBuilder out = new StringBuilder(order[0] + " " + order[2]);

    HashMap<String, Integer> ingredients =
        orderController.getOrderIngredientStrings(Integer.parseInt(order[0]));
    for (String ingredient : ingredients.keySet()) {
      out.append("\n - ");
      out.append(Localizer.localize(ingredient));
      out.append(" ");
      out.append(ingredients.get(ingredient));
    }

    return out.toString();
  }
}
