package controller;

import model.Order;
import model.Table;
import util.Logger;

import java.util.ArrayList;

/** Controls all tables in this restaurant. Manages all interactions with tables or bills. */
public class TableController {
  private final ArrayList<Integer> payments = new ArrayList<>();
  private final Table[] tables;

  /**
   * Constructs a table controller with a given number of tables.
   *
   * @param numTables the number of tables this table controller manages.
   */
  TableController(int numTables) {
    tables = new Table[numTables];
    for (int i = 0; i < tables.length; i++) {
      tables[i] = new Table();
    }
  }

  /**
   * Gets the total amount of money paid to the restaurant.
   *
   * @return the total amount of cents paid to the restaurant.
   */
  public int getTotalPayments() {
    int total = 0;
    for (int payment : payments) {
        total += payment;
    }
    return total;
  }

  /**
   * Gets the number of tables under this controller.
   *
   * @return the number of tables.
   */
  public int getNumberTables() {
    return tables.length;
  }

  /**
   * Gets the order numbers of all orders for the table with the given number.
   *
   * @param tableNumber the number of the table.
   * @return an array of order numbers.
   */
  public ArrayList<Integer> getTableOrderNumbers(int tableNumber) {
    Table table = getTable(tableNumber);
    ArrayList<Integer> orderNumbers = new ArrayList<>();

    for (Order order : table.getCurrentOrders()) {
      if (order != null) {
        orderNumbers.add(order.getOrderNumber());
      }
    }

    return orderNumbers;
  }

  /**
   * Gets the formatted string representation of the bill for the table with the given table number.
   *
   * @param tableNumber the number of the table.
   * @return the string representation of the bill.
   */
  public String getBillString(int tableNumber) {
    return tables[tableNumber].getBillString();
  }

  /**
   * Gets the formatted string representation of the bill for the customer with the given index on
   * the table with the given table number. If {@code -1} is passed as the customer index, prints
   * all bills.
   *
   * @param tableNumber the number of the table.
   * @param customerIndex the index of the customer.
   * @return the string representation of the bill.
   */
  public String getBillString(int tableNumber, int customerIndex) {
    if (customerIndex != -1) {
      return tables[tableNumber].getBillString(customerIndex);
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      for (String billString : tables[tableNumber].getBillStrings()) {
        stringBuilder.append(billString).append("\n\n");
      }
      return stringBuilder.toString();
    }
  }

  /**
   * Clears the bill for the table with the given table number.
   *
   * @param tableNumber the number of the table for which to clear the bill.
   */
  public void clearBill(int tableNumber) {
    int total = tables[tableNumber].getTotalBill();
    payments.add(total);
    tables[tableNumber].clearBill();

    Logger.billLog(tableNumber, "PAID", "paid off with a cent-value of " + Integer.toString(total));
  }

  /**
   * Adds a given order to the bill of the correct table.
   *
   * @param order the order to add to bill.
   */
  void addToBill(Order order) {
    tables[order.getTableNumber()].addToBill(order.getOrderNumber());
  }

  /**
   * Adds a given order to the correct table.
   *
   * @param order the order to add.
   */
  void addToTable(Order order) {
    tables[order.getTableNumber()].addToTable(order);
  }

  /**
   * Gets the table with the given table number.
   *
   * @param tableNumber the number of the table to be accessed.
   * @return the table with the given table number.
   */
  private Table getTable(int tableNumber) {
    return tables[tableNumber];
  }
}
