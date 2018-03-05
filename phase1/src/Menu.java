import java.util.ArrayList;

/**
 * This is a menu. It contains many possible items which can be served to constitute a meal for our valued customers.
 * Provides opportunities to add or get MenuItems, and the name of this menu. Should be used by the MenuController.
 */

public class Menu {
  private String name;
  private ArrayList<MenuItem> menuItems = new ArrayList<>();;

  Menu(String name) {
    this.name = name;
  }

  public MenuItem getMenuItem(String itemName) {
    for (MenuItem item : menuItems) {
      if (item.getName().equals(itemName)) {
        return item;
      }
    }
    return null;
  }

  public String getName() {
    return name;
  }

  public void addIngredientToMenuItem(String menuItemName, Ingredient ingredient) {
    MenuItem item = getMenuItem(menuItemName);
    item.addIngredient(ingredient);
  }

  public void addAdditionToMenuItem(String menuItemName, Ingredient addition) {
    MenuItem item = getMenuItem(menuItemName);
    item.addAddition(addition);
  }

  public void addMenuItem(String menuItemName, int price) {
    menuItems.add(new MenuItem(menuItemName, price));
  }
}
