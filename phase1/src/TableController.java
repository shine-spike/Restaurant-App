public class TableController {
  private Table[] tableList;

  public TableController(int numTables) {
    tableList = new Table[numTables];
    this.createTables();
  }

  private void createTables() {
    for (int i = 0; i < tableList.length; i++) {
      tableList[i] = new Table(i);
    }
  }

  public void addToBill(int tableNumber, Order order) {
    Table myTable = tableList[tableNumber - 1];
    myTable.addToBill(order);
  }

  public String printBill(int tableNumber) {
    return tableList[tableNumber - 1].printBill();
  }

}
