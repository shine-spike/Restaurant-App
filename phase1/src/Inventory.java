import java.util.ArrayList;

public class Inventory {
  private ArrayList<Ingredient> ingredients = new ArrayList<>();
  
  public Inventory(){
  }

  public Ingredient getIngredient(String ingredientName){
    for (Ingredient ingredient:
         ingredients)
      if (ingredient.getName() == ingredientName) return ingredient;
    return null;
  }

  public int getAmount(String ingredientName){
    Ingredient ingredient = getIngredient(ingredientName);
    if (ingredient == null){
      return 0;
    }
    else{
      return ingredient.getQuantity();
    }
  }

  public boolean enoughIngredient(String ingredientName, int quantityRequired){
    return getAmount(ingredientName) >= quantityRequired;
  }

  public boolean useIngredient(String ingredientName, int quantityRequired){
    if (enoughIngredient(ingredientName, quantityRequired)){
      Ingredient ingredient = getIngredient(ingredientName);
      ingredient.setQuantity(ingredient.getQuantity() - quantityRequired);
      return true;
    }
    else{
      return false;
    }
  }

  public void addIngredient(String name, int quantity, int threshold){
    Ingredient newIngredient = new Ingredient(name, quantity, threshold);
    ingredients.add(newIngredient);
  }

}
