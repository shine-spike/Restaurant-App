package controller;

import model.Ingredient;
import model.Menu;
import model.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controls all aspects of all the menus in a restaurant. Main interface for interaction with menus.
 */
public class MenuController {
  private final ArrayList<Menu> menus = new ArrayList<>();

  /**
   * Creates a menu with the given name and adds it to the collection of menus.
   *
   * @param menuName the name of the menu to add.
   */
  public void addMenu(String menuName) {
    menus.add(new Menu(menuName));
  }

  /**
   * Gets the Menu with the given name.
   *
   * @param menuName the name of the menu.
   * @return the Menu with the given name.
   */
  private Menu getMenu(String menuName) {
    for (Menu menu : menus) {
      if (menu.getName().equals(menuName)) {
        return menu;
      }
    }
    return null;
  }

  /**
   * Gets the ArrayList of Menus
   *
   * @return the instance variable menus
   */
  public ArrayList<Menu> getMenuList() {
    return menus;
  }

  /**
   * Adds a menu item with a given price to a the menu with the given name.
   *
   * @param menuName the name of the menu to add the item to.
   * @param menuItemName the name of the item to add.
   * @param price the price of the item.
   */
  public void addMenuItem(
      String menuName, String menuItemName, int price, HashMap<Ingredient, Integer> ingredients) {
    Menu menu = getMenu(menuName);
    if (menu == null) {
      System.out.println("Given menu could not be found. Skipping.");
      return;
    }

    menu.addMenuItem(menuItemName, price, ingredients);
  }

  /**
   * Gets the menu item with the given name from the menu with the given name.
   *
   * @param menuName the name of the menu to get the item from.
   * @param menuItemName the name of the item.
   * @return the corresponding MenuItem with the given name in the menu with the given name.
   */
  public MenuItem getMenuItem(String menuName, String menuItemName) {
    Menu menu = getMenu(menuName);
    if (menu == null) {
      System.out.println("Given menu could not be found. Skipping.");
      return null;
    }

    return menu.getMenuItem(menuItemName);
  }
}
