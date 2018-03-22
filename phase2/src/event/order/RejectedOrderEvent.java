package event.order;

import event.EventStatus;
import model.OrderStatus;

public class RejectedOrderEvent extends OrderEvent {
    private String reason;


    public RejectedOrderEvent(int employeeName, int orderNumber, String reason) {
        super(employeeName, orderNumber);
        this.reason = reason;
    }

    @Override
    public EventStatus process() {
        order.setStatus(OrderStatus.REJECTED);
        return EventStatus.COMPLETED;
    }
}
