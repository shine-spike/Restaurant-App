package event.order;

import event.EventStatus;
import model.Order;

import java.util.HashMap;


public class PlaceOrderEvent extends OrderEvent {
    /**
     * Places the order an employee with the given employee number takes from the table with given table number.
     * The order consists of a given menu item name from a given menu name with a list of ingredient names
     * to be added or subtracted from the menu item.
     *
     * @param employeeNumber     the number of the employee who took this order.
     * @param tableNumber        the table that placed this order.
     * @param menuItemString     the name of the menu item.
     * @return whether or not this order has been successfully placed. This can fail if
     * there are not enough ingredients to fulfill it.
     */
    public PlaceOrderEvent(int employeeNumber, int tableNumber, int customerIndex,
                           String menuNameString, String menuItemString, HashMap<String, Integer> ingredientStrings) {
        super(employeeNumber, null);
        this.order = restaurant.createOrder(employeeNumber, tableNumber, customerIndex,
                menuNameString, menuItemString, ingredientStrings);
    }

    PlaceOrderEvent(int employeeNumber, Order order) {
        super(employeeNumber, order);
    }

    @Override
    public EventStatus process() {
        boolean status = restaurant.registerOrder(order);
        return EventStatus.COMPLETED;
    }
}
