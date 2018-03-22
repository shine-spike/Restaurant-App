package event;

import model.OrderStatus;


public class AcceptedOrderEvent extends OrderEvent {
    public AcceptedOrderEvent(int employeeName, int orderNumber) {
        super(employeeName, orderNumber);
    }

    @Override
    public EventStatus process() {
        order.setStatus(OrderStatus.ACCEPTED);
        restaurant.addToBill(order);
        return EventStatus.COMPLETED;
    }
}
