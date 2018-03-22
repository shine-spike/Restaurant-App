package event;

public class PaidBillEvent extends BillEvent {
    public PaidBillEvent(int employeeNumber, int tableNumber, int billIndex) {
        super(employeeNumber, tableNumber, billIndex);
    }

    public PaidBillEvent(int employeeNumber, int tableNumber) {
        super(employeeNumber, tableNumber);
    }

    @Override
    public EventStatus process() {
        // TODO: implement single-customer clearance
        restaurant.getTableController().clearBill(tableNumber);
        return EventStatus.COMPLETED;
    }
}
