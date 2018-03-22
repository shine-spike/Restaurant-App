package event;

import model.Order;


abstract class OrderEvent extends Event {
    Order order;


    OrderEvent(int employeeNumber, int orderNumber) {
        super(employeeNumber);
        this.order = restaurant.getOrderController().getOrderFromNumber(orderNumber);
    }

    OrderEvent(int employeeNumber, Order order) {
        super(employeeNumber);
        this.order = order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
