package model;

import java.util.ArrayList;

/** Bill for a customer or table. */
public class Bill {
  private static final double TAX_PERCENT = 0.13;
  private static final double GRATUITY_PERCENT = 0.15;
  private static final int NUM_REQUIRED_FOR_GRATUITY = 8;

  private final ArrayList<Order> orders = new ArrayList<>();
  private int runningBasePrice = 0;

  /**
   * Adds the given order to the bill.
   *
   * @param order the order to add to the bill.
   */
  public void addOrder(Order order) {
    orders.add(order);
    runningBasePrice += order.getPrice();
  }

  /**
   * Returns the bill's ArrayList of orders.
   *
   * @return the current ArrayList of orders.
   */
  public ArrayList<Order> getOrders() {
    return orders;
  }

  /**
   * Returns a formatted string representation of the bill in the following format.
   *
   * @return the formatted bill string.
   */
  public String getBillString() {
    StringBuilder str = new StringBuilder();

    str.append("\n=== BILL ===\n");
    for (Order order : orders) {
      str.append(order.toString()).append("\n\n");
    }

    str.append("TOTAL: ").append(runningBasePrice);
    return str.toString();
  }
}
