import java.util.HashMap;

public class MenuController {
    private HashMap<String, Menu> menus = new HashMap<>();

    public void addMenu(String menuName) {
        menus.put(menuName, new Menu(menuName));
    }

    public void addItemToMenu(String menuName, String menuItemName, int price) {
        menus.get(menuName).createMenuItem(menuItemName, price);
    }

    public void addIngredientToMenuItem(String menuName, String menuItemName, Ingredient ingredient) {
        menus.get(menuName).addIngredientToMenuItem(menuItemName, ingredient);
    }

    public void addAdditionToMenuItem(String menuName, String menuItemName, Ingredient addition) {
        menus.get(menuName).addAdditionToMenuItem(menuItemName, addition);
    }

    public MenuItem getItemFromMenu(String menuName, String menuItemName) {
        return menus.get(menuName).getMenuItem(menuItemName);
    }
}
