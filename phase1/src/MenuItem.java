import java.util.ArrayList;

public class MenuItem {
    int price;
    String name;
    ArrayList<Ingredient> ingredients;
    ArrayList<Ingredient> substitutions;

    MenuItem(int price, String name, ArrayList<Ingredient> ingredients, ArrayList<Ingredient> subs){
        this.price = price;
        this.name = name;
        this.ingredients = ingredients;
        this.substitutions = subs;
    }

    public int getPrice(){
        return price;
    }

    public String getName(){
        return name;
    }

    public Ingredient[] getAllIngredients(){
        ArrayList<Ingredient> allIngredients = new ArrayList<>();
        allIngredients.addAll(ingredients);
        ArrayList<Ingredient> newIngredients = new ArrayList<>();
        for (Ingredient sub:
             substitutions) {
            if (allIngredients.contains(sub)){
                Ingredient original = allIngredients.get(allIngredients.indexOf(sub));
                original.setQuantity(original.getQuantity() + sub.getQuantity());
            }
            else {
                newIngredients.add(sub);
            }
        }

        allIngredients.addAll(newIngredients);
        return allIngredients.toArray(new Ingredient[allIngredients.size()]);
    }

    public Ingredient[] getIngredients(){
        return ingredients.toArray(new Ingredient[ingredients.size()]);
    }

    public Ingredient[] getSubstitutions(){
        return substitutions.toArray(new Ingredient[substitutions.size()]);
    }

    public String toString(){
        StringBuilder out = new StringBuilder(name);
        for(Ingredient i : substitutions){
            out.append(System.lineSeparator() + "  - " + i.getName());
        }

        return out.toString();
    }
}
