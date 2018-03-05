import java.util.ArrayList;

/**
 * This is a menu. It contains many possible items which can be served to constitute a meal for our valued customers.
 * Provides opportunities to add or get MenuItems, and the name of this menu. Should be used by the MenuController.
 */

public class Menu {
    String name;
    private ArrayList<MenuItem> menuItems;

    public Menu(String name){
        menuItems = new ArrayList<>();
        this.name = name;
    }

    public MenuItem getMenuItem(String itemName){
        for(MenuItem i : menuItems){
            if(i.getName().equals(itemName)){
                return i;
            }
        }

        return null;
    }

    public String toString(){
        StringBuilder out = new StringBuilder();

        for(MenuItem i : menuItems){
            out.append(menuItems.toString());

        }

        return out.toString();
    }

    public String getName(){
        return name;
    }

    public void addIngredientToMenuItem(String menuItemName, Ingredient ingredient){
        MenuItem item = getMenuItem(menuItemName);
        item.addIngredient(ingredient);
    }

    public void addAdditionToMenuItem(String menuItemName, Ingredient addition){
        MenuItem item = getMenuItem(menuItemName);
        item.addSubstitution(addition);
    }

    public void addMenuItem(String menuItemName, int price){
        MenuItem newItem = new MenuItem(price, menuItemName);
    }
}
