import java.util.ArrayList;

public class Menu {
    ArrayList<MenuItem> menuItems;

    public Menu(){
        menuItems = new ArrayList<MenuItem>();
    }

    public addMenuItem(ArrayList<MenuItem> menuItems){
        this.menuItems = menuItems;
    }

    public MenuItem getMenuItem(String itemName){
        for(MenuItem i : menuItems){
            if(menuItems.name = itemName){
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
