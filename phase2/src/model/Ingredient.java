package model;

import util.Localizer;

import java.io.Serializable;

/** Ingredient in the restaurant. Has all information it needs to stand alone. */
public class Ingredient implements Serializable {
  private final String name;
  private int threshold;
  private int quantity;

  /**
   * Constructs a new ingredient with the given name, reordering threshold, and starting quantity.
   *
   * @param name the name of this ingredient.
   * @param threshold the threshold to reorder.
   * @param quantity the starting quantity.
   */
  public Ingredient(String name, int threshold, int quantity) {
    this.name = name;
    this.threshold = threshold;
    this.quantity = quantity;
  }

  /**
   * Gets the name of this ingredient.
   *
   * @return the name of this ingredient.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns whether or not this ingredient should be reordered based off the reordering threshold.
   *
   * @return whether or not this ingredient should be reordered.
   */
  public boolean shouldReorder() {
    return quantity < threshold;
  }

  /**
   * Gets the current quantity available of this ingredient.
   *
   * @return the currently available quantity of this ingredient.
   */
  public int getQuantity() {
    return this.quantity;
  }

  /**
   * Modifies the quantity of this ingredient.
   *
   * @param deltaQuantity the amount to change by.
   */
  public void modifyQuantity(int deltaQuantity) {
    this.quantity += deltaQuantity;
  }

  /**
   * Modifies the threshold of this ingredient.
   *
   * @param threshold the new threshold.
   */
  public void setThreshold(int threshold) {
    this.threshold = threshold;
  }

  /**
   * Returns this ingredients threshold.
   *
   * @return the threshold of this ingredient.
   */
  public int getThreshold() {
    return threshold;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return Localizer.localize(name);
  }
}
