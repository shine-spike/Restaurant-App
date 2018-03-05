/**
 * Representation of all tables in the restaurant. Manages all interaction with individual tables.
 *
 * Stores an array of tables and methods to manipulate or gain required information from any table.
 */

public class TableController
  {
  // Array of tables in a restaurant
  private Table[] tableList;

  TableController(int numTables)
  {
    // Creates an array of a given length full of empty tables
    tableList = new Table[numTables];

    this.createTables();
  }

  // Populates each index with a constructed table
  private void createTables()
  {
    for (int i = 0; i < tableList.length; i++)
    {
      tableList[i] = new Table(i);
    }
  }

  // Calls the payBill method for the table of a given number
  public int payBill(int tableNumber)
  {
    return tableList[tableNumber].payBill();
  }

  // Adds a given order to the bill of the table it belongs to
  public void addToBill(Order order) {
    // Stores the table that the order belongs to
    Table myTable = tableList[order.getTableNumber()];

    // Calls the addToBill method of Table
    myTable.addToBill(order);
  }

  // Prints the bill, with the price and all orders
  public String printBill(int tableNumber) {
    return tableList[tableNumber].printBill();
  }

}
