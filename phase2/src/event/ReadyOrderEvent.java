package event;

import model.OrderStatus;

public class ReadyOrderEvent extends OrderEvent {
    public ReadyOrderEvent(int employeeName, int orderNumber) {
        super(employeeName, orderNumber);
    }

    @Override
    public EventStatus process() {
        restaurant.inventory.consumeIngredients(order);
        order.setStatus(OrderStatus.READY);
        return EventStatus.COMPLETED;
    }
}
