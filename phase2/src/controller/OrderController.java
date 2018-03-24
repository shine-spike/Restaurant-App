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
   * @return the {@link Order} that has the given order number.
   */
  public Order getOrderFromNumber(int orderNumber) {
    for (Order order : orders) {
      if (order.getOrderNumber() == orderNumber) {
        return order;
      }
    }
    return null;
  }

  public void cancelOrder(int employeeNumber, Order order) {
    order.setStatus(OrderStatus.CANCELLED);
  }

  public void registerOrder(Order order) {
    orders.add(order);
  }

  public ArrayList<Order> ordersFromStatus(OrderStatus orderStatus) {
    ArrayList<Order> out = new ArrayList<>();

    for (Order i : orders) {
      if (i.getStatus().equals(orderStatus)) {
        out.add(i);
      }
    }

    return out;
  }
}
