package event.inventory;

import event.Event;

abstract class InventoryEvent extends Event {
    String ingredientName;


    InventoryEvent(int employeeNumber, String ingredientName) {
        super(employeeNumber);
        this.ingredientName = ingredientName;
    }
}
