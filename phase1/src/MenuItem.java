import java.util.ArrayList;

public class MenuItem {
  private int price;
  private String name;

  private ArrayList<Ingredient> ingredients = new ArrayList<>();
  private ArrayList<Ingredient> additions = new ArrayList<>();

  MenuItem(String name, int price) {
    this.price = price;
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public String getName() {
    return name;
  }

  public Ingredient[] getAllIngredients() {
    ArrayList<Ingredient> allIngredients = new ArrayList<>(ingredients);
    ArrayList<Ingredient> newIngredients = new ArrayList<>();

    for (Ingredient sub : additions) {
      if (allIngredients.contains(sub)) {
        Ingredient original = allIngredients.get(allIngredients.indexOf(sub));
        original.setQuantity(original.getQuantity() + sub.getQuantity());
      } else {
        newIngredients.add(sub);
      }
    }

    allIngredients.addAll(newIngredients);
    return allIngredients.toArray(new Ingredient[allIngredients.size()]);
  }

  public Ingredient[] getIngredients() {
    return ingredients.toArray(new Ingredient[ingredients.size()]);
  }

  public Ingredient[] getAdditions() {
    return additions.toArray(new Ingredient[additions.size()]);
  }

  public void addIngredient(Ingredient ingredient) {
    ingredients.add(ingredient);
  }

  public void addAddition(Ingredient ingredient) {
    additions.add(ingredient);
  }
}
