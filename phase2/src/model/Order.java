package model;

import parsing.Localizer;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Represent an order of a single menu item with any additions or subtractions
 * with all required information to travel through the restaurant system.
 */
public class Order {
  // The largest order number stored for all orders
  private static int currentOrderNum = 0;

  private final int orderNumber;
  private final int tableNumber;
  private final int employeeNumber;
  private final MenuItem menuItem;
  private final ArrayList<Ingredient> ingredients;

  // Lists of additions and subtractions on this order
  private final ArrayList<Ingredient> additions = new ArrayList<>();
  private final ArrayList<Ingredient> subtractions = new ArrayList<>();

  // Whether or not this order has been seen and is being prepared
  private OrderStatus status = OrderStatus.CREATED;


  /**
   * Constructs an order consisting of a given menu item at a given table,
   * taken by the employee with the given number.
   *
   * @param employeeNumber the number of the employee who took this order.
   * @param tableNumber    the number of the table which placed this order.
   * @param menuItem       the MenuItem ordered.
   */
  public Order(int employeeNumber, int tableNumber, MenuItem menuItem) {
    this.orderNumber = currentOrderNum;
    currentOrderNum++;

    this.employeeNumber = employeeNumber;
    this.tableNumber = tableNumber;
    this.menuItem = menuItem;
    this.ingredients = new ArrayList<>(Arrays.asList(menuItem.getIngredients()));
  }

  /**
   * Gets the number of the table that placed this order.
   *
   * @return the number of the table that placed this order.
   */
  public int getTableNumber() {
    return tableNumber;
  }

  /**
   * Gets the price of this order.
   *
   * @return the price of this order.
   */
  public int getPrice() {
    return menuItem.getPrice();
  }

  /**
   * Gets the number of this order.
   *
   * @return the number of this order.
   */
  public int getOrderNumber() {
    return orderNumber;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  /**
   * Deeply duplicates this order and returns the copy.
   *
   * @return a deep copy of this order.
   */
  public Order duplicate() {
    Order duplicateOrder = new Order(employeeNumber, tableNumber, menuItem);
    for (Ingredient addition : additions) {
      duplicateOrder.addAddition(addition);
    }
    for (Ingredient subtraction : subtractions) {
      duplicateOrder.addSubtraction(subtraction);
    }
    duplicateOrder.setStatus(OrderStatus.CREATED);

    return duplicateOrder;
  }

  /**
   * Adds an Ingredient to this order or
   * does nothing if the ingredient is not on the list of permitted additions or basic ingredients for this order.
   *
   * @param addition the Ingredient to be added.
   */
  public void addAddition(Ingredient addition) {
    if (Arrays.asList(menuItem.getAdditions()).contains(addition) ||
            Arrays.asList(menuItem.getIngredients()).contains(addition)) {
      ingredients.add(addition);
      additions.add(addition);
    }
  }

  /**
   * Removes an ingredient from this order or
   * does nothing if the ingredient is not an ingredient in this order.
   *
   * @param subtraction the Ingredient to be removed.
   */
  public void addSubtraction(Ingredient subtraction) {
    if (Arrays.asList(menuItem.getIngredients()).contains(subtraction)) {
      ingredients.remove(subtraction);
      subtractions.add(subtraction);
    }
  }

  /**
   * Returns the list of ingredients needed to make this order
   * including the additions and excluding the subtractions.
   *
   * @return the list of ingredients to make this order.
   */
  public ArrayList<Ingredient> getIngredients() {
    return ingredients;
  }

  /**
   * Expresses this order in string format with the collection of additions and subtractions.
   * The string is in the following format.
   * <p>
   * [MENU_ITEM_1_NAME] [MENU_ITEM_1_PRICE]
   * - ADD [ADDITION_1], [ADDITION_2], ...
   * - NO [SUBTRACTION_1], [SUBTRACTION_2], ...
   * <p>
   * ...
   *
   * @return the formatted string representation of this order.
   */
  public String toString() {
    StringBuilder out = new StringBuilder(Localizer.localize(menuItem.getName()) + " " + menuItem.getPrice());

    if (additions.size() > 0) {
      out.append(System.lineSeparator()).append("  - ADD ");
    }
    for (int i  = 0; i < additions.size(); i++) {
      out.append(Localizer.localize(additions.get(i).getName()));
      if (i != additions.size() - 1) {
        out.append(", ");
      }
    }

    if (subtractions.size() > 0) {
      out.append(System.lineSeparator()).append("  - NO ");
    }
    for (int i  = 0; i < subtractions.size(); i++) {
      out.append(Localizer.localize(subtractions.get(i).getName()));
      if (i != subtractions.size() - 1) {
        out.append(", ");
      }
    }

    return out.toString();
  }
}
