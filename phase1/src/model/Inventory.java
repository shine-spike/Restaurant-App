package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Controls all aspects of inventory management and reordering of ingredients.
 */
public class Inventory {
  private static final String REORDER_REQUEST_FILE = "phase1/requests.txt";
  private static final int DEFAULT_REORDER_AMOUNT = 20;

  private final ArrayList<Ingredient> ingredients;

  /**
   * Constructs an inventory with an empty ingredient container.
   */
  Inventory() {
    this.ingredients = new ArrayList<>();
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
   * Restocks a given list of ingredients with their respective quantities.
   *
   * @param ingredientNames the names of the ingredients to restock.
   * @param quantities      the quantities of the ingredients to add.
   */
  // DO NOT REMOVE, THIS IS TO SATISFY SPECIFICATIONS
  public void restockIngredients(ArrayList<String> ingredientNames, ArrayList<Integer> quantities) {
    for (int i = 0; i < ingredientNames.size(); i++) {
      restockIngredient(ingredientNames.get(i), quantities.get(i));
    }
  }

  /**
   * Print to file the need to reorder the given ingredient.
   *
   * @param ingredient the ingredient to reorder.
   */
  private void reorder(Ingredient ingredient) {
    // Find the file we want to write to
    File file = new File(REORDER_REQUEST_FILE);

    try {
      // Attempt to create the file and notify if it was created now
      if (file.createNewFile()) {
        System.out.println(file.getName() + " not found. Created a new one.");
      }

      // Write out to the file
      PrintWriter out = new PrintWriter(new FileWriter(file));
      out.println("Reorder request for " + DEFAULT_REORDER_AMOUNT + " of " + ingredient.getName() + ".");
      out.close();
    } catch (IOException e) {
      System.out.println(file.getName() + " not found and could not be created.");
    }
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
}
