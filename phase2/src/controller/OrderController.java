package controller;

import model.Ingredient;
import model.Order;
import model.OrderStatus;
import util.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderController {
  // Holder for all orders are in the restaurant
  private final ArrayList<Order> orders = new ArrayList<>();

  /**
   * Gets the order with a given order number from all the orders.
   *
   * @param orderNumber the order number to search for.
   * @return the order information of the order that has the given order number or {@code null} if
   *     no such order exists.
   */
  public String[] getOrderInformationFromNumber(int orderNumber) {
    Order order = getOrderFromNumber(orderNumber);
    if (order != null) {
      return order.getOrderInformation();
    }
    return null;
  }

  /**
   * Gets the order information from the given order numbers.
   *
   * @param orderNumbers the numbers of the orders to get.
   * @return a two-dimensional array of order information.
   */
  public String[][] getOrderInformationFromNumbers(Integer... orderNumbers) {
    int numOrders = orderNumbers.length;
    String[][] orderInformation = new String[numOrders][];

    for (int i = 0; i < numOrders; i++) {
      orderInformation[i] = getOrderInformationFromNumber(orderNumbers[i]);
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
   * Gets the numbers of all orders that a cook should see.
   *
   * @return the list order numbers of all orders a cook should see.
   */
  public ArrayList<Integer> getCookOrderNumbers() {
    OrderStatus[] orderStatuses = {OrderStatus.PLACED, OrderStatus.SEEN, OrderStatus.REDO};
    ArrayList<Integer> orderNumbers = new ArrayList<>();

    for (OrderStatus orderStatus : orderStatuses) {
      for (Order order : getOrdersFromStatus(orderStatus)) {
        orderNumbers.add(order.getOrderNumber());
      }
    }

    return orderNumbers;
  }

  /**
   * Registers a given order in the controller.
   *
   * @param order the order to register.
   */
  public void registerOrder(Order order) {
    orders.add(order);
  }

  /**
   * Registers a given order to be placed and changes its status accordingly.
   *
   * @param orderNumber the number of the order to register.
   * @return whether or not the order has been successfully registered. This can fail if there are
   *     not enough ingredients in the inventory to satisfy the order.
   */
  public boolean placeOrder(int orderNumber) {
    Order order = getOrderFromNumber(orderNumber);

    if (order != null) {
      if (Restaurant.getInstance().getInventory().confirmOrder(order)) {
        Logger.orderLog(
            order.getOrderNumber(), "PLACE", "placed by table " + order.getTableNumber());
        order.setStatus(OrderStatus.PLACED);
        return true;
      }

      Logger.orderLog(
          order.getOrderNumber(),
          "UNSATISFIABLE",
          "discarded because there are not enough ingredients to create it");
      order.setStatus(OrderStatus.UNSATISFIABLE);
    }

    return false;
  }

  /**
   * Cancels a given order.
   *
   * @param orderNumber the number of the order to cancel.
   */
  public void cancelOrder(int orderNumber) {
    Order order = getOrderFromNumber(orderNumber);

    if (order != null) {
      Logger.orderLog(order.getOrderNumber(), "CANCEL", "cancelled");
      order.setStatus(OrderStatus.CANCELLED);
    }
  }

  /**
   * Sees a given order.
   *
   * @param orderNumber the number of the order to see.
   */
  public void seeOrder(int orderNumber) {
    Order order = getOrderFromNumber(orderNumber);

    if (order != null) {
      Logger.orderLog(order.getOrderNumber(), "SEE", "seen");
      order.setStatus(OrderStatus.SEEN);
    }
  }

  /**
   * Ready a given order, which means that the ingredients constituting the order should be
   * consumed.
   *
   * @param orderNumber the number of the order to ready.
   */
  public void readyOrder(int orderNumber) {
    Order order = getOrderFromNumber(orderNumber);

    if (order != null) {
      Logger.orderLog(order.getOrderNumber(), "READY", "prepared");
      Restaurant.getInstance().getInventory().consumeIngredients(order);
      order.setStatus(OrderStatus.READY);
    }
  }

  /**
   * Accept a given order, which means it will be added to the of its table.
   *
   * @param orderNumber the number of the order to accept.
   */
  public void acceptOrder(int orderNumber) {
    Order order = getOrderFromNumber(orderNumber);

    if (order != null) {
      Logger.orderLog(
          order.getOrderNumber(), "ACCEPT", "accepted by table " + order.getTableNumber());
      Restaurant.getInstance().getTableController().addToBill(order);
      order.setStatus(OrderStatus.ACCEPTED);
    }
  }

  /**
   * Rejects a given order with a given reason.
   *
   * @param orderNumber the number of the order to reject.
   * @param reason the reason of rejection.
   */
  public void rejectOrder(int orderNumber, String reason) {
    Order order = getOrderFromNumber(orderNumber);

    if (order != null) {
      Logger.orderLog(
          order.getOrderNumber(),
          "REJECT",
          "rejected by table " + order.getTableNumber() + " for reason " + reason);
      order.setStatus(OrderStatus.REJECTED);
    }
  }

  /**
   * Redo a given order with a given reason, which means that the order will be duplicated and put
   * back into the system.
   *
   * @param orderNumber the number of the order to redo.
   * @param reason the reason of redoing.
   * @return whether or not the order can be redone. This can fail for any reason placing an order
   *     can fail.
   */
  public boolean redoOrder(int orderNumber, String reason) {
    Order order = getOrderFromNumber(orderNumber);

    if (order != null) {
      Logger.orderLog(
          order.getOrderNumber(),
          "REDO",
          "requested for redo by table " + order.getTableNumber() + " for reason " + reason);

      Order duplicate = order.duplicate();
      if (placeOrder(order.getOrderNumber())) {
        Logger.orderLog(
            duplicate.getOrderNumber(),
            "REDO",
            "assigned as a redo for order " + order.getOrderNumber());
        order.setStatus(OrderStatus.REDO);
        return true;
      }
    }
    return false;
  }

  /**
   * Gets all the orders with the given status.
   *
   * @param orderStatus the status of the orders to get.
   * @return the list of all orders with the given status.
   */
  private ArrayList<Order> getOrdersFromStatus(OrderStatus orderStatus) {
    ArrayList<Order> out = new ArrayList<>();

    for (Order order : orders) {
      if (order.getStatus().equals(orderStatus)) {
        out.add(order);
      }
    }

    return out;
  }

  /**
   * Gets the order with the given order number.
   *
   * @param orderNumber the order number of the order to get.
   * @return the order with the given number or {@code null} if no such order exists.
   */
  private Order getOrderFromNumber(int orderNumber) {
    for (Order order : orders) {
      if (order.getOrderNumber() == orderNumber) {
        return order;
      }
    }
    return null;
  }
}
