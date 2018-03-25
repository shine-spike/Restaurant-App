package util;

import controller.Restaurant;
import model.Ingredient;
import model.MenuItem;

import java.util.HashMap;

/**
 * This factory serves primarily to create new menu items from given inputs. It contains only static
 * methods to achieve this.
 */
public class MenuItemFactory {
  /**
   * Creates a menu item with the given parameters and returns it.
   *
   * @param name the name of the menu item.
   * @param price the price of the menu item in cents.
   * @param ingredientAmounts the map of ingredient names to amounts in the item.
   * @return the menu item created.
   */
  
  public static MenuItem createMenuItem(String name, int price, HashMap<String, Integer> ingredientAmounts) {
    HashMap<Ingredient, Integer> ingredients = new HashMap<>();
    for (String ingredientName : ingredientAmounts.keySet()) {
      Ingredient ingredient = Restaurant.getInstance().getInventory().getIngredient(ingredientName);
      ingredients.put(ingredient, ingredientAmounts.get(ingredientName));
    }
    return new MenuItem(name, price, ingredients);
  }
}
