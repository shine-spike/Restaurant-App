import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Order {
  private static int currentOrderNum = 0;

  private int orderNum;
  private int tableNum;
  private int serverId;
  private MenuItem menuItem;
  private List<Ingredient> additions;
  private List<Ingredient> subtractions;
  private boolean seen;

  public Order(
      int serverId,
      int tableNum,
      MenuItem menuItem,
      Ingredient[] additions,
      Ingredient[] subtractions) {
    this.orderNum = currentOrderNum;
    currentOrderNum ++;

    this.serverId = serverId;
    this.tableNum = tableNum;
    this.menuItem = menuItem;
    this.additions = Arrays.asList(additions);
    this.subtractions = Arrays.asList(subtractions);
    this.seen = false;
  }

  public int getTableNum() {
    return tableNum;
  }

  public MenuItem getMenuItem() {
    return menuItem;
  }

  public int getPrice() {
    return menuItem.getPrice();
  }

  public boolean isSeen() {
    return seen;
  }

  public ArrayList<Ingredient> getIngredientsList() {
    Ingredient[] ingredients = menuItem.getIngredients();
    ArrayList<Ingredient> ingredientsList = new ArrayList<Ingredient>(Arrays.asList(ingredients));

    ingredientsList.removeAll(subtractions);
    ingredientsList.addAll(additions);

    return ingredientsList;
  }

  public void orderSeen() {
    seen = true;
  }

  public String toString() {
    // TODO May need to add information depending on menuItem.toString()
    // TODO Need to add information about additions, and subtractions
    return menuItem.toString();
  }
}
