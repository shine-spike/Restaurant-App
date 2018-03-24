package controller;

import model.Ingredient;
import model.Order;
import util.Localizer;
import util.Reorderer;

import java.util.ArrayList;

/** Controls all aspects of inventory management and reordering of ingredients. */
public class Inventory {
  private static final int DEFAULT_REORDER_AMOUNT = 20;

  private final ArrayList<Ingredient> ingredients;

  /** Constructs an inventory with an empty ingredient container. */
  Inventory() {
    this.ingredients = new ArrayList<>();
  }

  /**
   * Adds an ingredient with the given name to the inventory.
   *
   * @param name the name of the ingredient.
   * @param quantity the initial amount of this ingredient.
   * @param threshold the reordering threshold of this ingredient.
   */
  public void addIngredient(String name, int quantity, int threshold) {
    ingredients.add(new Ingredient(name, quantity, threshold));
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
   * Confirms an order can be satisfied.
   *
   * @param order the order to be checked.
   * @return whether or not it can be satisfied.
   */
  public boolean confirmOrder(Order order) {
    for (Ingredient ingredient : order.getIngredients().keySet()) {
      if (ingredient.getQuantity() < order.getIngredients().get(ingredient)) {
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
    for (Ingredient ingredient : order.getIngredients().keySet()) {
      ingredient.modifyQuantity(-order.getIngredients().get(ingredient));
      if (ingredient.shouldReorder()) {
        Reorderer.reorder(ingredient, DEFAULT_REORDER_AMOUNT);
      }
    }
  }

  /**
   * Restocks a given ingredient with a given quantity.
   *
   * @param ingredientName the name of the ingredient to restock.
   * @param quantity the quantity of the ingredient to add.
   */
  public void restockIngredient(String ingredientName, int quantity) {
    getIngredient(ingredientName).modifyQuantity(quantity);
  }

  /**
   * Returns an ArrayList of the names of items in the inventory whose names contain the search
   * term.
   *
   * @param searchTerm the search term we're checking ingredient names for.
   */
  public ArrayList<String> search(String searchTerm) {
    ArrayList<String> foundNames = new ArrayList<>();
    for (Ingredient ingredient : ingredients) {
      String ingredientName = ingredient.getName();

      // Search both the regular name and the respective localized name
      if (ingredientName.contains(searchTerm)
          || Localizer.localize(ingredientName).contains(searchTerm)) {
        foundNames.add(ingredientName);
      }
    }
    return foundNames;
  }
}
