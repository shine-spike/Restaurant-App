package event;


abstract class BillEvent extends Event {
    int tableNumber;


    BillEvent(int employeeNumber, int tableNumber) {
        super(employeeNumber);
        this.tableNumber = tableNumber;
    }
}
