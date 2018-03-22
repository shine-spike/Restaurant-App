package event;

import model.Ingredient;

public class ReceiveEvent extends Event {
    private String ingredientName;
    private int amount;


    public ReceiveEvent(int employeeNumber, String ingredientName, int amount) {
        super(employeeNumber);
        this.ingredientName = ingredientName;
        this.amount = amount;
    }

    @Override
    public EventStatus process() {
        restaurant.getInventory().restockIngredient(ingredientName, amount);
        return EventStatus.COMPLETED;
    }
}
