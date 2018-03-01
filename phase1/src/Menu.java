import java.util.ArrayList;

public class Menu {
    private ArrayList<MenuItem> menuItems;

    public Menu(){
        menuItems = new ArrayList<>();
    }

    public void addMenuItem(ArrayList<MenuItem> menuItems){
        this.menuItems = menuItems;
    }

    public MenuItem getMenuItem(String itemName){
        for(MenuItem i : menuItems){
            if(i.getName().equals(itemName)){
                return i;
            }
        }

        return null;
    }

    public String toString(){
        StringBuilder out = new StringBuilder();

        for(MenuItem i : menuItems){
            out.append(menuItems.toString());
        }

        return out.toString();
    }
}
