/**
 * Represents a table in a restaurant.
 *
 * Stores a bill and the methods to interact with it.
 */

public class Table
{
  private int tableID;
  private Bill myBill;

  // Creates a table with a given ID number and an empty bill
  Table(int tableNum)
  {
    this.tableID = tableNum;
    myBill = new Bill();
  }

  //Removes the Table's bill and returns the price of the bill
  public int payBill()
  {
    int billPrice = myBill.billTotal();
    myBill = new Bill();
    return billPrice;
  }

  // Adds a given order to the table's bill
  public void addToBill(Order order)
  {
    myBill.addOrder(order);
  }

  // Prints the table's bill
  public String printBill()
  {
    return myBill.toString();
  }

}
