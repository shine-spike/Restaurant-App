package event;

public abstract class BillEvent extends Event {
    int tableNumber;
    int billIndex;


    BillEvent(int employeeNumber, int tableNumber, int billIndex) {
        super(employeeNumber);
        this.tableNumber = tableNumber;
        this.billIndex = billIndex;
    }

    BillEvent(int employeeNumber, int tableNumber) {
        this(employeeNumber, tableNumber, -1);
    }
}
