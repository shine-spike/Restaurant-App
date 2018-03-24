package controller;

import model.Order;
import model.OrderStatus;

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
  public boolean placeOrder(Order order) {
    if (Restaurant.getInstance().getInventory().confirmOrder(order)) {
      registerOrder(order);
      return true;
    }

    order.setStatus(OrderStatus.UNSATISFIABLE);
    return false;
  }

  /**
   * Cancels a given order.
   *
   * @param order the order to cancel.
   */
  public void cancelOrder(Order order) {
    order.setStatus(OrderStatus.CANCELLED);
  }

  /**
   * Sees a given order.
   *
   * @param order the order to see.
   */
  public void seeOrder(Order order) {
    order.setStatus(OrderStatus.SEEN);
  }

  /**
   * Ready a given order, which means that the ingredients constituting the order should be
   * consumed.
   *
   * @param order the order to ready.
   */
  public void readyOrder(Order order) {
    Restaurant.getInstance().getInventory().consumeIngredients(order);
    order.setStatus(OrderStatus.READY);
  }

  /**
   * Accept a given order, which means it will be added to the of its table.
   *
   * @param order the order to accept.
   */
  public void acceptOrder(Order order) {
    Restaurant.getInstance().getTableController().addToBill(order);
    order.setStatus(OrderStatus.ACCEPTED);
  }

  /**
   * Rejects a given order.
   *
   * @param order the order to reject.
   */
  public void rejectOrder(Order order) {
    order.setStatus(OrderStatus.REJECTED);
  }

  /**
   * Redo a given order, which means that the order will be duplicated and put back into the system.
   *
   * @param order the order to redo.
   * @return whether or not the order can be redone. This can fail for any reason placing an order
   *     can fail.
   */
  public boolean redoOrder(Order order) {
    if (placeOrder(order.duplicate())) {
      order.setStatus(OrderStatus.REDO);
      return true;
    }
    return false;
  }
}
