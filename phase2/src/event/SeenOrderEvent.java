package event;

import model.OrderStatus;

public class SeenOrderEvent extends OrderEvent {
    public SeenOrderEvent(int employeeName, int orderNumber) {
        super(employeeName, orderNumber);
    }

    @Override
    public EventStatus process() {
        order.setStatus(OrderStatus.SEEN);
        return EventStatus.COMPLETED;
    }
}
