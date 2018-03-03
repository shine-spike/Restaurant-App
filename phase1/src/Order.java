import java.util.ArrayList;
import java.util.Arrays;

public class Order {
  private static int currentOrderNum = 0;

  private int orderNumber;
  private int tableNumber;
  private int employeeNumber;
  private MenuItem menuItem;
  private ArrayList<Ingredient> ingredients;
  private ArrayList<Ingredient> additions;
  private ArrayList<Ingredient> subtractions;
  private boolean seen;

  public Order(int serverNumber, int tableNumber, MenuItem menuItem) {
    this.orderNumber = currentOrderNum;
    currentOrderNum++;

    this.employeeNumber = serverNumber;
    this.tableNumber = tableNumber;
    this.menuItem = menuItem;
    this.ingredients = new ArrayList<>(Arrays.asList(menuItem.getIngredients()));
    this.additions = new ArrayList<>();
    this.subtractions = new ArrayList<>();
    this.seen = false;
  }

  public void addAddition(Ingredient toAdd){
    ingredients.add(toAdd);
    additions.add(toAdd);
  }

  public void addSubtraction(Ingredient toRemove){
    ingredients.remove(toRemove);
    subtractions.add(toRemove);
  }

  public int getEmployeeNumber() { return employeeNumber; }

  public int getTableNumber() {
    return tableNumber;
  }

  public MenuItem getMenuItem() {
    return menuItem;
  }

  public int getPrice() {
    return menuItem.getPrice();
  }

  public int getOrderNumber() {
    return orderNumber;
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
