package util;

import controller.Restaurant;
import model.Ingredient;
import model.Menu;
import model.MenuItem;
import model.Order;

import java.util.HashMap;

/**
 * This factory serves primarily to create new orders from given inputs. It contains only static
 * methods to achieve this.
 */
public class OrderFactory {
  /**
   * Creates an order with the given parameters and returns it.
   *
   * @param tableNumber the number of the table this order was placed for.
   * @param customerIndex the index of the customer this order is for.
   * @param menuName the name of the menu the menu item of this order is from.
   * @param menuItemName the name of the menu item for this order.
   * @param ingredientChanges the map of ingredient name to change from the default menu item.
   * @return the order created.
   */
  public static Order createOrder(
      int tableNumber,
      int customerIndex,
      String menuName,
      String menuItemName,
      HashMap<String, Integer> ingredientChanges) {
    Restaurant restaurant = Restaurant.getInstance();
    Menu menu = restaurant.getMenuController().getMenu(menuName);

    MenuItem menuItem;
    if (menu != null) {
      menuItem = menu.getMenuItem(menuItemName);
    } else {
      return null;
    }

    if (menuItem != null) {
      HashMap<Ingredient, Integer> ingredients = new HashMap<>();

      for (String ingredientString : ingredientChanges.keySet()) {
        ingredients.put(
            restaurant.getInventory().getIngredient(ingredientString),
            ingredientChanges.get(ingredientString));
      }

      return new Order(tableNumber, customerIndex, menu, menuItem, ingredients);
    }

    return null;
  }
}
