package event;

import util.Logger;

public class PaidBillEvent extends BillEvent {
    public PaidBillEvent(int employeeNumber, int tableNumber) {
        super(employeeNumber, tableNumber);
    }

    @Override
    public EventStatus process() {
        restaurant.getTableController().clearBill(tableNumber);
        return EventStatus.COMPLETED;
    }
}
