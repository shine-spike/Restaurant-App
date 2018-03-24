package util;

import controller.Restaurant;
import model.Ingredient;
import model.MenuItem;
import model.Order;

import java.util.HashMap;

public class OrderFactory {
  public static Order createOrder(
      int employeeNumber,
      int tableNumber,
      int customerIndex,
      String menuNameString,
      String menuItemString,
      HashMap<String, Integer> ingredientStrings) {
    Restaurant restaurant = Restaurant.getInstance();

    MenuItem menuItem = restaurant.getMenuController().getMenuItem(menuNameString, menuItemString);
    HashMap<Ingredient, Integer> ingredients = new HashMap<>();

    // Adds each subtraction and addition to the order
    for (String ingredientString : ingredientStrings.keySet()) {
      ingredients.put(
          restaurant.getInventory().getIngredient(ingredientString),
          ingredientStrings.get(ingredientString));
    }

    return new Order(employeeNumber, tableNumber, customerIndex, menuItem, ingredients);
  }
}
