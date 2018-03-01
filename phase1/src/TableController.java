public class TableController
{
  private Table[] tableList;

  public TableController(int numTables)
  {
      tableList = new Table[numTables];
      this.createTables();
  }

  private void createTables()
  {
    int tableNumber = 1;
    for (Table table: tableList)
    {
      table = new Table(tableNumber);
      tableNumber ++;
    }
  }

  public void addToBill(Order order)
  {

  }

  public String printBill(Table table)
  {
    return "";
  }


}
