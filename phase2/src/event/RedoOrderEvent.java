package event;

import model.Order;
import model.OrderStatus;

public class RedoOrderEvent extends OrderEvent {
    private String reason;


    public RedoOrderEvent(int employeeName, int orderNumber, String reason) {
        super(employeeName, orderNumber);
        this.reason = reason;
    }

    @Override
    public EventStatus process() {
        order.setStatus(OrderStatus.REDO);
        new PlaceOrderEvent(employeeNumber, order.duplicate()).process();
        return EventStatus.COMPLETED;
    }
}
