import java.util.ArrayList;

/**
 * Represents a table in a restaurant.
 * <p>
 * Stores a bill and the methods to interact with it.
 */

public class Table {
  private int tableNumber;
  private Bill currentBill = new Bill();
  private ArrayList<Bill> previousBills = new ArrayList<>();

  // Creates a table with a given ID number and an empty bill
  Table(int tableNumber) {
    this.tableNumber = tableNumber;
  }

  // Removes the Table's bill
  public void payBill() {
    previousBills.add(currentBill);
    currentBill = new Bill();
  }

  // Adds a given order to the table's bill
  public void addToBill(Order order) {
    currentBill.addOrder(order);
  }

  // Prints the table's bill
  public String printBill() {
    return currentBill.toString();
  }
}
