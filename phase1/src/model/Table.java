package model;

import java.util.ArrayList;

/**
 * Represents a table in a restaurant. Stores the bill for this table.
 */
class Table {
  private Bill currentBill = new Bill();
  private final ArrayList<Bill> billHistory = new ArrayList<>();


  /**
   * Create a table with an empty bill and no bill history.
   */
  Table() {

  }

  /**
   * Clears this tables bill and adds it to the bill history.
   */
  public void clearBill() {
    billHistory.add(currentBill);
    currentBill = new Bill();
  }

  /**
   * Adds a given order to the tables bill.
   *
   * @param order the order to add to the bill.
   */
  public void addToBill(Order order) {
    currentBill.addOrder(order);
  }

  /**
   * Returns this table's formatted bill.
   *
   * @return the formatted bill string.
   */
  public String printBill() {
    return currentBill.toString();
  }
}
