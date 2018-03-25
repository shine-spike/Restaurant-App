package controller;

import model.Order;
import model.OrderStatus;
import util.Logger;

import java.util.ArrayList;

public class OrderController {
  // Holder for all orders are in the restaurant
  private final ArrayList<Order> orders = new ArrayList<>();

  /**
   * Gets the order with a given order number from all the orders.
   *
   * @param orderNumber the order number to search for.
   * @return the order that has the given order number.
   */
  
  public Order getOrderFromNumber(int orderNumber) {
    for (Order order : orders) {
      if (order.getOrderNumber() == orderNumber) {
        return order;
      }
    }
    return null;
  }

  /**
   * Gets all the orders with the given status.
   *
   * @param orderStatus the status of the orders to get.
   * @return the list of all orders with the given status.
   */
  
  public ArrayList<Order> ordersFromStatus(OrderStatus orderStatus) {
    ArrayList<Order> orders = new ArrayList<>();

    for (Order order : orders) {
      if (order.getStatus().equals(orderStatus)) {
        orders.add(order);
      }
    }

    return orders;
  }

  /**
   * Registers a given order in the controller.
   *
   * @param order the order to register.
   */
  private void registerOrder(Order order) {
    order.setStatus(OrderStatus.PLACED);
    orders.add(order);
  }

  /**
   * Registers a given order to be placed and changes its status accordingly.
   *
   * @param order the order to register.
   * @return whether or not the order has been successfully registered. This can fail if there are
   *     not enough ingredients in the inventory to satisfy the order.
   */
  public boolean placeOrder( Order order) {
    if (Restaurant.getInstance().getInventory().confirmOrder(order)) {
      Logger.orderLog(order.getOrderNumber(), "PLACE", "placed by table " + order.getTableNumber());
      registerOrder(order);
      return true;
    }

    Logger.orderLog(
        order.getOrderNumber(),
        "UNSATISFIABLE",
        "discarded because there are not enough ingredients to create it");
    order.setStatus(OrderStatus.UNSATISFIABLE);
    return false;
  }

  /**
   * Cancels a given order.
   *
   * @param order the order to cancel.
   */
  public void cancelOrder( Order order) {
    Logger.orderLog(order.getOrderNumber(), "CANCEL", "cancelled");
    order.setStatus(OrderStatus.CANCELLED);
  }

  /**
   * Sees a given order.
   *
   * @param order the order to see.
   */
  public void seeOrder( Order order) {
    Logger.orderLog(order.getOrderNumber(), "SEE", "seen");
    order.setStatus(OrderStatus.SEEN);
  }

  /**
   * Ready a given order, which means that the ingredients constituting the order should be
   * consumed.
   *
   * @param order the order to ready.
   */
  public void readyOrder( Order order) {
    Logger.orderLog(order.getOrderNumber(), "READY", "prepared");
    Restaurant.getInstance().getInventory().consumeIngredients(order);
    order.setStatus(OrderStatus.READY);
  }

  /**
   * Accept a given order, which means it will be added to the of its table.
   *
   * @param order the order to accept.
   */
  public void acceptOrder( Order order) {
    Logger.orderLog(
        order.getOrderNumber(), "ACCEPT", "accepted by table " + order.getTableNumber());
    Restaurant.getInstance().getTableController().addToBill(order);
    order.setStatus(OrderStatus.ACCEPTED);
  }

  /**
   * Rejects a given order with a given reason.
   *
   * @param order the order to reject.
   * @param reason the reason of rejection.
   */
  public void rejectOrder( Order order, String reason) {
    Logger.orderLog(
        order.getOrderNumber(),
        "REJECT",
        "rejected by table " + order.getTableNumber() + " for reason " + reason);
    order.setStatus(OrderStatus.REJECTED);
  }

  /**
   * Redo a given order with a given reason, which means that the order will be duplicated and put
   * back into the system.
   *
   * @param order the order to redo.
   * @param reason the reason of redoing.
   * @return whether or not the order can be redone. This can fail for any reason placing an order
   *     can fail.
   */
  public boolean redoOrder( Order order, String reason) {
    Logger.orderLog(
        order.getOrderNumber(),
        "REDO",
        "requested for redo by table " + order.getTableNumber() + " for reason " + reason);

    Order duplicate = order.duplicate();
    if (placeOrder(duplicate)) {
      Logger.orderLog(
          duplicate.getOrderNumber(),
          "REDO",
          "assigned as a redo for order " + order.getOrderNumber());
      order.setStatus(OrderStatus.REDO);
      return true;
    }
    return false;
  }
}
