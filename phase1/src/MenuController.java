import java.util.HashMap;

/**
 * Controls all aspects of all the menus in a restaurant. Main interface for interaction with menus.
 */
public class MenuController {
  private HashMap<String, Menu> menus = new HashMap<>();


  /**
   * Creates a menu with the given name and adds it to the collection of menus.
   *
   * @param menuName the name of the menu to add.
   */
  public void addMenu(String menuName) {
    menus.put(menuName, new Menu());
  }

  /**
   * Adds a menu item with a given price to a the menu with the given name.
   *
   * @param menuName     the name of the menu to add the item to.
   * @param menuItemName the name of the item to add.
   * @param price        the price of the item.
   */
  public void addItemToMenu(String menuName, String menuItemName, int price) {
    menus.get(menuName).addMenuItem(menuItemName, price);
  }

  /**
   * Adds a given Ingredient to the menu item with a given name in the menu with the given name.
   *
   * @param menuName     the name of the menu the item is in.
   * @param menuItemName the name of the item.
   * @param ingredient   the Ingredient to add.
   */
  public void addIngredientToMenuItem(String menuName, String menuItemName, Ingredient ingredient) {
    menus.get(menuName).addIngredientToMenuItem(menuItemName, ingredient);
  }

  /**
   * Adds a given Ingredient to be a valid addition to the menu item with a given name in the menu with the given name.
   *
   * @param menuName     the name of the menu the item is in.
   * @param menuItemName the name of the item.
   * @param addition     the Ingredient that is a valid addition.
   */
  public void addAdditionToMenuItem(String menuName, String menuItemName, Ingredient addition) {
    menus.get(menuName).addAdditionToMenuItem(menuItemName, addition);
  }

  /**
   * Gets the menu item with the given name from the menu with the given name.
   *
   * @param menuName     the name of the menu to get the item from.
   * @param menuItemName the name of the item.
   * @return the corresponding MenuItem with the given name in the menu with the given name.
   */
  public MenuItem getItemFromMenu(String menuName, String menuItemName) {
    return menus.get(menuName).getMenuItem(menuItemName);
  }
}
