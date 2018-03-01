public class Order {
    static private int current_order_number = 0;

    private int tableNum;
    private int serverId;
    private MenuItem menuItem;
    private Ingredient[] additions;
    private Ingredient[] subtractions;
    private boolean seen;

    public int getTableNum() {
        return tableNum;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getPrice(){
        return menuItem.getPrice();
    }

    public String toString(){
        menuItem.toString();
    }

    public Ingredient[] getIngredientsList(){
        return null;
    }
}
