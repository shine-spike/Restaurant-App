package event;

public class PrintBillEvent extends BillEvent {
    public PrintBillEvent(int employeeNumber, int tableNumber, int billIndex) {
        super(employeeNumber, tableNumber, billIndex);
    }

    public PrintBillEvent(int employeeNumber, int tableNumber) {
        super(employeeNumber, tableNumber);
    }

    @Override
    public EventStatus process() {
        System.out.println(restaurant.tableController.printBill(tableNumber));
        return EventStatus.COMPLETED;
    }
}
