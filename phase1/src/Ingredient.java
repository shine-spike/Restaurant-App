import java.util.ArrayList;

public class Ingredient {
  private String name;
  private int threshold;
  private int quantity;

  Ingredient(String name, int threshold, int quantity) {
    this.name = name;
    this.threshold = threshold;
    this.quantity = quantity;
  }

  public boolean equals(Object associate) {
    if (associate instanceof Ingredient) {
      return this.name.equals(((Ingredient) associate).getName());
    } else {
      return false;
    }
  }

  public String getName() {
    return this.name;
  }

  public int getThreshold() {
    return this.threshold;
  }

  public int getQuantity() {
    return this.quantity;
  }

  public void setQuantity(int newQuantity) {
    this.quantity = newQuantity;
  }
}
