package GUI;

import javafx.scene.control.Tab;

public abstract class RestaurantTab {
    // The JavaFX tab that this class represents
    private Tab tab;

    /**
     * Constructs a restaurant tab
     */
    public RestaurantTab(){
        initializeTab();
    }

    /**
     * Initializes this RestaurantTab's JavaFX tab
     */
    public abstract void initializeTab();

    /**
     * Gets this RestaurantTab's JavaFX tab
     * @return The JavaFX tab that this class represents
     */
    public Tab getTab() {
        return tab;
    }

    /**
     * Sets this RestaurantTab's JavaFX newTab
     * @param newTab The ne JavaFX newTab
     */
    protected void setTab(Tab newTab){
        this.tab = newTab;
    }

    /**
     * Updates all the nodes of this tab with the appropriate new information
     */
    public abstract void update();
}
