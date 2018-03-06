package model;

import java.util.ArrayList;

/**
 * Controls all aspects of inventory management and reordering of ingredients.
 */
public class Inventory {
  private final ArrayList<Ingredient> ingredients;

  /**
   * Constructs an inventory with an empty ingredient container.
   */
  Inventory() {
    this.ingredients = new ArrayList<>();
  }

  /**
   * Prints a formatted view of the inventory with all inventory items and their quantities.
   */
  public void printInventory() {
    String formatString = "%-30.30s  %-30.30s%n";
    System.out.println("\n== INVENTORY ==");
    System.out.printf(formatString, "NAME", "QUANTITY");
    for (Ingredient ingredient : ingredients) {
      System.out.printf(formatString, ingredient.getName(), ingredient.getQuantity());
    }
  }

  /**
   * Gets the Ingredient with the given name.
   *
   * @param ingredientName the name of the ingredient.
   * @return the Ingredient with that name. If no ingredient has that name, returns {@code null}.
   */
  public Ingredient getIngredient(String ingredientName) {
    for (Ingredient ingredient : ingredients) {
      if (ingredient.getName().equals(ingredientName)) {
        return ingredient;
      }
    }
    return null;
  }

  /**
   * Adds an ingredient with the given name to the inventory.
   *
   * @param name      the name of the ingredient.
   * @param quantity  the initial amount of this ingredient.
   * @param threshold the reordering threshold of this ingredient.
   */
  public void addIngredient(String name, int quantity, int threshold) {
    ingredients.add(new Ingredient(name, quantity, threshold));
  }

  /**
   * Confirms an order can be satisfied.
   *
   * @param order the order to be checked.
   * @return whether or not it can be satisfied.
   */
  public boolean confirmOrder(Order order) {
    for (Ingredient ingredient : order.getIngredients()) {
      if (ingredient.getQuantity() <= 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * Consumes ingredients from the inventory according to what is in the given order.
   *
   * @param order the order that has been prepared.
   */
  public void consumeIngredients(Order order) {
    for (Ingredient ingredient : order.getIngredients()) {
      ingredient.modifyQuantity(-1);
      if (ingredient.shouldReorder()) {
        reorder(ingredient);
      }
    }
  }

  /**
   * Restocks a given ingredient with a given quantity.
   *
   * @param ingredientName the name of the ingredient to restock.
   * @param quantity       the quantity of the ingredient to add.
   */
  public void restockIngredient(String ingredientName, int quantity) {
    getIngredient(ingredientName).modifyQuantity(quantity);
  }

  /**
   * Print to file the need to reorder the given ingredient.
   *
   * @param ingredient the ingredient to reorder.
   */
  private void reorder(Ingredient ingredient) {
    // TODO: implement
  }
}
