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

  public int billTotal()
  {
    int total = 0;
    for (Order order : orders)
    {
      total += order.getPrice();
    }
    return total;
  }

  public String toString()
  {
    StringBuilder str = new StringBuilder();
    str.append("Bill: \n");
    for (Order order: orders)
    {
      str.append(order.toString()).append("\n");
    }
    str.append("Bill total: \n");
    return str.toString();
  }
}
