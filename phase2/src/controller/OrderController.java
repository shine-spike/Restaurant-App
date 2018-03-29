package controller;

import model.Ingredient;
import model.Order;
import model.OrderStatus;
import util.Logger;
import util.OrderFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/** Controller for interactions with orders in the restaurant. */
public class OrderController {
  private final ArrayList<Order> orders = new ArrayList<>();

  /**
   * Gets the information of the order with a given order number.
   *
   * @param orderNumber the order number to search for.
   * @return the order information of the order that has the given order number or {@code null} if
   *     no such order exists.
   */
  public String[] getOrderInformation(int orderNumber) {
    Order order = getOrderFromNumber(orderNumber);
    if (order != null) {
      return order.getOrderInformation();
    }
    return null;
  }

  /**
   * Gets the order information of the orders with the given order numbers.
   *
   * @param orderNumbers the numbers of the orders to get.
   * @return a list of order information.
   */
  public ArrayList<String[]> getOrderInformation(ArrayList<Integer> orderNumbers) {
    ArrayList<String[]> orderInformation = new ArrayList<>();
    for (int orderNumber : orderNumbers) {
      orderInformation.add(getOrderInformation(orderNumber));
    }
    return orderInformation;
  }

  /**
   * Gets the ingredients and their amount required for the order with the given order number.
   *
   * @param orderNumber the number of the order
   * @return a map of ingredient name to amount needed.
   */
  public HashMap<String, Integer> getOrderIngredientStrings(int orderNumber) {
    Order order = getOrderFromNumber(orderNumber);
    HashMap<String, Integer> ingredientStrings = new HashMap<>();

    if (order != null) {
      for (Ingredient ingredient : order.getIngredients().keySet()) {
        ingredientStrings.put(ingredient.getName(), order.getIngredients().get(ingredient));
      }
    }

    return ingredientStrings;
  }

  /**
   * Gets the numbers of all orders that have not yet been seen by a cook.
   *
   * @return the list of order numbers of all unseen orders.
   */
  public ArrayList<Integer> getUnseenOrderNumbers() {
    ArrayList<Integer> orderNumbers = new ArrayList<>();
    for (Order order : getOrdersFromStatus(OrderStatus.PLACED, OrderStatus.REDO)) {
      orderNumbers.add(order.getOrderNumber());
      System.out.println("Look");
    }
    return orderNumbers;
  }

  /**
   * Gets the numbers of all orders that have been seen by a cook.
   *
   * @return the list order numbers of all orders that have been seen.
   */
  public ArrayList<Integer> getSeenOrderNumbers() {
    ArrayList<Integer> orderNumbers = new ArrayList<>();
    for (Order order : getOrdersFromStatus(OrderStatus.SEEN)) {
      orderNumbers.add(order.getOrderNumber());
    }
    return orderNumbers;
  }

  /**
   * Gets the numbers of all ready orders.
   *
   * @return the list order numbers of all ready orders.
   */
  public ArrayList<Integer> getReadyOrderNumbers() {
    ArrayList<Integer> orderNumbers = new ArrayList<>();
    for (Order order : getOrdersFromStatus(OrderStatus.READY)) {
      orderNumbers.add(order.getOrderNumber());
    }
    return orderNumbers;
  }

  /**
   * Creates an order with the given parameters and registers it.
   *
   * @param employeeNumber the number of the employee who placed this order.
   * @param tableNumber the number of the table this order was placed for.
   * @param customerIndex the index of the customer this order is for.
   * @param menuName the name of the menu the menu item of this order is from.
   * @param menuItemName the name of the menu item for this order.
   * @param ingredientChanges the map of ingredient name to change from the default menu item.
   * @return whether or not the order can be satisfied.
   */
  public boolean createOrder(
      int employeeNumber,
      int tableNumber,
      int customerIndex,
      String menuName,
      String menuItemName,
      HashMap<String, Integer> ingredientChanges) {
    Order order =
        OrderFactory.createOrder(
            employeeNumber, tableNumber, customerIndex, menuName, menuItemName, ingredientChanges);

    return order != null && placeOrder(order);
  }

  /**
   * Cancels the order with the given order number if it can be cancelled.
   *
   * @param orderNumber the number of the order to cancel.
   * @return whether or not this order has been cancelled.
   */
  public boolean cancelOrder(int orderNumber) {
    OrderStatus[] cancelableStatuses = {
      OrderStatus.PLACED, OrderStatus.SEEN, OrderStatus.READY, OrderStatus.REDO
    };

    Order order = getOrderFromNumber(orderNumber);
    if (order != null && Arrays.asList(cancelableStatuses).contains(order.getStatus())) {
      Logger.orderLog(order.getOrderNumber(), "CANCEL", "cancelled");
      order.setStatus(OrderStatus.CANCELLED);
      return true;
    }
    return false;
  }

  /**
   * Sees the order with the given order number if it can be seen.
   *
   * @param orderNumber the number of the order to see.
   * @return whether or not this order has been seen.
   */
  public boolean seeOrder(int orderNumber) {
    OrderStatus[] seeableStatuses = {OrderStatus.PLACED, OrderStatus.REDO};
    Order order = getOrderFromNumber(orderNumber);

    if (order != null && Arrays.asList(seeableStatuses).contains(order.getStatus())) {
      Logger.orderLog(order.getOrderNumber(), "SEE", "seen");
      order.setStatus(OrderStatus.SEEN);
      return true;
    }
    return false;
  }

  /**
   * Readies the order with the given order number if it can be readied, which means that the
   * ingredients constituting the order should be consumed.
   *
   * @param orderNumber the number of the order to ready.
   * @return whether or not this order has been be readied.
   */
  public boolean readyOrder(int orderNumber) {
    OrderStatus[] readiableStatuses = {OrderStatus.PLACED, OrderStatus.REDO, OrderStatus.SEEN};
    Order order = getOrderFromNumber(orderNumber);

    if (order != null && Arrays.asList(readiableStatuses).contains(order.getStatus())) {
      Logger.orderLog(order.getOrderNumber(), "READY", "prepared");
      Restaurant.getInstance().getInventory().consumeIngredients(order);
      order.setStatus(OrderStatus.READY);
      return true;
    }
    return false;
  }

  /**
   * Accepts the order with the given order number if it can be accepted, which means it will be
   * added to the bill of its table.
   *
   * @param orderNumber the number of the order to accept.
   * @return whether or not this order has been be accepted.
   */
  public boolean acceptOrder(int orderNumber) {
    OrderStatus[] acceptableStatuses = {OrderStatus.READY};
    Order order = getOrderFromNumber(orderNumber);

    if (order != null && Arrays.asList(acceptableStatuses).contains(order.getStatus())) {
      Logger.orderLog(
          order.getOrderNumber(), "ACCEPT", "accepted by table " + order.getTableNumber());
      Restaurant.getInstance().getTableController().addToBill(order);
      order.setStatus(OrderStatus.ACCEPTED);
      return true;
    }
    return false;
  }

  /**
   * Rejects the order with the given order number with a given reason.
   *
   * @param orderNumber the number of the order to reject.
   * @param reason the reason of rejection.
   * @return whether or not this order has been be rejected.
   */
  public boolean rejectOrder(int orderNumber, String reason) {
    OrderStatus[] rejectableStatuses = {OrderStatus.READY};
    Order order = getOrderFromNumber(orderNumber);

    if (order != null && Arrays.asList(rejectableStatuses).contains(order.getStatus())) {
      Logger.orderLog(
          order.getOrderNumber(),
          "REJECT",
          "rejected by table " + order.getTableNumber() + " for reason " + reason);
      order.setStatus(OrderStatus.REJECTED);
      return true;
    }
    return false;
  }

  /**
   * Requests for redo of the order with the given order number with a given reason, which means it
   * will be sent back to the kitchen to be fixed.
   *
   * @param orderNumber the number of the order to redo.
   * @param reason the reason of redoing.
   * @return whether or not this order has been requested for redo.
   */
  public boolean redoOrder(int orderNumber, String reason) {
    OrderStatus[] redoableStatuses = {OrderStatus.READY};
    Order order = getOrderFromNumber(orderNumber);

    if (order != null && Arrays.asList(redoableStatuses).contains(order.getStatus())) {
      Logger.orderLog(
          order.getOrderNumber(),
          "REDO",
          "requested for redo by table " + order.getTableNumber() + " for reason " + reason);
      order.setStatus(OrderStatus.REDO);
      return true;
    }
    return false;
  }

  /**
   * Registers a given order in the controller.
   *
   * @param order the order to register.
   * @return whether or not this order can be satisfied.
   */
  private boolean placeOrder(Order order) {
    orders.add(order);
    if (Restaurant.getInstance().getInventory().confirmOrder(order)) {
      Logger.orderLog(order.getOrderNumber(), "PLACE", "placed by table " + order.getTableNumber());
      order.setStatus(OrderStatus.PLACED);

      Restaurant.getInstance().getTableController().addToTable(order);
      return true;
    } else {
      Logger.orderLog(
          order.getOrderNumber(),
          "UNSATISFIABLE",
          "discarded because there are not enough ingredients to create it");
      order.setStatus(OrderStatus.UNSATISFIABLE);
      return false;
    }
  }

  /**
   * Gets all the orders with the given statuses.
   *
   * @param orderStatuses the statuses of the orders to get.
   * @return the list of all orders with the given statuses.
   */
  private ArrayList<Order> getOrdersFromStatus(OrderStatus... orderStatuses) {
    ArrayList<Order> orders = new ArrayList<>();
    for (OrderStatus status : orderStatuses) {
      for (Order order : this.orders) {
        if (order.getStatus().equals(status)) {
          orders.add(order);
        }
      }
    }
    return orders;
  }

  /**
   * Gets the order with the given order number.
   *
   * @param orderNumber the order number of the order to get.
   * @return the order with the given number or {@code null} if no such order exists.
   */
  private Order getOrderFromNumber(int orderNumber) {
    if (orders.size() > orderNumber) {
      return orders.get(orderNumber);
    }
    return null;
  }
}
