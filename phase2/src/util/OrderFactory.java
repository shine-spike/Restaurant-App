package util;

import controller.Restaurant;
import model.Ingredient;
import model.MenuItem;
import model.Order;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * This factory serves primarily to create new orders from given inputs. It contains only static
 * methods to achieve this.
 */
public class OrderFactory {
  /**
   * Creates an order with the given parameters and returns it.
   *
   * @param employeeNumber the number of the employee who placed this order.
   * @param tableNumber the number of the table this order was placed for.
   * @param customerIndex the index of the customer this order is for.
   * @param menuNameString the name of the menu the menu item of this order is from.
   * @param menuItemString the name of the menu item for this order.
   * @param ingredientChanges the map of ingredient name to change from the default menu item.
   * @return the order created.
   */
  @NotNull
  public static Order createOrder(
      int employeeNumber,
      int tableNumber,
      int customerIndex,
      String menuNameString,
      String menuItemString,
      HashMap<String, Integer> ingredientChanges) {
    Restaurant restaurant = Restaurant.getInstance();
    MenuItem menuItem = restaurant.getMenuController().getMenuItem(menuNameString, menuItemString);
    HashMap<Ingredient, Integer> ingredients = new HashMap<>();

    for (String ingredientString : ingredientChanges.keySet()) {
      ingredients.put(
          restaurant.getInventory().getIngredient(ingredientString),
          ingredientChanges.get(ingredientString));
    }

    return new Order(employeeNumber, tableNumber, customerIndex, menuItem, ingredients);
  }
}
