package model;

import java.util.ArrayList;
import java.util.HashMap;

/** Represents a table in a restaurant. Stores the bill for this table. */
public class Table {
  private static final int NUM_REQUIRED_FOR_GRATUITY = 8;

  private final ArrayList<Order> currentOrders = new ArrayList<>();
  private Bill currentBill = new Bill();
  private final HashMap<Integer, Bill> customerBills = new HashMap<>();

  /** Create a table with an empty bill and no bill history. */
  public Table() {}

  /** Clears this tables bill and adds it to the bill history. */
  public void clearBill() {
    currentOrders.clear();
    customerBills.clear();
    currentBill = new Bill();
  }

  /**
   * Adds a given order number on the table to the tables bill.
   *
   * @param orderNumber the number of the order to add to the bill.
   */
  public void addToBill(int orderNumber) {
    Order order = getOrder(orderNumber);
    if (order != null) {
      currentBill.addOrder(order);

      int customerIndex = order.getCustomerIndex();
      if (customerBills.containsKey(customerIndex)) {
        customerBills.get(customerIndex).addOrder(order);
      } else {
        Bill customerBill = new Bill();
        customerBill.addOrder(order);
        customerBills.put(customerIndex, customerBill);
        checkGratuity();
      }
    }
  }

  /**
   * Adds a given order to the table.
   *
   * @param order the order to add to the table.
   */
  public void addToTable(Order order) {
    currentOrders.add(order);
  }

  /**
   * Gets this table's current orders.
   *
   * @return the list of current orders for this table.
   */
  public ArrayList<Order> getCurrentOrders() {
    return currentOrders;
  }

  /**
   * Gets this table's formatted bill string.
   *
   * @return the formatted bill string.
   */
  public String getBillString() {
    return currentBill.getBillString();
  }

  /**
   * Gets the formatted bill string of the customer with the given customer index on this table.
   *
   * @param customerIndex the index of the customer.
   * @return the string representation of the customer's bill.
   */
  public String getBillString(int customerIndex) {
    if (customerBills.containsKey(customerIndex)) {
      return customerBills.get(customerIndex).getBillString();
    }
    return "";
  }

  /**
   * Gets all the formatted bill strings of this table.
   *
   * @return a list of formatted bill strings.
   */
  public ArrayList<String> getBillStrings() {
    ArrayList<String> billStrings = new ArrayList<>();
    for (Bill bill : customerBills.values()) {
      billStrings.add(bill.getBillString());
    }
    return billStrings;
  }

  /**
   * Gets the total of this tables current bill.
   *
   * @return the total price of the tables current bill.
   */
  public int getTotalBill() {
    return currentBill.getTotal();
  }

  /**
   * Gets the order with the given order number on this table.
   *
   * @param orderNumber the number of the order.
   * @return the order with the given order number or {@code null} if no such order exists.
   */
  private Order getOrder(int orderNumber) {
    for (Order order : currentOrders) {
      if (order.getOrderNumber() == orderNumber) {
        return order;
      }
    }
    return null;
  }

  /** Checks if automatic gratuity applies to the current table, and applies it if so. */
  private void checkGratuity() {
    if (customerBills.size() >= NUM_REQUIRED_FOR_GRATUITY) {
      currentBill.setGratuity(true);
      for (Bill bill : customerBills.values()) {
        bill.setGratuity(true);
      }
    }
  }
}
