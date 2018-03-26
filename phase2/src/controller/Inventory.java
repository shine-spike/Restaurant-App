package controller;

import model.Ingredient;
import model.Order;
import util.Localizer;
import util.Logger;
import util.Reorderer;

import java.util.ArrayList;

/** Controls all aspects of inventory management and reordering of ingredients. */
public class Inventory {
  private static final int DEFAULT_REORDER_AMOUNT = 20;

  private final ArrayList<Ingredient> ingredients = new ArrayList<>();

  /** Constructs an empty inventory. */
  Inventory() {}

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
   * Gets strings representing all ingredients in the inventory.
   *
   * @return the list of strings representing all ingredients in the inventory.
   */
  public ArrayList<String> getIngredientStrings() {
    ArrayList<String> ingredientStrings = new ArrayList<>();
    for (Ingredient ingredient : ingredients) {
      ingredientStrings.add(ingredient.getName());
    }
    return ingredientStrings;
  }

  /**
   * Restocks a given ingredient with a given quantity.
   *
   * @param ingredientName the name of the ingredient to restock.
   * @param quantity the quantity of the ingredient to add.
   */
  public boolean restockIngredient(String ingredientName, int quantity) {
    Ingredient ingredient = getIngredient(ingredientName);
    if (ingredient != null) {
      Logger.inventoryLog("RESTOCK", quantity, ingredientName, "restocked");
      ingredient.modifyQuantity(quantity);
      return true;
    }
    return false;
  }

  /**
   * Returns a list of the names of items in the inventory whose names contain the search term, both
   * localized and unlocalized.
   *
   * @param searchTerm the search term we are checking ingredient names for.
   * @return the list of names of ingredients matching the search term.
   */
  public ArrayList<String> search(String searchTerm) {
    ArrayList<String> foundIngredients = new ArrayList<>();
    for (Ingredient ingredient : ingredients) {
      String ingredientName = ingredient.getName();

      // Search both the regular name and the respective localized name
      if (ingredientName.contains(searchTerm)
              || Localizer.localize(ingredientName).contains(searchTerm)) {
        foundIngredients.add(ingredient.getName());
      }
    }
    return foundIngredients;
  }

  /**
   * Gets the ingredient with the given name.
   *
   * @param ingredientName the name of the ingredient.
   * @return the ingredient with that name or {@code null} if no such ingredient exists.
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
   * Confirms a given order can be satisfied with the current inventory.
   *
   * @param order the order to be checked.
   * @return whether or not the order can be satisfied.
   */
  boolean confirmOrder(Order order) {
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
  void consumeIngredients(Order order) {
    for (Ingredient ingredient : order.getIngredients().keySet()) {
      int amount = order.getIngredients().get(ingredient);
      if (amount == 0) {
        continue;
      }

      Logger.inventoryLog("CONSUME", amount, ingredient.getName(), "consumed");
      ingredient.modifyQuantity(-amount);

      if (ingredient.shouldReorder()) {
        Logger.reorderLog(ingredient.getName());
        Reorderer.reorder(ingredient.getName(), DEFAULT_REORDER_AMOUNT);
      }
    }
  }
}
