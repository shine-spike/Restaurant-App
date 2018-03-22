package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A menu item in a menu, it is basically a list of ingredients with available additions and a price.
 */
public class MenuItem {
  private final int price;
  private final String name;

  private final HashMap<Ingredient, Integer> ingredients;


  /**
   * Constructs a new menu item with the given name and price.
   *
   * @param name  the name of the menu item.
   * @param price the price of the menu item.
   */
  MenuItem(String name, int price, HashMap<Ingredient, Integer> ingredients) {
    this.price = price;
    this.name = name;
    this.ingredients = ingredients;
  }

  /**
   * Gets the price of this menu item.
   *
   * @return the price of the menu item.
   */
  public int getPrice() {
    return price;
  }

  /**
   * Gets the name of this menu item.
   *
   * @return the name of this menu item.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the ingredients that constitute this basic menu item.
   *
   * @return the ingredients of this menu item.
   */
  public HashMap<Ingredient, Integer> getIngredients() {
    return ingredients;
  }
}
