package model;

import java.io.Serializable;
import java.util.ArrayList;

/** A menu in the restaurant. Stores and manages menu items. */
public class Menu implements Serializable {
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

  public ArrayList<MenuItem> getMenuItems() {
    return menuItems;
  }

  /** Adds the given menu item to the menu. */
  public void addMenuItem(MenuItem menuItem) {
    menuItems.add(menuItem);
  }

  /**
   * Gets a menu item from this menu
   *
   * @param itemName the name of the menu item.
   * @return the menu item in this menu with the given name, or {@code null} if the item with the
   *     name does not exist.
   */
  public MenuItem getMenuItem(String itemName) {
    for (MenuItem item : menuItems) {
      if (item.getName().equals(itemName)) {
        return item;
      }
    }
    return null;
  }
}
