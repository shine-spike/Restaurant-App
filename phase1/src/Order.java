/** Represents a table's order of a single menuItem, with additions and subtractions */
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Order {
  // The current order number stored for all orders
  private static int currentOrderNum = 0;

  // This order's order number
  private int orderNumber;
  // The number of the table who placed this order
  private int tableNumber;
  // The id of the employee that took this order
  private int employeeNumber;
  // The menu item
  private MenuItem menuItem;
  // The ingredients required to make this order
  private ArrayList<Ingredient> ingredients;

  // The list of additions to this order
  private ArrayList<Ingredient> additions;
  // The list of subtractions to this order
  private ArrayList<Ingredient> subtractions;
  private boolean seen;

  /**
   * Constructs an order of menuItem placed by serverNumber, at table tableNum
   *
   * @param serverNumber the id of the server who entered this order
   * @param tableNumber the number of the table who placed this order
   * @param menuItem the menuItem ordered
   */
  public Order(int serverNumber, int tableNumber, MenuItem menuItem) {
    this.orderNumber = currentOrderNum;
    currentOrderNum++;

    this.employeeNumber = serverNumber;
    this.tableNumber = tableNumber;
    this.menuItem = menuItem;
    this.ingredients = new ArrayList<>(Arrays.asList(menuItem.getIngredients()));
    this.additions = new ArrayList<>();
    this.subtractions = new ArrayList<>();
    this.seen = false;
  }

  /**
   * Adds an Ingredient to this order, does nothing if the ingredient is not on the list of permitted substitutions for
   * this order
   *
   * @param toAdd the Ingredient to be added
   */
  public void addAddition(Ingredient toAdd) {
    if (Arrays.asList(menuItem.getIngredients()).contains(toAdd)) {
      ingredients.add(toAdd);
      additions.add(toAdd);
    }
  }

  /**
   * Removes an ingredient from this order, does nothing if the ingredient is not on the menuItem
   *
   * @param toRemove the Ingredient to be removed
   */
  public void addSubtraction(Ingredient toRemove) {
      if (Arrays.asList(menuItem.getIngredients()).contains(toRemove)) {
          ingredients.remove(toRemove);
          subtractions.add(toRemove);
      }
  }

  /**
   * getter for this employeeNumber
   *
   * @return the id of the employee who took this order
   */
  public int getEmployeeNumber() {
    return employeeNumber;
  }

  /**
   * getter for tableNumber
   *
   * @return the number of the table that placed this order
   */
  public int getTableNumber() {
    return tableNumber;
  }

  /**
   * getter for menuItem
   *
   * @return the menuItem that was ordered
   */
  public MenuItem getMenuItem() {
    return menuItem;
  }

  /**
   * getter for price
   *
   * @return the price of this Order
   */
  public int getPrice() {
    return menuItem.getPrice();
  }

  /**
   * getter for orderNumber
   *
   * @return the number of this Order
   */
  public int getOrderNumber() {
    return orderNumber;
  }

  /** @return True iff this order has been seen by a chef */
  public boolean isSeen() {
    return seen;
  }

  /**
   * Returns the list of ingredients needed to make this order including the additions, not
   * including the subtractions
   *
   * @return the list of ingredients to make this order
   */
  public ArrayList<Ingredient> getIngredients() {
    return ingredients;
  }

  /** Records that this order has been seen by a chef */
  public void orderSeen() {
    seen = true;
  }

  /**
   * Expresses this order with the list of additions and subtractions
   *
   * @return a formatted string of the form
   *
   * [menuItem] [price]
   *  - ADD [Addition 1]
   *  - ADD [Addition 2]
   *  ...
   *  - NO [Subtraction 1]
   *  - NO [Subtraction 2]
   *  ...
   */
  public String toString() {
    StringBuilder out = new StringBuilder(menuItem.getName() + " " + menuItem.getPrice());
    ;
    for (Ingredient i : additions) {
      out.append(System.lineSeparator() + "  - ADD " + i.getName());
    }
    for (Ingredient i : subtractions) {
      out.append(System.lineSeparator() + "  - NO " + i.getName());
    }

    return out.toString();
  }
}
