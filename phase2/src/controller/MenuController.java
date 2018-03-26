package controller;

import model.Menu;
import model.MenuItem;

import java.util.ArrayList;

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
   * Gets the menu with the given name.
   *
   * @param menuName the name of the menu.
   * @return the menu with the given name or {@code null} if no such menu exists.
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
   * Gets all menus in the menu controller.
   *
   * @return the list of held menus.
   */
  public ArrayList<Menu> getMenuList() {
    return menus;
  }

  /**
   * Adds the given menu item to the menu with the given name.
   *
   * @param menuName the name of the menu to add the item to.
   * @param menuItem the menu item to add.
   */
  public void addMenuItem(String menuName, MenuItem menuItem) {
    Menu menu = getMenu(menuName);
    if (menu == null) {
      // TODO: remove print
      System.out.println("Given menu could not be found. Skipping.");
      return;
    }
    menu.addMenuItem(menuItem);
  }

  /**
   * Gets the menu item with the given name from the menu with the given name.
   *
   * @param menuName the name of the menu to get the menu item from.
   * @param menuItemName the name of the menu item.
   * @return the menu with the given name in the menu with the given name, or {@code null} if no
   *     corresponding menu item exists.
   */
  public MenuItem getMenuItem(String menuName, String menuItemName) {
    Menu menu = getMenu(menuName);
    if (menu == null) {
      // TODO: remove print
      System.out.println("Given menu could not be found. Skipping.");
      return null;
    }
    return menu.getMenuItem(menuItemName);
  }

  /**
   * Gets the menus in this controller.
   *
   * @return a list of strings representing the menus.
   */
  public ArrayList<String> getMenus() {
    ArrayList<String> menuStrings = new ArrayList<>();
    for (Menu menu : menus) {
        menuStrings.add(menu.getName());
    }
    return menuStrings;
  }

  /**
   * Gets the menu items from the menu with the given name.
   *
   * @param menuName the name of the menu.
   * @return a list of strings representing the menu items.
   */
  public ArrayList<String> getMenuItems(String menuName) {
    Menu menu = getMenu(menuName);
    ArrayList<String> menuItemStrings = new ArrayList<>();

    if (menu != null) {
      for (MenuItem menuItem : menu.getMenuItems()) {
        menuItemStrings.add(menuItem.getName());
      }
    }

    return menuItemStrings;
  }
}
