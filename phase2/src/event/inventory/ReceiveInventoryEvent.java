package event.inventory;


import event.EventStatus;

public class ReceiveInventoryEvent extends InventoryEvent {
    private int amount;


    public ReceiveInventoryEvent(int employeeNumber, String ingredientName, int amount) {
        super(employeeNumber, ingredientName);
        this.amount = amount;
    }

    @Override
    public EventStatus process() {
        restaurant.getInventory().restockIngredient(ingredientName, amount);
        return EventStatus.COMPLETED;
    }
}
