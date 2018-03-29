package GUI.manager;

import GUI.elements.*;
import controller.OrderController;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import util.Localizer;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderManagerPage extends CustomPage {
  private final OrderController orderController = Restaurant.getInstance().getOrderController();

  private ArrayList<Integer> orderNumbers = new ArrayList<>();
  private final ListView<String> orderListView = new ListView<>();

  OrderManagerPage() {
    update();
  }

  @Override
  public void populateTab(CustomTab tab) {
    CustomGridPane grid = new CustomGridPane(50);
    grid.setHgap(25);
    grid.setPercentageColumns(25, 25, 25, 25);
    grid.setEvenRows(24);

    CustomLabel warningText = new CustomLabel();
    warningText.setBold();
    warningText.setWarning();
    warningText.center();
    grid.add(warningText, 0, 23, 4, 1);

    CustomLabel seenOrderLabel = new CustomLabel("Pending Orders");
    seenOrderLabel.setFontSize(20);
    seenOrderLabel.setBold();
    seenOrderLabel.center();
    grid.add(seenOrderLabel, 1, 6, 2, 1);

    grid.add(orderListView, 1, 7, 2, 14);

    // Back Button
    if (tab.canGoBack()) {
      grid.add(getBackButton(tab), 0, 22, 5, 2);
    }

    tab.setCurrentPage(this, grid);
  }

  @Override
  public void update() {

    orderNumbers.addAll(orderController.getReadyOrderNumbers());
    orderNumbers = orderController.getSeenOrderNumbers();
    orderNumbers.addAll(orderController.getUnseenOrderNumbers());
    orderListView.setItems(
            FXCollections.observableArrayList(
                    activeOrdersFormat(orderController.getOrderInformation(orderNumbers))));
  }

  /**
   * Formats all the Orders in an ArrayList for the active ordersList
   *
   * @param orders The ArrayList of Orders to be formatted
   * @return an ArrayList of formatted Strings for the active orders list
   */
  private ArrayList<String> activeOrdersFormat(ArrayList<String[]> orders) {
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
}
