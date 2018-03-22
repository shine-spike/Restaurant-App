package model;

import java.util.ArrayList;

/**
 * Represents a table in a restaurant. Stores the bill for this table.
 */
public class Table {
  private Bill currentBill = new Bill();
  private ArrayList<Bill> customerBills = new ArrayList<>();

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
   * Returns this table's unformatted bill.
   *
   * @return the unformatted bill.
   */
  public Bill getCurrentBill(){
    return currentBill;
  }

  /**
   * Returns this table's formatted bill.
   *
   * @return the formatted bill string.
   */
  public String printBill() {
    return currentBill.toString();
  }

  public String printBill(int customerNumber) {
    return customerBills.get(customerNumber).toString();
  }
}
