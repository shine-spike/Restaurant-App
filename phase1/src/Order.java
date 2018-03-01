import java.util.ArrayList;
import java.util.Arrays;

public class Order {
  private static int current_order_number = 0;

  private int tableNum;
  private int serverId;
  private MenuItem menuItem;
  private Ingredient[] additions;
  private Ingredient[] subtractions;
  private boolean seen;

  public int getTableNum() {
    return tableNum;
  }

  public MenuItem getMenuItem() {
    return menuItem;
  }

  public int getPrice() {
    return menuItem.getPrice();
  }

  public String toString() {
    // TODO May need to add information depending on menuItem.toString()
    // TODO Need to add information about additions, and subtractions
    return menuItem.toString();
  }

  public ArrayList<Ingredient> getIngredientsList() {
    Ingredients[] ingredients = menuItem.getIngredients();
    ArrayList[] ingredientsList = new ArrayList<Ingredients>(Arrays.asList(ingredients));

    for(Ingredient i : subtractions){ingredientList.remove(i);}
    for(Ingredient i : additions){ingredientList.add(i);}

    return ingredientsList;
  }
}
