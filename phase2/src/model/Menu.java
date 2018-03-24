package model;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A menu in the restaurant. Stores and manages menu items.
 */
public class Menu {
  private final String name;
  private final ArrayList<MenuItem> menuItems = new ArrayList<>();

  /**
   * Constructs an empty menu with the given name.
   *
   * @param name the name of this menu.
   */
  public Menu(String name) {
    this.name = name;
  }

  /**
   * Gets the name of this menu.
   *
   * @return the name of this menu.
   */
  public String getName() {
    return name;
  }

  /**
   * Adds a new menu item with the given name and price to this menu.
   *
   * @param menuItemName the name of the new menu item.
   * @param price        the price of the new menu item.
   */
  public void addMenuItem(String menuItemName, int price, HashMap<Ingredient, Integer> ingredients) {
    menuItems.add(new MenuItem(menuItemName, price, ingredients));
  }

  /**
   * Gets a menu item from this menu
   *
   * @param itemName the name of the menu item.
   * @return the menu item in this menu with the given name, or {@code null} if the item with the name does not exist.
   */
  @Nullable
  public MenuItem getMenuItem(String itemName) {
    for (MenuItem item : menuItems) {
      if (item.getName().equals(itemName)) {
        return item;
      }
    }
    return null;
  }
}
