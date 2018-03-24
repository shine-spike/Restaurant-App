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
   * Registers a given order in the controller.
   *
   * @param order the order to register.
   */
  public void registerOrder(Order order) {
    order.setStatus(OrderStatus.PLACED);
    orders.add(order);
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
}
