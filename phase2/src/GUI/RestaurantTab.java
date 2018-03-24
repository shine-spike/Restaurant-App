package GUI;

import javafx.scene.control.Tab;

public abstract class RestaurantTab {
    // The JavaFX tab that this class represents
    private Tab tab;
    // The number of the employee using this tab
    private int employeeNumber;

    /**
     * Constructs a restaurant tab for the employee with the id employeeNumber
     */
    public RestaurantTab(int employeeNumber){
        this.employeeNumber = employeeNumber;
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
    void setTab(Tab newTab){
        this.tab = newTab;
    }

    public int getEmployeeNumber(){
        return employeeNumber;
    }

    /**
     * Updates all the nodes of this tab with the appropriate new information
     */
    public abstract void update();
}
