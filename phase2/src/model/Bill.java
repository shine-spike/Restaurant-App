package model;

import util.Localizer;

import java.util.ArrayList;

/** Bill for a customer or table. */
public class Bill {
  private static final double TAX_PERCENT = 0.13;
  private static final double GRATUITY_PERCENT = 0.15;

  private final ArrayList<Order> orders = new ArrayList<>();
  private int runningBasePrice = 0;
  private boolean hasGratuity = false;

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

  public void setGratuity(boolean hasGratuity) {
    this.hasGratuity = hasGratuity;
  }

  /**
   * Returns a formatted string representation of the bill in the following format.
   *
   * @return the formatted bill string.
   */
  public String getBillString() {
    StringBuilder str = new StringBuilder();

    str.append("=== BILL ===\n");
    for (Order order : orders) {
      str.append(String.format("$%.2f", order.getPrice() / 100.0))
          .append(" ")
          .append(Localizer.localize(order.getMenuItem().getName()))
          .append("\n");
    }
    str.append("\n");

    str.append("TAX:      ").append(TAX_PERCENT * 100).append("%").append("\n");
    if (hasGratuity) {
      str.append("GRATUITY: ").append(GRATUITY_PERCENT * 100).append("%").append("\n");
    }
    str.append("TOTAL:    ").append(String.format("$%.2f", getTotal() / 100.0)).append("\n");
    return str.toString();
  }

  int getTotal() {
    int price = runningBasePrice;
    price *= 1 + TAX_PERCENT;
    if (hasGratuity) {
      price *= 1 + GRATUITY_PERCENT;
    }

    return price;
  }
}
