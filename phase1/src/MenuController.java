import java.util.HashMap;

public class MenuController {
    private HashMap<String, Menu> menus = new HashMap<>();

    public void addMenu(String menuName) {
        menus.put(menuName, new Menu());
    }

    public void addItemToMenu(String menuName, String menuItemName, int price) {
        menus.get(menuName).addMenuItem(menuItemName, price);
    }

    public void addIngredientToMenuItem(String menuName, String menuItemName, String ingredientName) {
        menus.get(menuName).addIngredientToMenuItem(menuItemName, ingredientName);
    }

    public void addAdditionToMenuItem(String menuName, String menuItemName, String additionName) {
        menus.get(menuName).addAdditionToMenuItem(menuItemName, additionName);
    }
}
