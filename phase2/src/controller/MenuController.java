package controller;

import model.Ingredient;
import model.Menu;
import model.MenuItem;
import util.SerializableContents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Controls all aspects of all the menus in a restaurant. Main interface for interaction with menus.
 */
public class MenuController implements SerializableContents<Menu> {
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
   * Removes the menu with the given name.
   *
   * @param menuName the name of the menu to remove.
   */
  public void removeMenu(String menuName) {
    Menu menu = getMenu(menuName);
    if (menu != null) {
      menus.remove(menu);
    }
  }

  /**
   * Adds the given menu item to the menu with the given name.
   *
   * @param menuName the name of the menu to add the item to.
   * @param menuItem the menu item to add.
   */
  public void addMenuItem(String menuName, MenuItem menuItem) {
    Menu menu = getMenu(menuName);
    if (menu != null) {
      menu.addMenuItem(menuItem);
    }
  }

  /**
   * Removes the menu item with the given name from the menu with the given name.
   *
   * @param menuName the name of the menu to remove from.
   * @param menuItemName the name of the menu item to remove.
   */
  public void removeMenuItem(String menuName, String menuItemName) {
    Menu menu = getMenu(menuName);
    if (menu != null) {
      MenuItem menuItem = menu.getMenuItem(menuItemName);
      if (menuItem != null) {
        menu.getMenuItems().remove(menuItem);
      }
    }
  }

  /**
   * Gets the menus in this controller.
   *
   * @return a list of strings representing the menus.
   */
  public ArrayList<String> getMenuStrings() {
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
  public ArrayList<String> getMenuItemStrings(String menuName) {
    Menu menu = getMenu(menuName);
    ArrayList<String> menuItemStrings = new ArrayList<>();

    if (menu != null) {
      for (MenuItem menuItem : menu.getMenuItems()) {
        menuItemStrings.add(menuItem.getName());
      }
    }

    return menuItemStrings;
  }

  /**
   * Gets the ingredients of a menu item with a given name from the menu with the given name.
   *
   * @param menuName the name of the menu.
   * @param menuItemName the name of the menu item.
   * @return a list of strings representing the ingredients.
   */
  public HashMap<String, Integer> getIngredientStrings(String menuName, String menuItemName) {
    MenuItem menuItem = getMenuItem(menuName, menuItemName);
    HashMap<String, Integer> ingredientStrings = new HashMap<>();

    if (menuItem != null) {
      for (Ingredient ingredient : menuItem.getIngredients().keySet()) {
        ingredientStrings.put(ingredient.getName(), menuItem.getIngredients().get(ingredient));
      }
    }

    return ingredientStrings;
  }

  /**
   * Modifies the ingredients of a menu item with the given name. If the ingredient already exists
   * in the menu item, sets its quantity to the new value, otherwise adds the ingredient to the menu
   * item. If the quantity is {@code -1}, removes the item.
   *
   * @param menuName the name of the menu.
   * @param menuItemName the name of the menu item.
   * @param ingredientName the name of the ingredient.
   * @param quantity the amount of the ingredient.
   */
  public void modifyMenuItemIngredients(
      String menuName, String menuItemName, String ingredientName, int quantity) {
    MenuItem menuItem = getMenuItem(menuName, menuItemName);
    Ingredient ingredient = Restaurant.getInstance().getInventory().getIngredient(ingredientName);

    if (menuItem != null) {
      if (quantity == -1) {
        menuItem.getIngredients().remove(ingredient);
      } else {
        menuItem.getIngredients().put(ingredient, quantity);
      }
    }
  }

  /**
   * Gets the price of a menu item with a given name from a menu with a given name.
   *
   * @param menuName the name of the menu.
   * @param menuItemName the name of the menu item.
   * @return the price of the menu item or {@code -1} if such an item does not exist.
   */
  public int getMenuItemPrice(String menuName, String menuItemName) {
    Menu menu = getMenu(menuName);
    if (menu != null) {
      MenuItem menuItem = menu.getMenuItem(menuItemName);
      if (menuItem != null) {
        return menuItem.getPrice();
      }
    }
    return -1;
  }

  /**
   * Sets the price of a menu item with a given name from a menu with a given name.
   *
   * @param menuName the name of the menu.
   * @param menuItemName the name of the menu item.
   * @param price the new price of the menu item.
   */
  public void setMenuItemPrice(String menuName, String menuItemName, int price) {
    Menu menu = getMenu(menuName);
    if (menu != null) {
      MenuItem menuItem = menu.getMenuItem(menuItemName);
      if (menuItem != null) {
        menuItem.setPrice(price);
      }
    }
  }

  /**
   * Gets the menu with the given name.
   *
   * @param menuName the name of the menu.
   * @return the menu with the given name or {@code null} if no such menu exists.
   */
  public Menu getMenu(String menuName) {
    for (Menu menu : menus) {
      if (menu.getName().equals(menuName)) {
        return menu;
      }
    }
    return null;
  }

  /**
   * Gets the menu item with the given name from the menu with the given name.
   *
   * @param menuName the name of the menu to get the menu item from.
   * @param menuItemName the name of the menu item.
   * @return the menu with the given name in the menu with the given name, or {@code null} if no
   *     corresponding menu item exists.
   */
  private MenuItem getMenuItem(String menuName, String menuItemName) {
    Menu menu = getMenu(menuName);
    if (menu != null) {
      return menu.getMenuItem(menuItemName);
    }
    return null;
  }

  @Override
  public Menu[] getContents() {
    Menu[] menuArray = new Menu[menus.size()];
    menus.toArray(menuArray);
    return menuArray;
  }

  @Override
  public void setContents(Menu[] contents) {
    menus.clear();
    menus.addAll(Arrays.asList(contents));
  }

  @Override
  public String getName() {
    return "menus";
  }
}
