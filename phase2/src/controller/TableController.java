package controller;

import model.Order;
import model.Table;

import java.util.ArrayList;

/** Controls all tables in this restaurant. Manages all interactions with tables or bills. */
public class TableController {
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
   * Gets the order information of all orders in the current bill of the given table.
   *
   * @param tableNumber the number of the table.
   * @return an array of order information.
   */
  public ArrayList<String[]> getTableOrderInformation(int tableNumber) {
    Table table = getTable(tableNumber);
    ArrayList<String[]> orderStrings = new ArrayList<>();

    for (Order order : table.getCurrentBill().getOrders()) {
      if (order != null) {
        orderStrings.add(order.getOrderInformation());
      }
    }

    return orderStrings;
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
   * the table with the given table number
   *
   * @param tableNumber the number of the table.
   * @param customerIndex the index of the customer.
   * @return the string representation of the bill.
   */
  public String getBillString(int tableNumber, int customerIndex) {
    return tables[tableNumber].getBillString(customerIndex);
  }

  /**
   * Clears the bill for the table with the given table number.
   *
   * @param tableNumber the number of the table for which to clear the bill.
   */
  public void clearBill(int tableNumber) {
    tables[tableNumber].clearBill();
  }

  /**
   * Adds a given order to the bill of the correct table.
   *
   * @param order the order to add to bill.
   */
  void addToBill(Order order) {
    tables[order.getTableNumber()].addToBill(order);
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
