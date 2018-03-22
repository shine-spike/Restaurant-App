package controller;

import model.Order;
import model.Table;

/**
 * Controls all tables in this restaurant. Manages all interactions with tables or bills.
 */
public class TableController {
  private final Table[] tables;


  /**
   * Construct a table controller with a given number of tables.
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
   * Returns the table with the given table number.
   *
   * @param tableNumber the number of the table to be accessed.
   * @return the table with the given table number.
   */
  public Table getTable(int tableNumber){ return tables[tableNumber];}

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
  public void addToBill(Order order) {
    tables[order.getTableNumber()].addToBill(order);
  }

  /**
   * Returns the formatted string representation of the bill for the table with the given table number.
   *
   * @param tableNumber the number of the table for which to print the bill.
   * @return the string representation of the bill of the table with the given table number.
   */
  public String printBill(int tableNumber) {
    return tables[tableNumber].printBill();
  }

  public String printBill(int tableNumber, int customerIndex) {
    return tables[tableNumber].printBill(customerIndex);
  }
}
