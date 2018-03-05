import java.util.ArrayList;

/**
 * Represents a bill
 * <p>
 * Stores an ArrayList of bills
 */
public class Bill {
  private ArrayList<Order> orders;

  Bill() {
    orders = new ArrayList<>();
  }

  public void addOrder(Order order) {
    orders.add(order);
  }

  // Computes the total price of all orders on the bill
  public int billTotal() {
    int total = 0;
    for (Order order : orders) {
      total += order.getPrice();
    }
    return total;
  }

  /*
   * Returns a string in the following format:
   *
   * Bill:
   * [MenuItem] [MenuItemPrice]
   * [MenuItem] [MenuItemPrice]
   * ...
   * Bill total:
   * [totalPrice]
   */
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("Bill: \n");
    for (Order order : orders) {
      str.append(order.toString()).append("\n");
    }
    str.append("Bill total: \n");
    str.append(this.billTotal());
    return str.toString();
  }
}
