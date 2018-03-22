package event;


public class ReceiveEvent extends InventoryEvent {
    private int amount;


    public ReceiveEvent(int employeeNumber, String ingredientName, int amount) {
        super(employeeNumber, ingredientName);
        this.amount = amount;
    }

    @Override
    public EventStatus process() {
        restaurant.getInventory().restockIngredient(ingredientName, amount);
        return EventStatus.COMPLETED;
    }
}
