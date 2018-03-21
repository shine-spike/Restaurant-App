package event;

import model.MenuItem;
import model.Order;

import java.util.ArrayList;


public class PlaceOrderEvent extends OrderEvent {
    /**
     * Places the order an employee with the given employee number takes from the table with given table number.
     * The order consists of a given menu item name from a given menu name with a list of ingredient names
     * to be added or subtracted from the menu item.
     *
     * @param employeeNumber     the number of the employee who took this order.
     * @param tableNumber        the table that placed this order.
     * @param menuNameString     the name of the menu this order's menu item is in.
     * @param menuItemString     the name of the menu item.
     * @param subtractionStrings the list of names of ingredients to remove from the menu item.
     * @param additionStrings    the list of names of ingredients to add to the menu item.
     * @return whether or not this order has been successfully placed. This can fail if
     * there are not enough ingredients to fulfill it.
     */
    public PlaceOrderEvent(int employeeNumber, int tableNumber, String menuNameString, String menuItemString,
                           ArrayList<String> subtractionStrings, ArrayList<String> additionStrings) {
        super(employeeNumber, null);

        MenuItem menuItem = restaurant.menuController.getItemFromMenu(menuNameString, menuItemString);
        Order order = new Order(employeeNumber, tableNumber, menuItem);

        // Adds each subtraction and addition to the order
        for (String subtraction : subtractionStrings) {
            order.addSubtraction(restaurant.inventory.getIngredient(subtraction));
        }
        for (String addition : additionStrings) {
            order.addAddition(restaurant.inventory.getIngredient(addition));
        }

        this.order = order;
    }

    public PlaceOrderEvent(int employeeNumber, Order order) {
        super(employeeNumber, order);
    }

    @Override
    public EventStatus process() {
        boolean status = restaurant.registerOrder(order);
        return EventStatus.COMPLETED;
    }
}
