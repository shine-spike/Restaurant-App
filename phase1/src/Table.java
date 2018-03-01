public class Table
{
  private int tableID;
  private Bill myBill;

  public Table(int tableNum)
  {
    this.tableID = tableNum;
  }

  //Removes the Table's bill and returns the price of the bill
  public int payBill()
  {
    int billPrice = myBill.billTotal();
    myBill = null;
    return billPrice;
  }

  public void addToBill(Order order)
  {
    myBill.addOrder(order);
  }

  public String printBill()
  {
    return myBill.toString();
  }

  public Bill getBill()
  {
    return myBill;
  }


}
