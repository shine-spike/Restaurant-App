package model;

import java.util.ArrayList;

/**
 * A menu item in a menu, it is basically a list of ingredients with available additions and a price.
 */
public class MenuItem {
  private int price;
  private String name;

  private ArrayList<Ingredient> ingredients = new ArrayList<>();
  private ArrayList<Ingredient> additions = new ArrayList<>();


  /**
   * Constructs a new menu item with the given name and price.
   *
   * @param name  the name of the menu item.
   * @param price the price of the menu item.
   */
  MenuItem(String name, int price) {
    this.price = price;
    this.name = name;
  }

  /**
   * Gets the price of this menu item.
   *
   * @return the price of the menu item.
   */
  public int getPrice() {
    return price;
  }

  /**
   * Gets the name of this menu item.
   *
   * @return the name of this menu item.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the ingredients that constitute this basic menu item.
   *
   * @return the ingredients of this menu item.
   */
  public Ingredient[] getIngredients() {
    return ingredients.toArray(new Ingredient[ingredients.size()]);
  }

  /**
   * Gets the possible additions to this menu item.
   *
   * @return the possible additions to this menu item.
   */
  public Ingredient[] getAdditions() {
    return additions.toArray(new Ingredient[additions.size()]);
  }

  /**
   * Adds an ingredient to this menu item.
   *
   * @param ingredient the Ingredient to add to the basic menu item.
   */
  public void addIngredient(Ingredient ingredient) {
    ingredients.add(ingredient);
  }

  /**
   * Adds a possible addition to this menu item.
   *
   * @param ingredient the Ingredient that is a possible addition to the menu item.
   */
  public void addAddition(Ingredient ingredient) {
    additions.add(ingredient);
  }
}
