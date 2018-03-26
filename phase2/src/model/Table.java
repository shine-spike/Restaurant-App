package model;

import java.util.ArrayList;

/** Represents a table in a restaurant. Stores the bill for this table. */
public class Table {

  private final ArrayList<Bill> billHistory = new ArrayList<>();
  private Bill currentBill = new Bill();
  private ArrayList<Bill> customerBills = new ArrayList<>();

  /** Create a table with an empty bill and no bill history. */
  public Table() {}

  /** Clears this tables bill and adds it to the bill history. */
  public void clearBill() {
    billHistory.add(currentBill);

    customerBills = new ArrayList<>();
    currentBill = new Bill();
  }

  /**
   * Adds a given order to the tables bill.
   *
   * @param order the order to add to the bill.
   */
  public void addToBill(Order order) {
    currentBill.addOrder(order);

    if (customerBills.size() > order.getCustomerIndex()) {
      customerBills.get(order.getCustomerIndex()).addOrder(order);
    }
    Bill customerBill = new Bill();
    customerBill.addOrder(order);
    customerBills.add(customerBill);
  }

  /**
   * Gets this table's bill.
   *
   * @return the total bill of this table.
   */
  public Bill getCurrentBill() {
    return currentBill;
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
    return customerBills.get(customerIndex).getBillString();
  }
}
