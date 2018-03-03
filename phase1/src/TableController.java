public class TableController {
  private Table[] tableList;

  TableController(int numTables) {
    tableList = new Table[numTables];
    this.createTables();
  }

  private void createTables() {
    for (int i = 0; i < tableList.length; i++) {
      tableList[i] = new Table(i);
    }
  }

  public void addToBill(Order order) {
    Table myTable = tableList[order.getTableNumber()];
    myTable.addToBill(order);
  }

  public String printBill(int tableNumber) {
    return tableList[tableNumber].printBill();
  }

}
