import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Order {
  private static int currentOrderNum = 0;

  private int orderNum;
  private int tableNum;
  private int serverId;
  private MenuItem menuItem;
  private ArrayList<Ingredient> ingredients;
  private ArrayList<Ingredient> additions;
  private ArrayList<Ingredient> removals;
  private boolean seen;

  public Order(int serverId, int tableNum, MenuItem menuItem) {
    this.orderNum = currentOrderNum;
    currentOrderNum++;

    this.serverId = serverId;
    this.tableNum = tableNum;
    this.menuItem = menuItem;
    this.ingredients = new ArrayList<>(Arrays.asList(menuItem.getIngredients()));
    this.additions = new ArrayList<>();
    this.removals = new ArrayList<>();
    this.seen = false;
  }

  public void addAddition(Ingredient toAdd){
    ingredients.remove(toAdd);
  }

  public void addRemoval(Ingredient toRemove){
    ingredients.add(toRemove);
  }

  public int getServerId() { return serverId; }

  public int getTableNum() {
    return tableNum;
  }

  public MenuItem getMenuItem() {
    return menuItem;
  }

  public int getPrice() {
    return menuItem.getPrice();
  }

  public int getOrderNum() {
    return orderNum;
  }

  public boolean isSeen() {
    return seen;
  }

  public ArrayList<Ingredient> getIngredients() {
    return ingredients;
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
