package event.bill;


import event.Event;

abstract class BillEvent extends Event {
    int tableNumber;


    BillEvent(int employeeNumber, int tableNumber) {
        super(employeeNumber);
        this.tableNumber = tableNumber;
    }
}
