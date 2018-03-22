package event.bill;

import event.EventStatus;

public class PrintBillEvent extends BillEvent {
    private static final String LOG_MESSAGE = "Bill for table %1$d has been printed.";

    private int billIndex;


    public PrintBillEvent(int employeeNumber, int tableNumber, int billIndex) {
        super(employeeNumber, tableNumber);
        this.billIndex = billIndex;
    }

    public PrintBillEvent(int employeeNumber, int tableNumber) {
        super(employeeNumber, tableNumber);
        this.billIndex = -1;
    }

    @Override
    public EventStatus process() {
        if (billIndex == -1) {
            printBill(restaurant.getTableController().printBill(tableNumber));
        } else {
            printBill(restaurant.getTableController().printBill(tableNumber, billIndex));
        }
        return EventStatus.COMPLETED;
    }

    private void printBill(String bill) {
        System.out.println(bill);
    }
}
