import java.util.ArrayList;

public class Bill
{
  private ArrayList<Order> orders;

  public Bill()
  {
    orders = new ArrayList<>();
  }

  public void addOrder(Order order)
  {
      orders.add(order);
  }

  public String toString()
  {
      return "";
  }
}
