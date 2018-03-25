package controller;

import model.Ingredient;
import model.Order;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.Localizer;
import util.Logger;
import util.Reorderer;

import java.util.ArrayList;

/** Controls all aspects of inventory management and reordering of ingredients. */
public class Inventory {
  private static final int DEFAULT_REORDER_AMOUNT = 20;

  @NotNull
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
  @Nullable
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
  public boolean confirmOrder(@NotNull Order order) {
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
  public void consumeIngredients(@NotNull Order order) {
    for (Ingredient ingredient : order.getIngredients().keySet()) {
      int amount = order.getIngredients().get(ingredient);
      Logger.inventoryLog("CONSUME", amount, ingredient.getName(), "consumed");
      ingredient.modifyQuantity(-amount);

      if (ingredient.shouldReorder()) {
        Logger.reorderLog(ingredient.getName());
        Reorderer.reorder(ingredient.getName(), DEFAULT_REORDER_AMOUNT);
      }
    }
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
   * Returns an ArrayList of the names of items in the inventory whose names contain the search
   * term.
   *
   * @param searchTerm the search term we're checking ingredient names for.
   */
  @NotNull
  public ArrayList<String> search(@NotNull String searchTerm) {
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
